package com.solvd.qaprotours.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.solvd.qaprotours.config.kafka.KafkaMessage;
import com.solvd.qaprotours.config.kafka.MessageSender;
import com.solvd.qaprotours.domain.Pagination;
import com.solvd.qaprotours.domain.exception.ResourceDoesNotExistException;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.tour.TourCriteria;
import com.solvd.qaprotours.repository.TourRepository;
import com.solvd.qaprotours.service.HotelService;
import com.solvd.qaprotours.service.TourService;
import com.solvd.qaprotours.web.dto.TourDto;
import com.solvd.qaprotours.web.mapper.TourMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Varya Petrova
 * @author Lisov Ilya
 */
@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final MessageSender<TourDto> messageSender;
    private final TourMapper tourMapper;
    private final HotelService hotelService;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticsearchClient client;

    @Override
    @Transactional(readOnly = true)
    public Flux<Tour> getAll(final Pagination pagination,
                             final TourCriteria tourCriteria) {
        int pageSize = 20;
        if (pagination.getCurrentPage() == null
                || pagination.getPageSize() == null) {
            pagination.setCurrentPage(0);
            pagination.setPageSize(pageSize);
        }
        if (tourCriteria == null) {
            List<Tour> tours = getAllByCriteria(new TourCriteria(),
                    pagination.getCurrentPage(),
                    pagination.getPageSize());
            return Flux.just(tours.toArray(new Tour[0]));
        }
        List<Tour> tours = getAllByCriteria(tourCriteria,
                pagination.getCurrentPage(),
                pagination.getPageSize());
        return Flux.just(tours.toArray(new Tour[0]));
    }

    @SneakyThrows
    private List<Tour> getAllByCriteria(final TourCriteria tourCriteria,
                                        final int currentPage,
                                        final int pageSize) {
        CriteriaQuery searchQuery = new CriteriaQuery(new Criteria());
        tourCriteria.apply(searchQuery);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        searchQuery.setPageable(pageable);
        searchQuery.addSort(Sort.by("arrivalTime").ascending());
        searchQuery.addSort(Sort.by("rating").descending());
        SearchHits<TourDto> hits = elasticsearchOperations.search(
                searchQuery,
                TourDto.class
        );
        return hits.stream()
                .map(SearchHit::getContent)
                .map(tourMapper::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public Flux<Tour> getAll(final Pagination pagination,
                             final String description) {
        int pageSize = 20;
        if (pagination.getCurrentPage() == null
                || pagination.getPageSize() == null) {
            pagination.setCurrentPage(0);
            pagination.setPageSize(pageSize);
        }
        SearchResponse<TourDto> searchResponse = client.search(s -> s
                        .index("tours")
                        .sort(builder -> builder
                                .field(f -> f
                                        .field("arrivalTime")
                                        .order(SortOrder.Asc)))
                        .sort(builder -> builder
                                .field(f -> f
                                        .field("rating")
                                        .order(SortOrder.Desc)))
                        .from(pagination.getCurrentPage()
                                * pagination.getPageSize())
                        .size(pagination.getPageSize())
                        .query(q -> q
                                .matchPhrasePrefix(t -> t
                                        .field("description")
                                        .query(description)
                                )
                        ),
                TourDto.class);
        List<Tour> tours = searchResponse.hits().hits().stream()
                .map(Hit::source)
                .map(tourMapper::toEntity)
                .toList();
        return Flux.just(tours.toArray(new Tour[0]));
    }

    @Override
    @Transactional
    public Mono<Tour> save(final Tour tour) {
        tour.setDraft(true);
        if (tour.getHotel() != null) {
            hotelService.save(tour.getHotel()).subscribe();
        }
        return tourRepository.save(tour)
                .flatMap(t -> {
                    KafkaMessage<TourDto> message = new KafkaMessage<>();
                    message.setTopic("tours");
                    message.setPartition(0);
                    message.setKey(t.getId().toString());
                    message.setData(tourMapper.toDto(t));
                    return messageSender.sendMessage(message)
                            .then();
                })
                .then(Mono.just(tour));
    }

    @Override
    @Transactional
    public Mono<Tour> publish(final Tour tour) {
        tour.setDraft(false);
        return tourRepository.save(tour)
                .flatMap(t -> {
                    KafkaMessage<TourDto> message = new KafkaMessage<>();
                    message.setTopic("tours");
                    message.setPartition(0);
                    message.setKey(t.getId().toString());
                    message.setData(tourMapper.toDto(t));
                    return messageSender.sendMessage(message)
                            .then();
                })
                .then(Mono.just(tour));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Tour> getById(final Long tourId) {
        Mono<Tour> error = Mono.error(
                new ResourceDoesNotExistException(
                        "tour with id " + tourId + " does not exist"
                )
        );
        return tourRepository.findById(tourId)
                .switchIfEmpty(error);
    }

    @Override
    @Transactional
    public Mono<Void> delete(final Long tourId) {
        KafkaMessage<TourDto> message = new KafkaMessage<>();
        message.setTopic("tours");
        message.setPartition(0);
        message.setKey(tourId + "_delete");
        message.setData(null);
        return messageSender.sendMessage(message)
                .flatMap(r -> tourRepository.deleteById(tourId))
                .then();
    }

    @Override
    public Mono<Void> addImage(final Long tourId,
                               final String fileName) {
        return getById(tourId)
                .map(tour -> {
                    tour.getImageUrls().add(fileName);
                    return tour;
                })
                .flatMap(tourRepository::save)
                .flatMap(t -> {
                    KafkaMessage<TourDto> message = new KafkaMessage<>();
                    message.setTopic("tours");
                    message.setPartition(0);
                    message.setKey(t.getId().toString());
                    message.setData(tourMapper.toDto(t));
                    return messageSender.sendMessage(message)
                            .then();
                })
                .then();
    }

}

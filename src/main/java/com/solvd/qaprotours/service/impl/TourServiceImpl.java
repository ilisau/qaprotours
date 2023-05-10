package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.Pagination;
import com.solvd.qaprotours.domain.exception.ResourceDoesNotExistException;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.tour.TourCriteria;
import com.solvd.qaprotours.repository.TourRepository;
import com.solvd.qaprotours.service.TourService;
import com.solvd.qaprotours.web.dto.TourDto;
import com.solvd.qaprotours.web.kafka.KafkaMessage;
import com.solvd.qaprotours.web.kafka.TourMessageSenderImpl;
import com.solvd.qaprotours.web.mapper.TourMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Varya Petrova, Lisov Ilya
 */
@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private static final int PAGE_SIZE = 20;
    private final TourMessageSenderImpl messageSender;
    private final TourMapper tourMapper;

    @Override
    @Transactional(readOnly = true)
    public Flux<Tour> getAll(final Pagination pagination,
                             final TourCriteria tourCriteria) {
        if (pagination.getCurrentPage() == null
                || pagination.getPageSize() == null) {
            pagination.setCurrentPage(0);
            pagination.setPageSize(PAGE_SIZE);
        }
        Sort sort = Sort.by(Sort.Order.asc("arrivalTime"),
                Sort.Order.desc("rating"));
        return tourRepository.findAll(sort)
                .buffer(pagination.getPageSize())
                .skip(pagination.getCurrentPage())
                .take(1)
                .flatMapIterable(tours -> tours);
    }

    @Override
    @Transactional
    public Mono<Tour> save(final Tour tour) {
        return tourRepository.save(tour)
                .map(t -> {
                    KafkaMessage<TourDto> message = new KafkaMessage<>();
                    message.setTopic("tours");
                    message.setPartition(0);
                    message.setKey(t.getId().toString());
                    message.setData(tourMapper.toDto(t));
                    return messageSender.sendMessage(message);
                })
                .then(Mono.just(tour));
    }

    @Override
    @Transactional
    public Mono<Tour> publish(final Tour tour) {
        tour.setDraft(false);
        return tourRepository.save(tour)
                .map(t -> {
                    KafkaMessage<TourDto> message = new KafkaMessage<>();
                    message.setTopic("tours");
                    message.setPartition(0);
                    message.setKey(t.getId().toString());
                    message.setData(tourMapper.toDto(t));
                    return messageSender.sendMessage(message);
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
        return tourRepository.deleteById(tourId)
                .map(t -> {
                    KafkaMessage<TourDto> message = new KafkaMessage<>();
                    message.setTopic("tours");
                    message.setPartition(0);
                    message.setKey(tourId.toString());
                    message.setData(null);
                    return messageSender.sendMessage(message);
                })
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
                .map(t -> {
                    KafkaMessage<TourDto> message = new KafkaMessage<>();
                    message.setTopic("tours");
                    message.setPartition(0);
                    message.setKey(t.getId().toString());
                    message.setData(tourMapper.toDto(t));
                    return messageSender.sendMessage(message);
                })
                .then();
    }

}

package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.Image;
import com.solvd.qaprotours.domain.Pagination;
import com.solvd.qaprotours.domain.exception.AuthException;
import com.solvd.qaprotours.domain.exception.ImageUploadException;
import com.solvd.qaprotours.domain.exception.ServiceNotAvailableException;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.tour.TourCriteria;
import com.solvd.qaprotours.service.ImageService;
import com.solvd.qaprotours.service.TourService;
import com.solvd.qaprotours.web.dto.ImageDto;
import com.solvd.qaprotours.web.dto.TourCriteriaDto;
import com.solvd.qaprotours.web.dto.TourDto;
import com.solvd.qaprotours.web.dto.validation.OnCreate;
import com.solvd.qaprotours.web.dto.validation.ValidateExtension;
import com.solvd.qaprotours.web.mapper.ImageMapper;
import com.solvd.qaprotours.web.mapper.TourCriteriaMapper;
import com.solvd.qaprotours.web.mapper.TourMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Lisov Ilya
 */
@RestController
@RequestMapping("api/v1/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;
    private final ImageService imageService;
    private final TourMapper tourMapper;
    private final TourCriteriaMapper tourCriteriaMapper;
    private final ImageMapper imageMapper;
    private static final String IMAGE_SERVICE = "imageService";

    /**
     * Gets all tours by criteria and paging.
     *
     * @param currentPage     page number.
     * @param pageSize        page size.
     * @param tourCriteriaDto TourCriteriaDto object.
     * @return List of tours.
     */
    @GetMapping
    public Flux<TourDto> getAll(
            @RequestParam(required = false) final Integer currentPage,
            @RequestParam(required = false) final Integer pageSize,
            @RequestBody(required = false) final TourCriteriaDto tourCriteriaDto
    ) {
        TourCriteria tourCriteria = tourCriteriaMapper
                .toEntity(tourCriteriaDto);
        Pagination pagination = new Pagination(currentPage, pageSize);
        return tourService.getAll(pagination, tourCriteria)
                .map(tourMapper::toDto);
    }

    /**
     * Gets all tours by words in description.
     *
     * @param description words to autocomplete by
     * @param currentPage page number
     * @param pageSize    page size
     * @return list of tours
     */
    @GetMapping("search")
    public Flux<TourDto> getAllByDescription(
            @RequestParam(required = false) final String description,
            @RequestParam(required = false) final Integer currentPage,
            @RequestParam(required = false) final Integer pageSize
    ) {
        Pagination pagination = new Pagination(currentPage, pageSize);
        return tourService.getAll(pagination, description)
                .map(tourMapper::toDto);
    }

    /**
     * Saves draft tour.
     *
     * @param tourDto TourDto object.
     * @return created object.
     */
    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public Mono<TourDto> saveDraft(@RequestBody final TourDto tourDto) {
        Tour tour = tourMapper.toEntity(tourDto);
        tour.setDraft(true);
        return tourService.save(tour)
                .map(tourMapper::toDto);
    }

    /**
     * Publishes tour.
     * @param tourDto TourDto object.
     * @return created object.
     */
    @PostMapping("/publish")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('EMPLOYEE')")
    public Mono<TourDto> publish(
            @Validated(OnCreate.class) @RequestBody final TourDto tourDto
    ) {
        Tour tour = tourMapper.toEntity(tourDto);
        return tourService.publish(tour)
                .map(tourMapper::toDto);
    }

    /**
     * Gets tour by id.
     * @param tourId tour id.
     * @return tour.
     */
    @GetMapping("/{tourId}")
    @PreAuthorize("canAccessDraftTour(#tourId)")
    public Mono<TourDto> getById(@PathVariable final Long tourId) {
        return tourService.getById(tourId)
                .map(tourMapper::toDto);
    }

    /**
     * Deletes tour.
     * @param tourId tour id.
     * @return empty response.
     */
    @DeleteMapping("/{tourId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('EMPLOYEE')")
    public Mono<Void> delete(@PathVariable final Long tourId) {
        return tourService.delete(tourId);
    }

    /**
     * Uploads image to tour.
     * @param tourId tour id.
     * @param dto ImageDto object.
     * @return empty response.
     */
    @PostMapping("/{tourId}/photo")
    @PreAuthorize("hasRole('EMPLOYEE')")
    @ValidateExtension
    @CircuitBreaker(name = IMAGE_SERVICE, fallbackMethod = "handleError")
    public Mono<Void> uploadImage(@PathVariable final Long tourId,
                                  @ModelAttribute final ImageDto dto) {
        Image image = imageMapper.toEntity(dto);
        return imageService.uploadImage(tourId, image);
    }

    private void handleError(final Exception e) {
        if (e.getClass().equals(AccessDeniedException.class)) {
            throw new AuthException(e.getMessage());
        }
        if (e.getClass().equals(ImageUploadException.class)) {
            throw new ImageUploadException(e.getMessage());
        }
        throw new ServiceNotAvailableException();
    }

}

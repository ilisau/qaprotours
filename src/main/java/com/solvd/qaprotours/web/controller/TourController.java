package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.Image;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final String IMAGE_SERVICE = "imageService";

    @GetMapping
    public List<TourDto> getAll(@RequestParam(required = false) Integer currentPage,
                                @RequestParam(required = false) Integer pageSize,
                                @RequestBody(required = false) TourCriteriaDto tourCriteriaDto) {
        TourCriteria tourCriteria = tourCriteriaMapper.toEntity(tourCriteriaDto);
        List<Tour> tours = tourService.getAll(currentPage, pageSize, tourCriteria);
        return tourMapper.toDto(tours);
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public TourDto saveDraft(@RequestBody TourDto tourDto) {
        Tour tour = tourMapper.toEntity(tourDto);
        tour = tourService.save(tour);
        return tourMapper.toDto(tour);
    }

    @PostMapping("/publish")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('EMPLOYEE')")
    public TourDto publish(@Validated(OnCreate.class) @RequestBody TourDto tourDto) {
        Tour tour = tourMapper.toEntity(tourDto);
        tour = tourService.publish(tour);
        return tourMapper.toDto(tour);
    }

    @GetMapping("/{tourId}")
    @PreAuthorize("canAccessDraftTour(#tourId)")
    public TourDto getById(@PathVariable Long tourId) {
        Tour tour = tourService.getById(tourId);
        return tourMapper.toDto(tour);
    }

    @DeleteMapping("/{tourId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('EMPLOYEE')")
    public void delete(@PathVariable Long tourId) {
        tourService.delete(tourId);
    }

    @PostMapping("/{tourId}/photo")
    @PreAuthorize("hasRole('EMPLOYEE')")
    @ValidateExtension
    @CircuitBreaker(name = IMAGE_SERVICE, fallbackMethod = "handleError")
    public void uploadImage(@PathVariable Long tourId,
                            @ModelAttribute ImageDto dto) {
        Image image = imageMapper.toEntity(dto);
        imageService.uploadImage(tourId, image);
    }

    private void handleError(Exception e) {
        if (e.getClass().equals(AccessDeniedException.class)) {
            throw new AuthException(e.getMessage());
        }
        if (e.getClass().equals(ImageUploadException.class)) {
            throw new ImageUploadException(e.getMessage());
        }
        throw new ServiceNotAvailableException();
    }

}

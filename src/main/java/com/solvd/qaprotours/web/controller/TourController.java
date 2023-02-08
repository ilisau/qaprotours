package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.tour.TourCriteria;
import com.solvd.qaprotours.service.TourService;
import com.solvd.qaprotours.web.dto.TourCriteriaDto;
import com.solvd.qaprotours.web.dto.TourDto;
import com.solvd.qaprotours.web.dto.validation.OnCreate;
import com.solvd.qaprotours.web.mapper.TourCriteriaMapper;
import com.solvd.qaprotours.web.mapper.TourMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final TourMapper tourMapper;
    private final TourCriteriaMapper tourCriteriaMapper;

    @GetMapping("/all")
    public List<TourDto> getAll(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize,
                                @RequestBody(required = false) TourCriteriaDto tourCriteriaDto) {
        TourCriteria tourCriteria = tourCriteriaMapper.toEntity(tourCriteriaDto);
        List<Tour> tours = tourService.getAll(currentPage, pageSize, tourCriteria);
        return tourMapper.toDto(tours);
    }

    @PostMapping
    public void saveDraft(@RequestBody TourDto tourDto) {
        Tour tour = tourMapper.toEntity(tourDto);
        tourService.save(tour);
    }

    @PostMapping("/publish")
    public void publish(@Validated(OnCreate.class) @RequestBody TourDto tourDto) {
        Tour tour = tourMapper.toEntity(tourDto);
        tourService.save(tour);
    }

    @GetMapping("/{tourId}")
    public TourDto getById(@PathVariable Long tourId) {
        Tour tour = tourService.getById(tourId);
        return tourMapper.toDto(tour);
    }

    @DeleteMapping("/{tourId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long tourId) {
        tourService.delete(tourId);
    }

}

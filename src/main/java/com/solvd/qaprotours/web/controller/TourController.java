package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.Tour;
import com.solvd.qaprotours.service.TourService;
import com.solvd.qaprotours.web.dto.TourDto;
import com.solvd.qaprotours.web.dto.validation.OnCreate;
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

    @GetMapping
    public List<TourDto> getAll() {
        List<Tour> tours = tourService.getAll();
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

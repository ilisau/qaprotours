package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.Tour;
import com.solvd.qaprotours.domain.TourCriteria;
import com.solvd.qaprotours.repository.TourRepository;
import com.solvd.qaprotours.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;

    @Override
    public List<Tour> getAll(TourCriteria tourCriteria, Pageable page) {
        return tourRepository.findByCriteria(tourCriteria);
    }

    @Override
    public void save(Tour tour) {

    }

    @Override
    public Tour getById(Long tourId) {
        return null;
    }

    @Override
    public void delete(Long tourId) {

    }

}

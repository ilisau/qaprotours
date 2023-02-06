package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.Tour;
import com.solvd.qaprotours.service.TourService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourServiceImpl implements TourService {

    @Override
    public List<Tour> getAll() {
        return null;
    }

    @Override
    public void saveDraft(Tour tour) {

    }

    @Override
    public void publish(Tour tour) {

    }

    @Override
    public Tour getById(Long tourId) {
        return null;
    }

    @Override
    public void delete(Long tourId) {

    }

}

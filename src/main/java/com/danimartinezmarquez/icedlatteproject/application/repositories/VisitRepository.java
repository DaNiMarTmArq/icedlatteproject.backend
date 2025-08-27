package com.danimartinezmarquez.icedlatteproject.application.repositories;

import com.danimartinezmarquez.icedlatteproject.domain.Visit;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository {
    Visit save(Visit visit);
    Visit findById(Integer id);
    List<Visit> findAll();
    List<Visit> findByUserId(Integer userId);
    List<Visit> findByCoffeeShopId(Integer coffeeShopId);
    List<Visit> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    void deleteById(Integer id);
}

package com.danimartinezmarquez.icedlatteproject.api.repositories.jpa;

import com.danimartinezmarquez.icedlatteproject.api.models.VisitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitJpaRepository extends JpaRepository<VisitModel, Integer> {

    List<VisitModel> findByCreatedByUserId(Integer createdByUserId);

    List<VisitModel> findByCoffeeShopId(Integer coffeeShopId);

    List<VisitModel> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
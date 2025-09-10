package com.danimartinezmarquez.icedlatteproject.api.repositories.jpa;

import com.danimartinezmarquez.icedlatteproject.api.models.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentJpaRepository extends JpaRepository<CommentModel, Integer> {
    List<CommentModel> findByVisitId(Integer visitId);
    List<CommentModel> findByUserId(Integer userId);
    List<CommentModel> findByRatingBetween(Double minRating, Double maxRating);
    List<CommentModel> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}

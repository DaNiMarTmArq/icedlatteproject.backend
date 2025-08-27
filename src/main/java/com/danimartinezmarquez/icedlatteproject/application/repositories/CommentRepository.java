package com.danimartinezmarquez.icedlatteproject.application.repositories;

import com.danimartinezmarquez.icedlatteproject.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);
    Comment findById(Integer id);
    List<Comment> findByVisitId(Integer visitId);
    List<Comment> findByUserId(Integer userId);
    List<Comment> findByRatingRange(Double minRating, Double maxRating);
    List<Comment> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    void deleteById(Integer id);
}
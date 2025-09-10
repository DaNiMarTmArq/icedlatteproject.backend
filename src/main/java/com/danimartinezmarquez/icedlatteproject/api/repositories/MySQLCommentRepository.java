package com.danimartinezmarquez.icedlatteproject.api.repositories;

import com.danimartinezmarquez.icedlatteproject.api.models.CommentModel;
import com.danimartinezmarquez.icedlatteproject.api.repositories.jpa.CommentJpaRepository;
import com.danimartinezmarquez.icedlatteproject.application.repositories.CommentRepository;
import com.danimartinezmarquez.icedlatteproject.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MySQLCommentRepository implements CommentRepository {
    private final CommentJpaRepository jpaRepository;

    @Override
    public Comment save(Comment comment) {
        CommentModel model = toModel(comment);
        CommentModel saved = jpaRepository.save(model);
        return toDomain(saved);
    }

    @Override
    public Comment findById(Integer id) {
        return jpaRepository.findById(id)
                .map(this::toDomain)
                .orElse(null);
    }

    @Override
    public List<Comment> findByVisitId(Integer visitId) {
        return jpaRepository.findByVisitId(visitId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> findByUserId(Integer userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> findByRatingRange(Double minRating, Double maxRating) {
        return jpaRepository.findByRatingBetween(minRating, maxRating).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return jpaRepository.findByDateBetween(startDate, endDate).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }

    // ---------- Mapping helpers ----------
    private CommentModel toModel(Comment comment) {
        return CommentModel.builder()
                .commentId(comment.getCommentId())
                .rating(comment.getRating())
                .body(comment.getBody())
                .userId(comment.getUserId())
                .visitId(comment.getVisitId())
                .coffeeShopId(comment.getCoffeeShopId())
                .date(comment.getDate())
                .build();
    }

    private Comment toDomain(CommentModel model) {
        return Comment.builder()
                .commentId(model.getCommentId())
                .rating(model.getRating())
                .body(model.getBody())
                .userId(model.getUserId())
                .visitId(model.getVisitId())
                .coffeeShopId(model.getCoffeeShopId())
                .date(model.getDate())
                .build();
    }
}

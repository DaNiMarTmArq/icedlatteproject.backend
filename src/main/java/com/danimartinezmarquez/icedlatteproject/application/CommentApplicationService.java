package com.danimartinezmarquez.icedlatteproject.application;

import com.danimartinezmarquez.icedlatteproject.application.dtos.AddCommentRequest;
import com.danimartinezmarquez.icedlatteproject.application.dtos.CommentResponse;
import com.danimartinezmarquez.icedlatteproject.application.dtos.UpdateCommentRequest;
import com.danimartinezmarquez.icedlatteproject.application.errors.CommentNotFoundException;
import com.danimartinezmarquez.icedlatteproject.application.errors.VisitNotFoundException;
import com.danimartinezmarquez.icedlatteproject.application.repositories.CommentRepository;
import com.danimartinezmarquez.icedlatteproject.application.repositories.VisitRepository;
import com.danimartinezmarquez.icedlatteproject.domain.Comment;
import com.danimartinezmarquez.icedlatteproject.domain.Visit;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CommentApplicationService {

    private final CommentRepository commentRepository;
    private final VisitRepository visitRepository;


    public CommentResponse addCommentToVisit(Integer visitId, AddCommentRequest commentRequest) {
        validateVisitId(visitId);
        validateAddCommentRequest(commentRequest);

        Visit visit = visitRepository.findById(visitId);
        if (visit == null) {
            throw new VisitNotFoundException("Visit not found with id: " + visitId);
        }

        Comment comment = mapAddRequestToDomain(commentRequest, visitId);
        Comment savedComment = commentRepository.save(comment);

        // Add comment to visit domain object and save
        visit.addComment(savedComment);
        visitRepository.save(visit);

        return mapDomainToResponse(savedComment);
    }

    public CommentResponse getById(Integer commentId) {
        validateCommentId(commentId);

        Comment comment = commentRepository.findById(commentId);
        if (comment == null) {
            throw new CommentNotFoundException("Comment not found with id: " + commentId);
        }

        return mapDomainToResponse(comment);
    }

    public List<CommentResponse> getByVisitId(Integer visitId) {
        validateVisitId(visitId);

        List<Comment> comments = commentRepository.findByVisitId(visitId);
        return comments.stream()
                .map(this::mapDomainToResponse)
                .collect(Collectors.toList());
    }

    public List<CommentResponse> getByUserId(Integer userId) {
        validateUserId(userId);

        List<Comment> comments = commentRepository.findByUserId(userId);
        return comments.stream()
                .map(this::mapDomainToResponse)
                .collect(Collectors.toList());
    }

    public List<CommentResponse> getByRatingRange(Double minRating, Double maxRating) {
        validateRatingRange(minRating, maxRating);

        List<Comment> comments = commentRepository.findByRatingRange(minRating, maxRating);
        return comments.stream()
                .map(this::mapDomainToResponse)
                .collect(Collectors.toList());
    }

    public List<CommentResponse> getByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        validateDateRange(startDate, endDate);

        List<Comment> comments = commentRepository.findByDateRange(startDate, endDate);
        return comments.stream()
                .map(this::mapDomainToResponse)
                .collect(Collectors.toList());
    }

    public CommentResponse update(Integer commentId, UpdateCommentRequest updateRequest) {
        validateCommentId(commentId);
        validateUpdateCommentRequest(updateRequest);

        Comment existingComment = commentRepository.findById(commentId);
        if (existingComment == null) {
            throw new CommentNotFoundException("Comment not found with id: " + commentId);
        }

        Comment updatedComment = applyUpdatesToComment(existingComment, updateRequest);
        Comment savedComment = commentRepository.save(updatedComment);

        return mapDomainToResponse(savedComment);
    }

    public void deleteById(Integer commentId) {
        validateCommentId(commentId);

        Comment existingComment = commentRepository.findById(commentId);
        if (existingComment == null) {
            throw new CommentNotFoundException("Comment not found with id: " + commentId);
        }

        // Remove comment from visit if needed
        Integer visitId = existingComment.getVisitId();
        if (visitId != null) {
            Visit visit = visitRepository.findById(visitId);
            if (visit != null) {
                // Note: This would require adding a removeComment method to Visit domain
                // For now, just delete the comment from repository
            }
        }

        commentRepository.deleteById(commentId);
    }

    // Validation methods
    private void validateAddCommentRequest(AddCommentRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Comment request cannot be null");
        }
        if (request.getBody() == null || request.getBody().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment body is required");
        }
        if (request.getRating() == null) {
            throw new IllegalArgumentException("Comment rating is required");
        }
        if (request.getRating() < 1.0 || request.getRating() > 5.0) {
            throw new IllegalArgumentException("Rating must be between 1.0 and 5.0");
        }
        validateUserId(request.getUserId());
    }

    private void validateUpdateCommentRequest(UpdateCommentRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Update request cannot be null");
        }
        if (request.getBody() != null && request.getBody().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment body cannot be empty if provided");
        }
        if (request.getRating() != null && (request.getRating() < 1.0 || request.getRating() > 5.0)) {
            throw new IllegalArgumentException("Rating must be between 1.0 and 5.0");
        }
    }

    private void validateCommentId(Integer commentId) {
        if (commentId == null || commentId <= 0) {
            throw new IllegalArgumentException("Comment ID must be a positive integer");
        }
    }

    private void validateVisitId(Integer visitId) {
        if (visitId == null || visitId <= 0) {
            throw new IllegalArgumentException("Visit ID must be a positive integer");
        }
    }

    private void validateUserId(Integer userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive integer");
        }
    }

    private void validateRatingRange(Double minRating, Double maxRating) {
        if (minRating == null || maxRating == null) {
            throw new IllegalArgumentException("Min and max ratings are required");
        }
        if (minRating < 1.0 || minRating > 5.0 || maxRating < 1.0 || maxRating > 5.0) {
            throw new IllegalArgumentException("Ratings must be between 1.0 and 5.0");
        }
        if (minRating > maxRating) {
            throw new IllegalArgumentException("Min rating cannot be greater than max rating");
        }
    }

    private void validateDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date are required");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }

    // Mapping methods
    private Comment mapAddRequestToDomain(AddCommentRequest request, Integer visitId) {
        return Comment.builder()
                .body(request.getBody().trim())
                .rating(request.getRating())
                .userId(request.getUserId())
                .visitId(visitId)
                .date(LocalDateTime.now())
                .build();
    }

    private Comment applyUpdatesToComment(Comment existingComment, UpdateCommentRequest updateRequest) {
        if (updateRequest.getBody() != null) {
            existingComment.setBody(updateRequest.getBody().trim());
        }
        if (updateRequest.getRating() != null) {
            existingComment.setRating(updateRequest.getRating());
        }
        return existingComment;
    }

    private CommentResponse mapDomainToResponse(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .body(comment.getBody())
                .rating(comment.getRating())
                .userId(comment.getUserId())
                .visitId(comment.getVisitId())
                .date(comment.getDate())
                .build();
    }
}

package com.danimartinezmarquez.icedlatteproject.api.controllers;

import com.danimartinezmarquez.icedlatteproject.application.CommentApplicationService;
import com.danimartinezmarquez.icedlatteproject.application.dtos.AddCommentRequest;
import com.danimartinezmarquez.icedlatteproject.application.dtos.CommentResponse;
import com.danimartinezmarquez.icedlatteproject.application.dtos.UpdateCommentRequest;
import com.danimartinezmarquez.icedlatteproject.application.errors.CommentNotFoundException;
import com.danimartinezmarquez.icedlatteproject.application.errors.VisitNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentApplicationService commentService;

    @PostMapping("/visit/{visitId}")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Integer visitId,
            @RequestBody AddCommentRequest request
    ) {
        CommentResponse response = commentService.addCommentToVisit(visitId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getById(@PathVariable Integer commentId) {
        return ResponseEntity.ok(commentService.getById(commentId));
    }


    @GetMapping("/visit/{visitId}")
    public ResponseEntity<List<CommentResponse>> getByVisitId(@PathVariable Integer visitId) {
        return ResponseEntity.ok(commentService.getByVisitId(visitId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentResponse>> getByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(commentService.getByUserId(userId));
    }

    @GetMapping("/rating")
    public ResponseEntity<List<CommentResponse>> getByRatingRange(
            @RequestParam Double minRating,
            @RequestParam Double maxRating
    ) {
        return ResponseEntity.ok(commentService.getByRatingRange(minRating, maxRating));
    }

    @GetMapping("/date")
    public ResponseEntity<List<CommentResponse>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return ResponseEntity.ok(commentService.getByDateRange(startDate, endDate));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> update(
            @PathVariable Integer commentId,
            @RequestBody UpdateCommentRequest request
    ) {
        return ResponseEntity.ok(commentService.update(commentId, request));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Integer commentId) {
        commentService.deleteById(commentId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCommentNotFound(CommentNotFoundException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(VisitNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleVisitNotFound(VisitNotFoundException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleValidation(IllegalArgumentException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "Unexpected error: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
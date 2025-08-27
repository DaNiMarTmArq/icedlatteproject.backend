package com.danimartinezmarquez.icedlatteproject.application;

import com.danimartinezmarquez.icedlatteproject.application.dtos.*;
import com.danimartinezmarquez.icedlatteproject.application.errors.CoffeeShopNotFoundException;
import com.danimartinezmarquez.icedlatteproject.application.errors.VisitNotFoundException;
import com.danimartinezmarquez.icedlatteproject.application.repositories.CoffeeShopRepository;
import com.danimartinezmarquez.icedlatteproject.application.repositories.VisitRepository;
import com.danimartinezmarquez.icedlatteproject.domain.Comment;
import com.danimartinezmarquez.icedlatteproject.domain.Visit;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class VisitApplicationService {

    private final VisitRepository visitRepository;
    private final CoffeeShopRepository coffeeShopRepository;

    public VisitResponse createNew(CreateVisitRequest createRequest) {
        validateCreateRequest(createRequest);

        Visit visit = mapCreateRequestToDomain(createRequest);
        Visit savedVisit = visitRepository.save(visit);

        return mapDomainToResponse(savedVisit);
    }

    public VisitResponse getById(Integer visitId) {
        validateId(visitId);

        Visit visit = visitRepository.findById(visitId);
        if (visit == null) {
            throw new VisitNotFoundException("Visit not found with id: " + visitId);
        }

        return mapDomainToResponse(visit);
    }

    public List<VisitResponse> getAll() {
        List<Visit> visits = visitRepository.findAll();
        return visits.stream()
                .map(this::mapDomainToResponse)
                .collect(Collectors.toList());
    }

    public List<VisitResponse> getByUserId(Integer userId) {
        validateUserId(userId);

        List<Visit> visits = visitRepository.findByUserId(userId);
        return visits.stream()
                .map(this::mapDomainToResponse)
                .collect(Collectors.toList());
    }

    public List<VisitResponse> getByCoffeeShopId(Integer coffeeShopId) {
        validateCoffeeShop(coffeeShopId);

        List<Visit> visits = visitRepository.findByCoffeeShopId(coffeeShopId);
        return visits.stream()
                .map(this::mapDomainToResponse)
                .collect(Collectors.toList());
    }

    public List<VisitResponse> getByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        validateDateRange(startDate, endDate);

        List<Visit> visits = visitRepository.findByDateRange(startDate, endDate);
        return visits.stream()
                .map(this::mapDomainToResponse)
                .collect(Collectors.toList());
    }

    public VisitResponse update(Integer visitId, UpdateVisitRequest updateRequest) {
        validateId(visitId);
        validateUpdateRequest(updateRequest);

        Visit existingVisit = visitRepository.findById(visitId);
        if (existingVisit == null) {
            throw new VisitNotFoundException("Visit not found with id: " + visitId);
        }

        Visit updatedVisit = applyUpdatesToVisit(existingVisit, updateRequest);
        Visit savedVisit = visitRepository.save(updatedVisit);

        return mapDomainToResponse(savedVisit);
    }

    public void deleteById(Integer visitId) {
        validateId(visitId);

        Visit existingVisit = visitRepository.findById(visitId);
        if (existingVisit == null) {
            throw new VisitNotFoundException("Visit not found with id: " + visitId);
        }

        visitRepository.deleteById(visitId);
    }

    private void validateCreateRequest(CreateVisitRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Visit request cannot be null");
        }
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Visit title is required");
        }
        if (request.getDate() == null) {
            throw new IllegalArgumentException("Visit date is required");
        }
        validateUserId(request.getUserId());
        validateCoffeeShop(request.getCoffeeShopId());
    }

    private void validateUpdateRequest(UpdateVisitRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Update request cannot be null");
        }
        if (request.getTitle() != null && request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Visit title cannot be empty if provided");
        }
    }

    private void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive integer");
        }
    }

    //To do: use the User repo to validate that user exists in reality
    private void validateUserId(Integer userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive integer");
        }
    }


    private void validateCoffeeShop(Integer coffeeShopId) {
        if (coffeeShopId == null || coffeeShopId <= 0) {
            throw new IllegalArgumentException("Coffee shop ID must be a positive integer");
        }

        if (coffeeShopRepository.findById(coffeeShopId) == null)
            throw new CoffeeShopNotFoundException("Coffee shop not found with id: " + coffeeShopId);
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
    private Visit mapCreateRequestToDomain(CreateVisitRequest request) {
        return Visit.builder()
                .title(request.getTitle().trim())
                .date(request.getDate())
                .userId(request.getUserId())
                .coffeeShopId(request.getCoffeeShopId())
                .build();
    }

    private Visit applyUpdatesToVisit(Visit existingVisit, UpdateVisitRequest updateRequest) {
        if (updateRequest.getTitle() != null) {
            existingVisit.setTitle(updateRequest.getTitle().trim());
        }
        if (updateRequest.getDate() != null) {
            existingVisit.setDate(updateRequest.getDate());
        }
        return existingVisit;
    }


    private VisitResponse mapDomainToResponse(Visit visit) {
        List<CommentResponse> commentResponses = visit.getComments().stream()
                .map(this::mapCommentToResponse)
                .collect(Collectors.toList());

        return VisitResponse.builder()
                .visitId(visit.getVisitId())
                .title(visit.getTitle())
                .date(visit.getDate())
                .userId(visit.getUserId())
                .coffeeShopId(visit.getCoffeeShopId())
                .comments(commentResponses)
                .rating(visit.rating())
                .build();
    }

    private CommentResponse mapCommentToResponse(Comment comment) {
        return CommentResponse.builder()
                .body(comment.getBody())
                .rating(comment.getRating())
                .userId(comment.getUserId())
                .date(comment.getDate())
                .build();
    }
}

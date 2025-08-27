package com.danimartinezmarquez.icedlatteproject.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitResponse {
    private Integer visitId;
    private String title;
    private LocalDateTime date;
    private Integer userId;
    private Integer coffeeShopId;
    private List<CommentResponse> comments;
    private Double rating;
}

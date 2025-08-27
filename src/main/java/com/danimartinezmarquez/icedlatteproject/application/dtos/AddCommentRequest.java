package com.danimartinezmarquez.icedlatteproject.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCommentRequest {
    private String body;
    private Double rating;
    private Integer userId;
}

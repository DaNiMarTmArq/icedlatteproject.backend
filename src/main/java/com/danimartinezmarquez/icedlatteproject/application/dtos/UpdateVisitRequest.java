package com.danimartinezmarquez.icedlatteproject.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateVisitRequest {
    private String title;
    private LocalDateTime date;
}

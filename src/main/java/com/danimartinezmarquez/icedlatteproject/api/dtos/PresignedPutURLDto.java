package com.danimartinezmarquez.icedlatteproject.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PresignedPutURLDto {
    private String url;
    private int secondsTTL;
}

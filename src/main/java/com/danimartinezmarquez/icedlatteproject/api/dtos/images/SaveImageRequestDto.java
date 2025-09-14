package com.danimartinezmarquez.icedlatteproject.api.dtos.images;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaveImageRequestDto {
    @NotBlank
    private String photoPath;
    @NotNull
    private Integer userId;
    @NotNull
    private Integer commentId;
    @NotNull
    private Integer coffeeShopId;
}

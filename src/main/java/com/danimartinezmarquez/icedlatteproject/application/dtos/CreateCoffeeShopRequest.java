package com.danimartinezmarquez.icedlatteproject.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCoffeeShopRequest {
    private String name;
    private String location;
    private BigDecimal locationLatitude;
    private BigDecimal locationLongitude;
    private Boolean glutenFree;
    private Boolean lactoseFree;
    private Boolean vegetarianOptions;
    private String coverPhotoUrl;
}

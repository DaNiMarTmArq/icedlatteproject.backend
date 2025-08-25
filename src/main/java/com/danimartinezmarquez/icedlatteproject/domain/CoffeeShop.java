package com.danimartinezmarquez.icedlatteproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeShop {
    private Integer coffeeShopId;
    private String name;
    private String location;
    private BigDecimal locationLatitude;
    private BigDecimal locationLongitude;
    private Boolean glutenFree;
    private Boolean lactoseFree;
    private Boolean vegetarianOptions;
    private LocalDateTime dateAdded;
    private String coverPhotoUrl;
}
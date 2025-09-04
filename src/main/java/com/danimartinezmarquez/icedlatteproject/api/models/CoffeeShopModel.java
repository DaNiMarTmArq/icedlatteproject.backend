package com.danimartinezmarquez.icedlatteproject.api.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coffee_shops")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeShopModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coffee_shop_id", nullable = false, updatable = false)
    private Integer coffeeShopId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "location_latitude", precision = 9, scale = 6)
    private BigDecimal locationLatitude;

    @Column(name = "location_longitude", precision = 9, scale = 6)
    private BigDecimal locationLongitude;

    @Column(name = "gluten_free", nullable = false)
    private boolean glutenFree;

    @Column(name = "lactose_free", nullable = false)
    private boolean lactoseFree;

    @Column(name = "vegetarian_options", nullable = false)
    private boolean vegetarianOptions;

    @CreationTimestamp
    @Column(name = "date_added", nullable = false, updatable = false)
    private LocalDateTime dateAdded;

    @Column(name = "cover_photo_url", length = 255)
    private String coverPhotoUrl;
}
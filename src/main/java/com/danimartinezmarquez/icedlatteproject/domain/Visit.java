package com.danimartinezmarquez.icedlatteproject.domain;

import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Visit {

    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer visitId;

    private String title;

    private LocalDateTime date;

    private Float rating;

    @ToString.Exclude
    private User creator;

    @ToString.Exclude
    private CoffeeShop coffeeShop;

    @Builder.Default
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();
}
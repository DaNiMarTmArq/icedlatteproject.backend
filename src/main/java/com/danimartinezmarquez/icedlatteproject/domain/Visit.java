package com.danimartinezmarquez.icedlatteproject.domain;

import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @ToString.Exclude
    private User creator;
    @ToString.Exclude
    private CoffeeShop coffeeShop;
    @Builder.Default
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    public float calculateRating() {
        return (float) comments
                .stream()
                .mapToDouble(Comment::getRating)
                .average()
                .orElse(0);
    }
}
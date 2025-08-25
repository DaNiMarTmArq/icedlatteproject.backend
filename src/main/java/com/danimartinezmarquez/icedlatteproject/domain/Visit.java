package com.danimartinezmarquez.icedlatteproject.domain;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
    private Integer userId;
    private Integer coffeeShopId;
    @Builder.Default
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public BigDecimal rating() {
        if (comments.isEmpty()) return BigDecimal.ZERO;
        BigDecimal sum = comments.stream()
                .map(Comment::getRating)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(comments.size()), 2, RoundingMode.HALF_UP);
    }
}
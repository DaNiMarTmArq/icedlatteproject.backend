package com.danimartinezmarquez.icedlatteproject.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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

    public Double rating() {
       if (comments.isEmpty()) return 0.0;
       return comments.stream()
               .mapToDouble(Comment::getRating)
               .average()
               .orElse(0.0);
    }
}
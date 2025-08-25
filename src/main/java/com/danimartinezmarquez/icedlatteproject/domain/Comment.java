package com.danimartinezmarquez.icedlatteproject.domain;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comment {
    private Float rating;
    private String body;
    @ToString.Exclude
    private User writer;
    private LocalDateTime date;
}
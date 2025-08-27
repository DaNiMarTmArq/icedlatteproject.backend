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
    private Double rating;
    private String body;
    private Integer userId;
    private LocalDateTime date;
}
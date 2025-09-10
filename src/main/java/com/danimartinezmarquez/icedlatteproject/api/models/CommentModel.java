package com.danimartinezmarquez.icedlatteproject.api.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class CommentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Lob
    @Column(name = "body")
    private String body;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "visit_id")
    private Integer visitId;

    @Column(name = "coffee_shop_id")
    private Integer coffeeShopId;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;
}
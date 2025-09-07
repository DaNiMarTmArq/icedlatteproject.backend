package com.danimartinezmarquez.icedlatteproject.api.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
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
    private Float rating;

    @Lob
    @Column(name = "body")
    private String body;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", foreignKey = @ForeignKey(name = "fk_comments_visit_id"))
    private VisitModel visit;

    @Column(name = "coffee_shop_id")
    private Integer coffeeShopId;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;
}

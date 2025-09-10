package com.danimartinezmarquez.icedlatteproject.api.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "visits")
public class VisitModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private Integer visitId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "coffee_shop_id")
    private Integer coffeeShopId;

    @Column(name = "created_by_user_id", nullable = false)
    private Integer createdByUserId;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "visit_id", referencedColumnName = "visit_id")
    private List<CommentModel> comments = new ArrayList<>();
}
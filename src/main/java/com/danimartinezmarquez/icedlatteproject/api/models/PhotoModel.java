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
@Table(name = "photos")
public class PhotoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id", nullable = false, updatable = false)
    private Integer photoId;

    @Column(name = "bucket_name", nullable = false, length = 255)
    private String bucketName;

    @Column(name = "photo_path", nullable = false, length = 255)
    private String photoPath;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name = "coffee_shop_id")
    private Integer coffeeShopId;

    @Column(name = "date", insertable = false, updatable = false)
    private LocalDateTime date;
}
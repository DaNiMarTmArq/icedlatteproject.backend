package com.danimartinezmarquez.icedlatteproject.api.dtos.images;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDto {
    String imagePath;
    String imageURL;
    int expiresInSeconds;
}

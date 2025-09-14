package com.danimartinezmarquez.icedlatteproject.api.controllers;

import com.danimartinezmarquez.icedlatteproject.api.dtos.images.ImageMetadataDto;
import com.danimartinezmarquez.icedlatteproject.api.dtos.images.PresignedPutURLDto;
import com.danimartinezmarquez.icedlatteproject.api.dtos.images.SaveImageRequestDto;
import com.danimartinezmarquez.icedlatteproject.api.exceptions.FileExtensionNotValidException;
import com.danimartinezmarquez.icedlatteproject.api.models.PhotoModel;
import com.danimartinezmarquez.icedlatteproject.api.repositories.jpa.PhotoJpaRepository;
import com.danimartinezmarquez.icedlatteproject.api.services.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final PhotoJpaRepository photoJpaRepository;

    private final ImageService imageService;

    /**
     * Generates a presigned PUT URL for uploading an image directly to storage.
     *
     * @param imageKey the object key (including filename/extension) to upload to
     * @return 200 OK with the presigned URL payload, or 400 if the extension is invalid
     */
    @PostMapping("/create-presigned-url/{imageKey}")
    public ResponseEntity<PresignedPutURLDto> createPresignedPutUrl(@PathVariable String imageKey) {
        PresignedPutURLDto dto = imageService.createPresignedPutRequest(imageKey);
        return ResponseEntity.ok(dto);
    }

    /**
     * Save uploaded image metadata to the database.
     *
     * @param request DTO with bucket, path, and foreign key IDs
     * @return 201 Created with the saved metadata and Location header
     */
    @PostMapping("/save-image")
    public ResponseEntity<ImageMetadataDto> saveImageInfo(@Valid @RequestBody SaveImageRequestDto request) {
        ImageMetadataDto saved = imageService.saveImageMetadata(request);

        URI location = URI.create(String.format("/api/images/%s", saved.getPhotoPath()));
        return ResponseEntity.created(location).body(saved);
    }



    @ExceptionHandler(FileExtensionNotValidException.class)
    public ResponseEntity<String> handleInvalidExtension(FileExtensionNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
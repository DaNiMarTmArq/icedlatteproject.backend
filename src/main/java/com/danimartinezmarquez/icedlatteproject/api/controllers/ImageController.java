package com.danimartinezmarquez.icedlatteproject.api.controllers;

import com.danimartinezmarquez.icedlatteproject.api.dtos.PresignedPutURLDto;
import com.danimartinezmarquez.icedlatteproject.api.exceptions.FileExtensionNotValidException;
import com.danimartinezmarquez.icedlatteproject.api.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    /**
     * Generates a presigned PUT URL for uploading an image directly to storage.
     *
     * @param imageKey the object key (including filename/extension) to upload to
     * @return 200 OK with the presigned URL payload, or 400 if the extension is invalid
     */
    @PostMapping("/{imageKey}")
    public ResponseEntity<PresignedPutURLDto> createPresignedPutUrl(@PathVariable String imageKey) {
        PresignedPutURLDto dto = imageService.createPresignedPutRequest(imageKey);
        return ResponseEntity.ok(dto);
    }


    @ExceptionHandler(FileExtensionNotValidException.class)
    public ResponseEntity<String> handleInvalidExtension(FileExtensionNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
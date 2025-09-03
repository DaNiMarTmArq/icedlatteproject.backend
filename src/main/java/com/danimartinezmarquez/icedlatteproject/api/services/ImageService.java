package com.danimartinezmarquez.icedlatteproject.api.services;

import com.danimartinezmarquez.icedlatteproject.api.dtos.PresignedPutURLDto;
import com.danimartinezmarquez.icedlatteproject.api.exceptions.FileExtensionNotValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.Locale;
import java.util.Map;

/**
 * Service for generating presigned PUT URLs so clients can upload images directly to S3.
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Validate file key and extension (image-only whitelist).</li>
 *   <li>Normalize the final object key under the configured image folder.</li>
 *   <li>Presign a PUT request with a short-lived TTL.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class ImageService {

    private static final Map<String, String> IMAGE_CONTENT_TYPES = Map.of(
            "jpg", "image/jpeg",
            "jpeg", "image/jpeg",
            "png", "image/png",
            "gif", "image/gif",
            "bmp", "image/bmp",
            "webp", "image/webp"
    );

    /**
     * S3 bucket name where images are stored.
     */
    @Value("${spring.images.bucket}")
    private String bucketName;

    /**
     * Folder (prefix) inside the bucket where images live, e.g. "uploads/images/".
     * A trailing slash is optional in config; normalization ensures exactly one.
     */
    @Value("${spring.images.path}")
    private String imageFolder;

    /**
     * Presigned URL TTL in minutes (default 5). Must be within [1, 60].
     */
    private final int ttlMinutes = 5;

    private final S3Presigner presigner;

    /**
     * Create a presigned PUT URL for the provided file key.
     *
     * @param key filename or relative key (e.g. "avatar.png" or "users/123/avatar.png")
     * @return DTO containing the URL and expiration (seconds)
     * @throws FileExtensionNotValidException if the file extension is not a supported image type
     * @throws IllegalArgumentException       if the key is blank or contains illegal path segments
     */
    public PresignedPutURLDto createPresignedPutRequest(String key) {
        String sanitizedKey = sanitizeKey(key);
        String contentType = resolveContentType(sanitizedKey);

        Duration ttl = Duration.ofMinutes(ttlMinutes);

        String objectKey = joinFolderAndKey(normalizeFolder(imageFolder), sanitizedKey);

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(ttl)
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest presigned = presigner.presignPutObject(presignRequest);

        long expiresInSeconds = ttl.toSeconds();
        return new PresignedPutURLDto(presigned.url().toExternalForm(), (int) expiresInSeconds);
    }


    /**
     * Resolve a MIME type from a filename's extension using a strict whitelist.
     */
    private String resolveContentType(String filename) {
        String lower = filename.toLowerCase(Locale.ROOT);
        int dot = lower.lastIndexOf('.');
        String ext = (dot >= 0 && dot < lower.length() - 1) ? lower.substring(dot + 1) : "";
        String mime = IMAGE_CONTENT_TYPES.get(ext);
        if (mime == null) {
            throw new FileExtensionNotValidException("Invalid file type. Only images are allowed.");
        }
        return mime;
    }

    /**
     * Prevent path traversal and reject empty keys.
     * Allows subfolders but disallows absolute paths and parent directory escapes.
     */
    private String sanitizeKey(String key) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("File key must not be blank.");
        }
        String trimmed = key.trim();
        if (trimmed.startsWith("/") || trimmed.contains("..")) {
            throw new IllegalArgumentException("Illegal file key.");
        }
        // collapse redundant slashes
        return trimmed.replace("\\", "/").replaceAll("/{2,}", "/");
    }

    private String normalizeFolder(String folder) {
        if (folder == null || folder.isBlank()) return "";
        String f = folder.replace("\\", "/");
        f = f.replaceAll("/{2,}", "/");
        if (f.startsWith("/")) f = f.substring(1);
        if (!f.endsWith("/")) f = f + "/";
        return f;
    }

    private String joinFolderAndKey(String folder, String key) {
        return folder + key;
    }

}
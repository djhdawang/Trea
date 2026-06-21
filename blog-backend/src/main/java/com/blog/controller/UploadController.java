package com.blog.controller;

import com.blog.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@io.swagger.v3.oas.annotations.tags.Tag(name = "File Upload API")
public class UploadController {

    @Value("${blog.upload.path}")
    private String uploadPath;

    @Value("${blog.upload.url}")
    private String uploadUrl;

    @PostMapping("/image")
    @Operation(summary = "Upload image")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("Please select a file to upload");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return Result.error("Only image files can be uploaded");
        }

        if (file.getSize() > 10 * 1024 * 1024) {
            return Result.error("File size cannot exceed 10MB");
        }

        try {
            String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            File uploadDir = new File(uploadPath + datePath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String suffix = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString() + suffix;

            File destFile = new File(uploadDir, fileName);
            file.transferTo(destFile);

            String url = uploadUrl + datePath + "/" + fileName;
            return Result.success(url);
        } catch (IOException e) {
            return Result.error("File upload failed: " + e.getMessage());
        }
    }
}

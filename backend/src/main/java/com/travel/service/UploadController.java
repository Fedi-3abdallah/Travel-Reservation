package com.travel.controller;

import com.travel.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController @RequestMapping("/api/uploads")
public class UploadController {
    private final StorageService storage;
    public UploadController(StorageService storage) { this.storage = storage; }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> upload(@RequestParam("file") MultipartFile file) {
        return Map.of("url", storage.sauvegarder(file));
    }
}

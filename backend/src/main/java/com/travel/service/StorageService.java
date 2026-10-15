package com.travel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;

/** Enregistre les images envoyées par l'admin dans un dossier local (uploads/). */
@Service
public class StorageService {
    private static final Map<String, String> TYPES =
            Map.of("image/jpeg", "jpg", "image/png", "png", "image/webp", "webp", "image/gif", "gif");
    private final Path dossier;

    public StorageService(@Value("${app.upload-dir:uploads}") String dir) throws IOException {
        this.dossier = Paths.get(dir).toAbsolutePath().normalize();
        Files.createDirectories(dossier);
    }

    public Path getDossier() { return dossier; }

    /** Retourne le chemin public de l'image, ex. /uploads/3f2a...jpg */
    public String sauvegarder(MultipartFile fichier) {
        String ext = TYPES.get(fichier.getContentType());
        if (fichier.isEmpty() || ext == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Format non supporté (JPG, PNG, WEBP ou GIF).");
        String nom = UUID.randomUUID() + "." + ext; // nom généré : jamais celui du client
        try (InputStream in = fichier.getInputStream()) {
            Files.copy(in, dossier.resolve(nom));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Échec de l'enregistrement de l'image");
        }
        return "/uploads/" + nom;
    }
}

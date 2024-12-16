package dev.nichoko.diogenes.service;

import org.springframework.web.multipart.MultipartFile;

public interface AiService {

    String sendPrompt(String prompt);

    String sendPromptWithImage(String prompt, MultipartFile imageFile);
}

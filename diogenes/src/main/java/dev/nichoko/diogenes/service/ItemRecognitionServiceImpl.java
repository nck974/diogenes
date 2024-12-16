package dev.nichoko.diogenes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.nichoko.diogenes.exception.UnexpectedAiResponseException;
import dev.nichoko.diogenes.model.RecognizedItem;

@Service
public class ItemRecognitionServiceImpl implements ItemRecognitionService {

    private AiService aiService;

    @Autowired
    public ItemRecognitionServiceImpl(AiService aiService) {
        this.aiService = aiService;
    }

    private static final String IMAGE_RECOGNITION_PROMPT = """
            You are an AI assistant which helps creating an inventory:
            1. Your task is to find the main object in the provided photo.
            2. You only have to return the name of the item in the photo.
            4. If it is possible specify the model of the item.
            3. Returns the item name in a json where the key is called 'item'.
            4. Add a description of the item in a key 'description'.

            Example:
            {
                "item": "HP Laptop",
                "description": "A gray laptop of the brand HP".
            }
            """;

    @Override
    public RecognizedItem getItemInImage(MultipartFile imageFile) {
        String promptResponse = aiService.sendPromptWithImage(IMAGE_RECOGNITION_PROMPT, imageFile);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(promptResponse, RecognizedItem.class);
        } catch (Exception e) {
            throw new UnexpectedAiResponseException("The item of the image was not recognized");
        }
    }

}

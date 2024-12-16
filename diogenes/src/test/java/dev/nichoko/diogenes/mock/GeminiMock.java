package dev.nichoko.diogenes.mock;

public class GeminiMock {

    public static String getMockGeminiResponse() {
        return """
                {
                    "candidates": [
                        {
                            "content": {
                                "parts": [
                                    {
                                        "text": "{\\"item\\": \\"name\\", \\"description\\": \\"some description\\"}"
                                    }
                                ],
                                "role": "model"
                            },
                            "finishReason": "STOP",
                            "index": 0,
                            "safetyRatings": [],
                            "avgLogprobs": -0.000012974194500462285
                        }
                    ],
                    "usageMetadata": {
                        "promptTokenCount": 23,
                        "candidatesTokenCount": 14,
                        "totalTokenCount": 37
                    },
                    "modelVersion": "gemini-1.5-flash-002"
                }
                """;
    }

    public static String getMockGeminiResponseError() {
        return """
                {
                    "error": {
                    "code": 400,
                    "message": "Request contains an invalid argument.",
                    "status": "INVALID_ARGUMENT",
                    "details": [
                        {
                        "@type": "type.googleapis.com/google.rpc.BadRequest",
                        "fieldViolations": [
                            {
                            "field": "contents",
                            "description": "Invalid request payload format"
                            }
                        ]
                        }
                    ]
                    }
                }
                """;
    }
}

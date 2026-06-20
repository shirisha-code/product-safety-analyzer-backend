package com.productsafety.service;
import com.productsafety.dto.IngredientExplanationDTO;
import com.productsafety.repository.IngredientKnowledgeRepository;
import com.productsafety.entity.IngredientKnowledge;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IngredientKnowledgeService {
    
    private final RestTemplate restTemplate;
    private final IngredientKnowledgeRepository ingredientKnowledgeRepository;

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    public IngredientKnowledgeService(
            RestTemplate restTemplate,
            IngredientKnowledgeRepository ingredientKnowledgeRepository) {

        this.restTemplate = restTemplate;
        this.ingredientKnowledgeRepository =
                ingredientKnowledgeRepository;
    }

    @Value("${groq.api.key}")
private String apiKey;

@Value("${groq.model}")
private String model;

    public void testConfig() {
        System.out.println("Model = " + model);
        System.out.println("API Key Loaded = " + (apiKey != null));
    }

    public String testOpenRouter() {

        String url = "https://api.groq.com/openai/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("model", model);
        requestBody.put(
    "response_format",
    Map.of("type", "json_object")
);

        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", "Say hello from OpenRouter");

        requestBody.put("messages", List.of(message));

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url,
                        request,
                        String.class
                );

        return response.getBody();
    }
    public String analyzeIngredient(String ingredientName) {

    String url = "https://api.groq.com/openai/v1/chat/completions";

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(apiKey);
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, Object> requestBody = new HashMap<>();
    if (model != null && !model.isBlank()) {
    requestBody.put("model", model);
}

    Map<String, String> message = new HashMap<>();

    message.put("role", "user");

    message.put(
    "content",
    """
    Analyze the ingredient %s.

    Return ONLY valid JSON.
    alternative_ingredients must be a single text string.

Example:
"Hyaluronic Acid, Panthenol, Propylene Glycol"

Do not return arrays.

    Risk score rules:
    0 = Extremely Dangerous
    100 = Completely Safe

    {
      "ingredient_name":"",
      "general_safety":"",
      "risk_score":0,
      "adult_recommendation":"",
      "children_recommendation":"",
      "pregnancy_recommendation":"",
      "sensitive_skin_recommendation":"",
      "allergy_recommendation":"",
      "effect_on_body":"",
      "warning_message":"",
      "restricted_countries":"",
      "alternative_ingredients":"",
      "scientific_summary":""
    }

    Do not add markdown.
    Do not add explanation.
    Return JSON only.
    """.formatted(ingredientName)

    );

    requestBody.put("messages", List.of(message));

    HttpEntity<Map<String, Object>> request =
            new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response =
            restTemplate.postForEntity(
                    url,
                    request,
                    String.class
            );
            System.out.println(response.getBody());

    return response.getBody();
}
public String askAI(String prompt) {

    String url =
            "https://api.groq.com/openai/v1/chat/completions";

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(apiKey);
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, Object> requestBody = new HashMap<>();

    requestBody.put("model", model);

    Map<String, String> message = new HashMap<>();

    message.put("role", "user");
    message.put("content", prompt);

    requestBody.put(
            "messages",
            List.of(message));

    HttpEntity<Map<String, Object>> request =
            new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response =
            restTemplate.postForEntity(
                    url,
                    request,
                    String.class);

    return response.getBody();
}

public String extractContent(String response)
        throws Exception {

    JsonNode root =
            objectMapper.readTree(response);

    return root
            .path("choices")
            .get(0)
            .path("message")
            .path("content")
            .asText();
}
public boolean ingredientExists(String ingredientName) {

    return ingredientKnowledgeRepository
            .findByIngredientName(ingredientName)
            .isPresent();
}
public IngredientKnowledge getIngredientFromDatabase(
        String ingredientName) {

    Optional<IngredientKnowledge> ingredient =
            ingredientKnowledgeRepository
                    .findByIngredientName(ingredientName);

    return ingredient.orElse(null);
}
public IngredientKnowledge saveIngredient(
        IngredientKnowledge ingredientKnowledge) {

    return ingredientKnowledgeRepository
            .save(ingredientKnowledge);
}
public IngredientKnowledge createIngredientFromAI(
        String ingredientName) throws Exception {

    IngredientKnowledge ingredient =
            generateIngredientKnowledge(
                    ingredientName);

    return ingredientKnowledgeRepository
            .save(ingredient);
}
public IngredientKnowledge parseIngredientJson(
        String json) throws Exception {

    return objectMapper.readValue(
            json,
            IngredientKnowledge.class
    );
}

public IngredientKnowledge generateIngredientKnowledge(
        String ingredientName) throws Exception {

    String response =
            analyzeIngredient(ingredientName);

    String content =
            extractContent(response);

    System.out.println("AI CONTENT:");
    System.out.println(content);

    try {
    return parseIngredientJson(content);
} catch (Exception e) {
    System.out.println("INVALID JSON:");
    System.out.println(content);
    throw e;
}
}
public IngredientExplanationDTO getIngredientExplanation(
        String ingredientName) throws Exception {

    IngredientKnowledge ingredient =
            getIngredientFromDatabase(ingredientName);

    if (ingredient == null) {

        ingredient =
                createIngredientFromAI(ingredientName);
    }

    return new IngredientExplanationDTO(
            ingredient.getIngredientName(),
            ingredient.getRiskScore(),
            ingredient.getGeneralSafety(),
            ingredient.getWarningMessage(),
            ingredient.getEffectOnBody()
    );
}
}
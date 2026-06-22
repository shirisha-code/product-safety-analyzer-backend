package com.productsafety.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productsafety.dto.ProductAnalysisResponse;
import com.productsafety.dto.ProductDiscoveryResult;

import com.productsafety.repository.ProductRepository;
import com.productsafety.repository.IngredientRepository;
import com.productsafety.repository.ProductIngredientRepository;
import com.productsafety.repository.IngredientKnowledgeRepository;

import com.productsafety.service.IngredientKnowledgeService;
import com.productsafety.service.ProductAnalysisService;

import com.productsafety.entity.Product;
import com.productsafety.entity.Ingredient;
import com.productsafety.entity.ProductIngredient;
@Service
public class ProductDiscoveryService {
    private final RestTemplate restTemplate;
    private final ProductRepository productRepository;

private final IngredientRepository ingredientRepository;

private final ProductIngredientRepository productIngredientRepository;

private final IngredientKnowledgeRepository ingredientKnowledgeRepository;

private final IngredientKnowledgeService ingredientKnowledgeService;

private final ProductAnalysisService productAnalysisService;
private final ObjectMapper objectMapper =
        new ObjectMapper();

public ProductDiscoveryService(
        RestTemplate restTemplate,
        ProductRepository productRepository,
        IngredientRepository ingredientRepository,
        ProductIngredientRepository productIngredientRepository,
        IngredientKnowledgeRepository ingredientKnowledgeRepository,
        IngredientKnowledgeService ingredientKnowledgeService,
        ProductAnalysisService productAnalysisService) {

    this.restTemplate = restTemplate;
    this.productRepository = productRepository;
    this.ingredientRepository = ingredientRepository;
    this.productIngredientRepository = productIngredientRepository;
    this.ingredientKnowledgeRepository = ingredientKnowledgeRepository;
    this.ingredientKnowledgeService = ingredientKnowledgeService;
    this.productAnalysisService = productAnalysisService;
}
@Value("${groq.api.key}")
private String apiKey;

@Value("${groq.model}")
private String model;


public String discoverProductIngredients(
        String productName) {

    String url =
            "https://api.groq.com/openai/v1/chat/completions";

    HttpHeaders headers = new HttpHeaders();

    headers.setBearerAuth(apiKey);
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, Object> requestBody =
            new HashMap<>();

    requestBody.put("model", model);

    requestBody.put(
            "response_format",
            Map.of("type", "json_object")
    );

    Map<String, String> message =
            new HashMap<>();

    message.put("role", "user");

    message.put(
            "content",
            """
            Product: %s

            Return ONLY JSON.

            {
              "product_name":"",
              "brand":"",
              "category":"",
              "ingredients":[
                "Ingredient 1",
                "Ingredient 2",
                "Ingredient 3"
              ]
            }

            Return at least 10 to 20 major ingredients if known.

            Do not return fewer than 10 ingredients unless information is unavailable.

            Return ingredients only.
            No explanation.
            """
                    .formatted(productName)
    );

    requestBody.put(
            "messages",
            List.of(message)
    );

    HttpEntity<Map<String, Object>> request =
            new HttpEntity<>(
                    requestBody,
                    headers
            );

    ResponseEntity<String> response =
            restTemplate.postForEntity(
                    url,
                    request,
                    String.class
            );

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
public ProductDiscoveryResult parseDiscoveryJson(
        String json) throws Exception {

    return objectMapper.readValue(
            json,
            ProductDiscoveryResult.class
    );
}
private Integer getNextProductId() {

    return productRepository.findAll()
            .stream()
            .map(Product::getId)
            .max(Integer::compareTo)
            .orElse(0) + 1;
}
public Product saveProduct(
        ProductDiscoveryResult result) {

    Product existingProduct =
            productRepository
                    .findByNameIgnoreCase(
                            result.getProduct_name())
                    .orElse(null);

    if (existingProduct != null) {
        return existingProduct;
    }

    Product product = new Product();

    product.setId(getNextProductId());

    product.setName(
            result.getProduct_name());

    product.setBrand(
            result.getBrand());

    product.setCategory(
            result.getCategory());

    return productRepository.save(product);
}
private Integer getNextIngredientId() {

    return ingredientRepository.findAll()
            .stream()
            .map(Ingredient::getIngredientId)
            .max(Integer::compareTo)
            .orElse(0) + 1;
}
public List<Ingredient> saveIngredients(
        ProductDiscoveryResult result) {

    List<Ingredient> savedIngredients =
            new java.util.ArrayList<>();

    for (String ingredientName :
            result.getIngredients()) {

        Ingredient ingredient =
                ingredientRepository
                        .findByIngredientNameIgnoreCase(
                                ingredientName)
                        .orElse(null);

        if (ingredient == null) {

            ingredient = new Ingredient();

            ingredient.setIngredientId(
                    getNextIngredientId());

            ingredient.setIngredientName(
                    ingredientName);

            ingredient =
                    ingredientRepository.save(
                            ingredient);
        }

        savedIngredients.add(ingredient);
    }

    return savedIngredients;
}
public void saveProductIngredients(
        Product product,
        List<Ingredient> ingredients) {

    for (Ingredient ingredient : ingredients) {

        boolean exists =
                productIngredientRepository
                        .findByProduct_Id(
                                product.getId())
                        .stream()
                        .anyMatch(pi ->
                                pi.getIngredient()
                                  .getIngredientId()
                                  .equals(
                                      ingredient.getIngredientId()
                                  ));

        if (!exists) {

            ProductIngredient mapping =
                    new ProductIngredient();

            mapping.setProduct(product);

            mapping.setIngredient(ingredient);

            productIngredientRepository
                    .save(mapping);
        }
    }
}
public void generateMissingKnowledge(
        List<Ingredient> ingredients) {

    for (Ingredient ingredient : ingredients) {

        String ingredientName =
                ingredient.getIngredientName();

        boolean exists =
                ingredientKnowledgeService
                        .ingredientExists(
                                ingredientName);

        if (!exists) {

            try {

                System.out.println(
                        "Generating knowledge for: "
                                + ingredientName);

                ingredientKnowledgeService
                        .createIngredientFromAI(
                                ingredientName);

            } catch (Exception e) {

                System.out.println(
                        "Failed: "
                                + ingredientName);
            }
        }
    }
}
public ProductAnalysisResponse discoverAndAnalyze(
        String productName)
        throws Exception {
                if (productName == null ||
    productName.trim().length() < 5) {

    throw new RuntimeException(
            "Please enter a complete product name");
}

    String response =
            discoverProductIngredients(
                    productName);
                    

    String content =
            extractContent(response);

    ProductDiscoveryResult result =
            parseDiscoveryJson(content);

    Product product =
            saveProduct(result);

    List<Ingredient> ingredients =
            saveIngredients(result);

    saveProductIngredients(
            product,
            ingredients);

    generateMissingKnowledge(
            ingredients);

    return productAnalysisService
            .analyzeProduct(
                    product.getId());
}
}
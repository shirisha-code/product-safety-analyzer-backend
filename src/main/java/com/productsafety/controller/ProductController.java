package com.productsafety.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.productsafety.entity.IngredientKnowledge;
import com.productsafety.entity.Product;
import com.productsafety.entity.Ingredient;
import com.productsafety.repository.ProductRepository;
import com.productsafety.repository.IngredientKnowledgeRepository;
import com.productsafety.repository.ProductIngredientRepository;
import com.productsafety.entity.ProductIngredient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.productsafety.dto.ProductAnalysisResponse;
import com.productsafety.dto.ProductDetailResponse;
import com.productsafety.dto.ProductDiscoveryResult;
import com.productsafety.service.ProductAnalysisService;
import com.productsafety.service.ProductDiscoveryService;
import com.productsafety.service.IngredientKnowledgeService;
import com.productsafety.service.ProductRecommendationService;
import com.productsafety.dto.RecommendedProductDTO;
import com.productsafety.dto.IngredientExplanationDTO;
import com.productsafety.service.FullAnalysisService;
import com.productsafety.dto.FullProductAnalysisResponse;
import java.util.stream.Collectors;
import java.util.List;

@CrossOrigin(
    origins = {
        "http://localhost:5173",
        "https://product-safety-analyzer-frontend.vercel.app"
    }
)
@RestController
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductIngredientRepository productIngredientRepository;
    private final IngredientKnowledgeRepository ingredientKnowledgeRepository;
    private final IngredientKnowledgeService ingredientKnowledgeService;
    private final ProductAnalysisService productAnalysisService;
    private final ProductDiscoveryService productDiscoveryService;
    private final ProductRecommendationService productRecommendationService;
    private final FullAnalysisService fullAnalysisService;
    
    public ProductController(
        ProductRepository productRepository,
        ProductIngredientRepository productIngredientRepository,
        IngredientKnowledgeRepository ingredientKnowledgeRepository,
        IngredientKnowledgeService ingredientKnowledgeService,
        ProductAnalysisService productAnalysisService,
        ProductDiscoveryService productDiscoveryService,
        ProductRecommendationService productRecommendationService,
        FullAnalysisService fullAnalysisService) {

    this.productRepository = productRepository;
    this.productIngredientRepository = productIngredientRepository;
    this.ingredientKnowledgeRepository = ingredientKnowledgeRepository;
    this.ingredientKnowledgeService = ingredientKnowledgeService;
    this.productAnalysisService = productAnalysisService;
    this.productDiscoveryService = productDiscoveryService;
    this.productRecommendationService = productRecommendationService;
    this.fullAnalysisService = fullAnalysisService;
}

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @GetMapping("/test-product-ingredients")
    public List<ProductIngredient> getAllProductIngredients() {
        return productIngredientRepository.findAll();
    }

    @GetMapping("/knowledge")
    public List<IngredientKnowledge> getKnowledge() {
        return ingredientKnowledgeRepository.findAll();
    }
    @GetMapping("/discover/{productName}")
public String discoverProduct(
        @PathVariable String productName)
        throws Exception {

    String response =
            productDiscoveryService
                    .discoverProductIngredients(
                            productName);

    return productDiscoveryService
            .extractContent(response);
}

@GetMapping("/save-product-ai/{productName}")
public Product saveProductAI(
        @PathVariable String productName)
        throws Exception {

    String response =
            productDiscoveryService
                    .discoverProductIngredients(
                            productName);

    String content =
            productDiscoveryService
                    .extractContent(response);

    ProductDiscoveryResult result =
            productDiscoveryService
                    .parseDiscoveryJson(content);

    return productDiscoveryService
            .saveProduct(result);
}

@GetMapping("/discover-test/{productName}")
public ProductDiscoveryResult discoverTest(
        @PathVariable String productName)
        throws Exception {

    String response =
            productDiscoveryService
                    .discoverProductIngredients(
                            productName);

    String content =
            productDiscoveryService
                    .extractContent(response);

    return productDiscoveryService
            .parseDiscoveryJson(content);
}

@GetMapping("/save-ingredients/{productName}")
public List<String> saveIngredients(
        @PathVariable String productName)
        throws Exception {

    String response =
            productDiscoveryService
                    .discoverProductIngredients(
                            productName);

    String content =
            productDiscoveryService
                    .extractContent(response);

    ProductDiscoveryResult result =
            productDiscoveryService
                    .parseDiscoveryJson(content);

    productDiscoveryService
            .saveIngredients(result);

    return result.getIngredients();
}

@GetMapping("/save-mappings/{productName}")
public String saveMappings(
        @PathVariable String productName)
        throws Exception {

    String response =
            productDiscoveryService
                    .discoverProductIngredients(
                            productName);

    String content =
            productDiscoveryService
                    .extractContent(response);

    ProductDiscoveryResult result =
            productDiscoveryService
                    .parseDiscoveryJson(content);

    Product product =
            productDiscoveryService
                    .saveProduct(result);

    List<Ingredient> ingredients =
            productDiscoveryService
                    .saveIngredients(result);

    productDiscoveryService
            .saveProductIngredients(
                    product,
                    ingredients);

    return "Mappings Saved";
}

@GetMapping("/discover-and-analyze/{productName}")
public ProductAnalysisResponse
discoverAndAnalyze(
        @PathVariable String productName)
        throws Exception {

    return productDiscoveryService
            .discoverAndAnalyze(
                    productName);
}

    @GetMapping("/test-config")
    public String testConfig() {

        ingredientKnowledgeService.testConfig();

        return "Check terminal";
    }
    @GetMapping("/analyze-ingredient/{ingredientName}")
    public IngredientKnowledge analyzeIngredient(
        @PathVariable String ingredientName)
        throws Exception {

    if (ingredientKnowledgeService
            .ingredientExists(ingredientName)) {

        return ingredientKnowledgeService
                .getIngredientFromDatabase(
                        ingredientName);
    }

    return ingredientKnowledgeService
            .createIngredientFromAI(
                    ingredientName);
}

@GetMapping("/products/recommend/{id}")
public List<RecommendedProductDTO>
recommendProducts(
        @PathVariable Integer id) {

    return productRecommendationService
            .recommendProducts(id);
}

@GetMapping("/products/full-analysis/{productName}")
public FullProductAnalysisResponse
fullAnalysis(
        @PathVariable String productName)
        throws Exception {

    return fullAnalysisService
            .getFullAnalysis(
                    productName);
}
    @GetMapping("/test-openrouter")
    public String testOpenRouter() {
        return ingredientKnowledgeService.testOpenRouter();
    }

    @GetMapping("/ingredient-details/{ingredientName}")
public IngredientExplanationDTO getIngredientDetails(
        @PathVariable String ingredientName)
        throws Exception {

    return ingredientKnowledgeService
            .getIngredientExplanation(
                    ingredientName);
}

    @GetMapping("/products/details/{id}")
public ProductDetailResponse getProductDetails(
        @PathVariable Integer id) {

    return productAnalysisService
            .getProductDetails(id);
}

    @GetMapping("/products/analyze/{id}")
    public ProductAnalysisResponse analyzeProduct(  
            @PathVariable Integer id) {

    return productAnalysisService.analyzeProduct(id);
    }

    @GetMapping("/products/search/name/{name}")
public List<Product> searchByName(
        @PathVariable String name) {

    return productRepository
            .findByNameContainingIgnoreCase(name);
}
@GetMapping("/products/search/brand/{brand}")
public List<Product> searchByBrand(
        @PathVariable String brand) {

    return productRepository
            .findByBrandContainingIgnoreCase(brand);
}
@GetMapping("/products/search/category/{category}")
public List<Product> searchByCategory(
        @PathVariable String category) {

    return productRepository
            .findByCategoryContainingIgnoreCase(category);
}
    @GetMapping("/products/{id}/ingredients")
    public List<String> getIngredientsByProduct(@PathVariable Integer id) {

    List<ProductIngredient> productIngredients =
            productIngredientRepository.findByProduct_Id(id);

    return productIngredients.stream()
            .map(pi -> pi.getIngredient().getIngredientName())
            .collect(Collectors.toList());
    }
}
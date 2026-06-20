package com.productsafety.service;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.productsafety.dto.ProductAnalysisResponse;
import com.productsafety.dto.RecommendedProductDTO;
import com.productsafety.entity.Product;
import com.productsafety.repository.ProductRepository;

@Service
public class ProductRecommendationService {
    private final ProductRepository productRepository;
    private final ProductAnalysisService productAnalysisService;

public ProductRecommendationService(
        ProductRepository productRepository,
        ProductAnalysisService productAnalysisService) {

    this.productRepository = productRepository;
    this.productAnalysisService = productAnalysisService;
}
public List<RecommendedProductDTO> recommendProducts(
        Integer productId) {

    Product currentProduct =
            productRepository.findById(productId)
                    .orElseThrow();

    String category =
            currentProduct.getCategory();
            ProductAnalysisResponse currentAnalysis =
        productAnalysisService
                .analyzeProduct(productId);

int currentScore =
        currentAnalysis.getOverallScore();

    List<Product> allProducts =
            productRepository.findAll();

    List<RecommendedProductDTO> recommendations =
            new ArrayList<>();
            

    for (Product product : allProducts) {

        if (product.getId().equals(productId)) {
            continue;
        }

        if (product.getCategory() == null) {
    continue;
}

if (!category.equalsIgnoreCase(
        product.getCategory())) {
    continue;
}
        

        ProductAnalysisResponse analysis =
                productAnalysisService
                        .analyzeProduct(
                                product.getId());
                                if (analysis.getOverallScore()
        < currentScore+5) {

    continue;
}

        RecommendedProductDTO dto =
                new RecommendedProductDTO();

        dto.setProductId(product.getId());

        dto.setProductName(product.getName());

        dto.setScore(
                analysis.getOverallScore());
        String encodedName =
        URLEncoder.encode(
    product.getName(),
    StandardCharsets.UTF_8
);

dto.setAmazonLink(
        "https://www.amazon.in/s?k="
                + encodedName);

dto.setFlipkartLink(
        "https://www.flipkart.com/search?q="
                + encodedName);


dto.setNykaaLink(
        "https://www.nykaa.com/search/result/?q="
                + encodedName);
dto.setMyntraLink(
        "https://www.myntra.com/search?q="
                + encodedName);

dto.setAjioLink(
        "https://www.ajio.com/search/?text="
                + encodedName);

dto.setMeeshoLink(
        "https://www.meesho.com/search?q="
                + encodedName);

dto.setPurplleLink(
        "https://www.purplle.com/search?q="
                + encodedName);

dto.setFirstCryLink(
        "https://www.firstcry.com/search?query="
                + encodedName);

dto.setTiraLink(
        "https://www.tirabeauty.com/search?q="
                + encodedName);

System.out.println("AMAZON = " + dto.getAmazonLink());
System.out.println("FLIPKART = " + dto.getFlipkartLink());
System.out.println("NYKAA = " + dto.getNykaaLink());
System.out.println("MYNTRA = " + dto.getMyntraLink());
System.out.println("AJIO = " + dto.getAjioLink());
System.out.println("MEESHO = " + dto.getMeeshoLink());
System.out.println("PURPLLE = " + dto.getPurplleLink());
System.out.println("FIRSTCRY = " + dto.getFirstCryLink());
System.out.println("TIRA = " + dto.getTiraLink());
        recommendations.add(dto);
    }

    recommendations.sort(
            (a, b) ->
                    b.getScore()
                     .compareTo(a.getScore())
    );

    if (recommendations.size() > 3) {

        return recommendations.subList(0, 3);
    }

    return recommendations;
}
}
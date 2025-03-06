package com.muhkeun.productmanagerapi.service;

import com.muhkeun.productmanagerapi.constant.OptionType;
import com.muhkeun.productmanagerapi.model.dto.OptionTemplateResponse;
import com.muhkeun.productmanagerapi.model.dto.ProductOptionRequest;
import com.muhkeun.productmanagerapi.model.dto.ProductOptionResponse;
import com.muhkeun.productmanagerapi.model.entity.Product;
import com.muhkeun.productmanagerapi.model.entity.ProductOption;
import com.muhkeun.productmanagerapi.model.entity.ProductOptionValue;
import com.muhkeun.productmanagerapi.repository.OptionTemplateRepository;
import com.muhkeun.productmanagerapi.repository.ProductOptionRepository;
import com.muhkeun.productmanagerapi.repository.ProductOptionValueRepository;
import com.muhkeun.productmanagerapi.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptionService {

    private final ProductRepository productRepository;

    private final ProductOptionRepository productOptionRepository;

    private final OptionTemplateRepository optionTemplateRepository;

    private final ProductOptionValueRepository productOptionValueRepository;

    @Transactional(readOnly = true)
    public List<OptionTemplateResponse> getAllOptionTemplates() {
        return optionTemplateRepository.findAll().stream().map(OptionTemplateResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductOptionResponse> getAllProductOptions(Long userId, Long productId) {
        return productOptionRepository.findALLByProductUserIdAndProductId(userId, productId)
                .stream().map(ProductOptionResponse::new).toList();
    }

    @Transactional
    public void replaceProductOptions(Long userId, Long productId, List<ProductOptionRequest> requests) {

        if (requests.size() > 3) {
            throw new IllegalArgumentException("상품당 최대 3개의 옵션만 등록할 수 있습니다.");
        }

        Product product = productRepository.findByUserIdAndId(userId, productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));


        productOptionRepository.deleteByProductId(productId);

        for (ProductOptionRequest request : requests) {
            ProductOption option = ProductOption.builder()
                    .product(product)
                    .name(request.getName())
                    .description(request.getDescription())
                    .optionType(request.getType())
                    .isRequired(request.getIsRequired())
                    .inputAdditionalPrice(request.getType() == OptionType.INPUT ? request.getInputAdditionalPrice() : null)
                    .build();

            productOptionRepository.save(option);

            if (request.getType() == OptionType.SELECT) {
                for (var valueRequest : request.getValues()) {
                    ProductOptionValue value = ProductOptionValue.builder()
                            .productOption(option)
                            .optionValue(valueRequest.getOptionValue())
                            .additionalPrice(valueRequest.getAdditionalPrice())
                            .build();

                    productOptionValueRepository.save(value);
                }
            }
        }
    }
}


package com.muhkeun.productmanagerapi.model.validation;

import com.muhkeun.productmanagerapi.constant.OptionType;
import com.muhkeun.productmanagerapi.model.dto.ProductOptionRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class ProductOptionsValidator implements ConstraintValidator<ValidProductOptions, List<ProductOptionRequest>> {

    @Override
    public boolean isValid(List<ProductOptionRequest> options, ConstraintValidatorContext context) {
        if (options == null || options.isEmpty()) {
            return false;
        }

        // 상품당 최대 3개의 옵션만 등록 가능
        if (options.size() > 3) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("상품당 최대 3개의 옵션만 등록할 수 있습니다.")
                    .addConstraintViolation();
            return false;
        }

        for (ProductOptionRequest option : options) {
            // 입력형 옵션이면 values 사용 불가
            if (option.getType() == OptionType.INPUT && option.getValues() != null && !option.getValues().isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("입력형 옵션은 옵션 값을 가질 수 없습니다.")
                        .addPropertyNode("values").addConstraintViolation();
                return false;
            }

            // 선택형 옵션이면 inputAdditionalPrice 사용 불가
            if (option.getType() == OptionType.SELECT && option.getInputAdditionalPrice() != null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("선택형 옵션은 추가 금액을 입력할 수 없습니다.")
                        .addPropertyNode("inputAdditionalPrice").addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}

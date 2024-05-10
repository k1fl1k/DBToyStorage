package com.k1fl1k.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public record ToyStoreDto(
    @NotBlank(message = "Назва іграшки не може бути порожньою")
    @Size(min = 2, max = 255, message = "Назва іграшки має містити від 2 до 255 символів")
    String name,

    @NotBlank(message = "Опис іграшки не може бути порожнім")
    String description,

    @NotNull(message = "Ціна іграшки не може бути порожньою")
    BigDecimal price,

    UUID category_id,

    UUID manufacture_id
) {

}

package com.k1fl1k.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryStoreDto(
    @NotBlank(message = "Назва категорії не може бути порожньою")
    @Size(min = 2, max = 255, message = "Назва категорії має містити від 2 до 255 символів")
    String name,

    @NotBlank(message = "Опис категорії не може бути порожнім")
    String description
) {

}

package com.k1fl1k.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record CategoryUpdateDto(
    @NotNull(message = "Відсутній ідентифікатор категорії")
    UUID id,

    @NotBlank(message = "Ім'я категорії не може бути порожнім або містити тільки пробіли")
    @Size(min = 2, max = 255, message = "Ім'я категорії має містити від 2 до 255 символів")
    String name,

    @NotBlank(message = "Опис категорії не може бути порожнім або містити тільки пробіли")
    @Size(min = 2, message = "Опис категорії має містити принаймні 2 символи")
    String description
) {

}

package com.k1fl1k.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record ManufactureUpdateDto(
    @NotNull(message = "Відсутній ідентифікатор виробника")
    UUID id,

    @NotBlank(message = "Ім'я виробника не може бути порожнім або містити тільки пробіли")
    @Size(min = 2, max = 255, message = "Ім'я виробника має містити від 2 до 255 символів")
    String name,

    @NotBlank(message = "Опис виробника не може бути порожнім або містити тільки пробіли")
    @Size(min = 2, message = "Опис виробника має містити принаймні 2 символи")
    String description
) {

}

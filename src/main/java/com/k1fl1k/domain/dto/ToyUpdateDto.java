package com.k1fl1k.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;

public record ToyUpdateDto(
    @NotNull(message = "Відсутній ідентифікатор іграшки")
    UUID id,

    @NotBlank(message = "Ім'я іграшки не може бути порожнім або містити тільки пробіли")
    String name,

    @NotBlank(message = "Опис іграшки не може бути порожнім або містити тільки пробіли")
    String description,

    @Positive(message = "Ціна має бути додатнім числом")
    float price,

    @NotNull(message = "Відсутній ідентифікатор категорії")
    UUID category_id,

    @NotNull(message = "Відсутній ідентифікатор виробника")
    UUID manufacture_id
) {

}

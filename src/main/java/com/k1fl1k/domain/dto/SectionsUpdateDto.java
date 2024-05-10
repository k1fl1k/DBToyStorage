package com.k1fl1k.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record SectionsUpdateDto(
    @NotNull(message = "Відсутній ідентифікатор секції")
    UUID id,

    @NotBlank(message = "Ім'я секції не може бути порожнім або містити тільки пробіли")
    String name,

    @NotNull(message = "Відсутній ідентифікатор категорії")
    UUID category_id
) {

}

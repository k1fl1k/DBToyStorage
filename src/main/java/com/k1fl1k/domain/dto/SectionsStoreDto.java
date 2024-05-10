package com.k1fl1k.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record SectionsStoreDto(
    @NotBlank(message = "Назва розділу не може бути порожньою")
    @Size(min = 2, max = 255, message = "Назва розділу має містити від 2 до 255 символів")
    String name,

    UUID category_id
) {

}

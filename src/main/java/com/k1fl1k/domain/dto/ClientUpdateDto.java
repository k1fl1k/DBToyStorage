package com.k1fl1k.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record ClientUpdateDto(
    @NotNull(message = "Відсутній ідентифікатор клієнта")
    UUID id,

    @NotBlank(message = "Ім'я клієнта не може бути порожнім або містити тільки пробіли")
    @Size(min = 2, max = 255, message = "Ім'я клієнта має містити від 2 до 255 символів")
    String name,

    @NotBlank(message = "Телефон клієнта не може бути порожнім або містити тільки пробіли")
    @Pattern(regexp = "^\\+?\\d+$", message = "Неправильний формат номеру телефону")
    String phone,

    @NotBlank(message = "Адреса клієнта не може бути порожньою або містити тільки пробіли")
    @Size(min = 2, message = "Адреса клієнта має містити принаймні 2 символи")
    String address
) {

}

package com.k1fl1k.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClientStoreDto(
    @NotBlank(message = "Ім'я клієнта не може бути порожнім")
    @Size(min = 2, max = 255, message = "Ім'я клієнта має містити від 2 до 255 символів")
    String name,

    @NotBlank(message = "Номер телефону клієнта не може бути порожнім")
    @Pattern(regexp = "\\+?\\d{0,12}", message = "Неправильний формат номеру телефону")
    String phone,

    @NotBlank(message = "Адреса клієнта не може бути порожньою")
    String address
) {

}

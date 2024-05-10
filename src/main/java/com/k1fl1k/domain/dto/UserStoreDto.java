package com.k1fl1k.domain.dto;

import com.k1fl1k.persistence.entity.Users.UsersRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserStoreDto(
    @NotBlank(message = "Логін користувача не може бути порожнім")
    @Size(min = 6, max = 32, message = "Логін користувача має містити від 6 до 32 символів")
    String login,
    @NotBlank(message = "Пароль не може бути порожнім")
    @Size(min = 8, max = 72, message = "Пароль повинен містити від 8 до 72 символів")
    String password,
    UsersRole role,
    @NotBlank(message = "Ім'я користувача не може бути порожнім")
    @Size(min = 6, max = 64, message = "Ім'я користувача має містити від 6 до 64 символів")
    String name
) {

}
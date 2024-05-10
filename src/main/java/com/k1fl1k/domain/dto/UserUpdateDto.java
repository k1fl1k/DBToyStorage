package com.k1fl1k.domain.dto;

import com.k1fl1k.persistence.entity.Users.UsersRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record UserUpdateDto(
    @NotNull(message = "Відсутній іденитфікатор користувача")
    UUID id,

    @Size(min = 6, max = 32, message = "Ім'я користувача має містити від 6 до 32 символів")
    String login,

    @Size(min = 8, max = 72, message = "Пароль повинен містити від 8 до 72 символів")
    String password,

    UsersRole role,

    @Past(message = "День народження має бути в минулому")
    String name
) {

}
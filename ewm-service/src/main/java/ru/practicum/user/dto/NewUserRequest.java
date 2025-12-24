package ru.practicum.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewUserRequest {

    @NotBlank
    @Size(max = 254, message = "Длина имени не должна превышать 254 символа")
    String name;

    @NotBlank
    @Size(max = 254, message = "Длина email не должна превышать 254 символа")
    String email;
}

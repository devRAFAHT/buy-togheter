package com.buytogheter.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDto {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿÇç\\s]+$", message = "O nome deve conter apenas letras e espaços.")
    private String name;
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, max = 64, message = "A senha deve ter entre 8 e 64 caracteres.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,64}$", message = "A senha deve conter ao menos uma letra maiúscula, uma minúscula, um número e um caractere especial.")
    private String password;
    @Pattern(regexp = "^(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)$", message = "A URL do avatar deve ser válida e terminar em .jpg, .gif ou .png.")
    private String avatar;

}

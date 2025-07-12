package com.buytogheter.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginDto {

    @NotBlank(message = "O username é obrigatório.")
    private String username;
    @NotBlank(message = "A senha é obrigatória.")
    private String password;

}

package com.github.williamjbf.gestaocontasdomesticas.security.authentication;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String token;

    public LoginResponseDTO(final String token) {
        this.token = "Bearer " + token;
    }
}

package com.example.board_upgrade.Dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequestDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}

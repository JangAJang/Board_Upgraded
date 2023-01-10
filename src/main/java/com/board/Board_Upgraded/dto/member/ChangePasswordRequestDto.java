package com.board.Board_Upgraded.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChangePasswordRequestDto {

    private String newPassword;

    private String newPasswordCheck;
}

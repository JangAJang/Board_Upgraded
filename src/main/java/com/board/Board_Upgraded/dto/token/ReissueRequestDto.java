package com.board.Board_Upgraded.dto.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReissueRequestDto {

    private String refreshToken;

    private String accessToken;
}

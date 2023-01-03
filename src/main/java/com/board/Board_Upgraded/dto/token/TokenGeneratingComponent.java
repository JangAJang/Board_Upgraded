package com.board.Board_Upgraded.dto.token;

public enum TokenGeneratingComponent {

    AUTHORITIES_KEY("auth"),
    BEARER_TYPE("bearer"),
    ACCESS_TOKEN_EXPIRE_TIME(Long.toString(1000 * 60 * 60 * 24)),
    REFRESH_TOKEN_EXPIRE_TIME(Long.toString(1000 * 60 * 60 * 24 * 7));

    private final String component;

    private TokenGeneratingComponent(String component){
        this.component = component;
    }

    public String getComponent(){
        return this.component;
    }
}

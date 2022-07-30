package com.example.board_upgrade.jwt;

public interface JwtProperties {
    String SECRET = "janghee5395";
    int EXPIRATION_TIME = 60000*60*24;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";

}

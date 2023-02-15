package com.board.Board_Upgraded.controller;

import com.board.Board_Upgraded.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest_Reissue {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthService authService;


}

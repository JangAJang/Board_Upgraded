package com.board.Board_Upgraded.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public String base(){
        return "프론트 CORS 문제 없음";
    }

    @ApiOperation(value = "get메서드 테스트", notes = "get이 스웨거에 등록되는지 테스트 합니다")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/test/getTest")
    public String getController(){
        return "get";
    }

    @ApiOperation(value = "post메서드 테스트", notes = "post이 스웨거에 등록되는지 테스트 합니다")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/api/test/postTest")
    public String postController(@RequestBody String testUser){
        log.info("testUser = {}, testTime = {}", testUser, LocalDateTime.now());
        return "post";
    }

    @ApiOperation(value = "put메서드 테스트", notes = "put이 스웨거에 등록되는지 테스트 합니다")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/test/putTest")
    public String putController(){return "put";}

    @ApiOperation(value = "delete메서드 테스트", notes = "delete이 스웨거에 등록되는지 테스트 합니다")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/api/test/deleteTest")
    public String deleteController(){return "delete";}
}

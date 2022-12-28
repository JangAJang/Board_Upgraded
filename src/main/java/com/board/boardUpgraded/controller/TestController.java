package com.board.boardUpgraded.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    @ApiOperation(value = "get메서드 테스트", notes = "get이 스웨거에 등록되는지 테스트 합니다")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getTest")
    public void getController(){}

    @ApiOperation(value = "post메서드 테스트", notes = "post이 스웨거에 등록되는지 테스트 합니다")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/postTest")
    public void postController(){}

    @ApiOperation(value = "put메서드 테스트", notes = "put이 스웨거에 등록되는지 테스트 합니다")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/putTest")
    public void putController(){}

    @ApiOperation(value = "delete메서드 테스트", notes = "delete이 스웨거에 등록되는지 테스트 합니다")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/deleteTest")
    public void deleteController(){}
}

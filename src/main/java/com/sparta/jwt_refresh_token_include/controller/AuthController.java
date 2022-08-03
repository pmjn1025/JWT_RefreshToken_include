package com.sparta.jwt_refresh_token_include.controller;


import com.sparta.jwt_refresh_token_include.dto.*;
import com.sparta.jwt_refresh_token_include.entity.Member;
import com.sparta.jwt_refresh_token_include.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
//@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

//    @PostMapping("/api/member/signup")
//    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
//        return ResponseEntity.ok(authService.signup(memberRequestDto));
//    }

    @PostMapping("/api/member/signup")
    public ResponseDto<?> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return authService.signup(memberRequestDto);
    }


//    @PostMapping("/api/member/login")
//    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
//        return ResponseEntity.ok(authService.login(memberRequestDto));
//    }

//    @PostMapping("/api/member/login")
//    public ResponseEntity<?> login(@RequestBody MemberRequestDto memberRequestDto) {
//        return authService.login(memberRequestDto);
//    }

    @PostMapping("/api/member/login")
    public ResponseEntity<?> login(@RequestBody MemberRequestDto memberRequestDto) {
        return authService.login(memberRequestDto);
    }




    @PostMapping("/auth/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }


}

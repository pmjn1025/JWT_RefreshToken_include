package com.sparta.jwt_refresh_token_include.service;


import com.sparta.jwt_refresh_token_include.dto.*;
import com.sparta.jwt_refresh_token_include.entity.Member;
import com.sparta.jwt_refresh_token_include.entity.RefreshToken;
import com.sparta.jwt_refresh_token_include.jwt.JwtFilter;
import com.sparta.jwt_refresh_token_include.jwt.TokenProvider;
import com.sparta.jwt_refresh_token_include.repository.MemberRepository;
import com.sparta.jwt_refresh_token_include.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    String name="";
    String p1="";
    @Transactional
    public ResponseDto<?> signup(MemberRequestDto memberRequestDto) {

        name = memberRequestDto.getNickname();
        p1 = memberRequestDto.getPassword();
        String p2 = memberRequestDto.getPasswordConfirm();

        System.out.println(name);
        System.out.println(p1);
        System.out.println(p2);

        if (!p1.equals(p2)) {

            return ResponseDto.fail("PASSWORD_NOT_CORRECT", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");

        } else if (name.equals("")) {

            return ResponseDto.fail("NickName_Is_NULL", "아이디를 입력해주세요");

        } else if (memberRepository.existsByNickname(memberRequestDto.getNickname())) {

            return ResponseDto.fail("NickName_Is_Already exist", "중복된 닉네임입니다.");

        } else {

            Member member = memberRequestDto.toMember(passwordEncoder);
            memberRepository.save(member);

            return ResponseDto.success(member);

        }
    }


//    @Transactional
//    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
//
//        String p1 = memberRequestDto.getPassword();
//        String p2 = memberRequestDto.getRepassword();
//
//        System.out.println(p1);
//        System.out.println(p2);
//
//        if(!p1.equals(p2)){
//
//            //System.out.println("비밀번호가 일치하지 않습니다.");
//            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
//
//
//        }
//
//
//
//
//        if (memberRepository.existsByName(memberRequestDto.getName())) {
//            throw new RuntimeException("이미 가입되어 있는 유저입니다");
//        }
//
//        Member member = memberRequestDto.toMember(passwordEncoder);
//        return MemberResponseDto.of(memberRepository.save(member));
//    }


    @Transactional
    public ResponseEntity<?> login(MemberRequestDto memberRequestDto) {

        Optional<Member> member = memberRepository.findByNickname(memberRequestDto.getNickname());

        System.out.println(memberRequestDto.getNickname());
        System.out.println(memberRequestDto.getPassword());
        System.out.println(name);
        System.out.println(p1);

        if(!name.equals(memberRequestDto.getNickname())){

            return ResponseEntity.internalServerError().body(ResponseDto.fail("NickName_Error", "사용자를 찾을 수 없습니다."));

        }else if(!p1.equals( memberRequestDto.getPassword())){

            return ResponseEntity.internalServerError().body(ResponseDto.fail("Password_Error", "사용자를 찾을 수 없습니다."));

        }else {


            // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
            UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

            // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
            //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenDto.getAccessToken());


            // 4. RefreshToken 저장
//        RefreshToken refreshToken = RefreshToken.builder()
//                .key(authentication.getName())
//                .value(tokenDto.getRefreshToken())
//                .build();

            RefreshToken refreshToken = new RefreshToken(authentication.getName(), tokenDto.getRefreshToken());
            HttpHeaders httpHeaders1 = new HttpHeaders();
            httpHeaders1.add("Refresh", "Bearer " + refreshToken.getValue());

            refreshTokenRepository.save(refreshToken);

            // 5. 토큰 헤더에 발급
            return ResponseEntity.ok()
                    .headers(httpHeaders)
                    .headers(httpHeaders1)
                    .body(ResponseDto.success(member));

        }
    }


//    @Transactional
//    public ResponseEntity<?> login(MemberRequestDto memberRequestDto) {
//
//        Optional<Member> member = memberRepository.findByNickname(memberRequestDto.getNickname());
//
//        System.out.println(memberRequestDto.getNickname());
//        System.out.println(memberRequestDto.getPassword());
//
//
//        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
//        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();
//
//        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
//        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        // 3. 인증 정보를 기반으로 JWT 토큰 생성
//        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenDto.getAccessToken());
//
//
//        // 4. RefreshToken 저장
////        RefreshToken refreshToken = RefreshToken.builder()
////                .key(authentication.getName())
////                .value(tokenDto.getRefreshToken())
////                .build();
//
//        RefreshToken refreshToken = new RefreshToken(authentication.getName(),tokenDto.getRefreshToken());
//        HttpHeaders httpHeaders1 = new HttpHeaders();
//        httpHeaders1.add("Refresh", "Bearer " + refreshToken.getValue());
//
//        refreshTokenRepository.save(refreshToken);
//
//        // 5. 토큰 발급
//        return ResponseEntity.ok()
//                .headers(httpHeaders)
//                .headers(httpHeaders1)
//                .body(member);
//    }


//    @Transactional
//    public TokenDto login(MemberRequestDto memberRequestDto) {
//
//        System.out.println(memberRequestDto.getNickname());
//        System.out.println(memberRequestDto.getPassword());
//
//
//        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
//        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();
//
//        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
//        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        // 3. 인증 정보를 기반으로 JWT 토큰 생성
//        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
//
//        // 4. RefreshToken 저장
////        RefreshToken refreshToken = RefreshToken.builder()
////                .key(authentication.getName())
////                .value(tokenDto.getRefreshToken())
////                .build();
//
//        RefreshToken refreshToken = new RefreshToken(authentication.getName(),tokenDto.getRefreshToken());
//
//
//        refreshTokenRepository.save(refreshToken);
//
//        // 5. 토큰 발급
//        return tokenDto;
//    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
}

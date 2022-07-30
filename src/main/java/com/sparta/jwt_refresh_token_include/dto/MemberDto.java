package com.sparta.jwt_refresh_token_include.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

    //닉네임은 최소 4자 이상, 12자 이하 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하기
    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message ="최소 4자 이상으로 작성해주세요" )
    private String name;

    //비밀번호는 최소 4자 이상이며, 32자 이하 알파벳 소문자(a~z), 숫자(0~9) 로 구성하기
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^[a-z0-9]{4,32}$", message ="최소 4자이상 알파벳 소문자 숫자로 작성부탁드립니다." )
    private String password;

    @NotBlank(message = "비밀번호를 한번더 입력해주세요")
    @Pattern(regexp = "^[a-z0-9]{4,32}$", message ="최소 4자이상 알파벳 소문자 숫자로 작성부탁드립니다." )
    private String repassword;

}

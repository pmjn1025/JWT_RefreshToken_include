package com.sparta.jwt_refresh_token_include.dto;

import com.sparta.jwt_refresh_token_include.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String name;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getNickname());
    }
}

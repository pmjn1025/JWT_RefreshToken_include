package com.sparta.jwt_refresh_token_include.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.jwt_refresh_token_include.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member extends Timestamped {

    @Id
    @Column(name = "member_id", nullable = false)
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //기본 키 생성을 데이터베이스에 위임
    //즉, id 값을 null로 하면 DB가 알아서 AUTO_INCREMENT 해준다
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nickname;

    @JsonIgnore
    private String password;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Member(String nickname, String password, Authority authority) {
        this.nickname = nickname;
        this.password = password;
        this.authority = authority;
    }

    public Member(MemberDto memberDto, Authority authority) {
        this.nickname = memberDto.getNickname();
        this.password = memberDto.getPassword();
        this.authority = authority;
    }

    public Member(Optional<Member> member) {
        this.nickname = member.get().getNickname();
        this.password = member.get().getPassword();
        this.authority = member.get().getAuthority();
    }
}

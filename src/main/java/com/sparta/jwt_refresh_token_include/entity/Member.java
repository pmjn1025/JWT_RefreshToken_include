package com.sparta.jwt_refresh_token_include.entity;


import com.sparta.jwt_refresh_token_include.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member {

    @Id
    @Column(name = "member_id", nullable = false)
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //기본 키 생성을 데이터베이스에 위임
    //즉, id 값을 null로 하면 DB가 알아서 AUTO_INCREMENT 해준다
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Member(String name, String password, Authority authority) {
        this.name = name;
        this.password = password;
        this.authority = authority;
    }

    public Member(MemberDto memberDto, Authority authority) {
        this.name = memberDto.getName();
        this.password = memberDto.getPassword();
        this.authority = authority;
    }

}

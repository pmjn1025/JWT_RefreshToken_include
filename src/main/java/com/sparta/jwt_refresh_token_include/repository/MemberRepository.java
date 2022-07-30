package com.sparta.jwt_refresh_token_include.repository;

import com.sparta.jwt_refresh_token_include.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByName(String name);
    boolean existsByName(String name);

}

package com.sparta.jwt_refresh_token_include.repository;

import com.sparta.jwt_refresh_token_include.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByKey(String key);
}

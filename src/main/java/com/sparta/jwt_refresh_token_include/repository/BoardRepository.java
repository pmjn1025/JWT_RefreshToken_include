package com.sparta.jwt_refresh_token_include.repository;

import com.sparta.jwt_refresh_token_include.entity.Board;
import com.sparta.jwt_refresh_token_include.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {

    List<Board> findAllByOrderByModifiedAtDesc();

}

package com.sparta.jwt_refresh_token_include.repository;

import com.sparta.jwt_refresh_token_include.dto.CommentResponseDto;
import com.sparta.jwt_refresh_token_include.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Optional<Comment> findAllByBoard_Id(Long id);




}

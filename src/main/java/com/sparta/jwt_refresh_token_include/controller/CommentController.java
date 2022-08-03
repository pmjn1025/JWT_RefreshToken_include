package com.sparta.jwt_refresh_token_include.controller;


import com.sparta.jwt_refresh_token_include.dto.BoardDto;
import com.sparta.jwt_refresh_token_include.dto.CommentDto;
import com.sparta.jwt_refresh_token_include.repository.BoardRepository;
import com.sparta.jwt_refresh_token_include.repository.MemberRepository;
import com.sparta.jwt_refresh_token_include.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {

        this.commentService = commentService;


    }

    //댓글 목록 조회
    @GetMapping("/api/comment/{id}")
    public ResponseEntity<?> readComment(@PathVariable Long id){

        return commentService.readComment(id);

    }

    // 댓글 생성
    @PostMapping("/api/auth/comment")
    public ResponseEntity<?> createComment(@RequestHeader("authorization") String authorization,
                                           @RequestBody CommentDto commentDto) {

        return commentService.createComment(authorization, commentDto);

    }

    // 댓글 수정
    @PutMapping("/api/auth/comment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id,
                                           @RequestHeader("authorization") String authorization,
                                           @RequestBody CommentDto commentDto) {

        return commentService.updateComment(id,authorization,commentDto);


    }

    // 댓글 삭제
    @DeleteMapping("/api/auth/comment/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){

        return commentService.delete(id);

    }











}

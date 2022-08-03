package com.sparta.jwt_refresh_token_include.controller;

import com.sparta.jwt_refresh_token_include.dto.BoardDto;
import com.sparta.jwt_refresh_token_include.dto.ResponseDto;
import com.sparta.jwt_refresh_token_include.entity.Board;
import com.sparta.jwt_refresh_token_include.entity.Member;
import com.sparta.jwt_refresh_token_include.repository.BoardRepository;
import com.sparta.jwt_refresh_token_include.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BoardController {

    private final BoardRepository boardRepository;

    private final BoardService boardService;

    public BoardController(BoardRepository boardRepository,
                           BoardService boardService) {

        this.boardRepository = boardRepository;
        this.boardService = boardService;

    }

    //게시글 조회
    @GetMapping("/api/post")
    public ResponseDto<?> readAllPost() {

        return boardService.readAllPost();

    }

    // 게시글 상세조회
    // /api/post/{id}
    @GetMapping("/api/post/{id}")
    public ResponseDto<?> readDetailPost(@PathVariable Long id){


        return boardService.readDetailPost(id);

    }


    // 게시글 작성
    @PostMapping("/api/auth/post")
    public ResponseEntity<?> createPost(@RequestHeader("authorization") String authorization,
                                        @RequestBody BoardDto boardDto) {

        return boardService.createPost(authorization, boardDto);

    }

    // 게시글 수정
    @PutMapping("/api/auth/post/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id,
                                        @RequestHeader("authorization") String authorization,
                                        @RequestBody BoardDto boardDto) {


        return boardService.updatePost(id, authorization, boardDto);


    }

    @DeleteMapping("/api/auth/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {

        return boardService.delete(id);

    }

}

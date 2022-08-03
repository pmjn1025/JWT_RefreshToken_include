package com.sparta.jwt_refresh_token_include.dto;


import com.sparta.jwt_refresh_token_include.entity.Board;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BoardResponseDto1 {


    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate, modifiedDate;
    private Long userId;
    private List<CommentResponseDto> comments;

    /* Entity -> Dto*/ // 과제 출력형식으로 인한 임시 DTO

    public BoardResponseDto1(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.author = board.getAuthor();
        this.content = board.getContent();
        this.createdDate = board.getCreatedAt();
        this.modifiedDate = board.getModifiedAt();
        this.userId = board.getMember().getId();
        //this.comments = board.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }


}

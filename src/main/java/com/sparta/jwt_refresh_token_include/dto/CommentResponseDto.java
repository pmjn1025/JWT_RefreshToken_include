package com.sparta.jwt_refresh_token_include.dto;

import com.sparta.jwt_refresh_token_include.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
@Setter
public class CommentResponseDto {

    private Long id;
    private String content;
    private LocalDateTime createdDate, modifiedDate;
    private String author;
    private Long postsId;

    /* Entity -> Dto*/
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedAt();
        this.modifiedDate = comment.getModifiedAt();
        this.author = comment.getMember().getNickname();
        this.postsId = comment.getBoard().getId();
    }




}

package com.sparta.jwt_refresh_token_include.dto;

import com.sparta.jwt_refresh_token_include.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
public class CommentResponseDto1 {


    private Long id;
    private String content;
    private LocalDateTime createdDate, modifiedDate;
    private String author;
    private Long postsId;


    public CommentResponseDto1(Optional<Comment> comment) {
        this.id = comment.get().getId();
        this.content = comment.get().getContent();
        this.createdDate = comment.get().getCreatedAt();
        this.modifiedDate = comment.get().getModifiedAt();
        this.author = comment.get().getMember().getNickname();
        this.postsId = comment.get().getBoard().getId();
    }


}

package com.sparta.jwt_refresh_token_include.dto;

import com.sparta.jwt_refresh_token_include.entity.Board;
import com.sparta.jwt_refresh_token_include.entity.Comment;
import com.sparta.jwt_refresh_token_include.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class CommentDto {

    private Long postId;
    private String content;
    //private String nickname;
    //private Long postsId;
    private Member member;
    private Board board;



    public Comment toEntity() {
        return Comment.builder()
                //.id(id)
                .content(content)
                //.createdDate(createdDate)
                //.modifiedDate(modifiedDate)
                .member(member)
                .board(board)
                .build();
    }


}

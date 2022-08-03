package com.sparta.jwt_refresh_token_include.dto;

import com.sparta.jwt_refresh_token_include.entity.Board;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardDto {
   // private Long id;
    private String title;
    //private String author;
    private String content;
//    private int view;
     //private Long memberId;
//    private List<CommentDto> comments;     /* Entity -> Dto*/

   // public BoardDto(Board board) {
//        this.id = board.getId();
//        this.title = board.getTitle();
//        this.author = board.getAuthor();
//        this.content = board.getContent();
//        this.view = board.getView();
//        this.memberId = board.getMember().getId();
//        this.comments = board.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
    //}


}

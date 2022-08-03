package com.sparta.jwt_refresh_token_include.dto;


import com.sparta.jwt_refresh_token_include.entity.Board;
import com.sparta.jwt_refresh_token_include.entity.Board1;
import com.sparta.jwt_refresh_token_include.entity.Timestamped;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시글 정보를 리턴할 응답(Response) 클래스
 * Entity 클래스를 생성자 파라미터로 받아 데이터를 Dto로 변환하여 응답
 * 별도의 전달 객체를 활용해 연관관계를 맺은 엔티티간의 무한참조를 방지
 */

@Getter
@Setter
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate, modifiedDate;
    private Long userId;
    private List<CommentResponseDto> comments;

    /* Entity -> Dto*/

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.author = board.getAuthor();
        this.content = board.getContent();
        this.createdDate = board.getCreatedAt();
        this.modifiedDate = board.getModifiedAt();
        this.userId = board.getMember().getId();
        this.comments = board.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }




}




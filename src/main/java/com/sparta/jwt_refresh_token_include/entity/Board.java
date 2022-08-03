package com.sparta.jwt_refresh_token_include.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.jwt_refresh_token_include.dto.BoardDto;
import com.sparta.jwt_refresh_token_include.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "board")
@Entity
public class Board extends Timestamped {
    @Id
    @Column(name = "board_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

//    @Column(columnDefinition = "integer default 0")
//    private int view;

    // 자동 참조. JoinColumn
    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    //원하는 컬럼만 가지고올 수 있는지 찾아보기.

    //private long memberNum;
    @Nullable
    @JsonManagedReference
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments;

    public Board(String title,String content,String author) {

        this.title = title;
        this.content = content;
        this.author= author;
//        this.memberNum = memberNum;

    }
    public Board(String title,String content,String author,Member member) {

        this.title = title;
        this.content = content;
        this.author= author;
        this.member = member;

    }


    /* 게시글 수정 메소드 */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


}

package com.sparta.jwt_refresh_token_include.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "comments")
@Entity
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 댓글 내용

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board; // 게시글 번호

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 작성자


    /* 게시글 수정 메소드 */
    public void update(String content) {

        this.content = content;
    }


}

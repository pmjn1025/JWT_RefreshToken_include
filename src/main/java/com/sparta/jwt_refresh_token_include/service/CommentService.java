package com.sparta.jwt_refresh_token_include.service;

import com.sparta.jwt_refresh_token_include.dto.CommentDto;
import com.sparta.jwt_refresh_token_include.dto.CommentResponseDto;
import com.sparta.jwt_refresh_token_include.dto.CommentResponseDto1;
import com.sparta.jwt_refresh_token_include.dto.ResponseDto;
import com.sparta.jwt_refresh_token_include.entity.Board;
import com.sparta.jwt_refresh_token_include.entity.Comment;
import com.sparta.jwt_refresh_token_include.entity.Member;
import com.sparta.jwt_refresh_token_include.repository.BoardRepository;
import com.sparta.jwt_refresh_token_include.repository.CommentRepository;
import com.sparta.jwt_refresh_token_include.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Service
public class CommentService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;




    @Autowired
    public CommentService(MemberRepository memberRepository,
                          BoardRepository boardRepository,
                          CommentRepository commentRepository) {

        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public ResponseEntity<?> createComment(String authorization, CommentDto commentDto) {
        // ============================== 토큰 복호화 start====================================
        String payloadStr = "";
        String some = "";
        String bearerToken = authorization;

        if (bearerToken.startsWith("Bearer ")) {
            some = bearerToken.substring(7);
            Base64.Decoder decoder = Base64.getDecoder();
            final String[] splitJwt = some.split("\\.");
            final String headerStr = new String(decoder.decode(splitJwt[0].getBytes()));
            payloadStr = new String(decoder.decode(splitJwt[1].getBytes()));

            //System.out.println(headerStr + "," + payloadStr);
        }

        //JWT String으로 복호화한것 자르기.
        String str = payloadStr;
        String target = "sub";
        int target_num = str.indexOf(target);
        String result = str.substring(target_num, (str.substring(target_num).indexOf(",") + target_num));
        //System.out.println(result);

        // 자른 문자열에서 숫자만 추출
        String intStr = result.replaceAll("[^\\d]", "");
        //System.out.println("sub숫자 " + intStr);

        System.out.println(payloadStr);
//        System.out.println("12312313123");

        // String이니까 Long으로 변환.
        long userId = Long.parseLong(intStr);

        //System.out.println("long으로 변환한 숫자" + userId);


        // ============================== 토큰 복호화 end ====================================

        Member member = memberRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저 인덱스 없음.")
        );

        long boardId = commentDto.getPostId();

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );



        commentDto.setMember(member);
        commentDto.setBoard(board);
        Comment comment = commentDto.toEntity();

        commentRepository.save(comment);

        //long commentId =  comment.getId();

        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

        return ResponseEntity.ok()
                .body(ResponseDto.success(commentResponseDto));




    }

    @Transactional
    public ResponseEntity<?> readComment(Long id) {

        Optional<Comment>comments = commentRepository.findAllByBoard_Id(id);
        //Optional<Comment> optionalComment = commentRepository.findById(id);


        CommentResponseDto1 commentResponseDto1 = new CommentResponseDto1(comments);


        return ResponseEntity.ok()
                .body(ResponseDto.success(commentResponseDto1));
    }

    @Transactional
    public ResponseEntity<?> updateComment(Long id, String authorization, CommentDto commentDto) {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.equals("")){

            return ResponseEntity.internalServerError().body(ResponseDto.fail("BOARD_NOT_EXIST", "해당 게시글이 없습니다."));
        }

        Comment comment = optionalComment.get();
        comment.update(commentDto.getContent());
        commentRepository.save(comment);

        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

        return ResponseEntity.ok().body(ResponseDto.success(commentResponseDto));

    }

    public ResponseEntity<?> delete(Long id) {

        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.equals("")){

            return ResponseEntity.internalServerError().body(ResponseDto.fail("COMMENT_NOT_EXIST", "해당 게시글이 없습니다."));
        }

        commentRepository.deleteById(id);

        return ResponseEntity.ok().body(ResponseDto.success("delete success"));
    }
}

package com.sparta.jwt_refresh_token_include.service;

import com.sparta.jwt_refresh_token_include.dto.BoardDto;
import com.sparta.jwt_refresh_token_include.dto.BoardResponseDto1;
import com.sparta.jwt_refresh_token_include.dto.ResponseDto;
import com.sparta.jwt_refresh_token_include.entity.Board;
import com.sparta.jwt_refresh_token_include.entity.Member;
import com.sparta.jwt_refresh_token_include.repository.BoardRepository;
import com.sparta.jwt_refresh_token_include.repository.BoardRepository1;
import com.sparta.jwt_refresh_token_include.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Base64;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardRepository1 boardRepository1;

    // 게시글 조회. 댓글 포함 안되야되는데 나옴
    @Transactional
    public ResponseDto<?> readAllPost() {

        //Optional<Board> board = boardRepository.findAll()

        //System.out.println(board);
        System.out.println("123123132123123");

        return ResponseDto.success(boardRepository.findAllByOrderByModifiedAtDesc());
    }
    // 게시글 상세 조회 댓글 까지 나와야 됨.
    public ResponseDto<?> readDetailPost(Long id) {


        return ResponseDto.success(boardRepository.findById(id));


    }

    @Transactional
    @Nullable
    public ResponseEntity<?> createPost(@RequestHeader("authorization") String authorization, BoardDto boardDto) {
        //JWT String으로 복호화했고 복호화 sub이 회원 idx
        // JWTfilter의 resolveToken메서드가 키포인트였다.
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

        Member member = memberRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저 인덱스 없음.")
        );

        String title = boardDto.getTitle();
        String content = boardDto.getContent();
        String nickName = member.getNickname();
        long memberNum = member.getId();

        System.out.println(title + content);

        Board board = new Board(title,content,nickName,member);
        //Board1 board1 = new Board1(title,content,nickName);

        boardRepository.save(board);
        //boardRepository1.save(board1);
        // dto null comment
        BoardResponseDto1 boardResponseDto1 = new BoardResponseDto1(board);



        return ResponseEntity.ok()
                .body(ResponseDto.success(boardResponseDto1));



    }

    @Transactional
    public ResponseEntity<?> updatePost(Long id, String authorization, BoardDto boardDto) {

        Optional<Board> optionalBoard = boardRepository.findById(id);

        if (optionalBoard.equals("")){

            return ResponseEntity.internalServerError().body(ResponseDto.fail("BOARD_NOT_EXIST", "해당 게시글이 없습니다."));
        }

        Board board = optionalBoard.get();
        board.update(boardDto.getTitle(),boardDto.getContent());
        boardRepository.save(board);

        return ResponseEntity.ok().body(ResponseDto.success(board));
    }

    @Transactional
    public ResponseEntity<?> delete(Long id) {

        Optional<Board> optionalBoard = boardRepository.findById(id);

        if (optionalBoard.equals("")){

            return ResponseEntity.internalServerError().body(ResponseDto.fail("BOARD_NOT_EXIST", "해당 게시글이 없습니다."));
        }

        boardRepository.deleteById(id);

        return ResponseEntity.ok().body(ResponseDto.success("delete success"));


    }


}

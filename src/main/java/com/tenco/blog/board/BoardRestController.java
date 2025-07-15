package com.tenco.blog.board;

import com.tenco.blog._core.common.ApiUtil;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardRestController {

    private final BoardService boardService;

    // 게시글 전체 보기
    @GetMapping("/")
    public ResponseEntity<ApiUtil<List<BoardResponse.MainDTO>>> mian(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        log.info("게시글 목록 조회 API - 페이지 : {} 크기 : {}", page, size);
        List<BoardResponse.MainDTO> boardList = boardService.list(page, size);
        return ResponseEntity.ok(new ApiUtil<>(boardList));
    }

    // 게시글 상세 보기 API 설계
    // GET http:localhost:8080/api/board/{id} Http 1.1
    @GetMapping("/api/boards/{id}/detail")
    public ResponseEntity<ApiUtil<BoardResponse.DetailDTO>> detail (
        @PathVariable(name = "id") Long id, HttpSession session) {
        log.info("게시글 상세보기 조회 API - ID : {}", id);
        User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
        BoardResponse.DetailDTO detailDTO = boardService.detail(id,sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(detailDTO));
    }

    // 게시글 작성 API
    @PostMapping("/api/boards")
    public ResponseEntity<?> save(@RequestBody BoardRequest.SaveDTO saveDTO,
                                  HttpSession session) {
        log.info("게시글 작성 요청 API - title : {}", saveDTO.getTitle());
        saveDTO.validate();
        User sessionUser = (User) session.getAttribute(Define.SESSION_USER);

        BoardResponse.SaveDTO savedBoard = boardService.save(saveDTO, sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(savedBoard));


    }

    // 게시글 수정 API
    @PutMapping("/api/boards/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id")Long id,
                                    @RequestBody BoardRequest.UpdateDTO updateDTO,
                                    HttpSession session) {
        log.info("게시글 수정 API 호출 - ID : {}", id);
        // 인증 검사 먼저
        // 유효성 검사
        updateDTO.validate();
        User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
        BoardResponse.UdateDTO updateBoard = boardService.udate(id,updateDTO,sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(updateBoard));
    }


    // 게시글 삭제 API
    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<ApiUtil<String>> delete (
            @PathVariable(name = "id") Long id,
            HttpSession session) {
        User sessionUser = (User)session.getAttribute(Define.SESSION_USER);
        boardService.deleteById(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>("게시글 삭제 성공"));
    }



}

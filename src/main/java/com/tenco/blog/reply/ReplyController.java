package com.tenco.blog.reply;

import com.tenco.blog._core.common.ApiUtil;
import com.tenco.blog.user.User;
import com.tenco.blog._core.utils.Define;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final ReplyService replyService;

    // 댓글 저장 API 요청
    @PostMapping("/api/replies")
    public ResponseEntity<?> save (@Valid @RequestBody ReplyRequest.SaveDTO saveDTO, Errors error, HttpSession session) {
        User sessionUser = (User) session.getAttribute(Define.SESSION_USER);

        // 코드 컨벤션
        ReplyResponse.SaveDTO savedReply = replyService.save(saveDTO, sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(savedReply));
    }

    // 댓글 삭제 기능 요청
    //http://localhost:8080/api/replies/1?boardId=1
    @PostMapping("/api/replies/{id}")
    public ResponseEntity<ApiUtil<String>> delete(@PathVariable(name = "id") Long replyId, @Valid
                         @RequestParam(name = "boardId") Long boardId, Errors Error,
                         HttpSession session) {
        User sessionUser = (User) session.getAttribute(Define.SESSION_USER);
        replyService.deleteById(replyId, sessionUser);

        return ResponseEntity.ok(new ApiUtil<>("댓글 삭제 성공"));
    }


}

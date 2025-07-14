package com.tenco.blog.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository // JpaRepository 상속 <--- 선언 불필요
public interface ReplyJpaRepository extends JpaRepository<Reply, Long> {
    // CRUD 기능 추가
    // Save(Repley) 댓글 저장
    // findById(Long id): Id로 댓글 조회
    // deleteById(Long id): id로 댓글 삭제
    // findAll() : 모든 댓글 조회
    // 수정은 - 더티 체킹 사용 ( Reply 객체 조회 <-- 상태값 변경 ) 트랜 --> commit

    // 추후 필요한 기능은 JPQL, 네이티브 쿼리, 쿼리 메서드 등.. 추가
}

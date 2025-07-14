package com.tenco.blog._core.common;

import lombok.Data;

/**
 *  REST API 공통 응답 형식을 위한 클래스 설계
 *  모든 API 응답을 통일된 형태로 관리하기 위해 설계
 * {
 *   "status" : 200,
 *   "msg"  : "성공",
 *   "body" : { 실제 데이터 }
 * }
 */
@Data
public class ApiUtil<T> {

    private Integer status; // HTTP 상태 코드
    private String msg;     // 응답 메세지
    private T body;         // 실제 응답 데이터 (제네릭 사용)

    /**
     * 성공 응답 생성자
     */
    public ApiUtil(T body) {
        this.status = 200;
        this.msg = "성공";
        this.body = body;
    }

    /**
     * 실패 응답 생성자
     */
    public ApiUtil(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
        this.body = null;
    }

}

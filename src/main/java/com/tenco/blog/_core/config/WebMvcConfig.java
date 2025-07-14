package com.tenco.blog._core.config;

import com.tenco.blog._core.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration // IoC 처리 (싱글톤 패턴 관리)
public class WebMvcConfig implements WebMvcConfigurer {

    // DI 처리(생성자 의존 주입)
    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                // REST API 경로 변경
                .addPathPatterns("/api/**")
                // 공개 API는 제외 처리
                .excludePathPatterns(
                        "/api/boards", // 게시글 목록은 누가 응답 받을 수 있음
                        "/api/boards/{id:\\d+}", // 게시글 상세보기 누가나 응답 허용
                        "/api/auth/login",  // 로그인 요청은 누구나 허용
                        "/api/auth/join"  // 회원 가입 요청도 누구나 허용
                );

    }

    // cors 정책 설정

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api-test/**")
                //.allowedOrigins("https://api.kakao.com:8080") 특정 도메인만 등록 가능
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(false); // 인증이 필요한 경우 true

        // 필요하다면 중복 등록 가능
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);

    }


}




package net.kdigital.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration          // SecurityConfig 클래스가 설정 클래스임을 나타내는 Annotation
@EnableWebSecurity      // 웹 보안은 모두 이 클래스의 설정에 따름을 나타내는 Annotation
public class SecurityConfig {
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 웹 요청에 대한 접근 권한 설정
        http.authorizeHttpRequests((auth)->
                auth.requestMatchers("/", 
                                    "/board/boardList",
                                    "/board/boardDetail",
                                    "/user/join",
                                    "/user/joinProc",
                                    "/user/login",
                                    "/reply/replyAll",
                                    "/img/**",
                                    "/script/**",
                                    "/style/**").permitAll()   // permitAll() : 인증 절차 없이도 접근 가능한 요청
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
                    .anyRequest().authenticated()   //기타 다른 경로는 인증된 사용자만 접근 가능 (가장 마지막에 둘 것)
            );
        // Custom Login 설정 
        http.formLogin((auth)-> auth.loginPage("/user/login")
                                    .usernameParameter("userId")
                                    .passwordParameter("userPwd")
                                    .loginProcessingUrl("/user/loginProc") // Controller에서 작업하지 않음
                                    .defaultSuccessUrl("/")
                                    .permitAll());
        // Custom Logout 설정 
        http.logout((auth)-> auth.logoutUrl("/user/logout")                     // 로그아웃 처리 url
                                    .logoutSuccessUrl("/")               // 로그아웃 성공시 url
                                    .invalidateHttpSession(true)    // 세션 무효화
                                    .deleteCookies("JSESSIONID"));  // 로그아웃 성공시 제거할 쿠키명


        // CSRF(Cross-Site Request Forgery) 비활성화 - 개발 동안에는 무효화시켰다가 나중에 활성화할 것임
        // security는 사이트 위변조 기능이 설정되어 있기 때문에 모든 POST 요청 시 
        // CSRF 토큰도 보내야함
        // 그러므로 개발하는 동안에는 disabled 해줌 (POST, PUT, DELETE 진행되지 않음)
        http.csrf((auth)->auth.disable()); // 배포 시 활성화 꼭 해줘야 함

        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

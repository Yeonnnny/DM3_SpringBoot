package net.kdigital.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import net.kdigital.board.handler.CustomFailureHandler;
import net.kdigital.board.handler.CustomLoginSuccessHandler;

@Configuration				// SecurityConfig 클래스가 설정 클래스임을 나타내는 Annotation
@EnableWebSecurity			// 웹 보안은 모두 이 클래스의 설정에 따름을 나타내는 Annotation
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomFailureHandler failureHandler;
	private final CustomLoginSuccessHandler successHandler;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 웹 요청에 대한 접근권한 설정
		http
			.authorizeHttpRequests((auth) -> auth
					.requestMatchers(
							"/"
							, "/board/boardList"
							, "/board/boardDetail"
							, "/user/confirmId"
							, "/user/join"
							, "/user/joinProc"
							, "/user/login"
							, "/predict"
							, "/reply/replyAll"
							, "/images/**"
							, "/css/**"
							, "/script/**").permitAll()	// permitAll() 인증절차없이도 접근가능한 요청
					.requestMatchers("/admin/**").hasRole("ADMIN") 
					.requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
					.anyRequest().authenticated()		// 기타 다른 경로는 인증된 사용자만 접근가능(가장 마지막에 와야함)
					);
		
		// Custom Login 설정
		http
			.formLogin((auth) -> auth
					.loginPage("/user/login")
					.failureHandler(failureHandler)		// 로그인 실패시 처리할 핸들러 등록
					.successHandler(successHandler)
					.usernameParameter("userId")
					.passwordParameter("userPwd")
					.loginProcessingUrl("/user/loginProc").permitAll()
//					.defaultSuccessUrl("/").permitAll()
			);
		
		// 로그아웃 설정
		http
			.logout((auth) -> auth
				.logoutUrl("/user/logout")		// 로그아웃 처리 URL
				.logoutSuccessUrl("/")			// 로그아웃 성공시 URL
				.invalidateHttpSession(true)	// 세션 무효화
				.deleteCookies("JSESSIONID")	// 로그아웃 성공시 제거할 쿠키명
			);
		
		// CSRF(Cross-Site Request Forgery) 비활성화
		// 시큐리티는 사이트 위변조 기능이 설정되어 있기 때문에 모든 POST 요청 시 
		// CSRF 토큰도 보내야 한다.
		// 개발하는 동안에는 disabled 해준다. (POST, PUT, DELETE 이 진행되지 않음)
		http
			.csrf((auth) -> auth.disable()
		);
		
		return http.build();
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}










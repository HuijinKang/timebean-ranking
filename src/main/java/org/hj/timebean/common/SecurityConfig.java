package org.hj.timebean.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity//시큐리티 황성화 -> 스프링 시큐리티 필터가 스프링 필터 체인에 등록이 된다. //secured 어노테이션 활성화
@EnableGlobalMethodSecurity(securedEnabled = true) //securedEnabled 이걸 설정을 해주어야 @Secured("ROLE_ADMIN")을 인식하여 적용가능해진다
public class SecurityConfig { //extends WebSecurityConfigurerAdapter -> 지원 중단

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        logger.info("Security logger name -> {}",logger.getName());

        //시큐리티 인증 비활성화
        http.csrf((auth -> auth.disable()));
        //접근제한
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/user/**").authenticated()
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")//access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll());

        http.logout(logout -> logout
                .logoutUrl("/member/logout")
                .logoutSuccessUrl("/ranking/")
                .invalidateHttpSession(true)  // 세션 무효화
                .deleteCookies("JSESSIONID")  // 쿠키 삭제
        );
        return http.build();
    }
}
//메서드 체이닝: 기존 방식은 메서드 체이닝을 통해 구성하며, .and()로 설정 구역을 연결했습니다.
//새로운 DSL: 새로운 방식에서는 HttpSecurity 객체를 메서드 인자로 받아 설정을 구성하며,
//메서드 체이닝 없이 람다 표현식으로 각 설정을 구분합니다.

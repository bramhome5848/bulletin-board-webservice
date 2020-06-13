package com.lkj.book.config.auth;

import com.lkj.book.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity  //Spring Security 기능 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().headers().frameOptions().disable()    //h2-console화면을 사용하기 위한 옵션 disable
                .and()
                .authorizeRequests()    //url별 권한 관리를 설정하는 옵션 시작점
                //권한 관리 대상을 지정하는 옵션
                //URL, HTTP 메소드별로 지정 가능
                //지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한
                //api URL들은 USER권한을 가진 사람만 가능
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()   //나머지 URL들은 인증된 사용자들에게만 허용
                .and()
                .logout()
                .logoutSuccessUrl("/")  //로그아웃 성공시 "/" 주소로 이동
                .and()
                .oauth2Login()
                .userInfoEndpoint() //로그인 성공 이후 사용자 정보를 가져올 때 설정 담당
                .userService(customOAuth2UserService); //소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록

    }


}

package crud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginSuccessHandler handler;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(LoginSuccessHandler handler, @Lazy UserDetailsService userDetailsService) {
        this.handler = handler;
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder((passwordEncoder()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure (HttpSecurity http) throws Exception {
        String loginPage = "/login";
        String logoutPage = "/logout";
        String registration = "/registration";

        http
                .formLogin()
                // указываем страницу с формой логина
                .loginPage(loginPage)
                // указываем action с формы логина
                .loginProcessingUrl(loginPage)
                //указываем логику обработки при логине
                .successHandler(handler)
                // Указываем параметры логина и пароля с формы логина
                .usernameParameter("username")
                .passwordParameter("password")
                // даем доступ к форме логина всем
                .permitAll();

        http
                // делаем страницу регистрации недоступной для авторизированных пользователей
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(loginPage).anonymous()
                //страницы аутентификаци доступна всем
                .antMatchers(registration).anonymous()
                // защищенные URL
                .antMatchers("/admin/**").access("hasAuthority('ADMIN')")
                .antMatchers("/user/**").access("hasAnyAuthority('ADMIN', 'USER')").anyRequest().authenticated();

        http
                .logout()
                // .invalidateHttpSession(true) - почитать
                // .clearAuthentication(true) - почитать
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                .logoutRequestMatcher(new AntPathRequestMatcher(logoutPage))
                // указываем URL при удачном логауте
                .logoutSuccessUrl("/login?logout")
                //выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
                .and().csrf().disable(); // защита CSRF
    }
}




package ru.pb.market.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.pb.market.services.UserService;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Dao Authentication Provider");
        http
                .csrf().disable()       //Встроенная безопасноть, запрещающая обращаться к приложению из браузера (например для работы с thymeleaf)
                .cors().disable()  // не будут отсеиваться запросы от других сервисов
                .authorizeRequests()
                //.antMatchers("/auth/**").anonymous()

                .antMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                .antMatchers("/api/v1/products/**").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/api/v1/cart/**").authenticated()
                .antMatchers("/api/v1/orders/**").authenticated()


                .antMatchers(HttpMethod.GET, "/api/v1/users/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/users/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .antMatchers(                "/api/v1/users/**").hasAnyRole("SUPERADMIN")

                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  //так как rest, сессий у нас нет
                .and()
                .headers().frameOptions().disable() //неведомым образом разрешает работу с консолью h2
                .and()
                .exceptionHandling()            //если вы пытаетесь достучаться до защещнной части
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))//возвращаем ошибку 401

                                                        ;
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}


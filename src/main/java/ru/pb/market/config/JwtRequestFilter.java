package ru.pb.market.config;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.pb.market.exceptions.AppError;
import ru.pb.market.exceptions.UserAlreadyExistException;
import ru.pb.market.utils.JwtTokenUtil;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    //    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwt);
            } catch (ExpiredJwtException e) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "{\n" +
//                        "\"timestamp\":\"2022-11-28T19:58:03.824+00:00\",\n" +
//                        "\"status\":401,\n" +
//                        "\"error\":\"token expired\",\n" +
//                        "\"path\":\"/market/api/v1/cart\"\n" +
//                        "}");

                log.error("The token is expired");
//                String error = JsonUtils.convertObjectToJson(new BookServiceError(HttpStatus.UNAUTHORIZED.value(), "Jwt is expired"));
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, error);
//                return;
//                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

//            получить актуальные роли из БД: (нагрузка на базу!)
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(token);

            UsernamePasswordAuthenticationToken details = new UsernamePasswordAuthenticationToken(username, null, jwtTokenUtil.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            SecurityContextHolder.getContext().setAuthentication(details);
        }


        filterChain.doFilter(request, response);
    }

}


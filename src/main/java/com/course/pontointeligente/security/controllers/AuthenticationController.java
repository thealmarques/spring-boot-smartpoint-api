package com.course.pontointeligente.security.controllers;

import com.course.pontointeligente.response.Response;
import com.course.pontointeligente.security.dto.JwtAuthenticationDto;
import com.course.pontointeligente.security.dto.TokenDto;
import com.course.pontointeligente.security.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private static final String TOKEN_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Generates new Jwt.
     *
     * @param authenticationDto
     * @param result
     * @return ResponseEntity<Response<TokenDto>>
     * @throws AuthenticationException
     */
    @PostMapping
    public ResponseEntity<Response<TokenDto>> generateJwt(
            @Valid @RequestBody JwtAuthenticationDto authenticationDto, BindingResult result)
            throws AuthenticationException {
        Response<TokenDto> response = new Response<TokenDto>();

        if (result.hasErrors()) {
            log.error("Error generating token: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        log.info("Generating token for email {}.", authenticationDto.getEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationDto.getEmail(), authenticationDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getEmail());
        String token = jwtTokenUtil.getToken(userDetails);
        response.setData(new TokenDto(token));

        return ResponseEntity.ok(response);
    }

    /**
     * Refreshes Jwt
     *
     * @param request
     * @return ResponseEntity<Response<TokenDto>>
     */
    @PostMapping(value = "/refresh")
    public ResponseEntity<Response<TokenDto>> gerarRefreshTokenJwt(HttpServletRequest request) {
        log.info("Refreshing Jwt...");
        Response<TokenDto> response = new Response<TokenDto>();
        Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

        if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
            token = Optional.of(token.get().substring(7));
        }

        if (!token.isPresent()) {
            response.getErrors().add("Jwt token does not exists.");
        } else if (!jwtTokenUtil.isTokenValid(token.get())) {
            response.getErrors().add("Token invalid or expired.");
        }

        if (!response.getErrors().isEmpty()) {
            return ResponseEntity.badRequest().body(response);
        }

        String refreshedToken = jwtTokenUtil.refreshToken(token.get());
        response.setData(new TokenDto(refreshedToken));
        return ResponseEntity.ok(response);
    }
}

package com.chiliclub.chilichat.config.auth;

import com.chiliclub.chilichat.common.exception.RequestForbiddenException;
import com.chiliclub.chilichat.model.ErrorResponse;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.chiliclub.chilichat.common.utils.StringUtils.handleMessage;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RequestForbiddenException e) {
            log.error("RequestForbiddenException in Filter", e);
            setErrorResponse(response, e);
        }
    }

    private void setErrorResponse(HttpServletResponse response, RequestForbiddenException e) throws IOException {

        ErrorResponse errorResponse = new ErrorResponse(
                e.getErrorCode().getName(),
                handleMessage(e.getMessage(), e.getErrorCode())
        );

        objectMapper.getFactory().configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true); // 한글 깨지는 문자 해결

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}

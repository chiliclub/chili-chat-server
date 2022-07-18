package com.chiliclub.chilichat.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        /**
         * TODO: 세부 설정
         */
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); // 프론트 서버 or 프록시 호스트 확정되면 수정
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/user/**", config);

        return new CorsFilter(source);
    }
}

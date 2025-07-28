package askmyapi.amaserver.config;

import askmyapi.amaserver.auth.adapter.in.oauth2.Oauth2Adapter;
import askmyapi.amaserver.auth.adapter.in.security.AuthenticateSuccessAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

@Configuration
@Order(1)
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final Oauth2Adapter oauth2Adapter;
    private final CustomCorsConfigurationSource customCorsConfigurationSource;
    private final AuthenticateSuccessAdapter authenticateSuccessAdapter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(corsCustomizer -> corsCustomizer
                        .configurationSource(customCorsConfigurationSource)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/api/v1/auth/**",
                                "/oauth2/**",
                                "/api/v1/auth/refresh"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // OAuth 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(endpointConfig -> endpointConfig.userService(oauth2Adapter))
                        .successHandler(authenticateSuccessAdapter)
                );

        return http.build();
    }
}

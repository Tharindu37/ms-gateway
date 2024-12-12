package security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity){
        serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable)
                /* .cors(httpSecurityCorsConfigurer ->
                         httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))*/
                .authorizeExchange(authorize ->
                        authorize
                                .pathMatchers("user-service/api/v1/**").permitAll()
                                .pathMatchers("product-service/api/v1/**").permitAll()
                                .pathMatchers("order-payment-service/api/v1/**").permitAll()
                                .pathMatchers("/eureka/**")
                                .permitAll()
                                .anyExchange()
                                .authenticated())
                /*  .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(Customizer.withDefaults()));*/
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);


        return  serverHttpSecurity.build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("*")); // Specify your allowed origin
       /* corsConfig.setAllowedOrigins(List.of("https://www.developersstack.com","https://developersstack.com",
                "https://admin.developersstack.com"));*/
        //corsConfig.setAllowedOrigins(List.of("*"));
        corsConfig.setAllowedMethods(List.of("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.addAllowedHeader("*");
        corsConfig.setAllowCredentials(false); // Allow credentials

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return NimbusJwtDecoder.withIssuerLocation(issuerUri).build();
//    }
}

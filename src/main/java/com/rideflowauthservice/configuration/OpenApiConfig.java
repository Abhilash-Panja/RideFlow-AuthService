package com.rideflowauthservice.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "RideFlow Authentication API",
        version = "0.0.1-SNAPSHOT",
        description = "Passenger signup, cookie JWT login and validation. The existing JWT filter currently runs only on login, blocking first login without a cookie and skipping validation. Swagger documents this behavior; it does not repair authentication."
))
@SecurityScheme(
        name = "jwtCookie",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.COOKIE,
        paramName = "Jwt_Token",
        description = "Existing cookie transport. No Authorization Bearer support. Current filter blocks first login and skips /validate; see the authentication diagnostics in the guide."
)
public class OpenApiConfig {
}

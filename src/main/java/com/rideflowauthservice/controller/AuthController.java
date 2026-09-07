package com.rideflowauthservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.rideflowauthservice.dto.auth.PassengerLoginRequest;
import com.rideflowauthservice.dto.auth.PassengerLoginResponse;
import com.rideflowauthservice.dto.passenger.PassengerResponseDTO;
import com.rideflowauthservice.dto.auth.PassengerSignupRequest;
import com.rideflowauthservice.security.PassengerPrinciple;
import com.rideflowauthservice.service.AuthService;
import com.rideflowauthservice.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Passenger authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${cookie.expiration-ms}")
    private long cookieExpiry;

    @Operation(operationId = "rideFlowAuthService_signUp", summary = "Register a passenger account",
            description = "Creates the account record needed by credential lookup. It does not log the passenger in. AuthController.signUp -> AuthService.signUpPassenger -> PasswordEncoder.encode -> PassengerRepository.save -> PassengerMapper.toResponseDTO. Request name becomes passengerName; only id and passengerName are returned. No @Valid. No duplicate-email or permitted-role check. Success depends on the resolved 0.0.2-SNAPSHOT entity and database schema; the current published source is 0.0.5-SNAPSHOT. Audit setup is also a known blocker.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "JSON body is required by Spring MVC. Field descriptions distinguish service requirements from active validation.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.rideflowauthservice.dto.auth.PassengerSignupRequest.class),
                    examples = @ExampleObject(value = "{\n  \"email\": \"anita.rideflow@example.com\",\n  \"password\": \"RideFlowDemo!2026\",\n  \"phoneNumber\": \"9000000001\",\n  \"name\": \"Anita Sharma\",\n  \"role\": \"PASSENGER\"\n}")))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Register a passenger account completed on the controller success branch.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.rideflowauthservice.dto.passenger.PassengerResponseDTO.class), examples = @ExampleObject(value = "{\"id\":101,\"passengerName\":\"Anita Sharma\"}"))),
            @ApiResponse(responseCode = "400", description = "Unreadable JSON or enum binding error, if not masked by secured error dispatch.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Encoding, persistence, or other unhandled failure; no stable application error body is guaranteed.",
                    content = @Content)
    })
    @PostMapping("/signUp")
    public ResponseEntity<PassengerResponseDTO> signUp(@RequestBody PassengerSignupRequest passengerSignupRequestDto){
        PassengerResponseDTO passengerResponseDTO=authService.signUpPassenger(passengerSignupRequestDto);
       return new ResponseEntity<>(passengerResponseDTO,HttpStatus.CREATED);
    }
    @Operation(operationId = "rideFlowAuthService_login", summary = "Attempt passenger login",
            description = "Exercises password authentication and shows exactly why the current first-login flow is blocked. JwtAuthenticationFilter currently runs only on /login. With no cookie it returns 401 before the body is processed. If reached, AuthController.login -> AuthenticationManager -> CustomUserDetailsService.loadUserByUsername -> PassengerRepository.findPassengerByEmail -> BCrypt authentication -> JwtService.generateToken; controller sets Set-Cookie and returns PassengerLoginResponse. The 200 example is the controller success branch, not a working first-login promise. Cookie maxAge receives a millisecond-named value as seconds. JWT lifetime is 3,600,000 ms, while configured cookie lifetime becomes 3,600,000 seconds.",
            security = @SecurityRequirement(name = "jwtCookie"))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            description = "JSON body is required by Spring MVC. Field descriptions distinguish service requirements from active validation.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.rideflowauthservice.dto.auth.PassengerLoginRequest.class),
                    examples = @ExampleObject(value = "{\n  \"email\": \"anita.rideflow@example.com\",\n  \"password\": \"RideFlowDemo!2026\"\n}")))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Expected initial test: 401, empty body. Conditional controller success: 200 with the JSON below and Set-Cookie.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.rideflowauthservice.dto.auth.PassengerLoginResponse.class), examples = @ExampleObject(value = "{\"token\":\"<JWT issued by this application>\"}")),
                    headers = @Header(name = "Set-Cookie", description = "Jwt_Token cookie; Path=/; HttpOnly. Current maxAge uses the configured millisecond-named value as seconds.", schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "JwtAuthenticationFilter returns an empty response when Jwt_Token cookie is absent.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authentication/security rejection; custom JSON security handlers are not wired into the chain.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unhandled JWT parsing or other failure; filter exceptions have no stable JSON contract.",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<PassengerLoginResponse>login(@RequestBody PassengerLoginRequest passengerLoginRequest, HttpServletResponse servletResponse){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(passengerLoginRequest.getEmail(), passengerLoginRequest.getPassword())
        );

        PassengerPrinciple passengerPrinciple = (PassengerPrinciple) authentication.getPrincipal();
        assert passengerPrinciple != null;
        String token = jwtService.generateToken(passengerPrinciple);
        /*
        * Creating the Cookie and sending jwt_token as cookie in header
        * */
        ResponseCookie responseCookie= ResponseCookie.from("Jwt_Token",token)
                        .secure(false)
                        .httpOnly(true)
                        .path("/")
                        .maxAge(cookieExpiry)
                        .build();
        servletResponse.setHeader(HttpHeaders.SET_COOKIE,responseCookie.toString());
        return ResponseEntity.status(HttpStatus.OK).body(new PassengerLoginResponse(token));
    }

    @Operation(operationId = "rideFlowAuthService_validate", summary = "Check the protected validation route",
            description = "Separates possession of a JWT cookie from successful Spring Security authentication. SecurityFilterChain requires authenticated(); JwtAuthenticationFilter.shouldNotFilter skips /validate. Therefore a Jwt_Token cookie alone does not authenticate this request. If the controller is reached, it prints cookie values and returns the string Success; it calls no service or repository. Bearer headers are ignored by the custom filter. The two-argument UsernamePasswordAuthenticationToken used by that filter is unauthenticated, and token subject email is compared with passenger name. These are separate existing defects.",
            security = @SecurityRequirement(name = "jwtCookie"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Normal source-predicted result: security rejection, usually 403. The conditional controller success body is plain text Success.",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "Success"))),
            @ApiResponse(responseCode = "403", description = "Default security rejection is expected without another authenticated context; the JWT filter skips this path.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "If invoked with an authenticated context but no cookies, servletRequest.getCookies() can be null.",
                    content = @Content)
    })
    @GetMapping("/validate")
    public ResponseEntity<?>validate( HttpServletRequest servletRequest){
        for(Cookie cookie: servletRequest.getCookies()){
            System.out.println(cookie.getValue());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}

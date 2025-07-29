package askmyapi.amaserver.auth.adapter.in.web;

import askmyapi.amaserver.auth.application.port.in.RefreshTokenCommand;
import askmyapi.amaserver.auth.application.port.in.RefreshTokenUseCase;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthSpecs {

    private final RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            HttpServletResponse response,
            @RequestBody RefreshTokenRequest request) {
        var command = new RefreshTokenCommand(request.memberId(), request.refreshToken());
        var result = refreshTokenUseCase.refresh(command);

        return ResponseEntity.ok(new RefreshTokenResponse(
                result.accessToken(),
                result.refreshToken()
        ));
    }
}

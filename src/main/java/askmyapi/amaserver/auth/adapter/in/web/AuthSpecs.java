package askmyapi.amaserver.auth.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthSpecs {

    @Operation(
            summary = "리프레시 토큰 발급",
            description = "리프레시 토큰으로 액세스 토큰을 재발급합니다."
    )
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            HttpServletResponse response,
            @RequestBody RefreshTokenRequest request);
}

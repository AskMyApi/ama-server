package askmyapi.amaserver.auth.adapter.in.web;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthSpecs {

    public ResponseEntity<RefreshTokenResponse> refreshToken(
            HttpServletResponse response,
            @RequestBody RefreshTokenRequest request);
}

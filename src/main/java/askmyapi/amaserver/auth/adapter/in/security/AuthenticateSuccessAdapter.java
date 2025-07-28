package askmyapi.amaserver.auth.adapter.in.security;

import askmyapi.amaserver.auth.adapter.in.oauth2.model.UserPrincipal;
import askmyapi.amaserver.auth.application.AuthService;
import askmyapi.amaserver.auth.application.port.in.IssueTokenCommand;
import askmyapi.amaserver.auth.application.port.in.IssueTokenUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticateSuccessAdapter implements AuthenticationSuccessHandler {

    private final IssueTokenUseCase issueTokenUseCase;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var principal = (UserPrincipal) authentication.getPrincipal();
        var command = new IssueTokenCommand(principal.getName());
        var result = issueTokenUseCase.issue(command);

        // 토큰 정보를 JSON 형태로 응답
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        var writer = response.getWriter();
        writer.write(String.format(
            "{\"accessToken\":\"%s\",\"accessTokenExpiresIn\":%d,\"refreshToken\":\"%s\",\"refreshTokenExpiresIn\":%d}",
            result.accessToken(),
            result.accessTokenExpiresIn(),
            result.refreshToken(),
            result.refreshTokenExpiresIn()
        ));
        writer.flush();
    }
}

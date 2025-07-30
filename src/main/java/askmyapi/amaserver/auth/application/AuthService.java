package askmyapi.amaserver.auth.application;

import askmyapi.amaserver.auth.application.port.in.*;
import askmyapi.amaserver.auth.application.port.out.*;
import askmyapi.amaserver.auth.domain.OauthAuthInfo;
import askmyapi.amaserver.auth.domain.TokenProvider;
import askmyapi.amaserver.auth.domain.vo.OauthAuthenticate;
import askmyapi.amaserver.auth.domain.vo.OauthAuthenticate.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService implements LoadOrCreateOauthUseCase, IssueTokenUseCase, RefreshTokenUseCase  {

    private final MemberCommandPort memberCommandPort;
    private final AuthCommandPort authCommandPort;
    private final AuthQueryPort authQueryPort;
    private final TokenProvider tokenProvider;

    /**
     * Oauth 인증 정보를 로드하거나 생성합니다.
     */
    @Override
    public AuthResult.Load loadOrCreate(LoadOrCreateOauthCommand command) {
        var query = new OauthAuthInfoQuery(
                OauthProvider.fromValueIgnoreCase(
                        command.provider()
                ),
                command.socialId());

        return authQueryPort.findOauthAuthInfo(query)
                .map(authInfo -> new AuthResult.Load(authInfo.getAuthId().getValue(), authInfo.getMemberId()))
                .orElseGet(() ->{
                    var memberCmd = new CreateMemberCommand(command.username(), command.profileImageUrl());
                    var authenticate = OauthAuthenticate.create(command.provider(), command.socialId());

                    var memberId = memberCommandPort.create(memberCmd).memberId();
                    var authInfo = authCommandPort.createOauthAuthInfo(OauthAuthInfo.create(authenticate, memberId));
                    return new AuthResult.Load(authInfo.getAuthId().getValue(), authInfo.getMemberId());
                });
    }

    /**
     * 토큰을 발급합니다.
     */
    public AuthResult.IssueToken issue(IssueTokenCommand command) {
        var accessToken = tokenProvider.issueAccessToken(command.memberId());
        var refreshToken = tokenProvider.issueRefreshToken(command.memberId());
        authCommandPort.saveToken(new SaveRefreshTokenCommand(
                command.memberId(),
                refreshToken,
                tokenProvider.extractExpiration(refreshToken)
        ));
        return new AuthResult.IssueToken(
                accessToken,
                tokenProvider.extractExpiration(accessToken),
                refreshToken,
                tokenProvider.extractExpiration(refreshToken));
    }

    /**
     * 리프레시 토큰을 사용하여 새로운 액세스 토큰과 리프레시 토큰을 발급합니다.
     */
    public AuthResult.IssueToken refresh(RefreshTokenCommand command) {
        String token = authQueryPort.findRefreshToken(new FindRefreshTokenQuery(command.memberId()))
                .orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));
        if(!token.equals(command.refreshToken())) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        var accessToken = tokenProvider.issueAccessToken(command.memberId());
        var refreshToken = tokenProvider.issueRefreshToken(command.memberId());
        authCommandPort.saveToken(new SaveRefreshTokenCommand(
                command.memberId(),
                refreshToken,
                tokenProvider.extractExpiration(refreshToken)
        ));
        return new AuthResult.IssueToken(
                accessToken,
                tokenProvider.extractExpiration(accessToken),
                refreshToken,
                tokenProvider.extractExpiration(refreshToken));
    }
}

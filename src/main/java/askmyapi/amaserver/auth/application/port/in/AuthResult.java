package askmyapi.amaserver.auth.application.port.in;

import askmyapi.amaserver.auth.domain.AuthInfo;

public record AuthResult() {

    public record Id(
            String memberId
    ) {
        public static Id of(AuthInfo authInfo) {
            if (authInfo == null || authInfo.getMemberId() == null) {
                throw new IllegalArgumentException("인증 정보가 유효하지 않습니다.");
            }
            return new Id(authInfo.getMemberId());
        }
    }
}

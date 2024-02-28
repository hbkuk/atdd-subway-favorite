package nextstep.core.auth.application;

import nextstep.common.annotation.ApplicationTest;
import nextstep.core.auth.application.dto.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("토큰 서비스 레이어 테스트")
@ApplicationTest
public class TokenServiceTest {

    TokenService tokenService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Mock
    GithubClient githubClient;

    @BeforeEach
    void 사전_토큰_서비스_생성() {
        tokenService = new TokenService(jwtTokenProvider, githubClient);
    }

    @Nested
    class 토큰_발급 {

        final String value = "Temp Value";

        @Nested
        class 성공 {
            /**
             * When  토큰 발급을 요청할 경우
             * Then  정상적으로 토큰이 발급된다.
             */
            @Test
            void 토큰_발급_요청() {
                // when
                TokenResponse tokenResponse = tokenService.createToken(value);

                // then
                assertThat(tokenResponse.getAccessToken()).isNotBlank();
            }
        }
    }
}

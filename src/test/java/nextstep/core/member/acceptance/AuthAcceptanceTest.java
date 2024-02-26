package nextstep.core.member.acceptance;

import nextstep.common.annotation.AcceptanceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static nextstep.core.member.fixture.GithubMemberFixture.*;
import static nextstep.core.member.step.AuthSteps.성공하는_토큰_발급_요청;
import static nextstep.core.member.step.AuthSteps.실패하는_토큰_발급_요청;
import static nextstep.core.member.step.GithubSteps.*;
import static nextstep.core.member.step.MemberSteps.회원_생성_요청;

@DisplayName("인증 인수 테스트")
@AcceptanceTest
class AuthAcceptanceTest {

    @Nested
    class 토큰_발급 {
        @Nested
        class 성공 {
            /**
             * Given 회원을 생선한다.
             * When  생성된 회원 정보로 토큰 발급을 요청할 경우
             * Then  정상적으로 토큰이 발급된다.
             */
            @Test
            void 저장된_회원으로_토큰_발급_요청() {
                // given
                var 이메일 = "admin@email.com";
                var 비밀번호 = "password";

                회원_생성_요청(이메일, 비밀번호, 20);

                // when
                var 발급된_토큰 = 성공하는_토큰_발급_요청(이메일, 비밀번호);

                // then
                토큰_확인(발급된_토큰);
            }
        }

        @Nested
        class 실패 {
            /**
             * Given 회원을 생성한다.
             * When  생성된 회원의 비밀번호가 아닌 회원 정보로 토큰 발급을 요청할 경우
             * Then  토큰이 발급되지 않는다.
             */
            @Test
            void 비밀번호가_다른_회원의_토큰_발급_요청() {
                // given
                var 이메일 = "admin@email.com";
                var 비밀번호 = "password";
                var 변경된_비밀번호 = "changed password";

                회원_생성_요청(이메일, 비밀번호, 20);

                // then
                실패하는_토큰_발급_요청(이메일, 변경된_비밀번호);
            }

            /**
             * When  회원 저장소에 존재하지 않는 회원 정보로 토큰 발급을 요청할 경우
             * Then  토큰이 발급되지 않는다.
             */
            @Test
            void 존재하지_않는_회원의_토큰_발급_요청() {
                // given
                var 이메일 = "admin@email.com";
                var 비밀번호 = "password";

                // when, then
                실패하는_토큰_발급_요청(이메일, 비밀번호);
            }
        }
    }

    @Nested
    class 깃허브_로그인 {
        @Nested
        class 성공 {
            @Nested
            class 이미_깃허브로_가입된_회원 {

                @BeforeEach
                void 사전_깃허브_회원가입() {
                    깃허브_회원가입(KIM, HWANG, JUNG, LEE);
                }

                /**
                 * Given 깃허브를 통해 회원가입된 회원을 생성한다.
                 * When  깃허브로 로그인 요청을 경우
                 * Then  깃허브로 로그인 된다.
                 */
                @Test
                void 깃허브로_회원가입된_회원의_로그인_요청() {
                    // when
                    var 토큰_목록 = 깃허브_로그인_요청(KIM, HWANG, JUNG, LEE);

                    // then
                    토큰_확인(토큰_목록);
                }
            }

            @Nested
            class 이미_가입된_회원 {

                @BeforeEach
                void 사전_깃허브_회원가입() {
                    회원_생성_요청(KIM.getEmail(), "password", 10);
                    회원_생성_요청(HWANG.getEmail(), "password", 10);
                    회원_생성_요청(JUNG.getEmail(), "password", 10);
                    회원_생성_요청(LEE.getEmail(), "password", 10);
                }

                /**
                 * Given 깃허브가 아닌 이미 회원가입된 회원을 생성한다.
                 * When  깃허브로 로그인 요청을 경우
                 * Then  깃허브로 로그인 된다.
                 */
                @Test
                void 깃허브로_회원가입된_회원의_로그인_요청() {
                    // when
                    var 토큰_목록 = 깃허브_로그인_요청(KIM, HWANG, JUNG, LEE);

                    // then
                    토큰_확인(토큰_목록);
                }
            }

            @Nested
            class 가입되지_않은_회원 {
                /**
                 * When  깃허브로 로그인 요청을 경우
                 * Then  회원가입 후 깃허브로 로그인 된다.
                 */
                @Test
                void 깃허브로_회원가입_되지_않은_회원의_로그인_요청() {
                    // when
                    var 토큰_목록 = 깃허브_로그인_요청(KIM, HWANG, JUNG, LEE);

                    // given
                    토큰_확인(토큰_목록);
                }
            }
        }
    }
}
package nextstep.core.member.application;

import java.util.Objects;

public class GithubProfileResponse {
    private final String email;

    public GithubProfileResponse(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GithubProfileResponse that = (GithubProfileResponse) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}

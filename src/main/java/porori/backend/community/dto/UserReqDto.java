package porori.backend.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserReqDto {
    @Getter
    @Builder
    public static class AccessTokenReq{
        private String accessToken;

    }
}

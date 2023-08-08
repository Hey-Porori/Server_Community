package porori.backend.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class UserReqDto {
    @Getter
    @Builder
    public static class AccessTokenReq{
        private String accessToken;

    }

    @Getter
    @Builder
    public static class UserIdListReq{
        private List<Long> userIdList;
    }
}

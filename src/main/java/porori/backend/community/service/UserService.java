package porori.backend.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import porori.backend.community.config.BaseResponse;
import porori.backend.community.config.exception.user.TokenException;
import porori.backend.community.config.exception.user.UserBadGateWayException;
import porori.backend.community.dto.UserReqDto.UserIdListReq;
import porori.backend.community.dto.UserResDto.UserInfo;
import porori.backend.community.dto.UserResDto.CommunityUserInfo;
import porori.backend.community.dto.UserReqDto.AccessTokenReq;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final WebClient webClient;
    private static final String LOG_FORMAT = "Method : {}";

    public Long getUserId(String token) {
        String[] splitToken = token.split(" ");

        AccessTokenReq accessTokenReq = AccessTokenReq.builder()
                .accessToken(splitToken[1])
                .build();

        return sendTokenMeRequest(token, accessTokenReq).getUserId();
    }

    private UserInfo sendTokenMeRequest(String token, AccessTokenReq accessTokenReq) {
            return webClient.post()
                    .uri("/token/me")
                    .header("Authorization", token)
                    .bodyValue(accessTokenReq)
                    .retrieve()
                    .bodyToMono((new ParameterizedTypeReference<BaseResponse<UserInfo>>() {
                    }))
                    .map(response->{
                        if (response.getStatusCode() == 200) {
                            return response.getData();
                        } else {
                            throw new UserBadGateWayException();
                        }
                    })
                    .block();
    }

    //(테스트) 토큰 유효성 검증
    public void sendTestJwtRequest(String token) {
        try {
            webClient.post()
                    .uri("/test/jwt")
                    .header("Authorization", token)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (Exception e){
            log.warn(LOG_FORMAT, "sendTestJwtRequest");
            throw new TokenException();
        }
    }

    public List<CommunityUserInfo> sendCommunitiesInfoRequest(String token, UserIdListReq userIdList) {
        return webClient.post()
                .uri("/communities/info")
                .header("Authorization", token)
                .bodyValue(userIdList)
                .retrieve()
                .bodyToMono((new ParameterizedTypeReference<BaseResponse<Map<String, List<CommunityUserInfo>>>>() {
                }))
                .map(response->{
                    if (response.getStatusCode() == 200) {
                        log.warn("communityUserInfoBlocks : {}", "완");
                        return response.getData().get("communityUserInfoBlocks");
                    } else {
                        throw new UserBadGateWayException();
                    }
                })
                .block();
    }

}

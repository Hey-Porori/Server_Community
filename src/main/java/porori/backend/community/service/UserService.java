package porori.backend.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import porori.backend.community.config.BaseResponse;
import porori.backend.community.config.exception.user.TokenException;
import porori.backend.community.config.exception.user.UserBadGateWayException;
import porori.backend.community.dto.UserResDto.UserInfo;
import porori.backend.community.dto.UserResDto.CommunityUserInfo;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final WebClient webClient;
    private static final String LOG_FORMAT = "Method : {}";

    public Long getUserId(String token) {
        return sendTokenMeRequest(token).getUserId();
    }

    private UserInfo sendTokenMeRequest(String token) {
        return webClient.get()
                .uri("/token/me")
                .header("Authorization", token)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new TokenException()))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new UserBadGateWayException()))
                .bodyToMono((new ParameterizedTypeReference<BaseResponse<UserInfo>>() {
                }))
                .map(BaseResponse::getData)
                .block();
    }

    //(테스트) 토큰 유효성 검증
    public void sendTestJwtRequest(String token) {
        try {
            webClient.post()
                    .uri("/test/jwt")
                    .header("Authorization", token)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new TokenException()))
                    .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new UserBadGateWayException()))
                    .toBodilessEntity()
                    .block();
        } catch (Exception e) {
            log.warn(LOG_FORMAT, "sendTestJwtRequest");
            throw new TokenException();
        }
    }

    public List<CommunityUserInfo> sendCommunitiesInfoRequest(String token, List<Long> userIdList) {
        String param = userIdList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        log.warn(LOG_FORMAT, param);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/communities/info").queryParam("userIdList", param).build())
                .header("Authorization", token)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new TokenException()))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new UserBadGateWayException()))
                .bodyToMono((new ParameterizedTypeReference<BaseResponse<Map<String, List<CommunityUserInfo>>>>() {
                }))
                .map(response -> response.getData().get("communityUserInfoBlocks"))
                .block();
    }

}

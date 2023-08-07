package porori.backend.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import porori.backend.community.config.BaseResponse;
import porori.backend.community.config.exception.user.TokenException;
import porori.backend.community.config.exception.user.UserBadGateWayException;
import porori.backend.community.domain.user.UserInfo;
import porori.backend.community.dto.UserReqDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final WebClient webClient;
    private static final String LOG_FORMAT = "Method : {}";

    public Long getUserId(String token) {
        String[] splitToken = token.split(" ");

        UserReqDto.AccessTokenReq accessTokenReq = UserReqDto.AccessTokenReq.builder()
                .accessToken(splitToken[1])
                .build();

        return sendTokenMeRequest(token, accessTokenReq).getUserId();
    }

    private UserInfo sendTokenMeRequest(String token, UserReqDto.AccessTokenReq accessTokenReq) {
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
    public void tokenValidationRequest(String token) {
        try {
            webClient.post()
                    .uri("/test/jwt")
                    .header("Authorization", token)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (Exception e){
            log.warn(LOG_FORMAT, "tokenValidationRequest");
            throw new TokenException();
        }
    }

}

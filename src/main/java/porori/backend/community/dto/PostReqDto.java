package porori.backend.community.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class PostReqDto {
    @Getter
    public static class PostContentReq {
        private String title;
        private String content;
        private Double latitude;
        private Double longitude;
        private List<String> imageNameList;
        private List<String> tagList;
    }
}

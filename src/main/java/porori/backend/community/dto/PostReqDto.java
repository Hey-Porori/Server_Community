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
        private String location;
        private Double latitude;
        private Double longitude;
        private List<String> imageNameList;
        private List<String> tagList;
    }

    @Getter
    public static class CurrentLocationReq{
        private Double latitude;
        private Double longitude;
    }

    @Getter
    public static class PostIdListReq{
        private List<Long> postIdList;
    }

    @Getter
    public static class EditPostContentReq {
        private String title;
        private String content;
        private List<String> imageNameList;
        private List<String> tagList;
    }

}

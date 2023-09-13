package porori.backend.community.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import porori.backend.community.domain.core.BaseTimeEntity;
import porori.backend.community.dto.PostReqDto.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
@DynamicInsert
@DynamicUpdate
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String location;  //위, 경도 좌표의 주소(ex. 서울특별시 중구 필동로 1길 or 서울특별시 중구 필동 등 도로명 주소나 지번 주소 중 하나로 통일)

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @ColumnDefault("'ACTIVE'")
    private String status;

    @OneToMany(mappedBy = "postId", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PostAttach> imageList = new ArrayList<>();

    @OneToMany(mappedBy = "postId", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PostTag> tagList = new ArrayList<>();

    @Builder
    public Post(Long userId, String title, String content, String location, Double latitude, Double longitude, String status, List<PostAttach> imageList, List<PostTag> tagList) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.imageList = imageList;
        this.tagList = tagList;
    }

    public void changeStatus(String newStatus) {
        this.status = newStatus;
    }

    public void editPost(EditPostContentReq editPostContent){
        this.title = editPostContent.getTitle();
        this.content = editPostContent.getContent();
    }

    public void deletePost(){
        this.status = "DELETE";
    }
}

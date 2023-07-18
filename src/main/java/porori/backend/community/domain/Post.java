package porori.backend.community.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import porori.backend.community.domain.core.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
@DynamicInsert
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
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @ColumnDefault("'ACTIVE'")
    private String status;

    @OneToMany(mappedBy = "attachId", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PostAttach> imageList = new ArrayList<>();

    @OneToMany(mappedBy = "postTagId", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PostTag> tagList = new ArrayList<>();

    @Builder
    public Post(Long userId, String title, String content, Double latitude, Double longitude, String status, List<PostAttach> imageList, List<PostTag> tagList) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.imageList = imageList;
        this.tagList = tagList;
    }

    public void changeStatus(String newStatus) {
        this.status = newStatus;
    }
}

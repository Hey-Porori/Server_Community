package porori.backend.community.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import porori.backend.community.domain.core.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_attach")
@DynamicInsert
public class PostAttach extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attach_id")
    private Long attachId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id")
    private Post postId;

    @Column(name = "image_name", nullable = false, columnDefinition = "TEXT")
    private String imageName;

    @Builder
    public PostAttach(Post postId, String imageName) {
        this.postId = postId;
        this.imageName = imageName;
    }
}

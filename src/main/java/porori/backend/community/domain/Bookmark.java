package porori.backend.community.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import porori.backend.community.domain.core.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bookmark")
@DynamicInsert
public class Bookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long bookmarkId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id")
    private Post postId;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Builder
    public Bookmark(Post postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }
}

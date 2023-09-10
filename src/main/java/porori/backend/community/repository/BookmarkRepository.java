package porori.backend.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import porori.backend.community.domain.Bookmark;
import porori.backend.community.domain.Post;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByPostIdAndUserId(Post post, Long userId);

    Long countByPostId(Post postId);

    @Query("SELECT b.postId FROM Bookmark b WHERE b.userId = :userId order by b.bookmarkId desc")
    Page<Post> findAllByUserIdOrderByBookmarkIdDesc(Long userId, Pageable pageable);
}

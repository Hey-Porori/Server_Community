package porori.backend.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import porori.backend.community.domain.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostIdAndStatus(Long postId, String status);

    @EntityGraph(attributePaths = {"imageList"})
    @Query("SELECT p FROM Post p WHERE p.postId = :postId and p.status = 'ACTIVE'")
    Optional<Post> findByPostIdAndStatusWithImageList(@Param("postId") Long postId);

    @Query(value = "select * from post p " +
            "where (6371 * acos(cos(radians(:sLatitude)) * cos(radians(p.latitude)) " +
            "* cos(radians(p.longitude) - radians(:sLongitude)) " +
            "+ sin(radians(:sLatitude)) * sin(radians(p.latitude)))) <=2 and p.created_at>=:yesterday and p.status = 'ACTIVE' order by p.post_id limit 10", nativeQuery = true)
    List<Post> findNearByPosts(@Param("sLatitude")Double sLatitude, @Param("sLongitude")Double sLongitude, @Param("yesterday") LocalDateTime yesterday);

    Page<Post> findAllByUserIdAndStatusOrderByPostIdDesc(Long userId, String status, Pageable pageable);

}

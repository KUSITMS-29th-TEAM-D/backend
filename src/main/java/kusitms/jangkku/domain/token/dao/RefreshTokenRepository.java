package kusitms.jangkku.domain.token.dao;

import jakarta.transaction.Transactional;
import kusitms.jangkku.domain.token.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    @Query("SELECT u FROM RefreshToken u WHERE u.userId = :userId")
    RefreshToken findByUserId(UUID userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM RefreshToken u WHERE u.userId = :userId")
    void deleteByUserId(UUID userId);
}
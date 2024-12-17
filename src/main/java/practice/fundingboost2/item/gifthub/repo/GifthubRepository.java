package practice.fundingboost2.item.gifthub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practice.fundingboost2.item.gifthub.repo.entity.Gifthub;
import practice.fundingboost2.item.gifthub.repo.entity.GifthubId;

public interface GifthubRepository extends JpaRepository<Gifthub, GifthubId> {

    @Modifying(clearAutomatically = true)
    @Query("update Gifthub g set g.quantity = g.quantity + 1 where g.id=:id")
    void updateQuantityById(@Param("id") GifthubId id);
}

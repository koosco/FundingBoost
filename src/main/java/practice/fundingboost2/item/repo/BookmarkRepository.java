package practice.fundingboost2.item.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.fundingboost2.item.repo.entity.Bookmark;
import practice.fundingboost2.item.repo.entity.BookmarkId;

public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId> {

}

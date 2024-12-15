package practice.fundingboost2.item.repo.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.fundingboost2.member.repo.entity.Member;

@Entity
@Getter
@NoArgsConstructor
public class Bookmark {

    @EmbeddedId
    private BookmarkId id;

    public Bookmark(Long memberId, Long itemId) {
        this.id = new BookmarkId(memberId, itemId);
    }
}

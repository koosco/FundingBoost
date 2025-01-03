package practice.fundingboost2.item.item.repo.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BookmarkTest {

    @Test
    void givenMemberIdAndItemId_whenCreateBookmark_thenBookmarkIdEqualsToGivenId() {
        // given
        Long memberId = 1L;
        Long itemId = 1L;
        // when
        Bookmark bookmark = new Bookmark(memberId, itemId);
        // then
        assertThat(bookmark.getId()).isEqualTo(new BookmarkId(memberId, itemId));
    }
}
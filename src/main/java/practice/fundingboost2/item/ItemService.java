package practice.fundingboost2.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.item.repo.BookmarkRepository;
import practice.fundingboost2.item.repo.ItemRepository;
import practice.fundingboost2.item.repo.entity.Bookmark;
import practice.fundingboost2.item.repo.entity.BookmarkId;
import practice.fundingboost2.item.repo.entity.Item;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.repo.entity.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final BookmarkRepository bookmarkRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public Item findItem(Long id) {
        return itemRepository.findById(id)
            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ITEM));
    }

    public CommonSuccessDto likeItem(Long memberId, Long itemId) {
        Item item = findItem(itemId);
        Bookmark bookmark = new Bookmark(memberId, itemId);

        if (checkBookmark(bookmark.getId())) {
            bookmarkRepository.delete(bookmark);
            item.unmark();

            return CommonSuccessDto.fromEntity(true);
        }

        item.mark();
        bookmarkRepository.save(bookmark);

        return CommonSuccessDto.fromEntity(true);
    }

    private boolean checkBookmark(BookmarkId bookmarkId) {
        return bookmarkRepository.existsById(bookmarkId);
    }
}

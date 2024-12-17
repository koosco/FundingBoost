package practice.fundingboost2.item.item.app;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.item.item.repo.ItemRepository;
import practice.fundingboost2.item.item.repo.entity.Bookmark;
import practice.fundingboost2.item.item.repo.entity.BookmarkId;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.jpa.BookmarkRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final BookmarkRepository bookmarkRepository;
    private final ItemRepository itemRepository;

    public List<Item> findItemIn(List<Long> ids) {
        return itemRepository.findByIdIn(ids);
    }

    public Item findItem(Long id) {
        return itemRepository.findById(id);
    }

    public Item findItemWithOptions(Long itemId) {
        return itemRepository.findItemByIdWithOptions(itemId);
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

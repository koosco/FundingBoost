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

    public Boolean existsById(Long itemId, Long optionId) {
        return itemRepository.existsById(itemId, optionId);
    }

    public List<Item> findItemIn(List<Long> ids) {
        return itemRepository.findByIdIn(ids);
    }

    public Item findItem(Long id) {
        return itemRepository.findById(id);
    }

    public Item concurrencyFindItem(Long id) {
        return itemRepository.concurrencyFindItem(id);
    }

    public CommonSuccessDto likeItem(Long memberId, Long itemId) {
        Item item = concurrencyFindItem(itemId);
        BookmarkId bookmarkId = new BookmarkId(memberId, itemId);

        if (checkBookmark(bookmarkId)) {
            return dislikeItem(bookmarkId, item);
        }
        Bookmark bookmark = item.mark(bookmarkId);
        bookmarkRepository.save(bookmark);

        return CommonSuccessDto.fromEntity(true);
    }

    private CommonSuccessDto dislikeItem(BookmarkId bookmarkId, Item item) {
        bookmarkRepository.deleteById(bookmarkId);
        item.unmark();

        return CommonSuccessDto.fromEntity(true);
    }

    private boolean checkBookmark(BookmarkId bookmarkId) {
        return bookmarkRepository.existsById(bookmarkId);
    }
}

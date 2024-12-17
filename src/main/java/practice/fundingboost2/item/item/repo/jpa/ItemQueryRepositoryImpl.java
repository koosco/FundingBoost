package practice.fundingboost2.item.item.repo.jpa;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.item.item.repo.entity.Bookmark;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.QBookmark;
import practice.fundingboost2.item.item.repo.entity.QItem;
import practice.fundingboost2.item.item.repo.entity.QOption;
import practice.fundingboost2.item.item.ui.dto.GetItemDetailResponseDto;
import practice.fundingboost2.item.item.ui.dto.GetItemListResponseDto;
import practice.fundingboost2.item.item.ui.dto.GetOptionResponseDto;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final JPAQueryFactory queryFactory;

    private static final QItem item = QItem.item;
    private static final QOption option = QOption.option;
    private static final QBookmark bookmark = QBookmark.bookmark;

    @Override
    public GetItemListResponseDto getItems(Pageable pageable) {

        List<Item> items = queryFactory
            .selectFrom(item)
            .leftJoin(item.options, option)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new GetItemListResponseDto(items);
    }

    @Override
    public GetItemListResponseDto getLikedItems(Long memberId, Pageable pageable) {

        List<Item> items = queryFactory
            .select(item)
            .from(bookmark)
            .join(item).on(bookmark.id.itemId.eq(item.id))
            .where(bookmark.id.memberId.eq(memberId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new GetItemListResponseDto(items);
    }

    @Override
    public Optional<Item> findItemByIdWithOptions(Long itemId) {
        return Optional.ofNullable(queryFactory
            .selectFrom(item)
            .join(item.options, option).fetchJoin()
            .where(item.id.eq(itemId))
            .fetchOne());
    }

    @Override
    public GetItemDetailResponseDto getItemInfo(Long memberId, Long itemId) {
        Item item = findItem(itemId);
        boolean isLiked = findLike(memberId, itemId);

        List<GetOptionResponseDto> options = item.getOptions().stream()
            .map(GetOptionResponseDto::from)
            .toList();

        return GetItemDetailResponseDto.from(item, isLiked, options);
    }

    private boolean findLike(Long memberId, Long itemId) {
        Bookmark findBookmark = queryFactory
            .selectFrom(bookmark)
            .where(hasMemberId(memberId),
                bookmark.id.itemId.eq(itemId))
            .fetchOne();

        return findBookmark != null;
    }

    private Item findItem(Long itemId) {
        Item item = queryFactory
            .selectFrom(ItemQueryRepositoryImpl.item)
            .leftJoin(ItemQueryRepositoryImpl.item.options, option).fetchJoin()
            .where(ItemQueryRepositoryImpl.item.id.eq(itemId))
            .fetchOne();

        if (item == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_ITEM);
        }

        return item;
    }

    private BooleanExpression hasMemberId(Long memberId) {
        return memberId != null ? bookmark.id.memberId.eq(memberId) : Expressions.FALSE;
    }
}

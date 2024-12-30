package practice.fundingboost2.item.item.repo.querydsl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.exception.ErrorCode;
import practice.fundingboost2.item.item.repo.entity.Bookmark;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.QBookmark;
import practice.fundingboost2.item.item.repo.entity.QItem;
import practice.fundingboost2.item.item.repo.entity.QOption;
import practice.fundingboost2.item.item.ui.dto.GetItemDetailResponseDto;
import practice.fundingboost2.item.item.ui.dto.GetItemResponseDto;
import practice.fundingboost2.item.item.ui.dto.GetOptionResponseDto;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final JPAQueryFactory queryFactory;

    private static final QItem item = QItem.item;
    private static final QOption option = QOption.option;
    private static final QBookmark bookmark = QBookmark.bookmark;

    @Override
    public Page<GetItemResponseDto> getItems(String category, Pageable pageable) {

        Pageable orderedPageable = initPageable(pageable);

        List<GetItemResponseDto> dtos = queryFactory
            .selectFrom(item)
            .where(hasCategory(category))
            .leftJoin(item.options, option)
            .orderBy(hasSort(orderedPageable.getSort()))
            .offset(orderedPageable.getOffset())
            .limit(orderedPageable.getPageSize())
            .fetch()
            .stream()
            .map(GetItemResponseDto::new)
            .toList();
        
        return new PageImpl<>(dtos, orderedPageable, getPageCount(category));
    }

    private Long getPageCount(String category) {
        Long itemCount = queryFactory.select(item.count())
            .from(item)
            .where(hasCategory(category))
            .fetchOne();
        
        return itemCount != null ? itemCount : 0;
    }

    private Pageable initPageable(Pageable pageable) {
        return pageable.getSort().isUnsorted() ?
            PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("likeCount")))
            : pageable;
    }


    private OrderSpecifier<?> hasSort(Sort sort) {
        if (sort.isEmpty()) {
            return item.likeCount.desc();
        }

        String order = sort.iterator().next().getProperty();

        return switch (order) {
            case "funding" -> item.fundingCount.desc();
            case "review" -> item.reviewCount.desc();
            default -> item.likeCount.desc();
        };
    }

    private BooleanExpression hasCategory(String category) {
        return category != null ? item.category.eq(category) : null;
    }

    @Override
    public Page<GetItemResponseDto> getLikedItems(Long memberId, Pageable pageable) {

        List<GetItemResponseDto> dtos = queryFactory
            .select(item)
            .from(bookmark)
            .join(item).on(bookmark.id.itemId.eq(item.id))
            .where(bookmark.id.memberId.eq(memberId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(GetItemResponseDto::new)
            .toList();

        Long count = queryFactory
            .select(item.count())
            .from(item)
            .join(bookmark).on(bookmark.id.itemId.eq(item.id))
            .fetchOne();

        count = count != null ? count : 0;

        return new PageImpl<>(dtos, pageable, count);
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

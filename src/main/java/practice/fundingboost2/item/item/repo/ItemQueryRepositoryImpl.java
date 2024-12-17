package practice.fundingboost2.item.item.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import practice.fundingboost2.item.item.app.dto.GetItemListResponseDto;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.QBookmark;
import practice.fundingboost2.item.item.repo.entity.QItem;
import practice.fundingboost2.item.item.repo.entity.QOption;

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
}

package practice.fundingboost2.item.gifthub.repo;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import practice.fundingboost2.item.gifthub.repo.entity.QGifthub;
import practice.fundingboost2.item.gifthub.ui.dto.GetGifthubResponseDto;
import practice.fundingboost2.item.item.repo.entity.QItem;
import practice.fundingboost2.item.item.repo.entity.QOption;

@Repository
@RequiredArgsConstructor
public class GifthubQueryRepositoryImpl implements GifthubQueryRepository {

    private final JPAQueryFactory queryFactory;
    private static final QGifthub gifthub = QGifthub.gifthub;
    private static final QItem item = QItem.item;
    private static final QOption option = QOption.option;


    @Override
    public Page<GetGifthubResponseDto> getAllGifthub(Long memberId, Pageable pageable) {
        List<GetGifthubResponseDto> dtos = queryFactory
            .select(Projections.constructor(GetGifthubResponseDto.class,
                item.id, item.name, item.imageUrl, option.name, gifthub.quantity))
            .from(gifthub)
            .where(gifthub.id.memberId.eq(memberId))
            .join(item).on(gifthub.id.itemId.eq(item.id)).fetchJoin()
            .join(option).on(gifthub.id.optionId.eq(option.id)).fetchJoin()
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long pageCount = getPageCount(memberId);

        return new PageImpl<>(dtos, pageable, pageCount);
    }

    private Long getPageCount(Long memberId) {
        Long pageCount = queryFactory.select(gifthub.count())
            .from(gifthub)
            .where(gifthub.id.memberId.eq(memberId))
            .fetchOne();
        return pageCount != null ? pageCount : 0;
    }
}

package practice.fundingboost2.item.gifthub.repo;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import practice.fundingboost2.item.gifthub.repo.entity.QGifthub;
import practice.fundingboost2.item.gifthub.ui.dto.GetGifthubListResponseDto;
import practice.fundingboost2.item.gifthub.ui.dto.GetGifthubResponseDto;
import practice.fundingboost2.item.item.repo.entity.QItem;
import practice.fundingboost2.item.item.repo.entity.QOption;

@Repository
@RequiredArgsConstructor
public class GifthubQueryRepositoryImpl implements GifthubQueryRepository{

    private final JPAQueryFactory queryFactory;
    private static final QGifthub gifthub = QGifthub.gifthub;
    private static final QItem item = QItem.item;
    private static final QOption option = QOption.option;


    @Override
    public GetGifthubListResponseDto getAllGifthub(Long memberId, Pageable pageable) {
        return GetGifthubListResponseDto.from(queryFactory
            .select(Projections.constructor(GetGifthubResponseDto.class,
                item.id, item.name, item.imageUrl, option.name, gifthub.quantity.quantity))
            .from(gifthub)
            .join(item).on(gifthub.id.itemId.eq(item.id)).fetchJoin()
            .join(option).on(gifthub.id.optionId.eq(option.id)).fetchJoin()
            .where(gifthub.id.memberId.eq(memberId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch());
    }
}

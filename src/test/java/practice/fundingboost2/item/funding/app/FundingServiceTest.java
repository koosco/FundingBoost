package practice.fundingboost2.item.funding.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.item.funding.app.dto.CreateFundingItemRequestDto;
import practice.fundingboost2.item.funding.app.dto.CreateFundingRequestDto;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
@Transactional
class FundingServiceTest {

    @Autowired
    FundingService fundingService;

    @PersistenceContext
    EntityManager em;

    final List<Item> items = new ArrayList<>();
    Member member;

    final int ITEM_SIZE = 5;
    final int OPTION_SIZE = 5;


    @BeforeEach
    void init() {
        member = new Member("member1", "password", "nickname", "email");
        em.persist(member);

        for (int i = 1; i <= ITEM_SIZE; i++) {
            Item item = new Item(
                "item" + i,
                i * 1000,
                "https://koosco.tistory.com",
                "brand" + i,
                "category" + i
            );

            em.persist(item);
            items.add(item);

            for (int j = 1; j <= OPTION_SIZE; j++) {
                Option option = new Option(item, "option" + j, j * 10);
                em.persist(option);
            }
        }

        em.flush();
    }

    @Test
    void givenOneItem_whenCreateFunding_thenCreateNewFunding() {
        // given
        Item item = items.getFirst();
        Option option = item.getOptions().getFirst();
        CreateFundingItemRequestDto fundingItemDto = new CreateFundingItemRequestDto(item.getId(),
            option.getId(), 1);
        CreateFundingRequestDto dto = new CreateFundingRequestDto(List.of(fundingItemDto),
            LocalDateTime.now(), "BIRTHDAY", "happy birthday");

        // when
        CommonSuccessDto resultDto = fundingService.createFunding(member.getId(), dto);

        // then
        assertTrue(resultDto.isSuccess());
    }

    @Test
    void givenTenItems_whenCreateFunding_thenCreateNewFunding() {
        // given
        List<CreateFundingItemRequestDto> fundingItemDtos = new ArrayList<>();
        for (int i = 0; i < OPTION_SIZE; i++) {
            Item item = items.get(i);
            Option option = item.getOptions().getFirst();
            fundingItemDtos.add(new CreateFundingItemRequestDto(item.getId(), option.getId(), i));
        }
        CreateFundingRequestDto dto = new CreateFundingRequestDto(fundingItemDtos, LocalDateTime.now(), "BIRTHDAY",
            "happy birthday");
        // when
        CommonSuccessDto resultDto = fundingService.createFunding(member.getId(), dto);

        // then
        assertTrue(resultDto.isSuccess());
    }
}
package practice.fundingboost2.item.funding.app;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

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
import practice.fundingboost2.item.funding.app.dto.GetFundingHistoryListResponseDto;
import practice.fundingboost2.item.funding.app.dto.GetFundingResponseDto;
import practice.fundingboost2.item.funding.repo.entity.Contributor;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.entity.FundingItem;
import practice.fundingboost2.item.funding.repo.entity.FundingTag;
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

    @Test
    public void givenMemberIdAndFundingId_whenGetFunding_thenGetFundingDetails() {

        //given
        Funding funding = new Funding(member, "happy birthday", "BIRTHDAY", LocalDateTime.of(2025, 1, 31, 0, 0));
        em.persist(funding);

        items.forEach(item -> {
                    Option firstOption = item.getOptions().getFirst();
                    int sequence = items.indexOf(item);
                    FundingItem fundingItem =  new FundingItem(funding, item, firstOption, sequence);
                    em.persist(fundingItem);
                });

        Member friend1 = new Member("member2", "password", "nickname", "email");
        Member friend2 = new Member("member3", "password", "nickname", "email");
        em.persist(friend1);
        em.persist(friend2);

        Contributor contributor1 = new Contributor(friend1, funding, 1000);
        Contributor contributor2 = new Contributor(friend2, funding, 2000);
        em.persist(contributor1);
        em.persist(contributor2);

        //when
        GetFundingResponseDto result = fundingService.getFunding(member.getId(), funding.getId());

        //then
        assertThat(result.getFundingInfoResponseDto().fundingId()).isEqualTo(funding.getId());
        assertThat(result.getFundingInfoResponseDto().collectPrice()).isEqualTo(3000);

        assertThat(result.getFundingItemResponseDtos().get(3).itemName()).isEqualTo("item4");
        assertThat(result.getFundingItemResponseDtos().get(3).itemOption()).isEqualTo("option1");

        assertThat(result.getFundingParticipantDtos().getFirst().memberId()).isEqualTo(friend1.getId());
        assertThat(result.getFundingParticipantDtos().getFirst().price()).isEqualTo(1000);
    }

    @Test
    public void givenMemberId_whenGetFundingHistory_thenGetFundingHistoryList() {

        Funding funding1 = new Funding(member, "happy birthday", "BIRTHDAY", LocalDateTime.of(2025, 1, 31, 0, 0));
        Funding funding2 = new Funding(member, "happy graduate", "GRADUATE", LocalDateTime.of(2024, 12, 29, 0, 0));
        em.persist(funding1);
        em.persist(funding2);

        FundingItem fundingItem1 = new FundingItem(funding2, items.get(2), items.get(2).getOptions().get(1), 1);
        FundingItem fundingItem2 = new FundingItem(funding2, items.get(3), items.get(3).getOptions().get(2), 2);
        em.persist(fundingItem1);
        em.persist(fundingItem2);

        items.forEach(item -> {
            Option firstOption = item.getOptions().getFirst();
            int sequence = items.indexOf(item);
            FundingItem funding1Item =  new FundingItem(funding1, item, firstOption, sequence);
            em.persist(funding1Item);
        });

        Member friend1 = new Member("friend1", "password", "nickname", "email");
        Member friend2 = new Member("friend2", "password", "nickname", "email");
        Member friend3 = new Member("friend3", "password", "nickname", "email");
        Member friend4 = new Member("friend4", "password", "nickname", "email");
        em.persist(friend1);
        em.persist(friend2);
        em.persist(friend3);
        em.persist(friend4);

        Contributor contributor1 = new Contributor(friend1, funding1, 1000);
        Contributor contributor2 = new Contributor(friend2, funding1, 2000);
        Contributor contributor3 = new Contributor(friend3, funding2, 3000);
        Contributor contributor4 = new Contributor(friend4, funding2, 4000);
        em.persist(contributor1);
        em.persist(contributor2);
        em.persist(contributor3);
        em.persist(contributor4);

        //when
        GetFundingHistoryListResponseDto result = fundingService.getFundingHistory(member.getId());

        //then
        assertThat(result.fundingHistoryDtos().size()).isEqualTo(2);

        assertThat(result.fundingHistoryDtos().getLast().fundingTag()).isEqualTo(FundingTag.GRADUATE);
        assertThat(result.fundingHistoryDtos().getLast().collectPrice()).isEqualTo(7000);
        assertThat(result.fundingHistoryDtos().getFirst().fundingDeadline()).isEqualTo(funding1.getDeadLine());
        assertThat(result.fundingHistoryDtos().getFirst().fundingStart()).isEqualTo(funding1.getCreatedAt());
        assertThat(result.fundingHistoryDtos().getFirst().collectPrice()).isEqualTo(3000);
        assertThat(result.fundingHistoryDtos().getFirst().contributorCount()).isEqualTo(2);
    }
}
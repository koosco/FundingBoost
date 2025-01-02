package practice.fundingboost2.item.funding.app;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.item.funding.app.dto.CreateFundingItemRequestDto;
import practice.fundingboost2.item.funding.app.dto.CreateFundingRequestDto;
import practice.fundingboost2.item.funding.app.dto.GetFundingDetailResponseDto;
import practice.fundingboost2.item.funding.app.dto.GetFundingHistoryListResponseDto;
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
    final List<Member> friends = new ArrayList<>();
    final List<Funding> fundings = new ArrayList<>();
    Member member;

    final int ITEM_SIZE = 5;
    final int OPTION_SIZE = 5;
    final int FUNDING_SIZE = 5;
    final int FUNDING_ITEM_SIZE = 5;
    final int FRIEND_SIZE = 4;


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

        for (int i = 1; i <= FUNDING_SIZE; i++){
            Funding funding = new Funding(
                    member,
                    "funding" + i,
                    "ETC",
                    LocalDateTime.now().plusDays(i)
            );

            fundings.add(funding);
            em.persist(funding);

            for(int j = 1; j <= FUNDING_ITEM_SIZE; j++){
                FundingItem fundingItem = new FundingItem(
                        funding,
                        items.get(i-1),
                        items.get(i-1).getOptions().get(i-1),
                        i
                );

                em.persist(fundingItem);
            }

        }

        for(int i = 1; i <= FRIEND_SIZE; i++){
            Member friend = new Member(
                    "friend"+i+"@email",
                    "friend"+i,
                    "image"+i+".jpg",
                    "010" + String.valueOf(i).repeat(8)
            );

            em.persist(friend);
            friends.add(friend);
        }

        for(int i = 1; i <= FRIEND_SIZE; i++){
            Contributor contributor = new Contributor(
                    friends.get(i-1),
                    fundings.getFirst(),
                    i*1000
            );

            em.persist(contributor);
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
    public void givenMemberIdAndFundingId_whenFundingIsNotNull_thenReturnDto() {
        //given
        Long fundingId = fundings.getFirst().getId();
        Long memberId = member.getId();

        //when
        GetFundingDetailResponseDto result = fundingService.getFunding(memberId, fundingId);

        //then
        assertThat(result.getFundingInfoResponseDto().collectPrice()).isEqualTo(10000);
        assertThat(result.getFundingParticipantDtos().size()).isEqualTo(4);
        assertThat(result.getFundingItemResponseDtos().size()).isEqualTo(5);
    }

@Test
public void givenMemberIdAndFundingId_whenFundingIsNull_thenReturnEmptyDto() throws Exception {
    //given

    //when

    //then
}
    @Test
    public void givenMemberId_whenFundingHistoryIsNotEmpty_thenReturnDto() {
        //given
        Long memberId = member.getId();

        //when
        GetFundingHistoryListResponseDto result = fundingService.getFundingHistory(memberId);

        //then
        assertThat(result.fundingHistoryDtos().size()).isEqualTo(5);

        assertThat(result.fundingHistoryDtos().getLast().fundingTag()).isEqualTo(FundingTag.ETC);
        assertThat(result.fundingHistoryDtos().getFirst().contributorCount()).isEqualTo(4);
    }

    @Test
    public void givenMemberId_whenFundingHistoryIsEmpty_thenReturnEmptyDto() throws Exception {
        //given
        Long memberId = friends.getFirst().getId();

        //when
        GetFundingHistoryListResponseDto result = fundingService.getFundingHistory(memberId);

        //then
        assertThat(result.fundingHistoryDtos().isEmpty()).isEqualTo(true);
    }
}
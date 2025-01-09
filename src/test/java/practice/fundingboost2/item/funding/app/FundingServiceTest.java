package practice.fundingboost2.item.funding.app;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.item.funding.app.dto.GetFundingDetailResponseDto;
import practice.fundingboost2.item.funding.app.dto.GetFundingResponseDto;
import practice.fundingboost2.item.funding.app.interfaces.FundingRepository;
import practice.fundingboost2.item.funding.repo.entity.Contributor;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.entity.FundingItem;
import practice.fundingboost2.item.funding.repo.entity.FundingTag;
import practice.fundingboost2.item.funding.repo.jpa.ContributorRepository;
import practice.fundingboost2.item.funding.repo.jpa.FundingItemRepository;
import practice.fundingboost2.item.item.repo.ItemRepository;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.member.repo.MemberRepository;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
class FundingServiceTest {

    @Autowired
    FundingService fundingService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    FundingRepository fundingRepository;

    @Autowired
    ContributorRepository contributorRepository;

    @Autowired
    FundingItemRepository fundingItemRepository;

    Member member;
    List<Item> items = new ArrayList<>();
    List<Member> friends = new ArrayList<>();
    List<Funding> fundings = new ArrayList<>();
    List<Contributor> contributors = new ArrayList<>();

    final int ITEM_SIZE = 5;
    final int OPTION_SIZE = 5;
    final int FUNDING_SIZE = 5;
    final int FUNDING_ITEM_SIZE = 5;
    final int FRIEND_SIZE = 4;


    @BeforeEach
    void init() {
        member = new Member("email", "nickname", "imageUrl", "phoneNumber");
        memberRepository.save(member);

        initItem();
        initFunding();
        initFriends();
    }

    private void initFriends() {
        for (int i = 1; i <= FRIEND_SIZE; i++) {
            Member friend = new Member(
                "friend" + i + "@email",
                "friend" + i,
                "image" + i + ".jpg",
                "010" + String.valueOf(i).repeat(8)
            );
            friend.increasePoint(100_000);
            friends.add(friend);
        }
        friends = memberRepository.saveAll(friends);
    }

    private void initFunding() {
        for (int i = 1; i <= FUNDING_SIZE; i++) {
            Funding funding = new Funding(
                member,
                "funding" + i,
                "ETC",
                LocalDateTime.now().plusDays(i)
            );
            fundings.add(funding);

            for (int j = 1; j <= FUNDING_ITEM_SIZE; j++) {
                new FundingItem(
                    funding,
                    items.get(i - 1),
                    items.get(i - 1).getOptions().get(i - 1),
                    i
                );
            }
        }
        fundings = fundingRepository.saveAll(fundings);
    }

    private void initItem() {
        for (int i = 1; i <= ITEM_SIZE; i++) {
            Item item = new Item(
                "item" + i,
                i * 1000,
                "https://koosco.tistory.com",
                "brand" + i,
                "category" + i
            );
            items.add(item);

            for (int j = 1; j <= OPTION_SIZE; j++) {
                new Option(item, "option" + j, j * 10);
            }
        }
        items = itemRepository.saveAll(items);
    }

    @Test
    void givenMemberIdAndFundingId_whenFundingIsNotNull_thenReturnDto() {
        //given
        Long fundingId = fundings.getFirst().getId();
        Long memberId = member.getId();
        int raisePrice = 0;

        for (int i = 1; i <= FRIEND_SIZE; i++) {
            contributors.add(new Contributor(
                friends.get(i - 1),
                fundings.getFirst(),
                i * 1000
            ));
            raisePrice += i * 1000;
        }
        Funding funding = fundings.getFirst();
        fundingRepository.save(funding);
        List<FundingItem> fundingItems = funding.getFundingItems();
        fundingItemRepository.saveAll(fundingItems);
        contributors = contributorRepository.saveAll(contributors);

        //when
        GetFundingDetailResponseDto result = fundingService.getFunding(memberId, fundingId);

        //then
        assertThat(result.getFundingInfoResponseDto().collectPrice()).isEqualTo(raisePrice);
        assertThat(result.getFundingParticipantDtos().size()).isEqualTo(FRIEND_SIZE);
        assertThat(result.getFundingItemResponseDtos().size()).isEqualTo(FUNDING_ITEM_SIZE);
    }

    @Test
    void givenMemberId_whenFundingHistoryIsNotEmpty_thenReturnDto() {
        //given
        Long memberId = member.getId();
        for (int i = 1; i <= FRIEND_SIZE; i++) {
            contributors.add(new Contributor(
                friends.get(i - 1),
                fundings.getFirst(),
                i * 1000
            ));
        }
        fundingRepository.save(fundings.getFirst());
        contributors = contributorRepository.saveAll(contributors);

        //when
        List<GetFundingResponseDto> dtos = fundingService.getFundingHistory(memberId);

        //then
        assertThat(dtos.size()).isEqualTo(FUNDING_ITEM_SIZE);

        assertThat(dtos.getLast().tag()).isEqualTo(FundingTag.ETC);
        assertThat(dtos.getFirst().contributorCount()).isEqualTo(FRIEND_SIZE);
    }

    @Test
    void givenMemberId_whenFundingHistoryIsEmpty_thenReturnEmptyDto() {
        //given
        Long memberId = friends.getFirst().getId();

        //when
        List<GetFundingResponseDto> dtos = fundingService.getFundingHistory(memberId);

        //then
        assertThat(dtos.isEmpty()).isEqualTo(true);
    }
}
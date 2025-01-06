package practice.fundingboost2.item.funding.app;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.item.funding.app.dto.UpdateFundingRequestDto;
import practice.fundingboost2.item.funding.app.interfaces.FundingRepository;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.entity.FundingItem;
import practice.fundingboost2.item.item.repo.ItemRepository;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.member.repo.MemberRepository;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
public class FundingConcurrencyTest {

    @Autowired
    FundingService fundingService;

    @Autowired
    FundingRepository fundingRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    Member member;

    List<Member> friends = new ArrayList<>();

    List<Item> items = new ArrayList<>();

    Funding funding;

    static final int FRIEND_SIZE = 100;

    static final int ITEM_SIZE = 5;

    @BeforeEach
    void init() {
        IntStream.range(0, FRIEND_SIZE)
            .forEach(i -> {
                Member friend = new Member("email" + i, "name" + i, "image" + i, "phone" + i);
                friend.increasePoint(100_000);
                friends.add(friend);
            });
        member = memberRepository.save(new Member("email", "name", "image", "phone"));
        friends = memberRepository.saveAll(friends);

        IntStream.range(0, ITEM_SIZE)
            .forEach(i -> {
                items.add(
                    new Item("name" + i, 1_000_000 * i, "image" + i, "brand" + i, "category" + i));
            });
        items = itemRepository.saveAll(items);

        funding = new Funding(member, "message", "BIRTHDAY", LocalDateTime.now().plusDays(10));

        IntStream.range(0, ITEM_SIZE)
            .forEach(i -> {
                Option option = new Option(items.get(i), "option", 10);
                new FundingItem(funding, items.get(i), option, i);
            });
        fundingRepository.save(funding);
    }

    @Test
    void givenFunding_whenHundredPeopleFund_thenFundingCountMustBeHundred() throws InterruptedException {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(FRIEND_SIZE);
        CountDownLatch latch = new CountDownLatch(FRIEND_SIZE);

        // when
        for(Member friend : friends) {
            executorService.execute(() -> {
                UpdateFundingRequestDto dto = new UpdateFundingRequestDto(friend.getId(), null, null, 1000);
                fundingService.fund(funding.getId(), dto);
                latch.countDown();
            });
        }

        latch.await();
        executorService.close();

        // then
        funding = fundingRepository.findById(funding.getId());
        assertThat(funding.getFundingCount()).isEqualTo(FRIEND_SIZE);
    }
}

package practice.fundingboost2.item.funding.repo.jpa;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.item.funding.app.dto.GetFundingListResponseDto;
import practice.fundingboost2.item.funding.repo.entity.Funding;
import practice.fundingboost2.item.funding.repo.entity.FundingItem;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.member.repo.entity.Member;
import practice.fundingboost2.member.repo.entity.Relation;

@Transactional
@SpringBootTest
class FundingQueryRepositoryImplTest {

    @PersistenceContext
    EntityManager em;

    Member member;

    static final int ITEM_SIZE = 100;

    static final int FUNDING_SIZE = ITEM_SIZE / 5;

    final List<Member> friends = new ArrayList<>();

    final List<Item> items = new ArrayList<>();

    final List<Option> options = new ArrayList<>();

    @Autowired
    private FundingQueryRepositoryImpl fundingQueryRepositoryImpl;

    @BeforeEach
    void init() {

        for (int i = 0; i < ITEM_SIZE; i++) {
            Item item = new Item("name" + i, 1000 * i, "imageUrl" + i, "brand" + i, "category" + i);
            Option option = new Option(item, "option" + i, 10);
            items.add(item);
            options.add(option);
            em.persist(item);
            em.persist(option);
        }

        member = new Member("email", "name", "imageUrl", "phoneNumber");
        em.persist(member);
        for (int i = 0; i < FUNDING_SIZE; i++) {
            Member friend = new Member("email" + i, "name" + i, "imageUrl" + i, "phoneNumber" + i);
            friends.add(friend);
            em.persist(friend);

            Relation relation = new Relation(member.getId(), friend.getId());
            em.persist(relation);

            Funding funding = new Funding(friend, "message" + i, "BIRTHDAY", LocalDateTime.now().plusDays(1));
            em.persist(funding);

            for (int j = 5 * i; j < 5 * i + 5; j++) {
                FundingItem fundingItem = new FundingItem(funding, items.get(j), options.get(j), j % 5 + 1);
                em.persist(fundingItem);
            }
        }

        em.flush();
    }

    @Test
    void givenFunding_whenPageSizeIsTen_thenReturnDtoMustBeTen() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        // when
        GetFundingListResponseDto dto = fundingQueryRepositoryImpl.findFundings(member.getId(), pageRequest);
        // then
        assertThat(dto.fundings()).hasSize(10);
        assertThat(dto.fundings().getFirst().fundingItems()).hasSize(5);
    }

    @Test
    void givenFunding_whenPageSizeIsFive_thenReturnDtoMustBeFive() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 5);
        // when
        GetFundingListResponseDto dto = fundingQueryRepositoryImpl.findFundings(member.getId(), pageRequest);
        // then
        assertThat(dto.fundings()).hasSize(5);
    }

    @Test
    void givenFunding_whenPageNumberIs1_thenReturnSecondPageElements() {
        // given
        PageRequest pageRequest = PageRequest.of(1, 10);
        // when
        GetFundingListResponseDto dto = fundingQueryRepositoryImpl.findFundings(member.getId(), pageRequest);
        // then
        assertThat(dto.fundings().getFirst().fundingId()).isEqualTo(11);
    }
}
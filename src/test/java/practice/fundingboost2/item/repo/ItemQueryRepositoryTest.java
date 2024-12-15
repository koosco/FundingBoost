package practice.fundingboost2.item.repo;


import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.item.app.dto.GetItemListResponseDto;
import practice.fundingboost2.item.repo.entity.Item;
import practice.fundingboost2.item.repo.entity.Option;

@Transactional
@SpringBootTest
class ItemQueryRepositoryTest {

    @Autowired
    ItemQueryRepository itemQueryRepository;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {
        Item item1 = new Item("item1", 1000, "https://koosco.tistory.com", "brand1", "category1", 0, 0);
        em.persist(item1);
        Option option1 = new Option(item1, "option1", 10);
        em.persist(option1);

        Item item2 = new Item("item2", 2000, "https://koosco.tistory.com", "brand2", "category2", 5, 10);
        em.persist(item2);
        Option option2 = new Option(item2, "option2", 20);
        em.persist(option2);

        Item item3 = new Item("item3", 3000, "https://koosco.tistory.com", "brand3", "category3", 15, 30);
        em.persist(item3);
        Option option3 = new Option(item3, "option3", 30);
        em.persist(option3);

        em.flush();
    }

    @Test
    void findAllItems() {
        // given
        PageRequest pageable = PageRequest.of(0, 10);
        // when
        GetItemListResponseDto dto = itemQueryRepository.getItems(pageable);
        // then
        assertThat(dto.getItems()).hasSize(3);
    }
}
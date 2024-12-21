package practice.fundingboost2.item.item.repo.jpa;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;

@DataJpaTest
@Transactional
class JpaItemRepositoryTest {

    @Autowired
    JpaItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    Item item;

    Option option;

    @BeforeEach
    void init() {
        item = new Item("name", 0, "url", "brand", "category");
        em.persist(item);

        option = new Option(item, "option", 1);
        em.persist(option);

        em.flush();
    }
    
    @Test
    void givenItemAndOption_whenFindByItemIdAndOptionId_thenReturnTrue() {
        // given
        // when
        Boolean result = itemRepository.existsByIdAndOptions_Id(item.getId(), option.getId());
        // then
        assertTrue(result);
    }

    @Test
    void givenNotExistsItem_whenFindByItemIdAndOptionId_thenReturnFalse() {
        // given
        // when
        Boolean result = itemRepository.existsByIdAndOptions_Id(0L, 1L);
        // then
        assertFalse(result);
    }

    @Test
    void givenNotExistsOption_whenFindByItemIdAndOptionId_thenReturnFalse() {
        // given
        // when
        Boolean result = itemRepository.existsByIdAndOptions_Id(item.getId(), 0L);
        // then
        assertFalse(result);
    }

    @Test
    void givenInvalidOptionIdForItem_whenFindByItemIdAndOptionId_thenReturnFalse() {
        // given
        Item item2 = new Item("name", 0, "url", "brand", "category");
        em.persist(item2);

        Option option2 = new Option(item2, "option", 1);
        em.persist(option2);
        em.flush();

        // when
        Boolean result = itemRepository.existsByIdAndOptions_Id(item.getId(), option2.getId());
        // then
        assertFalse(result);
    }
}
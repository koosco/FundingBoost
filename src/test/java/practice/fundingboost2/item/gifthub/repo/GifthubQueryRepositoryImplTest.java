package practice.fundingboost2.item.gifthub.repo;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.item.gifthub.repo.entity.Gifthub;
import practice.fundingboost2.item.gifthub.repo.entity.GifthubId;
import practice.fundingboost2.item.gifthub.ui.dto.GetGifthubResponseDto;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
@Transactional
class GifthubQueryRepositoryImplTest {

    @Autowired
    GifthubQueryRepository gifthubQueryRepository;

    @PersistenceContext
    EntityManager em;

    Member member;

    Item item;

    final List<Gifthub> gifthubList = new ArrayList<>();

    static final int GIFTHUB_SIZE = 15;

    @BeforeEach
    void init() {
        // 1. Member 생성 및 저장
        member = new Member("email", "name", "imageUrl", "phone");
        em.persist(member);

        // 2. Item 생성 및 저장
        item = new Item("name", 1000, "imageUrl", "brand", "category");
        em.persist(item);

        for (int i = 0;i<GIFTHUB_SIZE;i++) {
            Option option = new Option(item, "option" + (i + 1), 100);
            em.persist(option);
            Gifthub gifthub = new Gifthub(new GifthubId(member.getId(), item.getId(), option.getId()));
            em.persist(gifthub);
            gifthubList.add(gifthub);
        }
        em.flush();
    }

    @Test
    void givenCreateFifteenGifthub_whenPageSizeIsTen_thenReturnTenGifthub() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        // when
        Page<GetGifthubResponseDto> dto = gifthubQueryRepository.getAllGifthub(member.getId(), pageRequest);

        // then
        assertThat(dto.getContent()).hasSize(10);
    }

    @Test
    void givenCreateFiveGifthub_whenPageSizeIsTen_thenReturnFiveGifthub() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 5);
        // when
        Page<GetGifthubResponseDto> dto = gifthubQueryRepository.getAllGifthub(member.getId(), pageRequest);
        // then
        assertThat(dto.getContent()).hasSize(5);
    }

    @Test
    void givenCreateFifteenGifthub_whenPageIsTwo_thenReturnFiveGifthub() {
        // given
        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<GetGifthubResponseDto> dto = gifthubQueryRepository.getAllGifthub(member.getId(), pageRequest);
        // then
        assertThat(dto.getContent()).hasSize(5);
    }

    @Test
    void givenCreateGifthub_whenOtherCall_thenReturnEmptyGifthub() {
        // given
        Member other = new Member("email", "name", "imageUrl", "phone");
        em.persist(other);
        em.flush();
        PageRequest pageRequest = PageRequest.of(0, 10);
        // when
        Page<GetGifthubResponseDto> dto = gifthubQueryRepository.getAllGifthub(other.getId(), pageRequest);
        // then
        assertThat(dto.getContent()).isEmpty();
    }

    @Test
    void givenGifthub_whenCall_thenReturnValidInfo() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        // when
        Page<GetGifthubResponseDto> dto = gifthubQueryRepository.getAllGifthub(member.getId(), pageRequest);
        GetGifthubResponseDto firstDto = dto.getContent().getFirst();

        // then
        assertThat(firstDto.itemId()).isEqualTo(item.getId());
        assertThat(firstDto.itemName()).isEqualTo(item.getName());
        assertThat(firstDto.itemImage()).isEqualTo(item.getImageUrl());
        assertThat(firstDto.option()).isEqualTo(item.getOptions().getFirst().getName());
        assertThat(firstDto.quantity()).isEqualTo(1);
    }
}
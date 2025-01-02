package practice.fundingboost2.item.item.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.item.item.repo.ItemRepository;
import practice.fundingboost2.item.item.repo.entity.Bookmark;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.jpa.BookmarkRepository;
import practice.fundingboost2.member.repo.MemberRepository;
import practice.fundingboost2.member.repo.entity.Member;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    MemberRepository memberRepository;

    Member member;

    Item item;

    @BeforeEach
    void init() {
        member = new Member("email", "name", "imageUrl", "phone");
        memberRepository.save(member);

        item = new Item("name", 1000, "imageUrl", "brand", "category");
        item = itemRepository.save(item);
    }

    @Test
    void givenNotExistsBookmark_whenMark_thenCreateNewBookmark() {
        // given
        Long memberId = member.getId();
        Long itemId = item.getId();
        Bookmark bookmark = new Bookmark(memberId, itemId);

        // when
        CommonSuccessDto dto = itemService.likeItem(memberId, itemId);

        // then
        item = itemRepository.findById(item.getId());
        assertThat(dto.isSuccess()).isTrue();
        assertThat(item.getLikeCount()).isEqualTo(1);
        assertTrue(bookmarkRepository.existsById(bookmark.getId()));
    }

    @Test
    void givenExistsBookmark_whenUnMark_thenDeleteBookmark() {
        // given
        Long memberId = member.getId();
        Long itemId = item.getId();
        Bookmark bookmark = new Bookmark(memberId, itemId);
        itemService.likeItem(memberId, itemId);

        // when
        itemService.likeItem(memberId, itemId);

        // then
        assertThat(item.getLikeCount()).isEqualTo(0);
        assertFalse(bookmarkRepository.existsById(bookmark.getId()));
    }

    @Test
    void givenItem_whenHundredMemberLikeConcurrently_thenItemLikeCountMustBeHundred() throws InterruptedException {
        // given
        final int MEMBER_COUNT = 100;
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < MEMBER_COUNT; i++) {
            Member friend = new Member("email" + i, "name" + i, "imageUrl" + i, "phone" + i);
            members.add(friend);
        }
        members = memberRepository.saveAll(members);

        ExecutorService executorService = Executors.newFixedThreadPool(MEMBER_COUNT);
        CountDownLatch latch = new CountDownLatch(MEMBER_COUNT);

        // when
        for (Member member : members) {
            executorService.execute(() -> {
                itemService.likeItem(member.getId(), item.getId());
                latch.countDown();
            });
        }

        latch.await();
        executorService.close();

        item = itemRepository.findById(item.getId());

        assertThat(item.getLikeCount()).isEqualTo(MEMBER_COUNT);
    }
}
package practice.fundingboost2.item.item.repo.querydsl;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.item.item.app.dto.GetReviewResponseDto;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.item.item.repo.entity.Review;
import practice.fundingboost2.member.repo.entity.Member;

@Transactional
@SpringBootTest
class ReviewQueryRepositoryImplTest {

    @Autowired
    ReviewQueryRepositoryImpl reviewQueryRepositoryImpl;

    @PersistenceContext
    EntityManager em;

    List<Member> reviewers = new ArrayList<>();

    Item item;

    Option ItemOption;

    List<Review> reviews = new ArrayList<>();

    final static int REVIEWER_SIZE = 20;

    @BeforeEach
    void init() {
        IntStream.range(0, REVIEWER_SIZE)
            .forEach(i -> {
                Member member = new Member("email" + i, "name" + i, "imageUrl" + i, "phoneNumber" + i);
                reviewers.add(member);
                em.persist(member);
            });

        item = new Item("name", 1000, "imageUrl", "brand", "category");
        em.persist(item);

        ItemOption = new Option(item, "option", 3);

        IntStream.range(0, REVIEWER_SIZE)
            .forEach(i -> {
                Review review = new Review((int) (Math.random() * 5) + 1, "content", reviewers.get(i), item, ItemOption);
                reviews.add(review);
                em.persist(review);
            });
        em.flush();
    }

    @Test
    void givenItemAndTwentyReview_whenGetReviews_thenReturnTenReviews() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        // when
        Page<GetReviewResponseDto> result = reviewQueryRepositoryImpl.getReviews(item.getId(), pageRequest);
        // then
        assertThat(result.getContent()).hasSize(10);
    }

    @Test
    void givenReview_whenPageSizeIsFive_thenReturnFiveReviews() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 5);
        // when
        Page<GetReviewResponseDto> result = reviewQueryRepositoryImpl.getReviews(item.getId(), pageRequest);
        // then
        assertThat(result.getContent()).hasSize(5);
    }

    @Test
    void givenReviewerSizeReview_whenGetReviews_thenTotalElementSizeMustReviewerSize() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        // when
        Page<GetReviewResponseDto> result = reviewQueryRepositoryImpl.getReviews(item.getId(), pageRequest);
        // then
        assertThat(result.getTotalElements()).isEqualTo(REVIEWER_SIZE);
    }

    @Test
    void givenTwentyReviews_whenGetReviews_thenTotalPageSizeMustBeTwo() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        // when
        Page<GetReviewResponseDto> result = reviewQueryRepositoryImpl.getReviews(item.getId(), pageRequest);
        // then
        assertThat(result.getTotalPages()).isEqualTo(REVIEWER_SIZE / 10);
    }
}

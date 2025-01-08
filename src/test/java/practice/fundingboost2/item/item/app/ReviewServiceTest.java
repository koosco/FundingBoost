package practice.fundingboost2.item.item.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.common.exception.CommonException;
import practice.fundingboost2.common.file.FileService;
import practice.fundingboost2.item.item.app.dto.CreateReviewRequestDto;
import practice.fundingboost2.item.item.app.dto.GetReviewResponseDto;
import practice.fundingboost2.item.item.repo.ReviewRepository;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Review;
import practice.fundingboost2.item.order.app.OrderService;
import practice.fundingboost2.item.order.repo.entity.Delivery;
import practice.fundingboost2.item.order.repo.entity.Order;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.repo.entity.Member;

@ExtendWith(MockitoExtension.class) // Mockito 초기화
class ReviewServiceTest {

    @InjectMocks
    ReviewService reviewService;

    @Mock
    MemberService memberService;

    @Mock
    OrderService orderService;

    @Mock
    ItemService itemService;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    FileService fileService;

    @Mock
    Member mockMember;

    final Long memberId = 1L;

    @Mock
    Item mockItem;

    final Long itemId = 1L;

    Review mockReview;

    final Long mockReviewId = 1L;

    @Mock
    Order mockOrder;

    final Long orderId = 1L;

    List<MultipartFile> files;

    List<String> uploadedImages;

    @BeforeEach
    void init() {
        mockReview = spy(new Review(5, "content", mockMember, mockItem, "optionName", List.of("uploaded/test.jpg")));
        files = List.of(new MockMultipartFile("file", "test.jpg", null, new byte[]{1, 2, 3}));
        uploadedImages = List.of("uploaded/test.jpg");
    }

    @Test
    void givenIdAndDtoAndFiles_whenCreateReview_thenSaveReview() {
        // given
        doReturn(mockReviewId).when(mockReview).getId();
        doReturn(mockMember).when(memberService).findMember(memberId);
        doReturn(mockItem).when(itemService).findItem(itemId);
        doReturn(uploadedImages).when(fileService).uploadMultipleFiles(files);
        doReturn(mockReview).when(reviewRepository).save(any());
        doNothing().when(mockOrder).validateReviewWritten(any());
        doReturn("optionName").when(mockOrder).getOptionName();
        doReturn(mockOrder).when(orderService).findOrder(orderId);
        CreateReviewRequestDto requestDto = new CreateReviewRequestDto(itemId, orderId, "content", 5);

        // when
        GetReviewResponseDto responseDto = reviewService.createReview(memberId, requestDto, files);

        // then
        assertThat(responseDto.reviewId()).isEqualTo(mockReviewId);
        assertThat(responseDto.memberId()).isEqualTo(memberId);
    }

    @Test
    void givenAlreadyReviewedOrder_whenCreateReview_thenThrowException() {
        // given
        doReturn(mockReviewId).when(mockReview).getId();
        doReturn(mockMember).when(memberService).findMember(memberId);
        doReturn(mockItem).when(itemService).findItem(itemId);
        doReturn(uploadedImages).when(fileService).uploadMultipleFiles(files);
        doReturn(mockReview).when(reviewRepository).save(any());
        Order order = spy(new Order(mockMember, mockItem, "optionName", mock(Delivery.class), 1));
        CreateReviewRequestDto requestDto = new CreateReviewRequestDto(itemId, orderId, "content", 5);
        doReturn(order).when(orderService).findOrder(any());
        reviewService.createReview(memberId, requestDto, files);

        // when
        // then
        assertThatThrownBy(() -> reviewService.createReview(memberId, requestDto, files))
            .isInstanceOf(CommonException.class);
    }
    
    @Test
    void givenReview_whenDeleteReview_thenRemoveFromRepository() {
        // given
        Review review = mock(Review.class);
        ReviewService reviewService = spy(this.reviewService);
        doReturn(review).when(reviewService).findReview(mockReviewId);
        doNothing().when(review).validateMember(any());
        doReturn(mockItem).when(review).getItem();
        doNothing().when(mockItem).removeReview();

        // when
        CommonSuccessDto dto = reviewService.deleteReview(memberId, mockReviewId);
        // then
        verify(reviewRepository, times(1)).delete(review);
        verify(fileService, times(1)).deleteFiles(review.getReviewImages());
        assertThat(dto.isSuccess()).isTrue();
    }
}

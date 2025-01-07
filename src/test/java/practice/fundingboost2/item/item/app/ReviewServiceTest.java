package practice.fundingboost2.item.item.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import practice.fundingboost2.common.file.FileService;
import practice.fundingboost2.item.item.app.dto.CreateReviewRequestDto;
import practice.fundingboost2.item.item.app.dto.GetReviewResponseDto;
import practice.fundingboost2.item.item.repo.ReviewRepository;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Review;
import practice.fundingboost2.item.order.app.OrderService;
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


    @BeforeEach
    void init() {
        mockReview = spy(new Review(5, "content", mockMember, mockItem, "optionName", List.of("uploaded/test.jpg")));
        doReturn(mockReviewId).when(mockReview).getId();
        when(memberService.findMember(memberId)).thenReturn(mockMember);
        doNothing().when(mockOrder).validateReviewWritten(any());
        doReturn("optionName").when(mockOrder).getOptionName();
        when(orderService.findOrder(orderId)).thenReturn(mockOrder);
        when(itemService.findItem(itemId)).thenReturn(mockItem);
    }

    @Test
    void givenIdAndDtoAndFiles_whenCreateReview_thenSaveReview() {
        // given
        CreateReviewRequestDto requestDto = new CreateReviewRequestDto(itemId, orderId, "content", 5);
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", null, new byte[]{1, 2, 3});
        List<MultipartFile> files = List.of(file);
        List<String> uploadedImages = List.of("uploaded/test.jpg");
        when(fileService.uploadMultipleFiles(files)).thenReturn(uploadedImages);
        when(reviewRepository.save(any())).thenReturn(mockReview);
        doNothing().when(mockOrder).writeReview();

        // when
        GetReviewResponseDto responseDto = reviewService.createReview(memberId, requestDto, files);

        // then
        assertThat(responseDto.reviewId()).isEqualTo(mockReviewId);
        assertThat(responseDto.memberId()).isEqualTo(memberId);
        assertThat(responseDto.memberName()).isEqualTo(mockMember.getNickname());
        assertThat(responseDto.memberImageUrl()).isEqualTo(mockMember.getImageUrl());
        assertThat(responseDto.reviewScore()).isEqualTo(mockReview.getScore());
        assertThat(responseDto.reviewCreatedAt()).isEqualTo(mockReview.getCreatedAt());
        assertThat(responseDto.reviewContent()).isEqualTo(mockReview.getContent());
        assertThat(responseDto.reviewImages()).isEqualTo(mockReview.getReviewImages());

        verify(memberService, times(1)).findMember(memberId);
        verify(itemService, times(1)).findItem(itemId);
        verify(orderService, times(1)).findOrder(orderId);
        verify(mockOrder, times(1)).validateReviewWritten(memberId);
        verify(mockOrder, times(1)).getOptionName();
        verify(mockOrder, times(1)).writeReview();
        verify(reviewRepository, times(1)).save(any());
        verify(fileService, times(1)).uploadMultipleFiles(files);
    }
}

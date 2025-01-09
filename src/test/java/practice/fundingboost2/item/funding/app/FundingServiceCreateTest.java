package practice.fundingboost2.item.funding.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.fundingboost2.common.dto.CommonSuccessDto;
import practice.fundingboost2.item.funding.app.dto.CreateFundingItemRequestDto;
import practice.fundingboost2.item.funding.app.dto.CreateFundingRequestDto;
import practice.fundingboost2.item.funding.app.interfaces.FundingRepository;
import practice.fundingboost2.item.item.app.ItemService;
import practice.fundingboost2.item.item.repo.OptionRepository;
import practice.fundingboost2.item.item.repo.entity.Item;
import practice.fundingboost2.item.item.repo.entity.Option;
import practice.fundingboost2.member.app.MemberService;
import practice.fundingboost2.member.repo.entity.Member;

@ExtendWith(MockitoExtension.class)
public class FundingServiceCreateTest {

    @InjectMocks
    FundingService fundingService;

    @Mock
    FundingRepository fundingRepository;

    @Mock
    ItemService itemService;

    @Mock
    MemberService memberService;

    @Mock
    OptionRepository optionRepository;

    @Mock
    Member member;

    final Long memberId = 1L;

    @Mock
    Item item;

    final Long itemId = 1L;

    @Mock
    Option option;

    final Long optionId = 1L;

    @BeforeEach
    void init() {
        doReturn(member).when(memberService).findMember(any());
    }

    @Test
    void givenOneItem_whenCreateFunding_thenCreateNewFunding() {
        // given
        doReturn(item).when(itemService).concurrencyFindItem(any());
        doReturn(Optional.of(option)).when(optionRepository).findById(any());
        CreateFundingRequestDto requestDto = new CreateFundingRequestDto(
            List.of(new CreateFundingItemRequestDto(itemId, optionId, 1)),
            LocalDateTime.now().plusDays(10),
            "BIRTHDAY",
            "Happy Birthday!");

        // when
        CommonSuccessDto responseDto = fundingService.createFunding(memberId, requestDto);

        // then
        assertThat(responseDto.isSuccess()).isTrue();
        verify(fundingRepository, times(1)).save(any());
    }

    @Test
    void givenFiveItem_whenCreateFunding_thenCreateNewFunding() {
        // given
        List<Long> itemIds = List.of(1L, 2L, 3L, 4L, 5L);
        List<Long> optionIds = List.of(1L, 2L, 3L, 4L, 5L);
        List<Item> items = List.of(
            mock(Item.class),
            mock(Item.class),
            mock(Item.class),
            mock(Item.class),
            mock(Item.class));

        List<Option> options = List.of(
            mock(Option.class),
            mock(Option.class),
            mock(Option.class),
            mock(Option.class),
            mock(Option.class));

        for (int i = 0; i < itemIds.size(); i++) {
            doReturn(items.get(i)).when(itemService).concurrencyFindItem(itemIds.get(i));
            doReturn(Optional.of(options.get(i))).when(optionRepository).findById(optionIds.get(i));
        }

        List<CreateFundingItemRequestDto> fundingItems = new ArrayList<>();
        for (int i = 0; i < itemIds.size(); i++) {
            fundingItems.add(new CreateFundingItemRequestDto(itemIds.get(i), optionIds.get(i), i + 1));
        }

        CreateFundingRequestDto requestDto = new CreateFundingRequestDto(
            fundingItems,
            LocalDateTime.now().plusDays(10),
            "BIRTHDAY",
            "Happy Birthday!"
        );

        // when
        CommonSuccessDto responseDto = fundingService.createFunding(memberId, requestDto);

        // then
        assertThat(responseDto.isSuccess()).isTrue();
        verify(fundingRepository, times(1)).save(any());
        verify(itemService, times(5)).concurrencyFindItem(any());
        verify(optionRepository, times(5)).findById(any());
    }
}

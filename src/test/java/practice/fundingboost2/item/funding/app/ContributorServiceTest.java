package practice.fundingboost2.item.funding.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.fundingboost2.item.funding.repo.entity.Contributor;
import practice.fundingboost2.item.funding.repo.jpa.ContributorRepository;

@ExtendWith(MockitoExtension.class)
class ContributorServiceTest {

    @InjectMocks
    ContributorService contributorService;

    @Mock
    ContributorRepository contributorRepository;

    @Mock
    Contributor savedContributor;

    @Test
    void givenContributor_whenSave_thenCanFindContributor() {
        // given
        Contributor savingContributor = mock(Contributor.class);
        doReturn(savedContributor).when(contributorRepository).save(savingContributor);
        // when
        contributorService.save(savingContributor);

        // then
        verify(contributorRepository, times(1)).save(savingContributor);
    }

    @Test
    void givenContributors_whenFindAllByFundingId_thenReturnContributorList() {
        // given
        Long fundingId = 1L;
        List<Contributor> contributors = List.of(
            mock(Contributor.class),
            mock(Contributor.class)
        );
        doReturn(contributors).when(contributorRepository).findAll_ByFundingId(fundingId);

        // when
        List<Contributor> result = contributorService.findAllByFundingId(fundingId);

        // then
        verify(contributorRepository, times(1)).findAll_ByFundingId(fundingId);
        assertThat(result).hasSize(2);
    }

    @Test
    void givenNoneContributors_whenFindAllByFundingId_thenReturnEmptyList() {
        // given
        Long fundingId = 1L;
        List<Contributor> contributors = List.of();
        doReturn(contributors).when(contributorRepository).findAll_ByFundingId(fundingId);

        // when
        List<Contributor> result = contributorService.findAllByFundingId(fundingId);

        // then
        verify(contributorRepository, times(1)).findAll_ByFundingId(fundingId);
        assertThat(result).isEmpty();
    }
}
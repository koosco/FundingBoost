package practice.fundingboost2.item.funding.app;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.fundingboost2.item.funding.repo.entity.Contributor;
import practice.fundingboost2.item.funding.repo.jpa.ContributorRepository;

@Service
@RequiredArgsConstructor
public class ContributorService {

    private final ContributorRepository contributorRepository;

    @Transactional
    public Contributor save(Contributor contributor) {
        return contributorRepository.save(contributor);
    }

    public List<Contributor> findAllByFundingId(Long fundingId) {
        return contributorRepository.findAll_ByFundingId(fundingId);
    }
}

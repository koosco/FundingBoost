package practice.fundingboost2.item.funding.app;

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
    public void save(Contributor contributor) {
        contributorRepository.save(contributor);
    }
}

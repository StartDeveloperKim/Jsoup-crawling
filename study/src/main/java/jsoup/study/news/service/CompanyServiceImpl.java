package jsoup.study.news.service;

import jsoup.study.company.domain.Company;
import jsoup.study.company.domain.MemoryCompanyRepository;
import jsoup.study.news.domain.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MemoryCompanyRepository memoryCompanyRepository;


    @PostConstruct
    public void saveCompaniesInMemoryRepository() {
        List<Company> companies = companyRepository.findAll();
        for (Company company : companies) {
            memoryCompanyRepository.addCompany(company.getName());
        }
    }
}

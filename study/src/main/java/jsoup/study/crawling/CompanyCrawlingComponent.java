package jsoup.study.crawling;

import jsoup.study.company.domain.Company;
import jsoup.study.company.domain.MemoryCompanyRepository;
import jsoup.study.news.domain.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class CompanyCrawlingComponent {

    private final CompanyRepository companyRepository;
    private final MemoryCompanyRepository memoryCompanyRepository;

    @Scheduled(fixedDelay = 20000)
    @Transactional
    public void crawlingCompanies() throws IOException {
        String baseUrl = "https://search.naver.com/search.naver?where=news&sm=tab_pge&query=%EC%86%8D%EB%B3%B4&sort=1&photo=0&field=0&pd=0&ds=&de=&mynews=0&office_type=0&office_section_code=0&news_office_checked=&nso=so:dd,p:all,a:all&start=";
        for (int i = 1; i <= 91; i += 10) {
            Document document = Jsoup.connect(baseUrl + i).get();
            Elements infoGroups = document.select(".info_group > .press");
            saveCompanies(infoGroups);
        }
    }

    public void saveCompanies(Elements infoGroups) {
        log.info("saveCompanies");
        List<Company> companies = new ArrayList<>();
        for (Element infoGroup : infoGroups) {
            String link = infoGroup.attr("href");
            String name = infoGroup.text().split("\\s")[0];
            if (!memoryCompanyRepository.checkDuplicateName(name)) {
                memoryCompanyRepository.addCompany(name);
                companies.add(Company.from(name, link));
            }
        }
        companyRepository.saveAll(companies);
    }
}

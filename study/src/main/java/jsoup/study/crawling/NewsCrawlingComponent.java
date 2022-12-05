package jsoup.study.crawling;

import jsoup.study.company.domain.Company;
import jsoup.study.company.domain.MemoryCompanyRepository;
import jsoup.study.company.domain.NewsRepository;
import jsoup.study.news.domain.CompanyRepository;
import jsoup.study.news.domain.News;
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
public class NewsCrawlingComponent {

    private final NewsRepository newsRepository;
    private final CompanyRepository companyRepository;
    private final MemoryCompanyRepository memoryCompanyRepository;

    @Scheduled(fixedDelay = 20000, initialDelay = 3000)
    @Transactional
    public void crawlingNews() throws IOException {
        String url = "https://search.naver.com/search.naver?where=news&sm=tab_pge&query=%EC%86%8D%EB%B3%B4&sort=1&photo=0&field=0&pd=0&ds=&de=&mynews=0&office_type=0&office_section_code=0&news_office_checked=&nso=so:dd,p:all,a:all&start=1";
        Document document = Jsoup.connect(url).get();

        saveNews(document);
    }

   public void saveNews(Document document) {
        Elements elements = document.select(".news_wrap");

        List<News> news = new ArrayList<>();
        for (Element element : elements) {
            String companyName = element.getElementsByClass("press")
                    .text().split("\\s")[0];
            String title = element.getElementsByClass("news_tit").text();
            String link = element.getElementsByClass("news_tit").attr("href");
            String thumbnail = element.getElementsByClass("api_get").attr("data-lazysrc");
            log.info("thumbnail {}", thumbnail);

            if (memoryCompanyRepository.checkDuplicateName(companyName)) {
                Company company = companyRepository.findByName(companyName);
                news.add(News.of(title, link, thumbnail, company));
            } else {
                memoryCompanyRepository.addCompany(companyName);
                String href = element.getElementsByClass("press").attr("href");
                Company savedCompany = companyRepository.save(Company.from(companyName, href));
                news.add(News.of(title, link, thumbnail, savedCompany));
            }
        }
       newsRepository.saveAll(news);
    }

}

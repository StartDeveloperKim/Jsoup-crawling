package jsoup.study.crawling;

import jsoup.study.company.domain.MemoryCompanyRepository;
import jsoup.study.company.domain.NewsRepository;
import jsoup.study.news.domain.CompanyRepository;
import jsoup.study.news.domain.News;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CrawlingComponent {

    private final NewsRepository newsRepository;
    private final CompanyRepository companyRepository;
    private final MemoryCompanyRepository memoryCompanyRepository;

    public void saveNewsFlash() throws IOException {
        String url = "https://search.naver.com/search.naver?where=news&sm=tab_pge&query=%EC%86%8D%EB%B3%B4&sort=1&photo=0&field=0&pd=0&ds=&de=&mynews=0&office_type=0&office_section_code=0&news_office_checked=&nso=so:dd,p:all,a:all&start=1";
        Document document = Jsoup.connect(url).get();

        Elements newsTitles = document.select(".news_tit");
        Elements infoGroups = document.select(".info_group > a");



        List<News> news = new ArrayList<>();
        for (Element newTitle : newsTitles) {
            String href = newTitle.attr("href");
            String title = newTitle.attr("title");
        }
    }
}

package jsoup.study.crawling;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/crawling")
public class CrawlingController {

    @GetMapping
    public String getCrawling() throws IOException {
        String url = "https://search.naver.com/search.naver?where=news&sm=tab_pge&query=%EC%86%8D%EB%B3%B4&sort=1&photo=0&field=0&pd=0&ds=&de=&mynews=0&office_type=0&office_section_code=0&news_office_checked=&nso=so:dd,p:all,a:all&start=1";
        Document document = Jsoup.connect(url).get();

        Elements elements = document.select(".news_tit");
        Elements infoGroups = document.select(".info_group > a");

        for (Element element : elements) {
            System.out.println("element.text() = " + element.toString());
            System.out.println("element.attr(\"href\") = " + element.attr("href"));
        }
        for (Element infoGroup : infoGroups) {
            System.out.println("infoGroup.toString() = " + infoGroup.toString());
            System.out.println("infoGroup.attr(\"href\") = " + infoGroup.attr("href"));
            System.out.println("infoGroup.text() = " + infoGroup.text());
        }
        return "ok";
    }
}

package jsoup.study.news.domain;

import jsoup.study.company.domain.Company;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@SequenceGenerator(
        name = "NEWS_SEQ_GENERATOR",
        sequenceName = "NEWS_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NEWS_SEQ_GENERATOR")
    @Column(name = "news_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String link;

    private String thumbnail;

    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    protected News() {
    }

    private News(String title, String link, String thumbnail) {
        this.title = title;
        this.link = link;
        this.thumbnail = thumbnail;
        this.createDate = LocalDateTime.now();
    }

    public static News of(String title, String link, String thumbnail, Company company) {
        News news = new News(title, link, thumbnail);
        news.setCompany(company);
        return news;
    }

    private void setCompany(Company company) {
        this.company = company;
        company.getNews().add(this);
    }
}

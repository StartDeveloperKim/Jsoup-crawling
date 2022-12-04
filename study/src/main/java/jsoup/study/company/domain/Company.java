package jsoup.study.company.domain;

import jsoup.study.news.domain.News;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@SequenceGenerator(
        name = "COMPANY_SEQ_GENERATOR",
        sequenceName = "COMPANY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_SEQ_GENERATOR")
    @Column(name = "company_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String link;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<News> news = new ArrayList<>();

    protected Company() {
    }

    private Company(String name, String link) {
        this.link = link;
        this.name = name;
    }

    public static Company from(String name, String link) {
        return new Company(name, link);
    }
}

package jsoup.study.news.domain;

import jsoup.study.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("select c from Company c where c.name=:name")
    Company findByName(@Param("name") String name);
}

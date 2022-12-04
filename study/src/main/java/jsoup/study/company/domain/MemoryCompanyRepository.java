package jsoup.study.company.domain;

import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public class MemoryCompanyRepository {

    private static HashSet<String> companyStore = new HashSet<>();

    public void addCompany(String name) {
        companyStore.add(name);
    }

    public Boolean checkDuplicateName(String name) {
        return companyStore.contains(name);
    }

    public void removeCompany(String name) {
        companyStore.remove(name);
    }
}

package code.hackathon.unisubscribe.DAOs;

import code.hackathon.unisubscribe.models.Company;
import code.hackathon.unisubscribe.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompanyDAOImpl implements CompanyDAO{

    private final CompanyRepository  companyRepository;

    @Override
    public List<Company> allCompanies(long clientId) {
        return null;
    }

    @Override
    public Company addCompany(Company company) {
        return null;
    }

    @Override
    public Company deleteCompany(int id) {
        return null;
    }

    @Override
    public Company updateCompany(Company company) {
        return null;
    }

    @Override
    public Company getCompany(long clientId, long companyId) {
        return null;
    }
}

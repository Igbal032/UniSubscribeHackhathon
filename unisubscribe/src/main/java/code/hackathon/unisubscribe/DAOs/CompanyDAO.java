package code.hackathon.unisubscribe.DAOs;

import code.hackathon.unisubscribe.models.Company;

import java.util.List;

public interface CompanyDAO {

    List<Company> allCompanies(long clientId);

    Company addCompany(Company company);

    Company deleteCompany(int id);

    Company updateCompany(Company company);

    Company getCompany(long clientId,long companyId);
}

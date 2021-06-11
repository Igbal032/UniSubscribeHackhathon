package code.hackathon.unisubscribe.services;

import code.hackathon.unisubscribe.DTOs.CompanyDTO;
import code.hackathon.unisubscribe.models.Company;

import java.util.List;

public interface CompanyService {

    List<CompanyDTO> allCompanies(long clientId);

    CompanyDTO addCompany(long clientId,CompanyDTO companyDTO);

    Company deleteCompany(long id);

    CompanyDTO updateCompany(CompanyDTO company);

    CompanyDTO getCompany(long clientId,long companyId);
}

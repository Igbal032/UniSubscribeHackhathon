package code.hackathon.unisubscribe.services;

import code.hackathon.unisubscribe.DTOs.ClientDTO;
import code.hackathon.unisubscribe.DTOs.CompanyDTO;
import code.hackathon.unisubscribe.models.Company;

import java.util.List;

public interface CompanyService {

    List<CompanyDTO> allCompanies(long clientId);

    List<CompanyDTO> deletedCompanies(long clientId);

    List<CompanyDTO> addCompany(long clientId,CompanyDTO companyDTO);

    List<CompanyDTO> deleteCompany(long clientId, long companyId);

    List<CompanyDTO> updateCompany(long clientId,CompanyDTO company);

    CompanyDTO getCompany(long clientId,long companyId);
}

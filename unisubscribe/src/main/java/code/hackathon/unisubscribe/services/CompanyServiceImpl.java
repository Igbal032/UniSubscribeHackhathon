package code.hackathon.unisubscribe.services;

import code.hackathon.unisubscribe.DAOs.ClientDAO;
import code.hackathon.unisubscribe.DAOs.CompanyDAO;
import code.hackathon.unisubscribe.DTOs.CompanyDTO;
import code.hackathon.unisubscribe.enums.Category;
import code.hackathon.unisubscribe.models.Client;
import code.hackathon.unisubscribe.models.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyDAO companyDAO;
    private final ClientDAO clientDAO;

    @Override
    public List<CompanyDTO> allCompanies(long clientId) {
        List<Company> companies = companyDAO.allCompanies(clientId);
        List<CompanyDTO> companyDTOList = companies.stream().map(company -> {
            return  CompanyDTO.builder()
                    .id(company.getId())
                    .companyName(company.getCompanyName())
                    .price(company.getPrice())
                    .detail(company.getDetail())
                    .isTime(company.isTime())
                    .notifyDate(company.getNotifyDate())
                    .category(company.getCategory().toString()).build();
        }).collect(Collectors.toList());
        return companyDTOList;
    }

    @Override
    public CompanyDTO addCompany(long clientId, CompanyDTO companyDTO) {
        Client client = clientDAO.getClient(clientId);
        LocalDate notifyLocalDate = LocalDate.now().minus(Period.ofDays(companyDTO.getNotifyDate()));
        Company company = Company.builder()
                .companyName(companyDTO.getCompanyName())
                .price(companyDTO.getPrice())
                .detail(companyDTO.getDetail())
//                .category(companyDTO.getCategory())
                .build();
        return null;
    }

    @Override
    public Company deleteCompany(long id) {
        return null;
    }

    @Override
    public CompanyDTO updateCompany(CompanyDTO company) {
        return null;
    }

    @Override
    public CompanyDTO getCompany(long clientId,long companyId) {
        Company company = companyDAO.getCompany(clientId,companyId);
        CompanyDTO companyDTO = CompanyDTO.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .category(company.getCategory().toString())
                .price(company.getPrice())
                .isTime(company.isTime())
                .detail(company.getDetail())
                .build();
        return companyDTO;
    }
}

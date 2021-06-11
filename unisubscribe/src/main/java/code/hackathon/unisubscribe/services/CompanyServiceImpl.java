package code.hackathon.unisubscribe.services;

import code.hackathon.unisubscribe.DAOs.ClientDAO;
import code.hackathon.unisubscribe.DAOs.CompanyDAO;
import code.hackathon.unisubscribe.DTOs.ClientDTO;
import code.hackathon.unisubscribe.DTOs.CompanyDTO;
import code.hackathon.unisubscribe.enums.Category;
import code.hackathon.unisubscribe.exceptions.ClientNotFound;
import code.hackathon.unisubscribe.models.Client;
import code.hackathon.unisubscribe.models.Company;
import code.hackathon.unisubscribe.utils.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
        List<Company> companies = companyDAO.allCompanies(clientId)
                .stream().filter(company -> company.getDeletedDate()==null).collect(Collectors.toList());
        companyDAO.allCompanies(clientId)
                .stream().filter(company -> company.getDeletedDate()==null).forEach(cm->{

                    Duration difference = Duration.between(cm.getExpiredDate(),cm.getNotificationDate());

        });

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
    public List<CompanyDTO> deletedCompanies(long clientId) {
        List<Company> deletedCompanies = companyDAO.allCompanies(clientId)
                .stream().filter(company -> company.getDeletedDate()!=null).collect(Collectors.toList());
        List<CompanyDTO> companyDTOList = convertModelsToDTOs(deletedCompanies);

        return companyDTOList;
    }

    @Override
    public List<CompanyDTO> addCompany(long clientId, CompanyDTO companyDTO) {
        Client client = clientDAO.getClient(clientId);
        Company company = convertDtoToModel(client, companyDTO);
        companyDAO.addCompany(clientId,company);
        List<Company> companies = companyDAO.allCompanies(clientId)
                .stream()
                .filter(c->c.getDeletedDate()==null)
                .collect(Collectors.toList());
        List<CompanyDTO> companyDTOList = convertModelsToDTOs(companies);
        return companyDTOList;
    }

    @Override
    public List<CompanyDTO> deleteCompany(long clientId, long companyId) {
        companyDAO.deleteCompany(clientId,companyId);
        List<Company> companies = companyDAO.allCompanies(clientId)
                .stream()
                .filter(c->c.getDeletedDate()==null)
                .collect(Collectors.toList());
        List<CompanyDTO> companyDTOList = convertModelsToDTOs(companies);
        return companyDTOList;
    }

    @Override
    public List<CompanyDTO> updateCompany(long clientId,long companyId,CompanyDTO companyDTO) {
        Client client = clientDAO.getClient(clientId);
        if (client==null)
            throw  new ClientNotFound("Not Found");
        Company company = convertDtoToModel(client,companyDTO);
        Company company1 = companyDAO.updateCompany(companyId,company);
        System.out.println(company1.getCompanyName()+"  geldi");
        List<Company> companies = companyDAO.allCompanies(clientId)
                .stream()
                .filter(c->c.getDeletedDate()==null)
                .collect(Collectors.toList());
        List<CompanyDTO> companyDTOList = convertModelsToDTOs(companies);
        return companyDTOList;
    }

    @Override
    public CompanyDTO getCompany(long clientId,long companyId) {
        Company company = companyDAO.getCompany(clientId,companyId);
        CompanyDTO companyDTO = convertModelToDTO(company);
        return companyDTO;
    }


    public List<CompanyDTO> convertModelsToDTOs(List<Company> companies){
        List<CompanyDTO> companyDTOList = companies.stream().map(company -> {
            return  CompanyDTO.builder()
                    .id(company.getId())
                    .companyName(company.getCompanyName())
                    .price(company.getPrice())
                    .detail(company.getDetail())
                    .isTime(company.isTime())
                    .expiredDate(company.getExpiredDate())
                    .notifyDate(company.getNotifyDate())
                    .category(company.getCategory().toString()).build();
        }).collect(Collectors.toList());
        return companyDTOList;
    }

    public Company convertDtoToModel(Client client,CompanyDTO companyDTO){
        System.out.println(client+" client");
        System.out.println(companyDTO+" companyDTO" );
        LocalDate notifyLocalDate = companyDTO.getExpiredDate().minus(Period.ofDays(companyDTO.getNotifyDate()));
        Category category = Category.valueOf(companyDTO.getCategory());
        Company company = Company.builder()
                .companyName(companyDTO.getCompanyName())
                .price(companyDTO.getPrice())
                .detail(companyDTO.getDetail())
                .notifyDate(companyDTO.getNotifyDate())
                .notificationDate(notifyLocalDate)
                .link(companyDTO.getLink())
                .client(client)
                .isTime(companyDTO.isTime())
                .expiredDate(companyDTO.getExpiredDate())
                .category(category)
                .createdDate(LocalDate.now())
                .build();
        return company;
    }

    public CompanyDTO convertModelToDTO(Company company){
        CompanyDTO companyDTO = CompanyDTO.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .category(company.getCategory().toString())
                .price(company.getPrice())
                .isTime(company.isTime())
                .notifyDate(company.getNotifyDate())
                .link(company.getLink())
                .detail(company.getDetail())
                .expiredDate(company.getExpiredDate())
                .build();
        return companyDTO;
    }

    public <T> Pagination<?> pagination(List<T> data, int pageNumber, int itemCountPerPage, StringBuffer url) {

        Pagination pagination = new Pagination(data, itemCountPerPage, pageNumber, url);
        return pagination;
    }


}

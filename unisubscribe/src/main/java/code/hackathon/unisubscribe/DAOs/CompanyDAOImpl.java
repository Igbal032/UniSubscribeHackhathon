package code.hackathon.unisubscribe.DAOs;

import code.hackathon.unisubscribe.exceptions.ClientNotFound;
import code.hackathon.unisubscribe.exceptions.CompanyNotFound;
import code.hackathon.unisubscribe.models.Client;
import code.hackathon.unisubscribe.models.Company;
import code.hackathon.unisubscribe.repositories.ClientRepository;
import code.hackathon.unisubscribe.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CompanyDAOImpl implements CompanyDAO{

    @PersistenceContext
    EntityManager entityManager;


    CompanyRepository  companyRepository;
    ClientRepository clientRepository;
    public CompanyDAOImpl(CompanyRepository companyRepository,ClientRepository clientRepository) {
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Company> allCompanies(long clientId) {
//        List<Company> companies = entityManager
//                .createQuery("SELECT client.companies FROM Client client WHERE client.id = :id", Company.class)
//                .setParameter("id", clientId).getResultList();
        List<Company> all = clientRepository.findById(clientId).get().getCompanies();
        System.out.println(all);
        if (all==null){
            throw new CompanyNotFound("sasa");
        }
        else {
            return all;
        }
    }

    @Override
    public Company addCompany(long clientId, Company company) {
        System.out.println(company+ " company");
        System.out.println(clientId+ " clientId");
        Client client = clientRepository.getClientById(clientId);
         client.getCompanies().add(company);
        System.out.println(company);
//        Client client = entityManager.createQuery("SELECT client FROM Client client WHERE client.id = :id", Client.class)
//                .setParameter("id", clientId).getSingleResult();
//        if (client==null){
//            throw new ClientNotFound("Client not found");
//        }else{
//
//        }
//
//
//        Company addedCompany = entityManager
//                .createNativeQuery("Insert :c into Company company WHERE company.client = :id", Company.class)
//                .setParameter("id", clientId).getSingleResult();
        return company;
    }

    @Override
    public Company deleteCompany(long clientId,long companyId) {
        Company findCompany = companyRepository.getById(companyId);
        findCompany.setDeletedDate(LocalDate.now());
        companyRepository.save(findCompany);

        return findCompany;
    }

    @Override
    public Company updateCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company getCompany(long clientId, long companyId) {
        Company  foundedCompany = null;
        List<Company> companies = entityManager
                .createQuery("SELECT client.companies FROM Client client WHERE client.id = :id", Company.class)
                .setParameter("id", clientId).getResultList();
        foundedCompany = companies.stream().filter(i-> i.getId()==companyId).findFirst().get();
        if (foundedCompany==null){
            throw new CompanyNotFound("Company not found");
        }
        else {
            return foundedCompany;
        }

    }
}
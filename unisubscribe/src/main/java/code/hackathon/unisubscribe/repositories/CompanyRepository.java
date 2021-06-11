package code.hackathon.unisubscribe.repositories;

import code.hackathon.unisubscribe.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository  extends CrudRepository<Company, Long> {

    Company getById(long clientId);

}
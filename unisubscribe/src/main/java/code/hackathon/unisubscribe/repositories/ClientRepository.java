package code.hackathon.unisubscribe.repositories;

import code.hackathon.unisubscribe.models.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

    Client getClientById(long clientId);


    @Query(value = "select c from Client c where c.email=:email")
    Client getClientByEmail(String email);
}
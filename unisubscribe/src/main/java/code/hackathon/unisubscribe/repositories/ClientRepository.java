package code.hackathon.unisubscribe.repositories;

import code.hackathon.unisubscribe.models.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

    Client getClientById(long clientId);
}
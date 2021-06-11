package code.hackathon.unisubscribe.DAOs;

import code.hackathon.unisubscribe.models.Client;
import code.hackathon.unisubscribe.repositories.ClientRepository;
import org.springframework.stereotype.Component;

@Component
public class ClientDAOImpl implements ClientDAO{
    ClientRepository clientRepository;

    public ClientDAOImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client getClient(long id) {
        return clientRepository.getClientById(
                id);
    }

    @Override
    public Client add(Client newClient) {
        clientRepository.save(newClient);
        return newClient;
    }
}
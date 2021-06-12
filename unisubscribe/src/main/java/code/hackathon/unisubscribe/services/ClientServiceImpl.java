package code.hackathon.unisubscribe.services;

import code.hackathon.unisubscribe.DAOs.ClientDAO;
import code.hackathon.unisubscribe.DTOs.ClientDTO;
import code.hackathon.unisubscribe.controllers.ClientController;
import code.hackathon.unisubscribe.exceptions.ClientNotFound;
import code.hackathon.unisubscribe.models.Client;
import code.hackathon.unisubscribe.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    private final ClientDAO clientDAO;
    private final ClientRepository clientRepository;
    Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Override
    public ClientDTO getClient(long id) {
        Client client = clientDAO.getClient(id);
        ClientDTO clientDTO = ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .build();
        return clientDTO;
    }

    @Override
    public ClientDTO addClient(Client newClient) {
        Client findClient = clientRepository.getClientByEmail(newClient.getEmail());
        if (findClient!=null){
            throw new ClientNotFound("Not fount");
        }
        logger.info("Get Client");
        String encPassword = new BCryptPasswordEncoder().encode(newClient.getPassword());
        newClient.setPassword(encPassword);
        newClient.setCreatedDate(LocalDate.now());
        Client client = clientDAO.addUser(newClient);
        ClientDTO clientDTO = convertModelToDTO(client);
        return clientDTO;
    }

    public ClientDTO convertModelToDTO(Client client){
        ClientDTO clientDTO = ClientDTO.builder()
                .name(client.getName())
                .surname(client.getSurname())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .createdDate(client.getCreatedDate())
                .build();
        return clientDTO;
    }
}

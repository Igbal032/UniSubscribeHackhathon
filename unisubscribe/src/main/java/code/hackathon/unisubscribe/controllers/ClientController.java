package code.hackathon.unisubscribe.controllers;


import code.hackathon.unisubscribe.DTOs.ClientDTO;
import code.hackathon.unisubscribe.DTOs.CompanyDTO;
import code.hackathon.unisubscribe.models.Client;
import code.hackathon.unisubscribe.services.ClientService;
import code.hackathon.unisubscribe.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final CompanyService companyService;

    Logger logger = LoggerFactory.getLogger(ClientController.class);

    /*
    get user with id
     */
    @PostMapping("/setUser")
    public ResponseEntity<Client> getClient(@RequestBody Client client){
        logger.info("Get Client");
        Client client1 = clientService.add(client);
        return new ResponseEntity<>(client1,HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable long clientId){
        logger.info("Get Client 11");
        ClientDTO clientDTO = clientService.getClient(clientId);
        logger.info("Get Client");
        return new ResponseEntity<>(clientDTO,HttpStatus.OK);
    }
    /*
    get all companies with user id
     */
    @GetMapping("/{clientId}/companies")
    public ResponseEntity<List<CompanyDTO>> getCompanies(@PathVariable long clientId){
        List<CompanyDTO> companyDTOList = companyService.allCompanies(clientId);
        logger.info("Get All Companies");
        return new ResponseEntity<>(companyDTOList,HttpStatus.OK);
    }

    /*
    create company
     */
    @PostMapping("/{clientId}/companies")
    public ResponseEntity<List<CompanyDTO>> createCompany(@PathVariable long clientId,@RequestBody CompanyDTO companyDTO){
        companyService.addCompany(clientId,companyDTO);
        List<CompanyDTO> companyDTOList = companyService.allCompanies(clientId);
        logger.info("Create Company");
        return new ResponseEntity<>(companyDTOList,HttpStatus.OK);
    }
    /*
    get company
     */
    @GetMapping("/{clientId}/{companyId}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable long clientId,@PathVariable long companyId){
        CompanyDTO newCompanyDTO = companyService.getCompany(clientId,companyId);
        logger.info("Get Company");
        return new ResponseEntity<>(newCompanyDTO,HttpStatus.OK);
    }

    @DeleteMapping("{clientId}/delete/{companyId}")
    public ResponseEntity<List<CompanyDTO>> deleteCompany(@PathVariable long clientId,@PathVariable long companyId){
        List<CompanyDTO> companyDTOList =  companyService.deleteCompany(clientId,companyId);
        logger.info("Delete Company");
        return new ResponseEntity<>(companyDTOList,HttpStatus.OK);
    }
    @GetMapping("/{clientId}/companies/deletedCompanies")
    public ResponseEntity<List<CompanyDTO>> getDeletedCompanies(@PathVariable long clientId){
        List<CompanyDTO> companyDTOList = companyService.deletedCompanies(clientId);
        logger.info("Get Deleted Companies");
        return new ResponseEntity<>(companyDTOList,HttpStatus.OK);
    }

}

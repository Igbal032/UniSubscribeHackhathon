package code.hackathon.unisubscribe.controllers;


import code.hackathon.unisubscribe.DTOs.ClientDTO;
import code.hackathon.unisubscribe.DTOs.CompanyDTO;
import code.hackathon.unisubscribe.models.Client;
import code.hackathon.unisubscribe.services.ClientService;
import code.hackathon.unisubscribe.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("api/students")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final CompanyService companyService;

    /*
    get user with id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable long clientId){
        ClientDTO clientDTO = clientService.getClient(clientId);
        return new ResponseEntity<>(clientDTO,HttpStatus.OK);
    }
    /*
    get all companies with user id
     */
    @GetMapping("/{id/}companies")
    public ResponseEntity<List<CompanyDTO>> getCompanies(@PathVariable long clientId){
        List<CompanyDTO> companyDTOList = companyService.allCompanies(clientId);
        return new ResponseEntity<>(companyDTOList,HttpStatus.OK);
    }

    /*
    create company
     */
    @PostMapping("/{id/}companies")
    public ResponseEntity<CompanyDTO> createCompany(@PathVariable long clientId,@RequestBody CompanyDTO companyDTO){
        CompanyDTO newCompanyDTO = companyService.addCompany(clientId,companyDTO);
        return new ResponseEntity<>(newCompanyDTO,HttpStatus.OK);
    }

    /*
    get company
     */
    @GetMapping("/{clientId/}/{companyId}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable long clientId,@PathVariable long companyId){
        CompanyDTO newCompanyDTO = companyService.getCompany(clientId,companyId);
        return new ResponseEntity<>(newCompanyDTO,HttpStatus.OK);
    }

}

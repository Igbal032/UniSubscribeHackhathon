package code.hackathon.unisubscribe.controllers;

import code.hackathon.unisubscribe.DTOs.ClientDTO;
import code.hackathon.unisubscribe.enums.Category;
import code.hackathon.unisubscribe.models.Client;
import code.hackathon.unisubscribe.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("api")
@RequiredArgsConstructor
public class CommonController {

    private final ClientService clientService;
//    private final OncePerRequestFilter corsFilter;

    @GetMapping("/getCategories")
    public ResponseEntity<List<String>> allCategories(){
        List<String> categories = Stream.of(Category.values())
                .map(Category::name)
                .collect(Collectors.toList());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ClientDTO> addClient(@RequestBody Client client){
        ClientDTO clientDTO =  clientService.addClient(client);
        return new ResponseEntity<>(clientDTO,HttpStatus.OK);
    }

}

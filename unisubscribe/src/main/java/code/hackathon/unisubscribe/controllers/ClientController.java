package code.hackathon.unisubscribe.controllers;


import code.hackathon.unisubscribe.DTOs.ClientDTO;
import code.hackathon.unisubscribe.DTOs.SubscriptionDTO;
import code.hackathon.unisubscribe.enums.Category;
import code.hackathon.unisubscribe.models.Client;
import code.hackathon.unisubscribe.services.ClientService;
import code.hackathon.unisubscribe.services.SubscriptionService;
import code.hackathon.unisubscribe.utils.Pagination;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final SubscriptionService subscriptionService;
    private final JavaMailSender javaMailSender;

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
    get all subscription with user id
     */
    @GetMapping("/{clientId}/subscriptions")
    public ResponseEntity<?> getSubscriptions(@PathVariable long clientId, @RequestParam(required = false) Integer pageNumber,
                                              @RequestParam(required = false) Integer countOfData,
                                              HttpServletRequest httpServletRequest){

        List<SubscriptionDTO> subscriptionDTOList = subscriptionService.allSubscriptions(clientId);
        if (pageNumber!=null&&countOfData!=null){
            Pagination<?> pagination = subscriptionService.pagination(subscriptionDTOList,pageNumber,countOfData,httpServletRequest.getRequestURL());
            return new ResponseEntity<>(pagination, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(subscriptionDTOList, HttpStatus.OK);
    }


    /*
    create subscription
     */
    @PostMapping("/{clientId}/subscription")
    public ResponseEntity<?> createSubscription(@PathVariable long clientId, @RequestBody SubscriptionDTO subscriptionDTO,
                                                @RequestParam(required = false) Integer pageNumber,
                                                @RequestParam(required = false) Integer countOfData,
                                                HttpServletRequest httpServletRequest){
        subscriptionService.addSubscription(clientId, subscriptionDTO);
        List<SubscriptionDTO> subscriptionDTOList = subscriptionService.allSubscriptions(clientId);
        logger.info("Create Subscription");
        if (pageNumber!=null&&countOfData!=null){
            Pagination<?> pagination = subscriptionService.pagination(subscriptionDTOList,pageNumber,countOfData,httpServletRequest.getRequestURL());
            return new ResponseEntity<>(pagination, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(subscriptionDTOList, HttpStatus.OK);
    }
    /*
    get subscription
     */
    @GetMapping("/{clientId}/subscriptions/{subscriptionId}")
    public ResponseEntity<SubscriptionDTO> getSubscription(@PathVariable long clientId, @PathVariable long subscriptionId){
        SubscriptionDTO newSubscriptionDTO = subscriptionService.getSubscription(clientId, subscriptionId);
        logger.info("Get Subscription");
        return new ResponseEntity<>(newSubscriptionDTO,HttpStatus.OK);
    }

    @DeleteMapping("{clientId}/subscriptions/delete/{subscriptionId}")
    public ResponseEntity<?> deleteSubscription(@PathVariable long clientId, @PathVariable long subscriptionId,
                                                @RequestParam(required = false) Integer pageNumber,
                                                @RequestParam(required = false) Integer countOfData,
                                                HttpServletRequest httpServletRequest){
        List<SubscriptionDTO> subscriptionDTOList =  subscriptionService.deleteSubscription(clientId, subscriptionId);
        if (pageNumber!=null&&countOfData!=null){
            Pagination<?> pagination = subscriptionService.pagination(subscriptionDTOList,pageNumber,countOfData,httpServletRequest.getRequestURL());
            return new ResponseEntity<>(pagination, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(subscriptionDTOList, HttpStatus.OK);
    }
    @GetMapping("/{clientId}/subscriptions/deletedSubscriptions")
    public ResponseEntity<?> getDeletedSubscriptions(@PathVariable long clientId,
                                                     @RequestParam(required = false) Integer pageNumber,
                                                     @RequestParam(required = false) Integer countOfData,
                                                     HttpServletRequest httpServletRequest){
        List<SubscriptionDTO> subscriptionDTOList = subscriptionService.deletedSubscription(clientId);
        if (pageNumber!=null&&countOfData!=null){
            Pagination<?> pagination = subscriptionService.pagination(subscriptionDTOList,pageNumber,countOfData,httpServletRequest.getRequestURL());
            return new ResponseEntity<>(pagination, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(subscriptionDTOList, HttpStatus.OK);
    }

    @PutMapping("/{clientId}/subscriptions/update/{subscriptionId}")
    public ResponseEntity<?> updateSubscription(@PathVariable long clientId, @PathVariable long subscriptionId, @RequestBody SubscriptionDTO subscriptionDTO,
                                                @RequestParam(required = false) Integer pageNumber,
                                                @RequestParam(required = false) Integer countOfData,
                                                HttpServletRequest httpServletRequest){
        List<SubscriptionDTO> subscriptionDTOList = subscriptionService.updateSubscription(clientId, subscriptionId, subscriptionDTO);
        logger.info("Get Deleted subscription");
        if (pageNumber!=null&&countOfData!=null){
            Pagination<?> pagination = subscriptionService.pagination(subscriptionDTOList,pageNumber,countOfData,httpServletRequest.getRequestURL());
            return new ResponseEntity<>(pagination, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(subscriptionDTOList, HttpStatus.OK);
    }

    @GetMapping("/getSubscriptionByCategory/{category}/{clientId}")
    public ResponseEntity<?> getSubscriptionByCategory(@PathVariable long clientId, @PathVariable String category,
                                                        @RequestParam(required = false) Integer pageNumber,
                                                        @RequestParam(required = false) Integer countOfData,
                                                        HttpServletRequest httpServletRequest){
        List<SubscriptionDTO> subscriptionDTOList = subscriptionService.getSubscriptionByCategory(clientId,category);
        if (pageNumber!=null&&countOfData!=null){
            Pagination<?> pagination = subscriptionService.pagination(subscriptionDTOList,pageNumber,countOfData,httpServletRequest.getRequestURL());
            return new ResponseEntity<>(pagination, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(subscriptionDTOList, HttpStatus.OK);

    }

    @PostMapping("/sendMail")
    public ResponseEntity<String> sendEmailToClients(@RequestParam String email,
                                                     @RequestParam String subject,
                                                     @RequestParam String content){

        subscriptionService.sendEmail(email,subject,content);
        return new ResponseEntity<>(HttpStatus.OK);
    }





}

package code.hackathon.unisubscribe.controllers;


import code.hackathon.unisubscribe.DTOs.ClientDTO;
import code.hackathon.unisubscribe.DTOs.SubscriptionDTO;
import code.hackathon.unisubscribe.config.JwtTokenUtil;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

//@CrossOrigin(
//        allowCredentials = "true",
//        origins = "*",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT}
//)
@Controller
@RequestMapping("api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final SubscriptionService subscriptionService;
    private final JwtTokenUtil jwtTokenUtil;

    Logger logger = LoggerFactory.getLogger(ClientController.class);

    /*
    get user with id
     */

    @GetMapping("/")
    public ResponseEntity<ClientDTO> getClient( HttpServletRequest httpServletRequest){
        logger.info("Get Client ");
        long userId = jwtTokenUtil.getUserId(httpServletRequest.getHeader("Authorization"));
        ClientDTO clientDTO = clientService.getClient(userId);
        return new ResponseEntity<>(clientDTO,HttpStatus.OK);
    }
    /*
    get all subscription with user id
     */
    @GetMapping("/subscriptions")
    public ResponseEntity<?> getSubscriptions(@RequestParam(required = false) Integer pageNumber,
                                              @RequestParam(required = false) Integer countOfData,
                                              HttpServletRequest httpServletRequest){
        long userId = jwtTokenUtil.getUserId(httpServletRequest.getHeader("Authorization"));
        List<SubscriptionDTO> subscriptionDTOList = subscriptionService.allSubscriptions(userId);
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
    @PostMapping("/subscription")
    public ResponseEntity<?> createSubscription(HttpServletRequest request, @RequestBody SubscriptionDTO subscriptionDTO,
                                                @RequestParam(required = false) Integer pageNumber,
                                                @RequestParam(required = false) Integer countOfData,
                                                HttpServletRequest httpServletRequest){
        long userId = jwtTokenUtil.getUserId(request.getHeader("Authorization"));
        subscriptionService.addSubscription(userId, subscriptionDTO);
        List<SubscriptionDTO> subscriptionDTOList = subscriptionService.allSubscriptions(userId);
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
    @GetMapping("/subscriptions/{subscriptionId}")
    public ResponseEntity<SubscriptionDTO> getSubscription(@PathVariable long subscriptionId,
                                                           HttpServletRequest httpServletRequest){
        long userId = jwtTokenUtil.getUserId(httpServletRequest.getHeader("Authorization"));
        SubscriptionDTO newSubscriptionDTO = subscriptionService.getSubscription(userId, subscriptionId);
        logger.info("Get Subscription");
        return new ResponseEntity<>(newSubscriptionDTO,HttpStatus.OK);
    }

    @DeleteMapping("subscriptions/delete/{subscriptionId}")
    public ResponseEntity<?> deleteSubscription(@PathVariable long subscriptionId,
                                                @RequestParam(required = false) Integer pageNumber,
                                                @RequestParam(required = false) Integer countOfData,
                                                HttpServletRequest httpServletRequest){
        long userId = jwtTokenUtil.getUserId(httpServletRequest.getHeader("Authorization"));
        List<SubscriptionDTO> subscriptionDTOList =  subscriptionService.deleteSubscription(userId, subscriptionId);
        if (pageNumber!=null&&countOfData!=null){
            Pagination<?> pagination = subscriptionService.pagination(subscriptionDTOList,pageNumber,countOfData,httpServletRequest.getRequestURL());
            return new ResponseEntity<>(pagination, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(subscriptionDTOList, HttpStatus.OK);
    }
    @GetMapping("/subscriptions/deletedSubscriptions")
    public ResponseEntity<?> getDeletedSubscriptions(
                                                     @RequestParam(required = false) Integer pageNumber,
                                                     @RequestParam(required = false) Integer countOfData,
                                                     HttpServletRequest httpServletRequest){
        long userId = jwtTokenUtil.getUserId(httpServletRequest.getHeader("Authorization"));
        List<SubscriptionDTO> subscriptionDTOList = subscriptionService.deletedSubscription(userId);
        if (pageNumber!=null&&countOfData!=null){
            Pagination<?> pagination = subscriptionService.pagination(subscriptionDTOList,pageNumber,countOfData,httpServletRequest.getRequestURL());
            return new ResponseEntity<>(pagination, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(subscriptionDTOList, HttpStatus.OK);
    }

    @GetMapping("/subscriptions/undelete/{subscriptionId}")
    public ResponseEntity<?> unDeleteSubscriptions(@PathVariable long  subscriptionId,
                                                     @RequestParam(required = false) Integer pageNumber,
                                                     @RequestParam(required = false) Integer countOfData,
                                                     HttpServletRequest httpServletRequest){
        long userId = jwtTokenUtil.getUserId(httpServletRequest.getHeader("Authorization"));
        List<SubscriptionDTO> subscriptionDTOList = subscriptionService.undeleteSubscription(userId,subscriptionId);
        if (pageNumber!=null&&countOfData!=null){
            Pagination<?> pagination = subscriptionService.pagination(subscriptionDTOList,pageNumber,countOfData,httpServletRequest.getRequestURL());
            return new ResponseEntity<>(pagination, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(subscriptionDTOList, HttpStatus.OK);
    }
    @PutMapping("/subscriptions/update/{subscriptionId}")
    public ResponseEntity<?> updateSubscription(@PathVariable long subscriptionId, @RequestBody SubscriptionDTO subscriptionDTO,
                                                @RequestParam(required = false) Integer pageNumber,
                                                @RequestParam(required = false) Integer countOfData,
                                                HttpServletRequest httpServletRequest){
        long userId = jwtTokenUtil.getUserId(httpServletRequest.getHeader("Authorization"));
        List<SubscriptionDTO> subscriptionDTOList = subscriptionService.updateSubscription(userId, subscriptionId, subscriptionDTO);
        logger.info("Get Deleted subscription");
        if (pageNumber!=null&&countOfData!=null){
            Pagination<?> pagination = subscriptionService.pagination(subscriptionDTOList,pageNumber,countOfData,httpServletRequest.getRequestURL());
            return new ResponseEntity<>(pagination, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(subscriptionDTOList, HttpStatus.OK);
    }

    @GetMapping("/getSubscriptionByCategory/{category}")
    public ResponseEntity<?> getSubscriptionByCategory(@PathVariable String category,
                                                        @RequestParam(required = false) Integer pageNumber,
                                                        @RequestParam(required = false) Integer countOfData,
                                                        HttpServletRequest httpServletRequest){
        long userId = jwtTokenUtil.getUserId(httpServletRequest.getHeader("Authorization"));
        List<SubscriptionDTO> subscriptionDTOList = subscriptionService.getSubscriptionByCategory(userId,category);
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

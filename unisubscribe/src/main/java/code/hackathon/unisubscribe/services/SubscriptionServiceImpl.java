package code.hackathon.unisubscribe.services;

import code.hackathon.unisubscribe.DAOs.ClientDAO;
import code.hackathon.unisubscribe.DAOs.SubscriptionDAO;
import code.hackathon.unisubscribe.DTOs.SubscriptionDTO;
import code.hackathon.unisubscribe.enums.Category;
import code.hackathon.unisubscribe.exceptions.ClientNotFound;
import code.hackathon.unisubscribe.models.Client;
import code.hackathon.unisubscribe.models.Subscription;
import code.hackathon.unisubscribe.repositories.SubscriptionRepository;
import code.hackathon.unisubscribe.utils.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionDAO subscriptionDAO;
    private final ClientDAO clientDAO;
    private final JavaMailSender javaMailSender;

    private final SubscriptionRepository subscriptionRepository;
    @Override
    public List<SubscriptionDTO> allSubscriptions(long clientId) {
        List<Subscription> subscriptions = subscriptionDAO.allSubscriptions(clientId)
                .stream().filter(subscription -> subscription.getDeletedDate()==null).collect(Collectors.toList());
        List<SubscriptionDTO> subscriptionDTOList = convertModelsToDTOs(subscriptions);
        return subscriptionDTOList;
    }

    @Override
    public List<SubscriptionDTO> deletedSubscription(long clientId) {
        List<Subscription> deletedSubscriptions = subscriptionDAO.allSubscriptions(clientId)
                .stream().filter(subscription -> subscription.getDeletedDate()!=null).collect(Collectors.toList());
        List<SubscriptionDTO> subscriptionDTOList = convertModelsToDTOs(deletedSubscriptions);

        return subscriptionDTOList;
    }

    @Override
    public List<SubscriptionDTO> addSubscription(long clientId, SubscriptionDTO subscriptionDTO) {
        Client client = clientDAO.getClient(clientId);
        subscriptionDTO.setNotified(false);
        Subscription subscription = convertDtoToModel(client, subscriptionDTO);
        subscriptionDAO.addSubscription(clientId, subscription);
        List<Subscription> subscriptions = subscriptionDAO.allSubscriptions(clientId)
                .stream()
                .filter(c->c.getDeletedDate()==null)
                .collect(Collectors.toList());
        List<SubscriptionDTO> subscriptionDTOList = convertModelsToDTOs(subscriptions);
        return subscriptionDTOList;
    }

    @Override
    public List<SubscriptionDTO> deleteSubscription(long clientId, long subscriptionId) {
        subscriptionDAO.deleteSubscription(clientId, subscriptionId);
        List<Subscription> subscriptions = subscriptionDAO.allSubscriptions(clientId)
                .stream()
                .filter(c->c.getDeletedDate()==null)
                .collect(Collectors.toList());
        List<SubscriptionDTO> subscriptionDTOList = convertModelsToDTOs(subscriptions);
        return subscriptionDTOList;
    }

    @Override
    public List<SubscriptionDTO> undeleteSubscription(long clientId, long subscriptionId) {
        subscriptionDAO.undeleteSubscription(clientId, subscriptionId);
        List<Subscription> deletedSubscriptions = subscriptionDAO.allSubscriptions(clientId)
                .stream().filter(subscription -> subscription.getDeletedDate()!=null).collect(Collectors.toList());
        List<SubscriptionDTO> subscriptionDTOList = convertModelsToDTOs(deletedSubscriptions);
        return subscriptionDTOList;
    }

    @Override
    public List<SubscriptionDTO> updateSubscription(long clientId, long subscriptionId, SubscriptionDTO subscriptionDTO) {
        Client client = clientDAO.getClient(clientId);
        if (client==null)
            throw  new ClientNotFound("Not Found");
        Subscription subscription = convertDtoToModel(client, subscriptionDTO);
        Subscription subscription1 = subscriptionDAO.updateSubscription(subscriptionId, subscription);
        List<Subscription> subscriptions = subscriptionDAO.allSubscriptions(clientId)
                .stream()
                .filter(c->c.getDeletedDate()==null)
                .collect(Collectors.toList());
        List<SubscriptionDTO> subscriptionDTOList = convertModelsToDTOs(subscriptions);
        return subscriptionDTOList;
    }

    @Override
    public List<SubscriptionDTO> getSubscriptionByCategory(long clientId, String category) {

        Category category1 = Category.valueOf(category.toUpperCase());
        List<Subscription> subscriptionLiST = subscriptionDAO.allSubscriptions(clientId)
                .stream().filter(w->w.getDeletedDate()==null&&w.getCategory().equals(category1))
                .collect(Collectors.toList());
        List<SubscriptionDTO> subscriptionDTOList = convertModelsToDTOs(subscriptionLiST);
        return subscriptionDTOList;
    }

    @Scheduled(fixedRate = 300000)
    public List<Subscription> checkSubscriptions() {
        List<Subscription> subscriptions = subscriptionDAO.getAllSubscriptions();
        for (Subscription subscription : subscriptions){
            LocalDate today = LocalDate.now();
            int difference = differenceOfDate(subscription.getExpiredDate(),today);
            if (difference < subscription.getNotifyDate() && subscription.isNotified()==false){
                subscription.setNotified(true);
                sendEmail(subscription.getClient().getEmail(),
                        "Expired Date will be reached",
                        "Subscription name: "+subscription.getSubscriptionName()+"\n"+"Expired Date: "+subscription.getExpiredDate());
            }
            subscriptionRepository.save(subscription);
        }
        return subscriptions;
    }

    @Override
    public SubscriptionDTO getSubscription(long clientId, long subscriptionId) {
        Subscription subscription = subscriptionDAO.getSubscription(clientId,subscriptionId);
        SubscriptionDTO subscriptionDTO = convertModelToDTO(subscription);
        return subscriptionDTO;
    }


    public List<SubscriptionDTO> convertModelsToDTOs(List<Subscription> subscriptions){
        List<SubscriptionDTO> subscriptionDTOList = subscriptions.stream().map(subscription -> {
            return  SubscriptionDTO.builder()
                    .id(subscription.getId())
                    .subscriptionName(subscription.getSubscriptionName())
                    .price(subscription.getPrice())
                    .detail(subscription.getDetail())
                    .link(subscription.getLink())
                    .notified(subscription.isNotified())
                    .expiredDate(subscription.getExpiredDate())
                    .notifyDate(subscription.getNotifyDate())
                    .category(subscription.getCategory().toString()).build();
        }).collect(Collectors.toList());
        return subscriptionDTOList;
    }

    public Subscription convertDtoToModel(Client client, SubscriptionDTO subscriptionDTO){

//        LocalDate notifyLocalDate = subscriptionDTO.getExpiredDate().minus(Period.ofDays(subscriptionDTO.getNotifyDate()));
        Category category = Category.valueOf(subscriptionDTO.getCategory());
        Subscription subscription = Subscription.builder()
                .subscriptionName(subscriptionDTO.getSubscriptionName())
                .price(subscriptionDTO.getPrice())
                .detail(subscriptionDTO.getDetail())
                .notifyDate(subscriptionDTO.getNotifyDate())
//                .notificationDate(notifyLocalDate)
                .link(subscriptionDTO.getLink())
                .client(client)
                .notified(subscriptionDTO.isNotified())
                .expiredDate(subscriptionDTO.getExpiredDate())
                .category(category)
                .createdDate(LocalDate.now())
                .build();
        return subscription;
    }

    public SubscriptionDTO convertModelToDTO(Subscription subscription){
        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
                .id(subscription.getId())
                .subscriptionName(subscription.getSubscriptionName())
                .category(subscription.getCategory().toString())
                .price(subscription.getPrice())
                .notified(subscription.isNotified())
                .notifyDate(subscription.getNotifyDate())
                .link(subscription.getLink())
                .detail(subscription.getDetail())
                .expiredDate(subscription.getExpiredDate())
                .build();
        return subscriptionDTO;
    }

    public <T> Pagination<?> pagination(List<T> data, int pageNumber, int itemCountPerPage, StringBuffer url) {

        Pagination pagination = new Pagination(data, itemCountPerPage, pageNumber, url);
        return pagination;
    }

    @Override
    public void sendEmail(String to, String subject,String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(content);
        javaMailSender.send(msg);

    }

    public int differenceOfDate(LocalDate today, LocalDate expiredDate){
        Period period = Period.between(today, expiredDate);
        System.out.println(period);
        return period.getDays();
    }
}

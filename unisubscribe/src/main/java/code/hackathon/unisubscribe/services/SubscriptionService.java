package code.hackathon.unisubscribe.services;

import code.hackathon.unisubscribe.DTOs.SubscriptionDTO;
import code.hackathon.unisubscribe.utils.Pagination;

import java.util.List;

public interface SubscriptionService {

    List<SubscriptionDTO> allSubscriptions(long clientId);

    List<SubscriptionDTO> deletedSubscription(long clientId);

    List<SubscriptionDTO> addSubscription(long clientId, SubscriptionDTO subscriptionDTO);

    List<SubscriptionDTO> deleteSubscription(long clientId, long subscriptionId);

    List<SubscriptionDTO> undeleteSubscription(long clientId, long subscriptionId);

    List<SubscriptionDTO> updateSubscription(long clientId, long subscriptionId, SubscriptionDTO company);

    List<SubscriptionDTO> getSubscriptionByCategory(long clientId, String category);

    SubscriptionDTO getSubscription(long clientId, long subscriptionId);

    <T> Pagination<?> pagination(List<T> students, int pageNumber, int countPerPage, StringBuffer url);

    void sendEmail(String to, String subject,String content);

    }

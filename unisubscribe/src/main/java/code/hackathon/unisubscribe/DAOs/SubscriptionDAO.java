package code.hackathon.unisubscribe.DAOs;

import code.hackathon.unisubscribe.models.Subscription;

import java.util.List;

public interface SubscriptionDAO {

    List<Subscription> allSubscriptions(long clientId);

    List<Subscription> getAllSubscriptions();

    Subscription addSubscription(long clientId, Subscription subscription);

    Subscription deleteSubscription(long clientId, long companyId);

    Subscription updateSubscription(long clientId, Subscription subscription);

    Subscription getSubscription(long clientId, long subscriptionId);
}
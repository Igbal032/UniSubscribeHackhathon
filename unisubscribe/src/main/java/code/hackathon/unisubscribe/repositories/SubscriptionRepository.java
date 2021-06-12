package code.hackathon.unisubscribe.repositories;

import code.hackathon.unisubscribe.models.Subscription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    Subscription getSubscriptionById(long subscriptionId);

    @Query(value = "select c from Subscription c where c.deletedDate is null")
    List<Subscription> getAllSubscriptions();

}
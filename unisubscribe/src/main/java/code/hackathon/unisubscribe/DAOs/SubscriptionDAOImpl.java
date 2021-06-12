package code.hackathon.unisubscribe.DAOs;

import code.hackathon.unisubscribe.exceptions.SubscriptionNotFound;
import code.hackathon.unisubscribe.models.Client;
import code.hackathon.unisubscribe.models.Subscription;
import code.hackathon.unisubscribe.repositories.ClientRepository;
import code.hackathon.unisubscribe.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SubscriptionDAOImpl implements SubscriptionDAO {

    @PersistenceContext
    EntityManager entityManager;

    private final SubscriptionRepository subscriptionRepository;
    private final ClientRepository clientRepository;

    @Override
    public List<Subscription> allSubscriptions(long clientId) {
        Client client = clientRepository.getClientById(clientId);
        if (client==null)
            throw new SubscriptionNotFound("NOt Found");
        List<Subscription> subscriptionList = client.getSubscription();
        return subscriptionList;
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        List<Subscription> companies = subscriptionRepository.getAllSubscriptions();
        if (companies==null){
            throw new SubscriptionNotFound("Not Fount");
        }
        return companies;
    }

    @Override
    public Subscription addSubscription(long clientId, Subscription subscription) {
        Client client = clientRepository.getClientById(clientId);
        subscription.setClient(client);
        System.out.println(client.getName() + " Hello Create  in DAO- ");
        subscription.setNotified(false);
        subscriptionRepository.save(subscription);
        return subscription;
    }

    @Override
    public Subscription deleteSubscription(long clientId, long companyId) {
        Subscription findSubscription = subscriptionRepository.getSubscriptionById(companyId);
        findSubscription.setDeletedDate(LocalDate.now());
        subscriptionRepository.save(findSubscription);

        return findSubscription;
    }

    @Override
    public Subscription undeleteSubscription(long clientId, long companyId) {

        Subscription findSubscription = subscriptionRepository.getSubscriptionById(companyId);
        findSubscription.setDeletedDate(null);
        subscriptionRepository.save(findSubscription);
        return findSubscription;
    }

    @Transactional
    @Override
    public Subscription updateSubscription(long companyId, Subscription subscription) {
        System.out.println(subscription);
        Subscription comp = subscriptionRepository.getSubscriptionById(companyId);
        if (comp==null)
            throw new SubscriptionNotFound("Not Fount");
        comp.setSubscriptionName(subscription.getSubscriptionName());
        comp.setPrice(subscription.getPrice());
        comp.setDetail(subscription.getDetail());
        comp.setCategory(subscription.getCategory());
        comp.setLink(subscription.getLink());
        comp.setClient(subscription.getClient());
        comp.setExpiredDate(subscription.getExpiredDate());
        comp.setNotified(subscription.isNotified());
        comp.setNotifyDate(subscription.getNotifyDate());
        comp.setNotificationDate(subscription.getNotificationDate());
        subscriptionRepository.save(comp);
        return comp;
    }

    @Override
    public Subscription getSubscription(long clientId, long companyId) {
        Client client = clientRepository.getClientById(clientId);
        Optional<Subscription> companies = client.getSubscription().stream().filter(w->w.getId()==companyId&&w.getDeletedDate()==null).findFirst();
        if (!companies.isPresent()){
            throw new SubscriptionNotFound("Company not found");
        }
        return companies.get();
    }

}
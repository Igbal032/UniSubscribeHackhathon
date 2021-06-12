package code.hackathon.unisubscribe.models;

import code.hackathon.unisubscribe.enums.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "subscription_name")
    private String subscriptionName;
    private double price;
    private String detail;
    private Category category;
    private String link;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private Client client;
    @Column(name = "expired_date")
    private LocalDate expiredDate;
    @Column(name = "is_time")
    private boolean notified;
    @Column(name = "notify_date")
    private int notifyDate;
    @Column(name = "notification_date")
    private LocalDate notificationDate;
    @Column(name = "created_date")
    private LocalDate createdDate;
    @Column(name = "deleted_date")
    private LocalDate deletedDate;
}

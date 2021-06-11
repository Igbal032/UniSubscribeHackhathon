package code.hackathon.unisubscribe.models;

import code.hackathon.unisubscribe.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "company_name")
    private String companyName;
    private double price;
    private String detail;
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Client client;
    @Column(name = "expired_date")
    private LocalDate expiredDate;
    @Column(name = "is_time")
    private boolean isTime;
    @Column(name = "notify_date")
    private int notifyDate;
    @Column(name = "notification_date")
    private LocalDate notificationDate;
    @Column(name = "created_date")
    private LocalDate createdDate;
    @Column(name = "deleted_date")
    private LocalDate deletedDate;
}

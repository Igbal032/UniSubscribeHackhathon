package code.hackathon.unisubscribe.DTOs;

import code.hackathon.unisubscribe.enums.Category;
import lombok.Builder;
import lombok.Data;


@Data
@Builder(toBuilder = true)
public class CompanyDTO {

    private long id;
    private String companyName;
    private double price;
    private String detail;
    private boolean isTime;
    private int notifyDate;
    private String category;

}

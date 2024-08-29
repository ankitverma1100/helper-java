package helper.helperapi.modelResponse;

import lombok.Data;

import java.util.Date;

@Data
public class GetFancySuspendedRes {
    private String fancyid;
    private String name;
    private String suspendedby;
    private Date createdon;
    private int count;
}

package helper.helperapi.modelResponse;

import lombok.Data;

@Data
public class FancyData {
    private String fancyid;
    private String fancyname;
    private int result;
    private String resultdeclareby;
    private String createdon;
    private RsfancyData rsfancy_data;
    private FancyDetail fancy_details;
}

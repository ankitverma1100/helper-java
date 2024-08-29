package helper.helperapi.modelResponse;

import lombok.Data;

import java.util.List;

@Data
public class FancyResponse {
    private String message;
    private List<FancyData> data;
}

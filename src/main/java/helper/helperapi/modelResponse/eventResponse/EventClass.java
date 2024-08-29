package helper.helperapi.modelResponse.eventResponse;

import lombok.Data;

import java.util.List;

@Data
public class EventClass {
    private Integer sportId;
    private String sport_name;
    private List<EventDataClass> eventList;
}


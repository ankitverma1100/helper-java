package helper.helperapi.modelResponse;

import lombok.Data;

@Data
public class MatchData {
    private int matchidcount ;
//    private MatchCountResponse getlock;
//
    public MatchData(int matchidcount) {
        this.matchidcount =matchidcount;

    }
}

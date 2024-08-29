package helper.helperapi.sqlModels;

import lombok.Data;

import java.io.Serializable;

@Data
public class ObjectCommonResponseModel implements Serializable {

    private static final long serialVersionUID = 1663411392934556481L;

    private boolean status;
    private String message;
    private Object Data;
}

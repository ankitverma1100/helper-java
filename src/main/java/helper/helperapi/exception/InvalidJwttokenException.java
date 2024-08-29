package helper.helperapi.exception;

import lombok.Data;

@Data
//@EqualsAndHashCode(callSuper=false)
public class InvalidJwttokenException extends RuntimeException {

    private static final long serialVersionUID = 5439241001693059339L;

    public String messages = "";

    public InvalidJwttokenException(String errorMessages) {
        super(errorMessages);
        this.messages = errorMessages;
    }
}

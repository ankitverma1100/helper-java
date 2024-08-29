package helper.helperapi.exception;

import lombok.Data;

@Data
public class ErrorMessageCustomException extends RuntimeException {

    private static final long serialVersionUID = -8851973090573713730L;
    private String message;
    private boolean status;
    public ErrorMessageCustomException(String message, boolean status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}

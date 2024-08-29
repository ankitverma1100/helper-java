package helper.helperapi.exception;

public class ModuleCollectionException  extends ExceptionInInitializerError{
    private static final long serialVersionUID = 1L;

    public ModuleCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return " with " + id + " not found!";
    }

    public static String AlreadyExists() {
        return "Todo with given name already exists";
    }

}


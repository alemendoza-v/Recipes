package recipes.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("You are not the owner of this recipe");
    }
}

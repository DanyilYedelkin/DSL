package sk.kpi.tuke.fj;

public class StateMachineException extends RuntimeException {
    public StateMachineException(String message) {
        super(message);
    }

    public StateMachineException(String message, Throwable cause) {
        super(message, cause);
    }
}

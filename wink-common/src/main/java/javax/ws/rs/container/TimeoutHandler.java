package javax.ws.rs.container;

public interface TimeoutHandler {
    void handleTimeout(AsyncResponse asyncResponse);
}

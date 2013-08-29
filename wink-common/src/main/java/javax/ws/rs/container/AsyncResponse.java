package javax.ws.rs.container;

public interface AsyncResponse {
    public boolean resume(Object response);
}

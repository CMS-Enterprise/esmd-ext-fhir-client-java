package gov.cms.esmd.common.model;

public class ClientResponse<T> {
    private T response;
    private boolean success;
    private String error;

    public ClientResponse() {
    }

    public ClientResponse(T response, boolean success, String error) {
        this.response = response;
        this.success = success;
        this.error = error;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
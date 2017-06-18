package aviation.recoding.zeppelin.api;

import com.google.gson.JsonObject;


public class ApiException extends Exception {

    private JsonObject response;

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public void setErrorResponse(JsonObject response) {
        this.response = response;
    }

    public JsonObject getErrorResponse() {
        return response;
    }
}

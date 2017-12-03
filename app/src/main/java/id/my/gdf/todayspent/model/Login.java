package id.my.gdf.todayspent.model;

/**
 * Created by prime10 on 12/3/17.
 */

public class Login {

    /**
     * message : Logged in
     * token : JWT_TOKEN
     */

    private String message;
    private String token;
    /**
     * error_code : 500
     */

    private String error_code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }
}

package id.my.gdf.todayspent.model;

/**
 * Created by prime10 on 12/3/17.
 */

public class LoginBody {

    /**
     * email : EMAIL
     * password : PASSWORD
     */

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

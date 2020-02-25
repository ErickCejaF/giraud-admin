package mx.com.giraud.giraudadmin.Responses;


import mx.com.giraud.giraudadmin.Data.LoginData;

/**
 * Created by erickceja on 28/11/17.
 * at 15:34
 */

public class LoginResponse {
    private LoginData data;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }
}

package mx.com.giraud.giraudadmin.Data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import mx.com.giraud.giraudadmin.Models.UserModel;

public class LoginData extends RealmObject {
    @PrimaryKey
    private String authorization;
    private UserModel user;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}

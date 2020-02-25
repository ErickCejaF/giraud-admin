package mx.com.giraud.giraudadmin.Callbacks;

import mx.com.giraud.giraudadmin.Responses.LoginResponse;
import okhttp3.ResponseBody;

public interface LoginCallback {
    void baseResponse(LoginResponse loginResponse);
    void baseError();
}

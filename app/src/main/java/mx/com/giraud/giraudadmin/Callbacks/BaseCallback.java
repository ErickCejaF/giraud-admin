package mx.com.giraud.giraudadmin.Callbacks;

import okhttp3.ResponseBody;

public interface BaseCallback {
    void baseResponse(ResponseBody responseBody);

    void baseError();
}

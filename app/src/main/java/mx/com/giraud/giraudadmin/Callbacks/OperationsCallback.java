package mx.com.giraud.giraudadmin.Callbacks;

import mx.com.giraud.giraudadmin.Responses.GetOperationsResponse;

public interface OperationsCallback {
    void baseResponse(GetOperationsResponse operationsResponse);
    void baseError();
}

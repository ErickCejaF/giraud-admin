package mx.com.giraud.giraudadmin.Rest;


import mx.com.giraud.giraudadmin.Responses.GetOperationsResponse;
import mx.com.giraud.giraudadmin.Responses.LoginResponse;
import mx.com.giraud.giraudadmin.Utils.Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRestTasks {

    @FormUrlEncoded
    @POST(Constants.ENDPOINT_LOGIN)
    Call<LoginResponse> wsLogin(
            @Field("correo") String email,
            @Field("password") String password
    );

    @GET(Constants.ENDPOINT_GET_OPERATIONS)
    Call<GetOperationsResponse> wsGetOperations();

    @FormUrlEncoded
    @POST(Constants.ENDPOINT_REGISTER_OPERATIONS)
    Call<ResponseBody> wsRegisterOperations(
            @Field("operation_id") int operationId,
            @Field("activity") String activity,
            @Field("comments") String comments,
            @Field("user_id") int userId
    );

}

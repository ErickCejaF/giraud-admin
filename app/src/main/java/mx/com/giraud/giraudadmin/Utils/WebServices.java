package mx.com.giraud.giraudadmin.Utils;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import mx.com.giraud.giraudadmin.Callbacks.BaseCallback;
import mx.com.giraud.giraudadmin.Callbacks.LoginCallback;
import mx.com.giraud.giraudadmin.Callbacks.OperationsCallback;
import mx.com.giraud.giraudadmin.Dialogs.LoaderDialog;
import mx.com.giraud.giraudadmin.Responses.GetOperationsResponse;
import mx.com.giraud.giraudadmin.Responses.LoginResponse;
import mx.com.giraud.giraudadmin.Rest.ApiRestAdapter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebServices {

    private static LoaderDialog loaderDialog;

    public static void wsLogin(Context context,
                               String email,
                               String password,
                               boolean showProgressBar,
                               LoginCallback loginCallback) {

        loaderDialog = new LoaderDialog(context);
        if (showProgressBar) {
            loaderDialog.show();
        }
        loaderDialog.setCancelable(false);
        loaderDialog.setCanceledOnTouchOutside(false);

        Call<LoginResponse> baseResponse;
        baseResponse = new ApiRestAdapter().getApiRest().wsLogin(
                email,
                password
        );

        Callback<LoginResponse> baseCallback = new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                loaderDialog.dismiss();
                switch (response.code()) {
                    case 200:
                    case 201:
                        loginCallback.baseResponse(response.body());
                        break;

                    default:
                        loginCallback.baseError();
                        try {
                            Toast.makeText(context, new JSONObject(response.errorBody().string())
                                    .get("messages").toString().replace("[", "")
                                    .replace("]", "")
                                    .replace("\"", "")
                                    .replace(",", "\n\n"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException | NullPointerException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loaderDialog.dismiss();
                loginCallback.baseError();
            }
        };
        baseResponse.enqueue(baseCallback);
    }

    public static void wsGetOperations(Context context,
                                       boolean showProgressBar,
                                       OperationsCallback operationsCallback) {

        loaderDialog = new LoaderDialog(context);
        if (showProgressBar) {
            loaderDialog.show();
        }
        loaderDialog.setCancelable(false);
        loaderDialog.setCanceledOnTouchOutside(false);

        Call<GetOperationsResponse> baseResponse;
        baseResponse = new ApiRestAdapter().getApiRest().wsGetOperations();

        Callback<GetOperationsResponse> baseCallback = new Callback<GetOperationsResponse>() {
            @Override
            public void onResponse(Call<GetOperationsResponse> call, Response<GetOperationsResponse> response) {
                loaderDialog.dismiss();
                switch (response.code()) {
                    case 200:
                    case 201:
                        operationsCallback.baseResponse(response.body());
                        break;

                    default:
                        operationsCallback.baseError();
                        try {
                            Toast.makeText(context, new JSONObject(response.errorBody().string())
                                    .get("messages").toString().replace("[", "")
                                    .replace("]", "")
                                    .replace("\"", "")
                                    .replace(",", "\n\n"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException | NullPointerException e) {
                            e.printStackTrace();
                        }
                        break;
                }

            }

            @Override
            public void onFailure(Call<GetOperationsResponse> call, Throwable t) {
                loaderDialog.dismiss();
                operationsCallback.baseError();
            }
        };
        baseResponse.enqueue(baseCallback);
    }


    public static void wsPostOperations(Context context,
                                        int operationId,
                                        String activity,
                                        String comments,
                                        int userId,
                                        boolean showProgressBar,
                                        BaseCallback baseCallback) {

        loaderDialog = new LoaderDialog(context);
        if (showProgressBar) {
            loaderDialog.show();
        }
        loaderDialog.setCancelable(false);
        loaderDialog.setCanceledOnTouchOutside(false);

        Call<ResponseBody> baseResponse;
        baseResponse = new ApiRestAdapter().getApiRest().wsRegisterOperations(
                operationId,
                activity,
                comments,
                userId
        );

        Callback<ResponseBody> responseCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loaderDialog.dismiss();

                switch (response.code()) {
                    case 200:
                    case 201:
                        baseCallback.baseResponse(response.body());
                        break;

                    default:
                        baseCallback.baseError();
                        try {
                            Toast.makeText(context, new JSONObject(response.errorBody().string())
                                    .get("messages").toString().replace("[", "")
                                    .replace("]", "")
                                    .replace("\"", "")
                                    .replace(",", "\n\n"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException | NullPointerException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loaderDialog.dismiss();
                baseCallback.baseError();
            }
        };
        baseResponse.enqueue(responseCallback);
    }


}

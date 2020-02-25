package mx.com.giraud.giraudadmin.Utils;

import android.Manifest;

import mx.com.giraud.giraudadmin.BuildConfig;

public class Constants {
    public static final String URL_BASE = BuildConfig.BASE_URL;
    public static final String PLATFORM = "android";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////USER ENDPOINTS

    public static final String ENDPOINT_LOGIN = "login";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////OPERATIONS ENDPOINTS
    public static final String ENDPOINT_OPERATIONS = "operations/";
    public static final String ENDPOINT_GET_OPERATIONS = ENDPOINT_OPERATIONS + "get-operations";
    public static final String ENDPOINT_REGISTER_OPERATIONS = ENDPOINT_OPERATIONS + "register-activity-by-operation";


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////CONSTANTS

    public static final int START_REGISTERING = 1303;

    public static final String[] IMAGE_PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

}

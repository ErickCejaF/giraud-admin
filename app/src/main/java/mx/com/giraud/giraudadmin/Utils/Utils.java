package mx.com.giraud.giraudadmin.Utils;

import android.content.Context;
import android.content.Intent;

import io.realm.Realm;
import io.realm.RealmResults;
import mx.com.giraud.giraudadmin.Activities.LoginActivity;
import mx.com.giraud.giraudadmin.Data.LoginData;
import mx.com.giraud.giraudadmin.Models.UserModel;

public class Utils {

    public static LoginData getSavedUser() {
        LoginData loginData= null;

        Realm mRealm;
        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();

        RealmResults<LoginData> searching = mRealm.where(LoginData.class)
                .findAll();

        if (searching != null && !searching.isEmpty()) {
            loginData = searching.get(0);
        }

        mRealm.commitTransaction();

        return loginData;
    }

    public static void saveUser(LoginData loginData) {
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(loginData);
        mRealm.commitTransaction();
        mRealm.close();
    }

    public static void logOut(Context context) {
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        mRealm.deleteAll();
        mRealm.commitTransaction();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

}

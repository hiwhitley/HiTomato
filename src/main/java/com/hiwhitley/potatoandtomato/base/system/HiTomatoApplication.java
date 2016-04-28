package com.hiwhitley.potatoandtomato.base.system;

import android.app.Application;
import android.content.Context;

import com.hiwhitley.potatoandtomato.dao.Constants;
import com.hiwhitley.potatoandtomato.dao.DaoMaster;
import com.hiwhitley.potatoandtomato.dao.DaoSession;
import com.orhanobut.logger.Logger;

/**
 * Created by hiwhitley on 2016/4/4.
 */
public class HiTomatoApplication extends Application{

    private static final String DEFAULT_TAG = "hiwhitley";

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.init();

    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

}

package com.hiwhitley.potatoandtomato.db;

import android.content.Context;

import com.hiwhitley.potatoandtomato.base.system.HiTomatoApplication;
import com.hiwhitley.potatoandtomato.bean.DailyEvent;
import com.hiwhitley.potatoandtomato.dao.DailyEventDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hiwhitley on 2016/4/15.
 */
public class DailyEventDbService {
    private static DailyEventDbService instance;
    private static Context appContext;
    private DailyEventDao mDailyEventDao;

    public DailyEventDbService() {
    }

    public static DailyEventDbService getInstance(Context context){
        if (instance == null) {
            instance = new DailyEventDbService();
            if (appContext == null) {
                appContext = context.getApplicationContext();
            }
            instance.mDailyEventDao = HiTomatoApplication.getDaoSession(appContext).getDailyEventDao();
        }
        return instance;
    }

    public long insertDailyEvent(DailyEvent item) {
        return mDailyEventDao.insert(item);
    }

    public void updateDailyEvent(DailyEvent item){
        mDailyEventDao.update(item);
    }

    public List<DailyEvent> loadAllDailyEvent(){
        List<DailyEvent> list =  mDailyEventDao.queryBuilder().orderDesc(DailyEventDao.Properties.StartTime).list();
        if (list.size() == 0)
            return new ArrayList<DailyEvent>();
        else
            return list;
    }
}

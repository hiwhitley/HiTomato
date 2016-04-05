package com.hiwhitley.potatoandtomato.db;

import android.content.Context;

import com.hiwhitley.potatoandtomato.base.system.HiTomatoApplication;
import com.hiwhitley.potatoandtomato.bean.Tomato;
import com.hiwhitley.potatoandtomato.dao.TomatoDao;

import java.util.List;

/**
 * Created by hiwhitley on 2016/4/4.
 */
public class TomatoDbService {
    private static TomatoDbService instance;
    private static Context appContext;
    private TomatoDao tomatoDao;
    public TomatoDbService() {
    }

    public static TomatoDbService getInstance(Context context) {
        if (instance == null) {
            instance = new TomatoDbService();
            if (appContext == null) {
                appContext = context.getApplicationContext();
            }
            instance.tomatoDao = HiTomatoApplication.getDaoSession(appContext).getTomatoDao();
        }
        return instance;
    }

    public List<Tomato> loadAllTomato(){
        return tomatoDao.loadAll();
    }

    public long insertTomato(Tomato item){
        return tomatoDao.insert(item);
    }

}

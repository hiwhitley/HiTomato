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


    public List<Tomato> loadAllTomato() {
        return tomatoDao.loadAll();
    }

    public List<Tomato> loadAllTomatoByOrder() {
        List<Tomato> list = tomatoDao.queryBuilder().orderAsc(TomatoDao.Properties.Order).list();
        return list;
    }

    public long insertTomato(Tomato item) {
        return tomatoDao.insert(item);
    }

    public int getTomatoMaxOrder() {
        List<Tomato> list = tomatoDao.queryBuilder().orderDesc(TomatoDao.Properties.Order).list();
        if (list.size() == 0)
            return 0;
        else
            return list.get(0).getOrder();
    }

    public void deleteTomato(Tomato item) {
        tomatoDao.delete(item);
    }

    public void swapTomato(Tomato from, Tomato to) {
        int order = from.getOrder();
        from.setOrder(to.getOrder());
        tomatoDao.update(from);
        to.setOrder(order);
        tomatoDao.update(to);
    }

}

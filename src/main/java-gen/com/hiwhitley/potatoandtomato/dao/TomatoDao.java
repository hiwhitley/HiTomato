package com.hiwhitley.potatoandtomato.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.hiwhitley.potatoandtomato.bean.Tomato;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TOMATO".
*/
public class TomatoDao extends AbstractDao<Tomato, Long> {

    public static final String TABLENAME = "TOMATO";

    /**
     * Properties of entity Tomato.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Content = new Property(1, String.class, "content", false, "CONTENT");
        public final static Property Order = new Property(2, Integer.class, "order", false, "ORDER");
        public final static Property Status = new Property(3, Integer.class, "status", false, "STATUS");
        public final static Property Time = new Property(4, String.class, "time", false, "TIME");
        public final static Property Reminder = new Property(5, String.class, "reminder", false, "REMINDER");
    };


    public TomatoDao(DaoConfig config) {
        super(config);
    }
    
    public TomatoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TOMATO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CONTENT\" TEXT NOT NULL ," + // 1: content
                "\"ORDER\" INTEGER," + // 2: order
                "\"STATUS\" INTEGER," + // 3: status
                "\"TIME\" TEXT NOT NULL ," + // 4: time
                "\"REMINDER\" TEXT);"); // 5: reminder
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TOMATO\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Tomato entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getContent());
 
        Integer order = entity.getOrder();
        if (order != null) {
            stmt.bindLong(3, order);
        }
 
        Integer status = entity.getStatus();
        if (status != null) {
            stmt.bindLong(4, status);
        }
        stmt.bindString(5, entity.getTime());
 
        String reminder = entity.getReminder();
        if (reminder != null) {
            stmt.bindString(6, reminder);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Tomato readEntity(Cursor cursor, int offset) {
        Tomato entity = new Tomato( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // content
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // order
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // status
            cursor.getString(offset + 4), // time
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // reminder
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Tomato entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setContent(cursor.getString(offset + 1));
        entity.setOrder(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setStatus(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setTime(cursor.getString(offset + 4));
        entity.setReminder(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Tomato entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Tomato entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}

package cn.edu.tsu.campuscommonwealgo.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import cn.edu.tsu.campuscommonwealgo.entity.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER".
*/
public class UserDao extends AbstractDao<User, Long> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Username = new Property(1, String.class, "username", false, "USERNAME");
        public final static Property Password = new Property(2, String.class, "password", false, "PASSWORD");
        public final static Property Nickname = new Property(3, String.class, "nickname", false, "NICKNAME");
        public final static Property AvatarUrl = new Property(4, String.class, "avatarUrl", false, "AVATAR_URL");
        public final static Property School = new Property(5, String.class, "school", false, "SCHOOL");
        public final static Property Token = new Property(6, String.class, "token", false, "TOKEN");
        public final static Property ThirdPartyUser = new Property(7, boolean.class, "thirdPartyUser", false, "THIRD_PARTY_USER");
    }


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USERNAME\" TEXT NOT NULL UNIQUE ," + // 1: username
                "\"PASSWORD\" TEXT NOT NULL ," + // 2: password
                "\"NICKNAME\" TEXT," + // 3: nickname
                "\"AVATAR_URL\" TEXT," + // 4: avatarUrl
                "\"SCHOOL\" TEXT," + // 5: school
                "\"TOKEN\" TEXT," + // 6: token
                "\"THIRD_PARTY_USER\" INTEGER NOT NULL );"); // 7: thirdPartyUser
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, User entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getUsername());
        stmt.bindString(3, entity.getPassword());
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(4, nickname);
        }
 
        String avatarUrl = entity.getAvatarUrl();
        if (avatarUrl != null) {
            stmt.bindString(5, avatarUrl);
        }
 
        String school = entity.getSchool();
        if (school != null) {
            stmt.bindString(6, school);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(7, token);
        }
        stmt.bindLong(8, entity.getThirdPartyUser() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getUsername());
        stmt.bindString(3, entity.getPassword());
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(4, nickname);
        }
 
        String avatarUrl = entity.getAvatarUrl();
        if (avatarUrl != null) {
            stmt.bindString(5, avatarUrl);
        }
 
        String school = entity.getSchool();
        if (school != null) {
            stmt.bindString(6, school);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(7, token);
        }
        stmt.bindLong(8, entity.getThirdPartyUser() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // username
            cursor.getString(offset + 2), // password
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // nickname
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // avatarUrl
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // school
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // token
            cursor.getShort(offset + 7) != 0 // thirdPartyUser
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUsername(cursor.getString(offset + 1));
        entity.setPassword(cursor.getString(offset + 2));
        entity.setNickname(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAvatarUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSchool(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setToken(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setThirdPartyUser(cursor.getShort(offset + 7) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(User entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(User entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(User entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

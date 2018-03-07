package cn.edu.tsu.campuscommonwealgo.dao.util;

import android.app.Activity;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import cn.edu.tsu.campuscommonwealgo.BaseApplication;
import cn.edu.tsu.campuscommonwealgo.dao.DaoSession;
import cn.edu.tsu.campuscommonwealgo.dao.UserDao;
import cn.edu.tsu.campuscommonwealgo.entity.User;

/**
 * Created by XWM on 2018/1/17.
 */

public class UserDaoUtil {
    private static UserDaoUtil instance;
    private static DaoSession mDaoSession;
    private static UserDao userDao;

    private UserDaoUtil(BaseApplication application, DaoSession daoSession) {
        if (daoSession == null) {
            mDaoSession = application.getDaoSession();
        } else {
            mDaoSession = daoSession;
        }
        userDao = mDaoSession.getUserDao();
    }

    public static UserDaoUtil getInstance(BaseApplication application) {
        DaoSession daoSession = null;
        if (instance == null) {
            synchronized (UserDaoUtil.class) {
                if (instance == null) {
                    instance = new UserDaoUtil(application, daoSession);
                }
            }
        }
        return instance;
    }

    public static void insertUser(User user) {
        long id = userDao.insertOrReplace(user);
    }

    public static void deleteUser(Long id) {
        userDao.deleteByKey(id);
    }

    public static void updateUser(User user) {
        userDao.update(user);
    }

    public static List<User> queryUserByUsername(String username) {
        return userDao.queryBuilder().where(UserDao.Properties.Username.eq(username)).list();
    }

    public static List<User> queryUserByToken(String token) {
        return userDao.queryBuilder().where(UserDao.Properties.Token.eq(token)).list();
    }

    public static List<User> queryAll() {
        return userDao.loadAll();
    }
}

package cn.edu.tsu.campuscommonwealgo;

import android.app.Application;
import android.os.Build;

import org.greenrobot.greendao.database.Database;

import cn.edu.tsu.campuscommonwealgo.dao.DaoMaster;
import cn.edu.tsu.campuscommonwealgo.dao.DaoSession;
import cn.edu.tsu.campuscommonwealgo.dao.util.UserDaoUtil;
import cn.edu.tsu.campuscommonwealgo.util.TransparentStatusBarUtil;

/**
 * Created by XWM on 2018/1/17.
 */

public class BaseApplication extends Application {
    private UserDaoUtil userDaoUtil;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
        userDaoUtil = UserDaoUtil.getInstance(this);
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "user.db", null);
        //获取可写数据库
        Database db = helper.getWritableDb();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}

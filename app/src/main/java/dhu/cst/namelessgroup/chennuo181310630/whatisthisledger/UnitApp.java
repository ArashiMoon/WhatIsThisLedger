package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger;

import android.app.Application;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.DBManager;

/*表示全局应用的类*/
public class UnitApp  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDB(getApplicationContext());
    }
}

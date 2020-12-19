package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.R;

public class DBopenHelper extends SQLiteOpenHelper {

    static final String DB_NAME="ledger.db";
    static final int DB_VERSION=1;
    public DBopenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Type表
        String sql="CREATE TABLE TYPETB(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "                       TYPENAME VARCHAR(10)," +
                "                       IMAGEID INTEGER," +
                "                       SIMAGEID INTEGER," +
                "                       KIND INTEGER);";
        db.execSQL(sql);
        insertType(db);
        //Account表
        sql="CREATE TABLE ACCOUNTTB(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "                   TYPENAME VARCHAR(10)," +
                "                   SIMAGEID INTEGER," +
                "                   REMARK VARCHAR(80)," +
                "                   MONEY FLOAT," +
                "                   TIME VARCHAR(60)," +
                "                   YEAR INTEGER," +
                "                   MONTH INTEGER," +
                "                   DAY INTEGER," +
                "                   KIND INTEGER);";
        db.execSQL(sql);
    }
//    TYPETB表初始化
    private void insertType(SQLiteDatabase db) {
//        插入数据
        String sql="INSERT INTO TYPETB(TYPENAME,IMAGEID,SIMAGEID,KIND) VALUES(?,?,?,?);";
        db.execSQL(sql,new Object[]{"其他", R.mipmap.out_qita,R.mipmap.out_qita_fs,0});
        db.execSQL(sql,new Object[]{"餐饮", R.mipmap.out_canyin,R.mipmap.out_canyin_fs,0});
        db.execSQL(sql,new Object[]{"交通", R.mipmap.out_jiaotong,R.mipmap.out_jiaotong_fs,0});
        db.execSQL(sql,new Object[]{"服饰", R.mipmap.out_fushi,R.mipmap.out_fushi_fs,0});
        db.execSQL(sql,new Object[]{"日用品", R.mipmap.out_riyongpin,R.mipmap.out_riyongpin_fs,0});
        db.execSQL(sql,new Object[]{"娱乐", R.mipmap.out_yule,R.mipmap.out_yule_fs,0});
        db.execSQL(sql,new Object[]{"零食", R.mipmap.out_lingshi,R.mipmap.out_lingshi_fs,0});
        db.execSQL(sql,new Object[]{"烟酒", R.mipmap.out_yanjiu,R.mipmap.out_yanjiu_fs,0});
        db.execSQL(sql,new Object[]{"学习用品", R.mipmap.out_xuexiyongpin,R.mipmap.out_xuexiyongpin_fs,0});
        db.execSQL(sql,new Object[]{"医疗", R.mipmap.out_yiliao,R.mipmap.out_yiliao_fs,0});
        db.execSQL(sql,new Object[]{"住房", R.mipmap.out_zhufang,R.mipmap.out_zhufang_fs,0});
        db.execSQL(sql,new Object[]{"水电费", R.mipmap.out_shuidian,R.mipmap.out_shuidianfei_fs,0});
        db.execSQL(sql,new Object[]{"话费", R.mipmap.out_huafei,R.mipmap.out_huafei_fs,0});
        db.execSQL(sql,new Object[]{"人情往来", R.mipmap.out_renqingwanglai,R.mipmap.out_renqingwanglai_fs,0});

        db.execSQL(sql,new Object[]{"其他", R.mipmap.in_qita,R.mipmap.in_qita_ls,1});
        db.execSQL(sql,new Object[]{"薪资", R.mipmap.in_gongzi,R.mipmap.in_gongzi_ls,1});
        db.execSQL(sql,new Object[]{"奖金", R.mipmap.in_jiangjin,R.mipmap.in_jiangjin_ls,1});
        db.execSQL(sql,new Object[]{"借钱", R.mipmap.in_jieqian,R.mipmap.in_jieqian_ls,1});
        db.execSQL(sql,new Object[]{"收债", R.mipmap.in_shouzhai,R.mipmap.in_shouzhai_ls,1});
        db.execSQL(sql,new Object[]{"利息", R.mipmap.in_lixi,R.mipmap.in_lixi_ls,1});
        db.execSQL(sql,new Object[]{"投资", R.mipmap.in_touzi,R.mipmap.in_touzi_ls,1});
        db.execSQL(sql,new Object[]{"二手交易", R.mipmap.in_ershoujiaoyi,R.mipmap.in_ershoujiaoyi_ls,1});
        db.execSQL(sql,new Object[]{"意外所得", R.mipmap.in_yiwaisuode,R.mipmap.in_yiwaisuode_ls,1});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

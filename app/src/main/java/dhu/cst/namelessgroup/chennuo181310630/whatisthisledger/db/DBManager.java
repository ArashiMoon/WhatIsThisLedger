package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.utils.FloatUtil;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.utils.ListUtil;

/*
* 负责管理数据库的类
* 主要对于表当中的内容进行操作
* */
public class DBManager {
    private static SQLiteDatabase db;
    /*初始化数据库对象*/
    public static void initDB(Context context){
        DBopenHelper helper = new DBopenHelper(context);
        db=helper.getWritableDatabase();
    }
    /**
     * 读取数据库中的数据
     */
    public static List<TypeBean> getTypeList(int kind){
        List<TypeBean> list=new ArrayList<>();
        //读取typetb表中的数据
        String sql="SELECT * FROM TYPETB WHERE KIND="+kind+";";
        Cursor cursor = db.rawQuery(sql, null);
//        存储入对象
        while(cursor.moveToNext()){
            String typename = cursor.getString(cursor.getColumnIndex("TYPENAME"));
            int imageid = cursor.getInt(cursor.getColumnIndex("IMAGEID"));
            int simageid = cursor.getInt(cursor.getColumnIndex("SIMAGEID"));
            int kind1 = cursor.getInt(cursor.getColumnIndex("KIND"));
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            TypeBean typeBean = new TypeBean(id, typename, imageid, simageid, kind1);
            list.add(typeBean);
        }
        return list;
    }
    /*
    * 向记账表中插入一条元素
    * */
    public static void insertItemToAccount(AccountBean bean){
        ContentValues values = new ContentValues();
        values.put("TYPENAME",bean.getTypename());
        values.put("SIMAGEID",bean.getsImageId());
        values.put("REMARK",bean.getRemark());
        values.put("MONEY",bean.getMoney());
        values.put("TIME",bean.getTime());
        values.put("YEAR",bean.getYear());
        values.put("MONTH",bean.getMonth());
        values.put("DAY",bean.getDay());
        values.put("KIND",bean.getKind());
        db.insert("ACCOUNTTB",null,values);
        Log.i("database", "insertItemToAccount:insert... ");
    }
    /*
    *   获取记账表中某一天的支出收入
     */
    public static List<AccountBean>getAccountListOneDayFromAccount(int year,int month,int day){
        List<AccountBean>list=new ArrayList<>();
        String sql="SELECT * FROM ACCOUNTTB WHERE YEAR=? AND MONTH=? AND DAY=? ORDER BY ID DESC;";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + ""});
//        遍历数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String typename = cursor.getString(cursor.getColumnIndex("TYPENAME"));
            String remark = cursor.getString(cursor.getColumnIndex("REMARK"));
            String time = cursor.getString(cursor.getColumnIndex("TIME"));
            int simageid = cursor.getInt(cursor.getColumnIndex("SIMAGEID"));
            int kind = cursor.getInt(cursor.getColumnIndex("KIND"));
            float money = cursor.getFloat(cursor.getColumnIndex("MONEY"));
            AccountBean accountBean = new AccountBean(id, typename, simageid, remark, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }
    /*
     *   获取记账表中某一天的支出收入
     */
    public static List<AccountBean>getAccountListOneMonthFromAccount(int year,int month){
        List<AccountBean>list=new ArrayList<>();
        String sql="SELECT * FROM ACCOUNTTB WHERE YEAR=? AND MONTH=? ORDER BY ID DESC;";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + ""});
//        遍历数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String typename = cursor.getString(cursor.getColumnIndex("TYPENAME"));
            String remark = cursor.getString(cursor.getColumnIndex("REMARK"));
            String time = cursor.getString(cursor.getColumnIndex("TIME"));
            int simageid = cursor.getInt(cursor.getColumnIndex("SIMAGEID"));
            int kind = cursor.getInt(cursor.getColumnIndex("KIND"));
            float money = cursor.getFloat(cursor.getColumnIndex("MONEY"));
            int day = cursor.getInt(cursor.getColumnIndex("DAY"));
            AccountBean accountBean = new AccountBean(id, typename, simageid, remark, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }
    /**
     * 统计某月份支出或收入有多少条
     */
    public static int getItemCountOneMonth(int year,int month,int kind){
        int total=0;
        String sql="SELECT COUNT(MONEY) FROM ACCOUNTTB WHERE YEAR=? AND MONTH=? AND KIND=?;";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            total=cursor.getInt(cursor.getColumnIndex("COUNT(MONEY)"));
        }
        return total;
    }
    /**
     * 获取某日的支出或收入总金额  kind 收入==1，支出==0
     */
    public static float getSumMoneyOneDay(int year,int month,int day,int kind){
        float total=0.0f;
        String sql="SELECT SUM(MONEY) FROM ACCOUNTTB WHERE YEAR=? AND MONTH=? AND DAY=? AND KIND=?;";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + "", kind + ""});
//        遍历数据(也就只有一条)
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("SUM(MONEY)"));
            total=money;
        }
        return total;
    }
    /*
     *   获取某月的支出或收入总金额  kind 收入==1，支出==0
     */
    public static float getSumMoneyOneMonth(int year,int month,int kind){
        float total=0.0f;
        String sql="SELECT SUM(MONEY) FROM ACCOUNTTB WHERE YEAR=? AND MONTH=? AND KIND=?;";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
//        遍历数据(也就只有一条)
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("SUM(MONEY)"));
            total=money;
        }
        return total;
    }
    /*
     *   获取某年的支出或收入总金额  kind 收入==1，支出==0
     */
    public static float getSumMoneyOneYear(int year,int kind){
        float total=0.0f;
        String sql="SELECT SUM(MONEY) FROM ACCOUNTTB WHERE YEAR=? AND MONTH=? AND KIND=?;";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "",  kind + ""});
//        遍历数据(也就只有一条)
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("SUM(MONEY)"));
            total=money;
        }
        return total;
    }
    /**
     * 根据传入的id，删除ACCOUNTTB表中的某一条记录
     */
    public static int deleteItemFromAccountById(int id){
        int pos = db.delete("ACCOUNTTB", "ID=?", new String[]{id + ""});
        return pos;
    }
    /**
     * 根据备注搜索记录
     */
    public static List<AccountBean> getAccountListByRemarkFromAccount(String remark){
        List<AccountBean>list=new ArrayList<>();
        String sql="SELECT * FROM ACCOUNTTB WHERE REMARK LIKE '%"+remark+"%';";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String typename = cursor.getString(cursor.getColumnIndex("TYPENAME"));
            String time = cursor.getString(cursor.getColumnIndex("TIME"));
            String remarktmp=cursor.getString(cursor.getColumnIndex("REMARK"));
            int simageid = cursor.getInt(cursor.getColumnIndex("SIMAGEID"));
            int kind = cursor.getInt(cursor.getColumnIndex("KIND"));
            float money = cursor.getFloat(cursor.getColumnIndex("MONEY"));
            int year = cursor.getInt(cursor.getColumnIndex("YEAR"));
            int month = cursor.getInt(cursor.getColumnIndex("MONTH"));
            int day = cursor.getInt(cursor.getColumnIndex("DAY"));
            AccountBean accountBean = new AccountBean(id, typename, simageid, remarktmp, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }
    /**
     * 查询记账表中包含几个年
     */
    public static List<Integer>getYearListFromAccount(){
        List<Integer>list =new ArrayList<>();
        String sql="SELECT DISTINCT(YEAR) FROM ACCOUNTTB ORDER BY YEAR ASC;";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int year = cursor.getInt(cursor.getColumnIndex("YEAR"));
            list.add(year);
        }
        return list;
    }
    /**
     * 删除记账表中所有数据
     */
    public static void deleteAllAccount(){
        String sql="DELETE FROM ACCOUNTTB;";
        db.execSQL(sql);
    }
    /**
     * 查询指定年月的收入或支出中每一类型的总金额
     */
    public static List<ChartItemBean> getChartListFromAccountByMonth(int year, int month, int kind){
        List<ChartItemBean> list=new ArrayList<>();
        float sumMoneyOneMonth = getSumMoneyOneMonth(year, month, kind);  //求出总金额
        String sql="SELECT TYPENAME,SIMAGEID,SUM(MONEY)AS TOTAL FROM ACCOUNTTB" +
                " WHERE YEAR=? AND MONTH=? AND KIND=?" +
                " GROUP BY TYPENAME" +
                " ORDER BY TOTAL DESC;";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        while (cursor.moveToNext()) {
            int simageid = cursor.getInt(cursor.getColumnIndex("SIMAGEID"));
            String typename = cursor.getString(cursor.getColumnIndex("TYPENAME"));
            float total = cursor.getFloat(cursor.getColumnIndex("TOTAL"));
            float pert= FloatUtil.div(total,sumMoneyOneMonth);
            ChartItemBean bean = new ChartItemBean(simageid, typename, pert, total);
            list.add(bean);
        }
        return list;
    }
    /**
     * 查询指定年月周的收入或支出中每一类型的总金额
     */
    public static List<ChartItemBean> getChartListFromAccountByWeek(int year, int month,int dayOfWeek,int dayOfMonth, int kind){
        List<ChartItemBean> list=new ArrayList<>();
        int startDay=dayOfMonth-dayOfWeek+1;
        int endDay=dayOfMonth-dayOfWeek+8;
        float sumMoneyOneWeek=0.0f;
        for (int i = startDay; i <endDay ; i++) {
            sumMoneyOneWeek+= getSumMoneyOneDay(year, month, i, kind);  //求出总金额
            String sql="SELECT TYPENAME,SIMAGEID,SUM(MONEY)AS TOTAL FROM ACCOUNTTB" +
                    " WHERE YEAR=? AND MONTH=? AND DAY=? AND KIND=?" +
                    " GROUP BY TYPENAME" +
                    " ORDER BY TOTAL DESC;";
            Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", i + "" , kind + ""});
            List<ChartItemBean> templist=new ArrayList<>();
            while (cursor.moveToNext()) {
                int simageid = cursor.getInt(cursor.getColumnIndex("SIMAGEID"));
                String typename = cursor.getString(cursor.getColumnIndex("TYPENAME"));
                float total = cursor.getFloat(cursor.getColumnIndex("TOTAL"));
                float pert=0.0f;    //暂时设置为0，在循环外设置真正的值
            ChartItemBean bean = new ChartItemBean(simageid, typename, pert, total);
                templist.add(bean);
            }
            list = ListUtil.merge(list, templist);
        }
//        设置pert
        List<ChartItemBean> beanList=new ArrayList<>();
        for (ChartItemBean item:list) {
            int simageid=item.getsImageId();
            String typename=item.getType();
            float total=item.getTotalMoney();
            float pert= FloatUtil.div(total,sumMoneyOneWeek);
            ChartItemBean bean = new ChartItemBean(simageid, typename, pert, total);
            beanList.add(bean);
        }
        Collections.sort(beanList);
        return beanList;
    }
    /**
     * 查询指定年月日的收入或支出中每一类型的总金额
     */
    public static List<ChartItemBean> getChartListFromAccountByDay(int year, int month,int dayOfMonth, int kind){
        List<ChartItemBean> list=new ArrayList<>();
        float sumMoneyOneDay = getSumMoneyOneDay(year, month,dayOfMonth, kind);  //求出总金额
        String sql="SELECT TYPENAME,SIMAGEID,SUM(MONEY)AS TOTAL FROM ACCOUNTTB" +
                " WHERE YEAR=? AND MONTH=? AND DAY=? AND KIND=?" +
                " GROUP BY TYPENAME" +
                " ORDER BY TOTAL DESC;";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", dayOfMonth+"", kind + ""});
        while (cursor.moveToNext()) {
            int simageid = cursor.getInt(cursor.getColumnIndex("SIMAGEID"));
            String typename = cursor.getString(cursor.getColumnIndex("TYPENAME"));
            float total = cursor.getFloat(cursor.getColumnIndex("TOTAL"));
            float pert= FloatUtil.div(total,sumMoneyOneDay);
            ChartItemBean bean = new ChartItemBean(simageid, typename, pert, total);
            list.add(bean);
        }
        return list;
    }
    /**
     * 获取该月收入或支出最高的一天的金额，将其设定为y轴最大值
     */
    public static float getMaxMoneyOneDayInMonth(int year,int month,int kind){
        String sql="SELECT SUM(MONEY) FROM ACCOUNTTB WHERE YEAR=? AND MONTH=? AND KIND=? GROUP BY DAY ORDER BY SUM(MONEY) DESC;";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("SUM(MONEY)"));
            return money;
        }
        return 0;
    }
    /**
     * 获取该月每日收入或支出总金额
     */
    public static List<BarChartItemBean>getSumMoneyOneDayInMonth(int year,int month,int kind){
        String sql="SELECT DAY,SUM(MONEY) FROM ACCOUNTTB WHERE YEAR=? AND MONTH=? AND KIND=? GROUP BY DAY;";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        List<BarChartItemBean>list=new ArrayList<>();
        while (cursor.moveToNext()) {
            int day = cursor.getInt(cursor.getColumnIndex("DAY"));
            float smoney = cursor.getFloat(cursor.getColumnIndex("SUM(MONEY)"));
            BarChartItemBean bean = new BarChartItemBean(year, month, day, smoney);
            list.add(bean);
        }
        return list;
    }
}

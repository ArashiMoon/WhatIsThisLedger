package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.adapter.AccountAdapter;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.AccountBean;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.DBManager;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.utils.BudgetDialog;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.utils.MoreDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListView todaylv;   //今日收支情况ListView
    ImageView searchIv;
    Button editBtn;
    ImageButton moreBtn;
//    数据源
    List<AccountBean> mDatas;
    AccountAdapter adapter;
    int year,month,day;
//    头布局相关控件
    View headerView;
    TextView topOutTv,topInTv,topBudgetTv,topConTv;
    ImageView topHideIv;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTime();
        initView();
        preferences=getSharedPreferences("budget", Context.MODE_PRIVATE);
//        添加ListView头布局
        addLVHeaderView();

        mDatas=new ArrayList<>();
//        适配器
        adapter = new AccountAdapter(this, mDatas);
        todaylv.setAdapter(adapter);
    }
//        初始化自带View(整合一下)
    private void initView() {
        todaylv=findViewById(R.id.main_lv);
        editBtn=findViewById(R.id.main_btn_edit);
        moreBtn=findViewById(R.id.main_btn_more);
        searchIv=findViewById(R.id.main_iv_search);
        editBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
        searchIv.setOnClickListener(this);
        setLVLongClickListener();

    }
//    设置ListView长按事件
    private void setLVLongClickListener() {
        todaylv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0) {//点击了头布局
                    return false;
                }
                int pos=position-1;
                AccountBean bean = mDatas.get(pos);//被点击的item
//                询问是否删除的对话框
                showDeleteItemDialog(bean);
                return false;
            }
        });
    }
//    询问是否删除的对话框
    private void showDeleteItemDialog(final AccountBean bean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息")
                .setMessage("您确定要删除这条记录吗？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int beanId = bean.getId();
//                        删除记录
                        DBManager.deleteItemFromAccountById(beanId);//数据库删除
                        mDatas.remove(bean);//数据源删除
                        adapter.notifyDataSetChanged();//更新适配器
                        setTopTvshow();//更新头布局
                    }
                });
        builder.create().show();
    }

    //    给ListView添加头布局
    private void addLVHeaderView() {
//        将布局转换成View对象
        headerView = getLayoutInflater().inflate(R.layout.item_mainlv_top, null);
        todaylv.addHeaderView(headerView);
//        查找头布局相关控件
        topOutTv=headerView.findViewById(R.id.item_mainlv_top_tv_out);
        topInTv=headerView.findViewById(R.id.item_mainlv_top_tv_in);
        topBudgetTv=headerView.findViewById(R.id.item_mainlv_top_tv_budget);
        topConTv=headerView.findViewById(R.id.item_mainlv_top_tv_day);
        topHideIv=headerView.findViewById(R.id.item_mainlv_top_iv_hide);

        topBudgetTv.setOnClickListener(this);
        headerView.setOnClickListener(this);
        topHideIv.setOnClickListener(this);

    }
//    获取今日时间
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        day=calendar.get(Calendar.DAY_OF_MONTH);
    }

//    当Activity获取焦点时调用
    @Override
    protected void onResume() {
        super.onResume();
        loadDBData();
        setTopTvshow();
    }
//    设置头布局中文本内容
    private void setTopTvshow() {
//        获取今日支出和收入总金额，显示于View中
        float incomeOneDay = DBManager.getSumMoneyOneDay(year, month, day, 1);
        float outcomeOneDay = DBManager.getSumMoneyOneDay(year, month, day, 0);
        String infoOneDay="今日支出 ￥"+outcomeOneDay+" 收入 ￥"+incomeOneDay;
        topConTv.setText(infoOneDay);
//        获取本月收入和支出总金额
        float incomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 1);
        float outcomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0);
        topInTv.setText("￥"+incomeOneMonth);
        topOutTv.setText("￥"+outcomeOneMonth);

//        设置预算剩余
        float bmoney = preferences.getFloat("bmoney", 0);//budget money
        if (bmoney==0) {
            topBudgetTv.setText("￥ 0");
        }else{
            float remainMoney=bmoney-outcomeOneMonth;
            topBudgetTv.setText("￥"+remainMoney);
        }
    }

    private void loadDBData() {
        List<AccountBean> list = DBManager.getAccountListOneDayFromAccount(year, month, day);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }
//    点击事件
    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()) {
            case R.id.main_iv_search:
                intent.setClass(this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.main_btn_edit:
                intent.setClass(this, RecordActivity.class);
                startActivity(intent);
                break;
            case R.id.main_btn_more:
//                弹出MoreDialog对话框
                showMoreDialog();
                break;
            case R.id.item_mainlv_top_tv_budget:
                showBudgetDialog();
                break;
            case R.id.item_mainlv_top_iv_hide:
//                切换TextView显示模式
                toggleShow();
                break;
        }
        if(v==headerView){
//            头布局被点击
            intent.setClass(this, MonthChartActivity.class);
            startActivity(intent);
        }
    }
//    显示更多对话框
    private void showMoreDialog() {
        MoreDialog moreDialog=new MoreDialog(this);
        moreDialog.show();
        moreDialog.setDialogSize();
    }

//    显示预算设置对话框
    private void showBudgetDialog() {
        BudgetDialog dialog = new BudgetDialog(this);
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new BudgetDialog.OnEnsureListener() {
            @Override
            public void onEnsure(float money) {
//                将预算金额写入SharedPreferences
                SharedPreferences.Editor editor=preferences.edit();
                editor.putFloat("bmoney",money);//budget money
                editor.commit();

//                计算剩余金额
                float outcomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0);
                float remainMoney=money-outcomeOneMonth;
                topBudgetTv.setText("￥"+remainMoney);
            }
        });
    }

    boolean isShow=true;
    /**
     * 点击头布局眼睛时，显示模式反转，↑默认show↑
     */
    private void toggleShow() {
        if (isShow) {//show→hide
            PasswordTransformationMethod passwordTransformationMethod = PasswordTransformationMethod.getInstance();
            topInTv.setTransformationMethod(passwordTransformationMethod);  //设置隐藏
            topOutTv.setTransformationMethod(passwordTransformationMethod);  //设置隐藏
            topBudgetTv.setTransformationMethod(passwordTransformationMethod);  //设置隐藏
            topHideIv.setImageResource(R.mipmap.ih_hide);
            isShow=false;
        }else{//hide→show
            HideReturnsTransformationMethod hideReturnsTransformationMethod = HideReturnsTransformationMethod.getInstance();
            topInTv.setTransformationMethod(hideReturnsTransformationMethod);  //设置显示
            topOutTv.setTransformationMethod(hideReturnsTransformationMethod);  //设置显示
            topBudgetTv.setTransformationMethod(hideReturnsTransformationMethod);  //设置显示
            topHideIv.setImageResource(R.mipmap.ih_show);
            isShow=true;
        }
    }
}
package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.frag_record;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.R;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.adapter.TypeBaseAdapter;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.AccountBean;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.TypeBean;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.utils.KeyBoardUtils;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.utils.RemarkDialog;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.utils.SelectTimeDialog;

/**
 * 记录页面中的支出收入模块
 */
public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener {

    KeyboardView keyboardView;
    EditText moneyEt;
    ImageView typeIv;
    TextView typeTv,remarkTv,timeTv;
    GridView typeGv;
    List<TypeBean>typeList;
    TypeBaseAdapter adapter;
    AccountBean accountBean;            //需要插入的数据-对象类

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean=new AccountBean();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_outcome, container, false);
        initView(view);
        //给GridView填充数据
        setInitTime();
        loadDataToGV();
        //设置GridView每一项的点击事件
        setGVListener();
        return view;
    }
    /*  获取当前时间，显示在timeTv上*/
    private void setInitTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = simpleDateFormat.format(date);
        timeTv.setText(time);
        accountBean.setTime(time);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(month);
        accountBean.setDay(day);
    }

    /*  设置GridView每一项的点击事件*/
    private void setGVListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectPos=position;//选中位置变化
                adapter.notifyDataSetInvalidated();//提示绘制发生变化
                TypeBean typeBean = typeList.get(position);

                String typename = typeBean.getTypename();
                typeTv.setText(typename);
                accountBean.setTypename(typename);

                int simageId = typeBean.getSimageId();
                typeIv.setImageResource(simageId);
                accountBean.setsImageId(simageId);
            }
        });
    }

    /*  给GridView填充数据*/
    public void loadDataToGV() {
        typeList=new ArrayList<>();
        adapter = new TypeBaseAdapter(getContext(), typeList);
        typeGv.setAdapter(adapter);
        //子类中再重写
    }

    private void initView(View view) {
        keyboardView=view.findViewById(R.id.frag_record_keyboard);
        moneyEt=view.findViewById(R.id.frag_record_et_money);
        typeIv=view.findViewById(R.id.frag_record_iv);
        typeGv=view.findViewById(R.id.frag_record_gv);
        typeTv=view.findViewById(R.id.frag_record_tv_type);
        remarkTv=view.findViewById(R.id.frag_record_tv_remark);
        timeTv=view.findViewById(R.id.frag_record_tv_time);

        remarkTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);
        //显示键盘
        KeyBoardUtils keyBoardUtils= new KeyBoardUtils(keyboardView,moneyEt);
        keyBoardUtils.showKeyboard();
        //监听确定按钮
        keyBoardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                //获取输入钱数
                String moneyStr = moneyEt.getText().toString();
                if (TextUtils.isEmpty(moneyStr)||moneyStr.equals("0")||Float.parseFloat(moneyStr)==0.0f) {
                    Toast.makeText(getContext(), "请输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                float money = Float.parseFloat(moneyStr);
                accountBean.setMoney(money);
                //获取信息，保存至数据库
                saveAccountToDB();
                //并返回上一级界面
                getActivity().finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_record_tv_time:
                showTimeDialog();
                break;
            case R.id.frag_record_tv_remark:
                showRemarkDialog();
                break;
        }
    }
    /*  弹出显示时间的对话框*/
    private void showTimeDialog() {
        SelectTimeDialog dialog = new SelectTimeDialog(getContext());
        dialog.show();
        //设置确定按钮点击的监听器
        dialog.setOnEnsureListener(new SelectTimeDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                timeTv.setText(time);
                accountBean.setTime(time);
                accountBean.setYear(year);
                accountBean.setMonth(month);
                accountBean.setDay(day);
            }
        });
    }

    /*  弹出备注对话框*/
    public void showRemarkDialog() {
        final RemarkDialog remarkDialog=new RemarkDialog(getContext());
        remarkDialog.show();
        remarkDialog.setDialogSize();

        remarkDialog.setOnEnsureListener(new RemarkDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
                String msg = remarkDialog.getEditText();
                if (!TextUtils.isEmpty(msg)) {
                    remarkTv.setText(msg);
                    accountBean.setRemark(msg);
                }
                remarkDialog.cancel();
            }
        });
    }

    /*抽象方法*/
    public abstract void saveAccountToDB();
}
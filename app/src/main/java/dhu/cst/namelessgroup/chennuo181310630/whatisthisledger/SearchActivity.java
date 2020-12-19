package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.adapter.AccountAdapter;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.AccountBean;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.DBManager;

public class SearchActivity extends AppCompatActivity {
    ListView searchLv;
    EditText searchEt;
    TextView emptyTv;
    ImageView searchIv;
    List<AccountBean> mDatas;   //数据源
    AccountAdapter adapter;     //适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        mDatas=new ArrayList<>();
        adapter=new AccountAdapter(this,mDatas);
        searchLv.setAdapter(adapter);
        searchLv.setEmptyView(emptyTv); //无数据时显示emptyTv控件
    }

    private void initView() {
        searchLv=findViewById(R.id.search_lv);
        searchEt=findViewById(R.id.search_et);
        searchIv=findViewById(R.id.search_iv_search);
        emptyTv=findViewById(R.id.search_tv_emptyinfo);
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                {
                    if(searchIv.callOnClick()) return true;
                }
                return false;
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_iv_back:
                finish();
                break;
            case R.id.search_iv_search:
                String str = searchEt.getText().toString().trim();
                if (TextUtils.isEmpty(str)) {
                    Toast.makeText(this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<AccountBean> list = DBManager.getAccountListByRemarkFromAccount(str);
                mDatas.clear();     //先清空
                mDatas.addAll(list);//再添加 不然上一次的还留着
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
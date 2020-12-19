package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.frag_record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.List;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.R;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.DBManager;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.TypeBean;

public class OutcomeFragment extends BaseRecordFragment {
    //重写loadDataToGV实现子类不同效果
    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据库当中的数据源
        List<TypeBean> outlist = DBManager.getTypeList(0);
        typeList.addAll(outlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.out_qita_fs);
        accountBean.setTypename("其他");
        accountBean.setsImageId(R.mipmap.out_qita_fs);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(0);
        DBManager.insertItemToAccount(accountBean);
    }
}
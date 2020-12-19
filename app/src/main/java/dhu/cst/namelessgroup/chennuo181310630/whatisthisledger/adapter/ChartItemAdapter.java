package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.R;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.ChartItemBean;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.utils.FloatUtil;

/**
 * 账单详情页面，ListView的适配器 日周月视图复用一下
 */

public class ChartItemAdapter extends BaseAdapter {
    Context context;
    List<ChartItemBean> mDatas;
    LayoutInflater inflater;

    public ChartItemAdapter(Context context, List<ChartItemBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null) {
            convertView=inflater.inflate(R.layout.item_chartfrag_lv,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
//        获取显示内容
        ChartItemBean bean=mDatas.get(position);
        holder.iv.setImageResource(bean.getsImageId());
        holder.typeTv.setText(bean.getType());
        float ratio=bean.getPert();
        String pert = FloatUtil.ratioToPercent(ratio);
        holder.pertTv.setText(pert);
        holder.totalTv.setText("￥ "+bean.getTotalMoney());
        return convertView;
    }

    class ViewHolder{
        TextView typeTv,pertTv,totalTv;
        ImageView iv;
        public ViewHolder(View view){
            typeTv=view.findViewById(R.id.item_chartfrag_tv_type);
            pertTv=view.findViewById(R.id.item_chartfrag_tv_pert);
            totalTv=view.findViewById(R.id.item_chartfrag_tv_sum);
            iv=view.findViewById(R.id.item_chartfrag_iv);
        }
    }
}

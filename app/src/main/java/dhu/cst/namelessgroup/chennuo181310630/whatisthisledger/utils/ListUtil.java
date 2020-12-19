package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.ChartItemBean;

public class ListUtil {
    public static List<ChartItemBean> merge(List<ChartItemBean> list1,List<ChartItemBean>list2){
        Map<Integer, ChartItemBean> target = new HashMap<Integer, ChartItemBean>();
        if (!list1.isEmpty() && !list2.isEmpty()) {
            //把list1放入Map target中，key取用户sImageId
            for (ChartItemBean tempBean : list1) {
                target.put(tempBean.getsImageId(), tempBean);
            }
            for (ChartItemBean bean2 : list2) {
                Integer sImageId = bean2.getsImageId();
                if (target.containsKey(sImageId)) {
                    ChartItemBean temp = target.get(sImageId);
                    temp.setTotalMoney(temp.getTotalMoney()+bean2.getTotalMoney());
                    target.put(sImageId, temp);
                } else {
                    target.put(sImageId, bean2);
                }
            }
            List<ChartItemBean> list = new ArrayList<ChartItemBean>(target.values());
            return list;
        }else if(!list1.isEmpty()&&list2.isEmpty()){
            return list1;
        }else if(list1.isEmpty()&&!list2.isEmpty()){
            return list2;
        }
        List<ChartItemBean>list=new ArrayList<>();
        return list;
    }
}

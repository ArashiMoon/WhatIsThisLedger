package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.utils;

import java.math.BigDecimal;

public class FloatUtil {
//    浮点数除法，保留4位小数
    public static float div(float v1,float v2){
        float v3=v1/v2;
        BigDecimal b1=new BigDecimal(v3);
        float val=b1.setScale(4,4).floatValue();
        return val;
    }
//    将浮点数类型，转换成百分比显示形式
    public static String ratioToPercent(float val){
        float v=val*100;
        BigDecimal b1=new BigDecimal(v);
        float v1=b1.setScale(2,4).floatValue();
        String pert=v1+"%";
        return pert;
    }
}

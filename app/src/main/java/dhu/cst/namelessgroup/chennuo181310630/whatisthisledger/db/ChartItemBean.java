package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db;

public class ChartItemBean implements Comparable<ChartItemBean> {
    int sImageId;
    String type;
    float pert;
    float totalMoney;

    public ChartItemBean(int sImageId, String type, float pert, float totalMoney) {
        this.sImageId = sImageId;
        this.type = type;
        this.pert = pert;
        this.totalMoney = totalMoney;
    }

    public ChartItemBean() {
    }

    public int getsImageId() {
        return sImageId;
    }

    public void setsImageId(int sImageId) {
        this.sImageId = sImageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPert() {
        return pert;
    }

    public void setPert(float pert) {
        this.pert = pert;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int compareTo(ChartItemBean arg0){
        if(this.getTotalMoney()-arg0.getTotalMoney()>0)return -1;
        else if(this.getTotalMoney()-arg0.getTotalMoney()==0)return 0;
        else return 1;
    }
}

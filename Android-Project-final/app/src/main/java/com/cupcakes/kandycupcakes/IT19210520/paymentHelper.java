package com.cupcakes.kandycupcakes.IT19210520;

public class paymentHelper {
  public  String nic,date,paiedState,paymenttype;
    public float totalamount;
    String maxid;



    public paymentHelper() {
    }

    public paymentHelper(String maxid,String nic, String date, float totalamount, String paiedState, String paymenttype) {
        this.maxid = maxid;
        this.nic = nic;
        this.date = date;
        this.totalamount = totalamount;
        this.paiedState = paiedState;
        this.paymenttype = paymenttype;
    }

    public String getNIC() {
        return nic;
    }

    public String getDate() {
        return date;
    }

    public float getTotalamount() {
        return totalamount;
    }

    public String getPaiedState() {
        return paiedState;
    }

    public void setNIC(String nic) {
        this.nic = nic;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotalamount(float totalamount) {
        this.totalamount = totalamount;
    }

    public void setPaiedState(String paiedState) {
        this.paiedState = paiedState;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public String getMaxid() {
        return maxid;
    }
}

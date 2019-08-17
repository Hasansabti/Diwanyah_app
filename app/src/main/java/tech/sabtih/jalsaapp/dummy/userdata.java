package tech.sabtih.jalsaapp.dummy;

import com.google.gson.annotations.SerializedName;

public class userdata {

    user details;
   // String req;
    //String unpays;
   // String foryou;


    private double myamnt;
    private double unpaid;


    public user getDetails() {
        return details;
    }

   // public String getReq() {
     //   return req;
   // }

    //public String getUnpays() {
    //    return unpays;
   // }

   // public String getForyou() {
   //     return foryou;
    //}

    public double getUnpaid() {
        return unpaid;
    }

    public double getMyamnt() {
        return myamnt;
    }
}

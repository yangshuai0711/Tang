package edu.heuu.campusAssistant.util;

/**
 * Created by ÎÄ¾²ÃÀÀöĞ¡TeTe on 2018/5/11.
 */

public  class Global {
    static public String phone;
    static public double balance;
    static public String decimal_str(float val, int nres){
        float a_amend = (float) 0.5;
        for (int i = 0; i < nres; i++){
            a_amend /= 10.0;
        }
        val += a_amend;

        int N = (int)val;
        float res = val - (float)N;
        String strRes = ".";
        for(int i = 0; i < nres; i++){
            res *= 10.0;
            int di = (int)(res);
            strRes += String.valueOf(di);
            res = res - (float) di;
        }
        return String.valueOf(N) + strRes;
    }
}

package com.example.gumptionlabs;

public class infoDatabaseWrite {
    private String email,imei,fname,uname,mob,dob;

    public infoDatabaseWrite(){

    }

    public infoDatabaseWrite(String email, String imei, String fname, String uname, String mob, String dob) {
        this.email = email;
        this.imei = imei;
        this.fname = fname;
        this.uname = uname;
        this.mob = mob;
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public String getImei() {
        return imei;
    }

    public String getFname() {
        return fname;
    }

    public String getUname() {
        return uname;
    }

    public String getMob() {
        return mob;
    }

    public String getDob() {
        return dob;
    }
}


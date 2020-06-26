package com.example.deleteandupdate;



public class MyCustomer
{
    String cid;
    String cname;
    String address;
    String city;
    public MyCustomer()
    {

    }
    public MyCustomer(String cid,String cname,String address,String city)
    {
        this.cid=cid;
        this.cname=cname;
        this.address=address;
        this.city=city;

    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCity() {
        return city;
    }

    public String getCname() {
        return cname;
    }

    public String getAddress() {
        return address;
    }

    public String getCid() {
        return cid;
    }
}

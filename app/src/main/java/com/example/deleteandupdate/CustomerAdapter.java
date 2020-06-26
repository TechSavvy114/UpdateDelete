package com.example.deleteandupdate;



import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomerAdapter extends ArrayAdapter<MyCustomer>
{
    private Activity context;
    List<MyCustomer> customer;
    public CustomerAdapter(Activity context,List<MyCustomer> customer)
    {
        super(context,R.layout.show,customer);
        this.context=context;
        this.customer=customer;
    }
    @Override
    public View getView(int po, View view, ViewGroup p)
    {
        LayoutInflater inflater=context.getLayoutInflater();
        View listviewitem=inflater.inflate(R.layout.show,null,true);
        TextView txname=listviewitem.findViewById(R.id.name);
        TextView txaddress=listviewitem.findViewById(R.id.address);
        TextView txmcity=listviewitem.findViewById(R.id.city);
        MyCustomer c=customer.get(po);
        txname.setText(c.getCname());
        txaddress.setText(c.getAddress());
        txmcity.setText(c.getCity());
        return listviewitem;
    }
}

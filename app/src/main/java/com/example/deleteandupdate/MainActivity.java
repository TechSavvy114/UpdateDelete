package com.example.deleteandupdate;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText et1,et2,et3,et4;
    Button b1;
    DatabaseReference cusreff;
    Button b2;
    List<MyCustomer> customer;
    ListView lt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=findViewById(R.id.cid);
        et2=findViewById(R.id.cname);
        et3=findViewById(R.id.address);
        et4=findViewById(R.id.city);
        b1=findViewById(R.id.reg);
        b2=findViewById(R.id.show);
        lt=findViewById(R.id.mylist);
        lt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyCustomer ob=customer.get(position);
                CalingUpdateandDelete(ob.cid,ob.cname,ob.address,ob.city);
            }
        });
        customer=new ArrayList<>();
        cusreff = FirebaseDatabase.getInstance().getReference("MyCustomer");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecords();
            }
        });

    }

    @Override
    public void onStart()
    {
        super.onStart();
        cusreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customer.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    MyCustomer cus=postSnapshot.getValue(MyCustomer.class);
                    customer.add(cus);
                }
                CustomerAdapter myad=new CustomerAdapter(MainActivity.this,customer);
                lt.setAdapter(myad);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void addRecords()
    {

        //getting the values to save
        String cid = et1.getText().toString().trim();
        String cname=et2.getText().toString().trim();
        String address=et3.getText().toString().trim();
        String city = et4.getText().toString();

        //checking if the value is provided
        if (!TextUtils.isEmpty(cname))
        {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Customers
            String id = cusreff.push().getKey();
            //creating an Customers Object
            MyCustomer std = new MyCustomer(id, cname, address,city);

            //Saving the Artist
            cusreff.child(id).setValue(std);

            //setting edittext to blank again
            et2.setText("");

            //displaying a success toast
            Toast.makeText(this, "Customers added", Toast.LENGTH_LONG).show();
        }
        else
        {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a Customers name", Toast.LENGTH_LONG).show();
        }

    }
    private void CalingUpdateandDelete(final  String cid,String cname,String address,String city)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View disview=inflater.inflate(R.layout.update,null);
        builder.setView(disview);
        final EditText updatename=disview.findViewById(R.id.cname);
        final EditText updateaddress=disview.findViewById(R.id.address);
        final EditText updatecity=disview.findViewById(R.id.city);
        updatename.setText(cname);
        updateaddress.setText(address);
        updatecity.setText(city);
        final Button update=disview.findViewById(R.id.update);
        final Button delete=disview.findViewById(R.id.delete);
        builder.setTitle(cname);
        final AlertDialog b=builder.create();
        b.show();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cname=updatename.getText().toString().trim();
                String address=updateaddress.getText().toString().trim();
                String city=updatecity.getText().toString().trim();
                updateUser(cid,cname,address,city);
                b.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deteleUser(cid);
                b.dismiss();
            }
        });

    }
    public boolean updateUser(String cid,String cname,String address,String city)
    {
        DatabaseReference  updatereff=FirebaseDatabase.getInstance().getReference("MyCustomer").child(cid);
        MyCustomer mycus=new MyCustomer(cid,cname,address,city);
        updatereff.setValue(mycus);
        Toast.makeText(getApplicationContext(),"Customers Update",Toast.LENGTH_LONG).show();
        return true;
    }
    public boolean deteleUser(String cid)
    {
        DatabaseReference deletereff=FirebaseDatabase.getInstance().getReference("MyCustomer").child(cid);
        deletereff.removeValue();
        Toast.makeText(getApplicationContext(),"Delete Records",Toast.LENGTH_LONG).show();
        return  true;
    }

}

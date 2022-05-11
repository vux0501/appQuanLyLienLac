package com.example.appqlttll;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddData extends AppCompatActivity
{
   EditText ten, bietDanh, ngaySinh, diaChi, soDienThoai, soThich, purl;
   Button submit,back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddata);

        ten=(EditText)findViewById(R.id.add_ten);
        bietDanh=(EditText)findViewById(R.id.add_bietdanh);
        ngaySinh=(EditText)findViewById(R.id.add_ngaysinh);
        diaChi=(EditText)findViewById(R.id.add_diachi);
        soDienThoai=(EditText)findViewById(R.id.add_sdt);
        soThich=(EditText)findViewById(R.id.add_sothich);
        purl=(EditText)findViewById(R.id.add_purl);

        back=(Button)findViewById(R.id.add_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        submit=(Button)findViewById(R.id.add_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processinsert();
            }
        });
    }

    private void processinsert()
    {
        Map<String,Object> map=new HashMap<>();
        map.put("ten",ten.getText().toString());
        map.put("bietDanh",bietDanh.getText().toString());
        map.put("ngaySinh",ngaySinh.getText().toString());
        map.put("diaChi",diaChi.getText().toString());
        map.put("soDienThoai",soDienThoai.getText().toString());
        map.put("soThich",soThich.getText().toString());


        map.put("purl",purl.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("nguoi").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       ten.setText("");
                       bietDanh.setText("");
                       ngaySinh.setText("");
                       soDienThoai.setText("");
                       diaChi.setText("");
                       soDienThoai.setText("");
                       soThich.setText("");
                       purl.setText("");
                        Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Thêm thất bại",Toast.LENGTH_LONG).show();
                    }
                });

    }
}
package com.example.appqlttll;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyAdapter extends FirebaseRecyclerAdapter<Model, MyAdapter.myviewholder>
{
    public MyAdapter(@NonNull FirebaseRecyclerOptions<Model> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final Model model)
    {
       holder.ten.setText(model.getTen());
       holder.bietDanh.setText(model.getBietDanh());
       holder.ngaySinh.setText(model.getNgaySinh());
       holder.diaChi.setText(model.getDiaChi());
       holder.soDienThoai.setText(model.getSoDienThoai());
       holder.soThich.setText(model.getSoThich());
       Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,2000)
                        .create();

                View myview=dialogPlus.getHolderView();
                final EditText purl=myview.findViewById(R.id.uimgurl);
                final EditText ten=myview.findViewById(R.id.uTen);
                final EditText bietdanh=myview.findViewById(R.id.uBietDanh);
                final EditText ngaysinh=myview.findViewById(R.id.uNgaySinh);
                final EditText diachi=myview.findViewById(R.id.uDiaChi);
                final EditText sodienthoai=myview.findViewById(R.id.uSoDienThoai);
                final EditText sothich=myview.findViewById(R.id.uSoThich);

                Button submit=myview.findViewById(R.id.usubmit);

                purl.setText(model.getPurl());
                ten.setText(model.getTen());
                bietdanh.setText(model.getBietDanh());
                ngaysinh.setText(model.getNgaySinh());
                diachi.setText(model.getDiaChi());
                sodienthoai.setText(model.getSoDienThoai());
                sothich.setText(model.getSoThich());


                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("purl",purl.getText().toString());
                        map.put("ten",ten.getText().toString());
                        map.put("bietDanh",bietdanh.getText().toString());
                        map.put("ngaySinh",ngaysinh.getText().toString());
                        map.put("diaChi",diachi.getText().toString());
                        map.put("soDienThoai",sodienthoai.getText().toString());
                        map.put("soThich",sothich.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("nguoi")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Xoa");
                builder.setMessage("Bạn có chắc xóa không?");

                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("nguoi")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });


    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
       return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        ImageView edit,delete;
        TextView ten, bietDanh, ngaySinh, diaChi, soDienThoai, soThich;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(CircleImageView) itemView.findViewById(R.id.img1);
            ten=(TextView)itemView.findViewById(R.id.ten_text);
            bietDanh=(TextView)itemView.findViewById(R.id.bietdanh_text);
            ngaySinh=(TextView)itemView.findViewById(R.id.ngaysinh_text);
            diaChi=(TextView)itemView.findViewById(R.id.diachi_text);
            soDienThoai=(TextView)itemView.findViewById(R.id.sdt_text);
            soThich=(TextView)itemView.findViewById(R.id.sothich_text);

            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);
        }
    }
}

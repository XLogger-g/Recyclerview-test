package com.example.schoolteacher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolteacher.EditActivity;
import com.example.schoolteacher.ClassActivity;
import com.example.schoolteacher.Model.Listdata;
import com.example.schoolteacher.R;
import com.example.schoolteacher.StreamActivity;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyHolder> {

    List<Listdata> noteslist;
    private Context context;
    public  NotesAdapter(List<Listdata> noteslist,Context context) {
        this.context=context;
        this.noteslist=noteslist;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);

        MyHolder myHolder=new MyHolder(view);
        return myHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int position) {
        final Listdata data=noteslist.get(position);
        myHolder.title.setText(data.getTitle());
        myHolder.desc.setText(data.getDesc());
        myHolder.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(context, myHolder.menuIcon);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setGravity(Gravity.END);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(context, EditActivity.class);
                                i.putExtra("id",data.id);
                                i.putExtra("title",data.title);
                                i.putExtra("desc",data.desc);
                                context.startActivity(i);
                                break;
                            case R.id.action_delete:
                                noteslist.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return noteslist.size();
    }


    class  MyHolder extends RecyclerView.ViewHolder  {
        TextView title,desc;
        ImageView menuIcon;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            menuIcon = itemView.findViewById(R.id.menuIcon);
            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.desc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Listdata listdata=noteslist.get(getAdapterPosition());
                    Intent i=new Intent(context, StreamActivity.class);
                    i.putExtra("id",listdata.id);
                    i.putExtra("title",listdata.title);
                    i.putExtra("desc",listdata.desc);
                    context.startActivity(i);
                }
            });


        }

    }



}
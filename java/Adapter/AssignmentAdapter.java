package com.example.schoolteacher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.example.schoolteacher.EditAssignActivity;
import com.example.schoolteacher.Model.AssignListdata;
import com.example.schoolteacher.Model.Listdata;
import com.example.schoolteacher.R;
import com.example.schoolteacher.StreamActivity;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.MyHolder>  {

    private List<AssignListdata> assignmentlist;
    private Context context;
    public  AssignmentAdapter(List<AssignListdata> assignmentlist, Context context) {
        this.context=context;
        this.assignmentlist=assignmentlist;
    }

    @NonNull
    @Override
    public AssignmentAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.assignment_item,viewGroup,false);

        AssignmentAdapter.MyHolder myHolder=new AssignmentAdapter.MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AssignmentAdapter.MyHolder myHolder, final int position) {


        final AssignListdata data=assignmentlist.get(position);
        myHolder.titleas.setText(data.getTitle());
        myHolder.descas.setText(data.getDesc());




    }

    @Override
    public int getItemCount() {
        return assignmentlist.size();
    }


    class  MyHolder extends RecyclerView.ViewHolder  {
        TextView titleas,descas;
        ImageView menuIcon;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            titleas=itemView.findViewById(R.id.title_as);
            descas=itemView.findViewById(R.id.desc_as);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AssignListdata listdata=assignmentlist.get(getAdapterPosition());
                    Intent i=new Intent(context, EditAssignActivity.class);
                    i.putExtra("id",listdata.id);
                    i.putExtra("title",listdata.titleas);
                    i.putExtra("desc",listdata.descas);
                    context.startActivity(i);
                }
            });


        }

    }
}

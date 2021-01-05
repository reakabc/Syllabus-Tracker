package com.abc.reak.syllabustracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.myViewHolder> {

    Context mContext;
    List<Chapter> list;
    DatabaseHelper databaseHelper;

    public ChapterAdapter(Context mContext, List<Chapter> list, DatabaseHelper helper) {
        this.mContext = mContext;
        this.list = list;
        this.databaseHelper = helper;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_view_chapter, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, final int position) {

        holder.name.setText(list.get(position).getName());
        holder.checkBox1.setChecked(list.get(position).is_theory_completed);
        holder.checkBox2.setChecked(list.get(position).is_numerical_completed);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, list.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //mark as done
                    DatabaseHelper helper = new DatabaseHelper(mContext);
                    boolean status = helper.markTheoryAsDone(list.get(position));
                    Toast.makeText(mContext, status ? "Marked as theory completed!": "Something went wrong!", Toast.LENGTH_SHORT).show();
                }else{
                    boolean status = databaseHelper.markTheoryAsNotDone(list.get(position));
                    Toast.makeText(mContext, status ? "Marked as theory completed!": "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //mark as done
                    boolean status = databaseHelper.markNumericalAsDone(list.get(position));
                    Toast.makeText(mContext, status ? "Marked as theory completed!": "Something went wrong!", Toast.LENGTH_SHORT).show();
                }else{
                    boolean status = databaseHelper.markNumericalAsNotDone(list.get(position));
                    Toast.makeText(mContext, status ? "Marked as theory completed!": "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        CheckBox checkBox1, checkBox2;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.chapter_name);
            checkBox1 = itemView.findViewById(R.id.checkbox_1);
            checkBox2 = itemView.findViewById(R.id.checkbox_2);

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}

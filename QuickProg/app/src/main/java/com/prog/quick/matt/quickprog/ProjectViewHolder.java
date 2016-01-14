package com.prog.quick.matt.quickprog;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.prog.quick.matt.quickprog.models.Project;

/**
 * Created by matt on 1/14/16.
 */
public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView nameTextView;
    private OnItemClickListener listener;

    public ProjectViewHolder(View itemView) {
        super(itemView);
        nameTextView = (TextView) itemView.findViewById(R.id.project_list_item_name);
        itemView.setOnClickListener(this);
    }

    public void bind (Project project) {
        nameTextView.setText(project.getName());
    }

    public TextView getNameTextView () {
        return nameTextView;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onItemClicked(v, getAdapterPosition());
        }
    }
}

package com.example.pawe.rxjavatestproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paweł on 08.05.2018.
 */

public class SimpleStringAdapter extends RecyclerView.Adapter<SimpleStringAdapter.ViewHolder> {
	
	private final Context context;
	private final List<String> stringList = new ArrayList<>();
	
	public SimpleStringAdapter(Context context) {
		this.context = context;
	}
	
	public void setStringList(List<String> list) {
		stringList.clear();
		stringList.addAll(list);
		notifyDataSetChanged();
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_list_item, parent, false);
		return new ViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.textView.setText(stringList.get(position));
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, stringList.get(position), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public int getItemCount() {
		return stringList.size();
	}
	
	class ViewHolder extends RecyclerView.ViewHolder {
		TextView textView;
		
		ViewHolder(View v) {
			super(v);
			textView = v.findViewById(R.id.color_display);
		}
	}

}

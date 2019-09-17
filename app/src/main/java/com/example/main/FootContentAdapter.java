package com.example.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//TODO 장소검색 어댑터
public class FootContentAdapter extends RecyclerView.Adapter<FootContentHolder> {
    ArrayList<FootContentList> tList = new ArrayList<>();
    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;


    public FootContentAdapter(ArrayList<FootContentList> _tList, Context _context) {
        tList = _tList;
    }

    @NonNull
    @Override
    public FootContentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_content, viewGroup, false);
        myHelper=new MyDBHelper(view.getContext());

        FootContentHolder footContentHolder = new FootContentHolder(view);
        return footContentHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull FootContentHolder holder, int _position) {
        final int position = _position;
        String location = tList.get(position).location;
        final int index = tList.get(position).index;
        ((TextView) holder.tvView).setText(location);

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB=myHelper.getWritableDatabase();
                Log.i("Test",index+"");
                sqlDB.execSQL("delete from location where num="+index+";");
                tList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick!=null){itemClick.onClick(v,position);}
            }
        });
//        holder.view.setOnLongClickListener(new View.OnLongClickListener(){
//            @Override
//            public boolean onLongClick(View v) {
//                if(itemLongClick!=null)itemLongClick.onItemLongClick(v,position);
//                return true;
//            }
//        });
    }

    private ItemClick itemClick;
    public interface ItemClick{
        public void onClick(View view, int position);
    }
    public void setItemClick(ItemClick itemClick){
        this.itemClick=itemClick;
    }

//    private ItemLongClick itemLongClick;
//    public interface ItemLongClick{
//        public void onItemLongClick(View view, int position);
//    }
//    public void setItemLongClick(ItemLongClick itemLongClick){
//        this.itemLongClick=itemLongClick;
//    }
    @Override
    public int getItemCount() {
        return tList.size();
    }
}

package com.example.adefault.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adefault.R;
import com.example.adefault.Decoration.SearchHistory;

import java.util.ArrayList;
import java.util.Locale;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {

    private ArrayList<SearchHistory> showList;
    private ArrayList<SearchHistory> itemList;
    private Context context;
    private View.OnClickListener onClickItem;

    public SearchHistoryAdapter(Context context, ArrayList<SearchHistory> itemList, View.OnClickListener onClickItem) {
        this.context = context;
        this.itemList = itemList;
        this.onClickItem = onClickItem;
        this.showList = new ArrayList<>();
        this.showList.addAll(itemList);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault()); //모두 소문자로 변환
        showList.clear();
        if (charText.length() == 0) { //없을 경우
            showList.addAll(itemList); //모두 보여줌
        } else {
            for (SearchHistory searchHistory : itemList) {
                //검색기록과 일치하는 것이 있을 경우
                if (searchHistory.getHistory().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    showList.add(searchHistory);

                }

            }
        }
        //listView 갱신
        notifyDataSetChanged();
    }//filter func

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.search_history_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchHistory item = itemList.get(position);
        holder.imageView.setImageAlpha(item.getIcon());
        holder.textView.setText(item.getHistory());
//        holder.textview.setText(item);
//        holder.textview.setTag(item);
//        holder.textview.setOnClickListener(onClickItem);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.history_image);
            textView = itemView.findViewById(R.id.history_text);
        }
    }

}
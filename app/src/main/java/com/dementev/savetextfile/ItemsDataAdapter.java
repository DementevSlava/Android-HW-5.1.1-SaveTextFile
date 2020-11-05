package com.dementev.savetextfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ItemsDataAdapter extends BaseAdapter {
    ExternalFiles externalFiles;
    Context context;

    private List<ItemData> items;
    private LayoutInflater inflater;

    private View.OnClickListener remBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            removeItem((Integer) v.getTag());
            externalFiles.saveExternalFile(getAdapterStrings());
        }
    };


    ItemsDataAdapter(Context context, List<ItemData> items, ExternalFiles externalFiles) {
        this.context = context;
        this.externalFiles = externalFiles;

        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void addItem(ItemData item) {
        this.items.add(item);
        notifyDataSetChanged();
    }

    // Удаляет элемент списка.
    void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    public List<String> getAdapterStrings() {
        List<String> list = new ArrayList<>();
        for (ItemData item : items) {
            list.add(item.getTitle());
        }
        return list;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ItemData getItem(int position) {
        if (position < items.size()) {
            return items.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_list_view, parent, false);
        }

        ItemData itemData = items.get(position);

        ImageView image = view.findViewById(R.id.icon);
        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);
        Button remBtn = view.findViewById(R.id.remBtn);

        image.setImageDrawable(itemData.getImage());
        title.setText(itemData.getTitle());
        subtitle.setText(itemData.getSubtitle());

        remBtn.setTag(position);
        remBtn.setOnClickListener(remBtnListener);

        return view;
    }
}
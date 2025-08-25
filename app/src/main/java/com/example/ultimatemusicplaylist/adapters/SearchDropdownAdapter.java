package com.example.ultimatemusicplaylist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ultimatemusicplaylist.R;

import java.util.ArrayList;
import java.util.List;

public class SearchDropdownAdapter extends ArrayAdapter<SearchItem> {

    private List<SearchItem> originalItems;
    private List<SearchItem> filteredItems;
    private int maxSuggestions;

    public SearchDropdownAdapter(@NonNull Context context, List<SearchItem> items, int maxSuggestions) {
        super(context, 0, items);
        this.originalItems = new ArrayList<>(items);
        this.filteredItems = new ArrayList<>(items);
        this.maxSuggestions = maxSuggestions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_search_dropdown, parent, false);
        }

        ImageView itemImage = convertView.findViewById(R.id.item_image);
        TextView itemName = convertView.findViewById(R.id.item_name);

        SearchItem item = getItem(position);
        if (item != null) {
            itemImage.setImageResource(item.getImageResId());
            itemName.setText(item.getName());
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return Math.min(filteredItems.size(), maxSuggestions);
    }

    @Nullable
    @Override
    public SearchItem getItem(int position) {
        if (position >= 0 && position < filteredItems.size()) {
            return filteredItems.get(position);
        }
        return null;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<SearchItem> suggestions = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    // Show top 5 items if search is empty
                    suggestions.addAll(originalItems);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (SearchItem item : originalItems) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            suggestions.add(item);
                        }
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItems.clear();
                if (results != null && results.values != null) {
                    filteredItems.addAll((List<SearchItem>) results.values);
                }
                notifyDataSetChanged();
            }
        };
    }
}


package com.eduard.dogs.dogs.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eduard.dogs.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    public Context context;
    public List<String> listDataHeader = new ArrayList<>();
    public HashMap<String, List<String>> listDataChild = new HashMap<>();

    public ExpandableListViewAdapter(Context ctx) {
        this.context = ctx;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.view_dogs_list_items_child, null);
        }

        TextView txtListChild = convertView
                .findViewById(R.id.dogs_name_text_child);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.view_dogs_list_item, null);
        }

        ImageView arrowIcon = convertView
                .findViewById(R.id.iv_indicator);

        if (getChildrenCount(groupPosition) > 0) {
            arrowIcon.setVisibility(View.VISIBLE);
            if (isExpanded) {
                arrowIcon.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            } else {
                arrowIcon.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            }
        } else {
            arrowIcon.setVisibility(View.GONE);
        }

        TextView lblListHeader = convertView
                .findViewById(R.id.dogs_name_text);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setData(List<String> listDataHeader, HashMap<String, List<String>> listDataChild) {
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
    }
}

package com.samsalek.accountdiary;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class AccountExpandableListAdapter implements ExpandableListAdapter {

    private Context context;
    private char[] accountGroups;
    private Map<Character, List<String>> accountMap;

    public AccountExpandableListAdapter(Context context, char[] swedishAlphabet, Map<Character, List<String>> accountMap) {

        this.context = context;
        this.accountGroups = swedishAlphabet;
        this.accountMap = accountMap;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return accountGroups.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Character accountGroup = accountGroups[groupPosition];
        return Objects.requireNonNull(accountMap.get(accountGroup)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        Character accountGroup = accountGroups[groupPosition];
        return accountMap.get(accountGroup);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Character accountGroup = accountGroups[groupPosition];
        return Objects.requireNonNull(accountMap.get(accountGroup)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String accountGroupName = String.valueOf(accountGroups[groupPosition]).toUpperCase();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_item, null);
        }

        // TODO - make this work somehow, someday
        /*
        if (Objects.requireNonNull(this.accountMap.get(this.accountGroups[groupPosition])).size() == 0) {
            if(isExpanded) {
                convertView.setOnClickListener(null);
                convertView.setBackgroundColor(Color.LTGRAY);
                Log.d(getClass().getName(), ""+groupPosition);
            }
        }
        */

        TextView item = convertView.findViewById(R.id.group_item);
        item.setText(accountGroupName);
        item.setTypeface(null, Typeface.BOLD);
        item.setTextSize(35);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Character accountGroup = accountGroups[groupPosition];
        String accountName = String.valueOf(Objects.requireNonNull(accountMap.get(accountGroup)).get(childPosition));
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_item, null);
        }

        TextView item = convertView.findViewById(R.id.child_item);
        item.setText(accountName);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}

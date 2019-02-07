package com.example.hezeyan.myapplication;

/**
 * Created by HPC on 5/2/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class CheckboxAdapter extends BaseAdapter {

    Context mContext;
    List<Checkbox> linkedList;
    protected LayoutInflater vi;

    private boolean[] checkBoxState = null;
    private HashMap<Checkbox, Boolean> checkedForCheckbox = new HashMap<>();


    public CheckboxAdapter(Context context, List<Checkbox> linkedList) {
        this.mContext = context;
        this.linkedList = linkedList;
        this.vi = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return linkedList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = vi.inflate(R.layout.row_layout, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.textView);
            holder.selectionBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Checkbox checkbox = linkedList.get(position);
        checkBoxState = new boolean[linkedList.size()];

        holder.title.setText(checkbox.getName());

        /** checkBoxState has the value of checkBox ie true or false,
         * The position is used so that on scroll your selected checkBox maintain its state **/
        if(checkBoxState != null)
            holder.selectionBox.setChecked(checkBoxState[position]);

        holder.selectionBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    checkBoxState[position] = true;
                    ischecked(position,true);
                }
                else {
                    checkBoxState[position] = false;
                    ischecked(position,false);
                }
            }
        });


        /**if country is in checkedForCountry then set the checkBox to true **/
        if (checkedForCheckbox.get(checkbox) != null) {
            holder.selectionBox.setChecked(checkedForCheckbox.get(checkbox));
        }

        /**Set tag to all checkBox**/
        holder.selectionBox.setTag(checkbox);

        return convertView;
    }


    private class ViewHolder {
        TextView title;
        CheckBox selectionBox;
    }

    public void ischecked(int position,boolean flag )
    {
        checkedForCheckbox.put(this.linkedList.get(position), flag);
    }


    public LinkedList<String> getSelectedCountry(){
        LinkedList<String> List = new LinkedList<>();
        for (Map.Entry<Checkbox, Boolean> pair : checkedForCheckbox.entrySet()) {
            if(pair.getValue()) {
                List.add(pair.getKey().getName());
            }
        }
        return List;
    }
}

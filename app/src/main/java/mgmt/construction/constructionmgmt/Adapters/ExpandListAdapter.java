package mgmt.construction.constructionmgmt.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import mgmt.construction.constructionmgmt.Classes.AppController;
import mgmt.construction.constructionmgmt.Classes.Child;
import mgmt.construction.constructionmgmt.Classes.Group;
import mgmt.construction.constructionmgmt.R;

/**
 * Created by Recluse on 2/2/2016.
 */
public class ExpandListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Group> groups;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ExpandListAdapter(Context context, ArrayList<Group> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Child> chList = groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Child child = (Child) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_item, null);
        }

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView tv = (TextView) convertView.findViewById(R.id.task_name);
        TextView tStatus = (TextView) convertView.findViewById(R.id.task_status);
        View v=convertView.findViewById(R.id.view_status);

      //  NetworkImageView iv = (NetworkImageView) convertView
         //       .findViewById(R.id.flag);
        // iv.setImageUrl(child.getImage(), imageLoader);
        tv.setText(child.getName());
        if(child.getStatus().compareToIgnoreCase("D")==0) {
            tStatus.setText("Task Done");
            v.setBackgroundColor(ContextCompat.getColor(context, R.color.task_done));
        }
        else if(child.getStatus().compareToIgnoreCase("O")==0) {
            tStatus.setText("Task Ongoing");
            v.setBackgroundColor(ContextCompat.getColor(context, R.color.task_ongoing));
        }
        else if(child.getStatus().compareToIgnoreCase("N")==0) {
            tStatus.setText("Task Not Yet Started");
            v.setBackgroundColor(ContextCompat.getColor(context, R.color.task_not_started));
        }


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<Child> chList = groups.get(groupPosition).getItems();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Group group = (Group) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.group_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.group_name);
        tv.setText(group.getName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}

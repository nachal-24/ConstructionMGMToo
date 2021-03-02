package mgmt.construction.constructionmgmt.Adapters;

import android.content.Context;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mgmt.construction.constructionmgmt.R;


/**
 * Created by Recluse on 9/6/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private Context context;
  /*  private List<Integer> mFragmentIcons = new ArrayList<>();
    private int[] imageResId = {
            R.drawable.construction_management,
            R.drawable.construction_management,
            R.drawable.construction_management,
            R.drawable.construction_management
    };*/
    public ViewPagerAdapter(Context context, FragmentManager manager) {
        super(manager);
        this.context=context;

    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        //mFragmentIcons.add(drawable);
        mFragmentTitleList.add(title);
    }
    @Override
    public CharSequence getPageTitle(int position) {

        return mFragmentTitleList.get(position);
    }

    public View getTabView(int position) {
        View tab = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tabText = (TextView) tab.findViewById(R.id.tabText);
        tabText.setTextColor(context.getResources().getColorStateList(R.color.tab_indicator_text));
        //ImageView tabImage = (ImageView) tab.findViewById(R.id.tabImage);
        tabText.setText(mFragmentTitleList.get(position));
        //  tabImage.setBackgroundResource(mFragmentIcons.get(position));
        if (position == 0) {
            tab.setSelected(true);
        }
        return tab;
    }
}

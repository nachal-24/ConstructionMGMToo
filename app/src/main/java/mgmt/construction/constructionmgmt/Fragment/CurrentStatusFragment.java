package mgmt.construction.constructionmgmt.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import mgmt.construction.constructionmgmt.Adapters.ViewPagerAdapter;
import mgmt.construction.constructionmgmt.R;

/**
 * Created by Recluse on 10/26/2015.
 */
public class CurrentStatusFragment extends Fragment {
    TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.current_status, parentViewGroup, false);
        setupTablayout(rootView);
        return rootView;
    }
    private void setupTablayout(View rootview){

        //  android.support.design.widget.AppBarLayout.LayoutParams params = new android.support.design.widget.AppBarLayout.LayoutParams(
        //          android.support.design.widget.AppBarLayout.LayoutParams.WRAP_CONTENT,
        //         android.support.design.widget.AppBarLayout.LayoutParams.WRAP_CONTENT
        //  );

        tabLayout = (TabLayout) rootview.findViewById(R.id.tabLayout);
        final android.support.v4.view.ViewPager viewPager=(android.support.v4.view.ViewPager) rootview.findViewById(R.id.viewpager);
        ;
        setupViewPager(viewPager);
       // tabLayout.post(new Runnable() {
       //     @Override
       ///     public void run() {
        tabLayout.setupWithViewPager(viewPager);
      //      }
       // });
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
       // tabLayout.getTabAt(0).setText("Progress");
      //  tabLayout.getTabAt(1).setText("Status");
        // params.setMargins(0, tabLayout.getHeight(), 0, 0);
        //tabLayout.setLayoutParams(params);
       for (int i = 0; i < tabLayout.getTabCount(); i++) {
           TabLayout.Tab tab = tabLayout.getTabAt(i);
           tab.setCustomView(adapter.getTabView(i));
         /*   TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab1, null);
            tabOne.setText("ONE");
            if(i==0)
            tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_action_maps_directions_icon, 0, 0);
            else if(i==1)
                tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_action_transport_train_icon, 0, 0);
            else if(i==2)
                tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_action_transport_bus_icon, 0, 0);
            else if(i==2)
                tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_action_location_2_icon, 0, 0);
            tabLayout.getTabAt(0).setCustomView(tabOne);*/

       }


        if(tabLayout == null)
            return;
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        // showToast("One");
                        break;
                    case 1:
                        // showToast("Two");
                        break;
                    case 2:
                        //  showToast("Three");
                        break;
                    case 3:
                        // showToast("Four");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //   tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        //  tabLayout.addTab(tabLayout.newTab().setText(R.string.title_section1));
        //   tabLayout.addTab(tabLayout.newTab().setText(R.string.title_section2));
        //  tabLayout.addTab(tabLayout.newTab().setText(R.string.title_section3));
        //   tabLayout.addTab(tabLayout.newTab().setText(R.string.title_section4));
    }
    private void showToast(String s)
    {
        Toast.makeText(getActivity().getApplicationContext(),(String)s,
                Toast.LENGTH_SHORT).show();
    }
    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getActivity().getApplicationContext(),getFragmentManager());
        adapter.addFrag(new PieChartFragment(),"Inv. Status");
        adapter.addFrag(new BarChartFragment(),"Progress");
        adapter.addFrag(new CalendarViewFragment(),"TimeLine");



        //adapter.addFrag(new TrainTimingsFragment(ContextCompat.getColor(getApplicationContext(), R.color.my_accent)), this.getString(R.string.title_section2),R.drawable.ic_action_transport_train_icon);
       // adapter.addFrag(new BusNoFragment(ContextCompat.getColor(getApplicationContext(), R.color.my_accent)), this.getString(R.string.title_section3),R.drawable.ic_action_transport_bus_icon);
        if(viewPager==null)
        {
            Log.v("CURRENTSTATUS","viewpager is null");

        }
        else if(adapter==null)
            Log.v("CURRENTSTATUS", "adapter is null");

        viewPager.setAdapter(adapter);
    }

    }

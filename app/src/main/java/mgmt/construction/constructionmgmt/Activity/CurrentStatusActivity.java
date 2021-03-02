package mgmt.construction.constructionmgmt.Activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import mgmt.construction.constructionmgmt.Adapters.ViewPagerAdapter;
import mgmt.construction.constructionmgmt.Classes.PreferencesHelper;
import mgmt.construction.constructionmgmt.Fragment.BarChartFragment;
import mgmt.construction.constructionmgmt.Fragment.CalendarViewFragment;
import mgmt.construction.constructionmgmt.Fragment.LineChartFragment;
import mgmt.construction.constructionmgmt.Fragment.PieChartFragment;
import mgmt.construction.constructionmgmt.R;

/**
 * Created by Recluse on 10/26/2015.
 */
public class CurrentStatusActivity extends RootActivity {
    DrawerLayout drawerLayout;
    TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private NavigationView navigationView;
    private BroadcastReceiver signOutBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_status);
       /* IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        signOutBroadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive", "Logout in progress");
                Intent taskIntent =
                        new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(taskIntent);
                finish();
            }
        };*/
        setupToolbar();
        setupNavigationView();
        setupTablayout();
    }
   /* private void setupNavigationView(){

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    }*/
    private void setupToolbar(){
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowTitleEnabled(true);
        // ab.setLogo(R.drawable.ic_launcher);
        //ab.setIcon(R.drawable.ic_launcher);
        ab.setDisplayHomeAsUpEnabled(true);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_player);
      //  setSupportActionBar(toolbar);
    }
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out: {
                Toast.makeText(getApplicationContext(), "Sign Out", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }*/
  private void setupTablayout(){

      //  android.support.design.widget.AppBarLayout.LayoutParams params = new android.support.design.widget.AppBarLayout.LayoutParams(
      //          android.support.design.widget.AppBarLayout.LayoutParams.WRAP_CONTENT,
      //         android.support.design.widget.AppBarLayout.LayoutParams.WRAP_CONTENT
      //  );

      tabLayout = (TabLayout) findViewById(R.id.tabLayout);
      final android.support.v4.view.ViewPager viewPager=(android.support.v4.view.ViewPager) findViewById(R.id.viewpager);
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
        Toast.makeText(getApplicationContext(),(String)s,
                Toast.LENGTH_SHORT).show();
    }
    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getApplicationContext(),getSupportFragmentManager());
        adapter.addFrag(new LineChartFragment(), "Status");
        adapter.addFrag(new BarChartFragment(), "Tasks");
        adapter.addFrag(new CalendarViewFragment(),"Orders");
        adapter.addFrag(new PieChartFragment(), "Cost");


        //adapter.addFrag(new TrainTimingsFragment(ContextCompat.getColor(getApplicationContext(), R.color.my_accent)), this.getString(R.string.title_section2),R.drawable.ic_action_transport_train_icon);
        // adapter.addFrag(new BusNoFragment(ContextCompat.getColor(getApplicationContext(), R.color.my_accent)), this.getString(R.string.title_section3),R.drawable.ic_action_transport_bus_icon);
        if(viewPager==null)
        {
            Log.v("CURRENTSTATUS", "viewpager is null");

        }
        else if(adapter==null)
            Log.v("CURRENTSTATUS", "adapter is null");

        viewPager.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(drawerLayout != null)
                    drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.sign_out:
                PreferencesHelper.signOut(this);
                Intent intent = new Intent(this, LoginActivity.class);
                // intent.putExtra("finish", true); // if you are checking for this in your other Activities
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            case R.id.change_pass:
                Intent intentPass = new Intent(this, PasswordChangeActivity.class);
                // intent.putExtra("finish", true); // if you are checking for this in your other Activities
               // intentPass.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                //        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                 //       Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentPass);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
    private void setupNavigationView() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.EOQ:
                        //Toast.makeText(getApplicationContext(), "Inbox Selected", Toast.LENGTH_SHORT).show();
                        Intent taskIntent =
                                new Intent(getApplicationContext(), EconomicOrderQuantityActivity.class);
                        startActivity(taskIntent);
                      //  overridePendingTransition(R.animator.anim_slide_in_left,
                        //        R.animator.anim_slide_out_left);
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.NonInstantEOQ:
                        //Toast.makeText(getApplicationContext(),"Stared Selected",Toast.LENGTH_SHORT).show();
                        Intent nonInstantEOQ =
                                new Intent(getApplicationContext(), NonInstantEOQActivity.class);
                        startActivity(nonInstantEOQ);
                        return true;
                    case R.id.TaskList:
                        Intent TaskListIntent =
                                new Intent(getApplicationContext(), TaskListActivity.class);
                        startActivity(TaskListIntent);
                        //Toast.makeText(getApplicationContext(),"Send Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.addTask:
                       // Toast.makeText(getApplicationContext(),"Drafts Selected",Toast.LENGTH_SHORT).show();
                        Intent addTaskIntent =
                                new Intent(getApplicationContext(), AddTaskActivity.class);
                        startActivity(addTaskIntent);
                        return true;
                    case R.id.ordersList:
                        //Toast.makeText(getApplicationContext(),"All Mail Selected",Toast.LENGTH_SHORT).show();
                        Intent ordersListIntent =
                                new Intent(getApplicationContext(), OrdersListActivity.class);
                        startActivity(ordersListIntent);
                        return true;
                   // case R.id.calendar_view:
                      //  Intent calendarIntent =
                        //        new Intent(getApplicationContext(), CalendarViewActivity.class);
                        //startActivity(calendarIntent);
                        //Toast.makeText(getApplicationContext(),"Trash Selected",Toast.LENGTH_SHORT).show();
                        //return true;
                   // case R.id.sign_out:
                     //   finish();
                        //Toast.makeText(getApplicationContext(),"Spam Selected",Toast.LENGTH_SHORT).show();
                     //   return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();






    }
    @Override
    protected void onDestroy()
    {super.onDestroy();
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(signOutBroadcastReceiver);

    }
}

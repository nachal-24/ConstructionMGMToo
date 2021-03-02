package mgmt.construction.constructionmgmt.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import mgmt.construction.constructionmgmt.Classes.Child;
import mgmt.construction.constructionmgmt.Classes.PreferencesHelper;
import mgmt.construction.constructionmgmt.Fragment.ExpTaskListResultFragment;
import mgmt.construction.constructionmgmt.Fragment.OrdersListFragment;
import mgmt.construction.constructionmgmt.Classes.DummyContent;
import mgmt.construction.constructionmgmt.Fragment.OrdersListResultFragment;
import mgmt.construction.constructionmgmt.R;

public class OrdersListActivity extends RootActivity implements OrdersListFragment.OnListFragmentInteractionListener,
        OrdersListResultFragment.OnFragmentInteractionListener
{
    DrawerLayout drawerLayout;
    OrdersListFragment ordersListFragment;
    OrdersListResultFragment ordersListResultFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        if (savedInstanceState == null) {
            ordersListFragment=OrdersListFragment.newInstance(1);
                    getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentParentViewGroup, ordersListFragment)
                    .commit();
        }
      //  else
      //  {
      //      ordersListFragment = (OrdersListFragment) getSupportFragmentManager().findFragmentByTag("ordertag");
      //  }
        setupToolbar();
        setupNavigationView();
    }
    private void setupNavigationView(){

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    }
    private void setupToolbar(){
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Orders List");
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
                super.onBackPressed();
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void onOrdersListFragmentInteraction(DummyContent.DummyItem mItem){
        //you can leave it empty
        //Toast.makeText(getApplicationContext(), mItem.content, Toast.LENGTH_LONG).show();
        OrdersListResult(mItem);
    }
    public void onOrdersListResultFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    public void OrdersListResult(DummyContent.DummyItem mItem)
    {
        ordersListResultFragment=OrdersListResultFragment.newInstance(mItem);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentParentViewGroup,ordersListResultFragment )
                .addToBackStack(null)
                .commit();
    }

  /*  @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
      //  if (outState!=null)
     //   getSupportFragmentManager()
          //      .putFragment(outState, "ordertag", ordersListFragment);

        //getSupportFragmentManager()
            //    .putFragment(outState, "orderresulttag", ordersListResultFragment);
    }*/
}

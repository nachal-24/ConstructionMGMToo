package mgmt.construction.constructionmgmt.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import mgmt.construction.constructionmgmt.Classes.Child;
import mgmt.construction.constructionmgmt.Classes.EconomicOrderQuantity;
import mgmt.construction.constructionmgmt.Classes.PreferencesHelper;
import mgmt.construction.constructionmgmt.Fragment.EOQFragment;
import mgmt.construction.constructionmgmt.Fragment.EOQResultFragment;
import mgmt.construction.constructionmgmt.Fragment.ExpTaskListFragment;
import mgmt.construction.constructionmgmt.Fragment.ExpTaskListResultFragment;
import mgmt.construction.constructionmgmt.R;

public class TaskListActivity extends RootActivity implements ExpTaskListFragment.OnFragmentInteractionListener,
ExpTaskListResultFragment.OnFragmentInteractionListener{
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentParentViewGroup, new ExpTaskListFragment())
                    .commit();
        }
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
        ab.setTitle("Tasks List");
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
    public void onExpTaskListFragmentInteraction(Child c){
        //you can leave it empty
        //Toast.makeText(getApplicationContext(), c.getName() + " " + c.getStatus(), Toast.LENGTH_LONG).show();
        ExpTaskListResult(c);
    }
    public void onExpTaskListResultFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    public void ExpTaskListResult(Child c)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentParentViewGroup, ExpTaskListResultFragment.newInstance(c))
                .addToBackStack(null)
                .commit();
    }
}

package mgmt.construction.constructionmgmt.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Calendar;
import java.util.Date;

import mgmt.construction.constructionmgmt.Classes.EconomicOrderQuantity;
import mgmt.construction.constructionmgmt.Classes.NonInstantEconomicOrderQuantity;
import mgmt.construction.constructionmgmt.Classes.PreferencesHelper;
import mgmt.construction.constructionmgmt.Fragment.EOQFragment;
import mgmt.construction.constructionmgmt.Fragment.EOQResultFragment;
import mgmt.construction.constructionmgmt.Fragment.NonInstEOQFragment;
import mgmt.construction.constructionmgmt.Fragment.NonInstEOQResultFragment;
import mgmt.construction.constructionmgmt.R;

/**
 * Created by Recluse on 1/19/2016.
 */

public class NonInstantEOQActivity extends RootActivity implements NonInstEOQFragment.OnFragmentInteractionListener,
        NonInstEOQResultFragment.OnFragmentInteractionListener
{
    DrawerLayout drawerLayout;
    EditText totQuan;
    EditText costPerOrd;
    EditText carCost;
    EditText totDays;
    EditText prodRate;
    EditText eItemDetail;
    Button mEmailSignInButton;
    TextView totMinCost;
    TextView optOrderSize;
    TextView noOfOrders;
    TextView ordersCycle;
    TextView maxInventoryLevel;
    TextView endDate;
    TextView startDate;
    String sItemDetail="";
    String sStartDate="";
    String sEndDate="";

    ImageButton startDatePicker;
    ImageButton endDatePicker;
    boolean sDtPicker=false;
    boolean eDtPicker=false;
    int syear;
    int smonthofyear;
    int sdayofmonth;
    int eyear;
    int emonthofyear;
    int edayofmonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.non_instant_eoq);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentParentViewGroup, new NonInstEOQFragment())
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
        ab.setDisplayUseLogoEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Non Inst. EOQ");
       // ab.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        //ab.setDisplayUseLogoEnabled(true);
       // ab.setDisplayShowTitleEnabled(true);
        // ab.setLogo(R.drawable.ic_launcher);
        //ab.setIcon(R.drawable.ic_launcher);
        //ab.setDisplayHomeAsUpEnabled(true);
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

    public void onNonInstEOQFragmentInteraction(NonInstantEconomicOrderQuantity eoq){
        //you can leave it empty
        //Log.v("RESULTCHECK","fragInt"+eoq.getOptimumOrderSize());
        NonInstEOQResult(eoq);
    }
    public void onNonInstEOQResultFragmentInteraction(Uri uri){
        //you can leave it empty
        //Log.v("RESULTCHECK","fragInt"+eoq.getOptimumOrderSize());

    }
    public void NonInstEOQResult(NonInstantEconomicOrderQuantity eoq)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentParentViewGroup, NonInstEOQResultFragment.newInstance(eoq))
                .addToBackStack(null)
                .commit();
    }

}
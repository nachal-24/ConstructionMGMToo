package mgmt.construction.constructionmgmt.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Calendar;

import mgmt.construction.constructionmgmt.Classes.EconomicOrderQuantity;
import mgmt.construction.constructionmgmt.Classes.NonInstantEconomicOrderQuantity;

import mgmt.construction.constructionmgmt.Classes.PreferencesHelper;
import mgmt.construction.constructionmgmt.Fragment.EOQFragment;
import mgmt.construction.constructionmgmt.Fragment.EOQResultFragment;
import mgmt.construction.constructionmgmt.Fragment.LoginFragment;
import mgmt.construction.constructionmgmt.R;

/**
 * Created by Recluse on 1/18/2016.
 */

public class EconomicOrderQuantityActivity extends RootActivity implements EOQFragment.OnFragmentInteractionListener,
        EOQResultFragment.OnFragmentInteractionListener{
    DrawerLayout drawerLayout;
    EditText totQuan;
    EditText costPerOrd;
    EditText carCost;
    EditText totDays;
    Button mEmailSignInButton;
    TextView totMinCost;
    TextView optOrderSize;
    TextView noOfOrders;
    TextView ordersCycle;
    TextView endDate;
    TextView startDate;
    ImageButton startDatePicker;
    ImageButton endDatePicker;
    boolean sDtPicker=false;
    boolean eDtPicker=false;
    String soptimumOrderSize="";
    String stotMinCost="";
    String snoOfOrders="";
    String sordersCycle="";
    String sItem="";
    int syear;
    int smonthofyear;
    int sdayofmonth;
    int eyear;
    int emonthofyear;
    int edayofmonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   overridePendingTransition(R.animator.anim_slide_in_right,
        //        R.animator.anim_slide_out_right);
        setContentView(R.layout.economic_order_quantity);
       /* startDate=(TextView) findViewById(R.id.startDateTV);
        endDate=(TextView) findViewById(R.id.endDateTV);
        startDatePicker  = (ImageButton)findViewById(R.id.startDate);
        startDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.v("DATEPICKER","button click");
                sDtPicker = true;
                DatePicker();
            }
        });
        endDatePicker  = (ImageButton)findViewById(R.id.endDate);
        endDatePicker  = (ImageButton)findViewById(R.id.endDate);
        endDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("DATEPICKER", "end button click");
                // endDatePicker();
                sDtPicker=false;
                Toast.makeText(getApplicationContext(), "End Button Click", Toast.LENGTH_SHORT).show();
                DatePicker();

            }
        });

        mEmailSignInButton = (Button) findViewById(R.id.calculateEOQ);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateEOQ();
            }
        });*/
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentParentViewGroup, new EOQFragment())
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
        ab.setTitle("Economic Order Quantity");
        //ab.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        //ab.setDisplayUseLogoEnabled(true);
        //ab.setDisplayShowTitleEnabled(true);
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

   /* public void onEOQFragmentInteraction(String soptimumOrderSize,String snoOfOrders,String sordersCycle,String stotMinCost,String sItem){
        //you can leave it empty
        this.soptimumOrderSize=soptimumOrderSize;
        this.snoOfOrders=snoOfOrders;
        this.sordersCycle=sordersCycle;
        this.stotMinCost=stotMinCost;
        this.sItem=sItem;
        Log.v("RESULTCHECK","fragInt"+soptimumOrderSize);
        EOQResult();
    }*/
   public void onEOQFragmentInteraction(EconomicOrderQuantity eoq){
       //you can leave it empty
       Log.v("RESULTCHECK","fragInt"+eoq.getOptimumOrderSize());
       EOQResult(eoq);
   }
    public void onEOQResultFragmentInteraction(Uri uri){
        //you can leave it empty
    }
  /*  public void EOQResult(EconomicOrderQuantity eoq)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentParentViewGroup, EOQResultFragment.newInstance(soptimumOrderSize,snoOfOrders,sordersCycle,stotMinCost,sItem))
                .addToBackStack(null)
                .commit();
    }*/
  public void EOQResult(EconomicOrderQuantity eoq)
  {
      getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.fragmentParentViewGroup, EOQResultFragment.newInstance(eoq))
              .addToBackStack(null)
              .commit();
  }
 /*   public void calculateEOQ(){
        try {


            totQuan = (EditText)findViewById(R.id.totalQuantity);
            double totalQuantity=0;
            if(totQuan.getText().toString().compareTo("")!=0 && totQuan.getText().toString()!=null )
                totalQuantity = Double.parseDouble(totQuan.getText().toString());

            double costPerOrder=0;
            costPerOrd = (EditText)findViewById(R.id.costPerOrder);
            if(costPerOrd.getText().toString().compareTo("")!=0 && costPerOrd.getText().toString()!=null)
                costPerOrder = Double.parseDouble(costPerOrd.getText().toString());

            double carryingCost=0;
            carCost = (EditText)findViewById(R.id.carryingCost);
            if(carCost.getText().toString().compareTo("")!=0 && carCost.getText().toString()!=null)
                carryingCost = Double.parseDouble(carCost.getText().toString());

            double totalDays=0;
            totDays = (EditText)
                    findViewById(R.id.totDays);
            if(totDays.getText().toString().compareTo("")!=0 && totDays.getText().toString()!=null)
                totalDays = Double.parseDouble(totDays.getText().toString());

            optOrderSize=(TextView)
                    findViewById(R.id.optOrderSize);
            totMinCost=(TextView) findViewById(R.id.totMinCost);
            noOfOrders=(TextView)
            findViewById(R.id.noOfOrders);
            ordersCycle=(TextView) findViewById(R.id.ordersCycle);





            if(carryingCost!=0 && costPerOrder!=0 && totalQuantity!=0 && totalDays!=0) {

                EconomicOrderQuantity eoq=new EconomicOrderQuantity(carryingCost,costPerOrder,totalQuantity,totalDays);

                // NonInstantEconomicOrderQuantity eoq=new NonInstantEconomicOrderQuantity(carryingCost,costPerOrder,totalQuantity,totalDays,150);
                double optimumOrderSize=eoq.optimalOrderSize();

                optOrderSize.setText("Optimum Order Size " + String.valueOf(optimumOrderSize));
                optOrderSize.setVisibility(View.VISIBLE);


                totMinCost.setText("Total Inventory Cost Rs." + String.valueOf(eoq.totalAnnualMinimumInventoryCost()));
                totMinCost.setVisibility(View.VISIBLE);


                noOfOrders.setText("No of Orders " + String.valueOf(eoq.noOfOrdersCycle()));
                // noOfOrders.setText("No of Orders " + String.valueOf(eoq.noOfProductionRunsPerCycle()));
                noOfOrders.setVisibility(View.VISIBLE);



                ordersCycle.setText("Orders Cycle " + String.valueOf(eoq.ordersCycleTime()) + " Days");
                // ordersCycle.setText("Orders Cycle " + String.valueOf(eoq.productionRun()) + " Days" + eoq.maximumInventoryLevel());
                ordersCycle.setVisibility(View.VISIBLE);
            }
            else {
                if(totalQuantity==0)
                {
                    totQuan.setError("This field is required");
                    totQuan.requestFocus();
                }
                else if(costPerOrder==0)
                {
                    costPerOrd.setError("This field is required");
                    costPerOrd.requestFocus();
                }
                else if(carryingCost==0)
                {
                    carCost.setError("This field is required");
                    carCost.requestFocus();
                }


                else if(totalDays==0)
                {
                    totDays.setError("This field is required");
                    totDays.requestFocus();
                }
                else
                {
                    optOrderSize.setText("Enter Necessary details");
                    optOrderSize.setVisibility(View.VISIBLE);
                }

            }
        }
        catch (Exception e){}
    }
    public  void DatePicker()
    {
        Log.v("DATEPICKER", "In satrrt method");
        //Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String date = "You picked the following State date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

                        if(sDtPicker==true) {

                            setStartDate(year, monthOfYear, dayOfMonth);
                        }
                        else
                        {

                            setEndDate(year, monthOfYear,dayOfMonth);
                        }


                        Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
                    }

                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.vibrate(false);
        // dpd.showYearPickerFirst(true);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }
    public void setStartDate(int year,int monthOfYear, int dayOfMonth)
    {
        sDtPicker=false;
        syear=year;
        smonthofyear=monthOfYear;
        sdayofmonth=dayOfMonth;
        startDate.setText(sdayofmonth + "/" + (smonthofyear + 1) + "/" + syear);
        startDate.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), "STArt Date set", Toast.LENGTH_SHORT).show();
    }

    public DateTime getStartDate()
    {
        DateTime startDate;
        if(eyear!=0 && emonthofyear>=0 && edayofmonth!=0)
            startDate = new DateTime(syear, smonthofyear+1, sdayofmonth, 12, 0);
        else
            startDate = new DateTime(1900, 1, 1, 12, 0);
        return startDate;
    }
    public void setEndDate(int year,int monthOfYear, int dayOfMonth)
    {
        eDtPicker=false;
        eyear=year;
        emonthofyear=monthOfYear;
        edayofmonth=dayOfMonth;
        endDate.setText(edayofmonth + "/" + (emonthofyear + 1) + "/" + eyear);
        endDate.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), "End Date set", Toast.LENGTH_SHORT).show();
        int days=totalNoOfDays();
        totDays = (EditText)findViewById(R.id.totDays);
        totDays.setText(days + "");
    }
    public DateTime getEndDate()
    {
        DateTime endDate;
        if(eyear!=0 && emonthofyear>=0 && edayofmonth!=0)
            endDate = new DateTime(eyear, emonthofyear+1, edayofmonth, 12, 0);
        else
            endDate = new DateTime(1900, 1, 1, 12, 0);
        return endDate;
    }

    public int totalNoOfDays()
    {

        int days = Days.daysBetween(getStartDate().withTimeAtStartOfDay(), getEndDate().withTimeAtStartOfDay()).getDays();
        Log.v("DATEPICKER", "totoalnoofdays" + days + " " + getStartDate() + " " + getEndDate());
        if(days>35000 || days<0)
            days=0;
        return  days;
    }*/
}
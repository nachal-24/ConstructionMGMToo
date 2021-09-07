package mgmt.construction.constructionmgmt.Activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import mgmt.construction.constructionmgmt.Classes.AppController;
import mgmt.construction.constructionmgmt.Classes.JSONObjectRequest;
import mgmt.construction.constructionmgmt.Classes.JSONParser;
import mgmt.construction.constructionmgmt.Classes.PreferencesHelper;
import mgmt.construction.constructionmgmt.R;

/**
 * Created by Recluse on 10/29/2015.
 */

public class AddTaskActivity extends RootActivity {
    DrawerLayout drawerLayout;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USERLOGIN = "product";
    private static final String TAG_PID = "pid";
    private static final String TAG_ID = "username";
    private static final String TAG_PASSWORD = "password";
    //private GetProductDetails mTask=null;
    private BroadcastReceiver signOutBroadcastReceiver;
    Button buttonAssignTask;
    Map<String, String> params=null;
EditText taskName;
    EditText totDays;
    //EditText startDate;
    //EditText finishDate;
    EditText predecessors;
    EditText resources;
    EditText totEstCost;
    TextView textTopic;
    Spinner taskType;
    Spinner taskStatus;
    char cTaskType;
    char cTaskStatus;
    TextView endDate;
    TextView startDate;
    TextView header;
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
    String sStartDate="";
    String sEndDate="";
    String sTotCost="";
    LinearLayout form;
    // single product url
    private static final String url_product_detials = "http://alamaari.com/construction/get_password.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assign_new_task);
        taskName=(EditText) findViewById(R.id.taskName);
        totDays=(EditText) findViewById(R.id.duration);
        //startDate=(EditText) findViewById(R.id.startDate);
        //finishDate=(EditText) findViewById(R.id.endDate);
        predecessors=(EditText) findViewById(R.id.predessor);
        resources=(EditText) findViewById(R.id.resourceneeded);
        taskType=(Spinner) findViewById(R.id.taskType);
        startDate=(TextView) findViewById(R.id.startDateTV);
        endDate=(TextView) findViewById(R.id.endDateTV);
        totEstCost=(EditText) findViewById(R.id.e_task_est_cost);
        form=(LinearLayout) findViewById(R.id.email_login_form1);
        header=(TextView) findViewById(R.id.section_label);
      /*  IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("mgmt.construction.constructionmgmt.Activity.AddTaskActivity");
        signOutBroadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceiveBroadcast", "Logout in progress");
                Toast.makeText(getApplicationContext(),"RECEIVED",Toast.LENGTH_LONG).show();
               // Intent taskIntent =
                //        new Intent(getApplicationContext(), LoginActivity.class);
              //  startActivity(taskIntent);
              //  finish();
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(signOutBroadcastReceiver, intentFilter);*/
       // StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        setupToolbar();
        setupNavigationView();
        buttonAssignTask=(Button)findViewById(R.id.section_label1);
        buttonAssignTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mTask = new GetProductDetails("abc@abc.com", "abc123");
                //Log.v("USERLOGIN",email);
                //mTask.execute((Void) null);

                taskStatus = (Spinner) findViewById(R.id.taskStatus);
                String tempTaskStatus = taskStatus.getSelectedItem().toString();
                if (tempTaskStatus.compareToIgnoreCase("not started yet") == 0) {
                    cTaskStatus = 'N';
                } else if (tempTaskStatus.compareToIgnoreCase("ongoing") == 0) {
                    cTaskStatus = 'O';
                } else if (tempTaskStatus.compareToIgnoreCase("work done") == 0) {
                    cTaskStatus = 'D';
                } else {
                    cTaskStatus = 'U';
                }
                //new GetAsync().execute(username, password);
                // Tag used to cancel the request
                // String tag_json_arry = "json_array_req";

                //String url = "http://ivapapps.16mb.com/get_test_password.php";

              /*  pDialog = new ProgressDialog(AddTaskActivity.this
                );
                pDialog.setMessage("Loading...");
                pDialog.show();

                JsonArrayRequest req = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.v("VOLLEY", response.toString());
                                Toast.makeText(getApplicationContext(), response.toString(),Toast.LENGTH_LONG).show();
                                pDialog.hide();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
                        Log.v("VOLLEY", error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                })  {

                  @Override
                    protected HashMap<String, String> getParams() {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("name", username);
                        params.put("password", password);

                        return params;
                    }

                };

// Adding request to request queue
                AppController.getInstance().addToRequestQueue(req, tag_json_arry);*/
                String tTaskName = taskName.getText().toString();
                String tDuration = totDays.getText().toString();
                //String sStartDate = startDate.getText().toString();
                //String sFinishDate = endDate.getText().toString();
                String sPredecessors=predecessors.getText().toString();
                String sResources=resources.getText().toString();
                sTotCost = totEstCost.getText().toString();
                boolean fieldCheck=false;
                String tag_json_obj = "json_obj_req";

                String url = "http://alamaari.com/construction/insert_task.php";
                Map<String, String> params;

                if (tTaskName != null && tTaskName.compareToIgnoreCase("") != 0
                        && tDuration != null && tDuration.compareToIgnoreCase("") != 0
                        && sStartDate != null && sStartDate.compareToIgnoreCase("") != 0
                        && sEndDate != null && sEndDate.compareToIgnoreCase("") != 0
                        && sTotCost != null && sTotCost.compareToIgnoreCase("") != 0) {
                    if(cTaskType=='T')
                    {
                        if(sPredecessors != null && sPredecessors.compareToIgnoreCase("") != 0
                                && sResources != null && sResources.compareToIgnoreCase("") != 0){
                            fieldCheck=true;
                        }
                        else{
                            if (sPredecessors == null || sPredecessors.compareToIgnoreCase("") == 0){
                                predecessors.setError("Task Predecessor is required");
                                predecessors.requestFocus();
                            }

                            else if( sResources == null || sResources.compareToIgnoreCase("") == 0){
                                resources.setError("Resources Detail is required");
                                resources.requestFocus();
                            }
                            else if( sTotCost == null || sTotCost.compareToIgnoreCase("") == 0){
                                totEstCost.setError("Estimated Cost Detail is required");
                                totEstCost.requestFocus();
                            }
                            else{
                                taskName.setError("Enter required fields");
                                taskName.requestFocus();
                            }
                        }
                    }
                    else{
                    fieldCheck=true;}
                }else
                {
                    if (tTaskName == null || tTaskName.compareToIgnoreCase("") == 0){
                        taskName.setError("Task Name is required");
                        taskName.requestFocus();
                    }

                    else if( sStartDate == null || sStartDate.compareToIgnoreCase("") == 0){
                        startDate.setError("Task Start Date is required");
                        startDate.requestFocus();
                    }
                    else if (sEndDate == null || sEndDate.compareToIgnoreCase("") == 0){
                        endDate.setError("Task finish Date is required");
                        endDate.requestFocus();
                    }

                    else if( tDuration == null || tDuration.compareToIgnoreCase("") == 0){
                        totDays.setError("Task Duration is required");
                        totDays.requestFocus();
                    }
                    else if( sTotCost == null || sTotCost.compareToIgnoreCase("") == 0){
                        totEstCost.setError("Estimated Cost Detail is required");
                        totEstCost.requestFocus();
                    }
                    else{
                        taskName.setError("Enter required fields");
                        taskName.requestFocus();
                    }

                }
                if(fieldCheck) {
                    pDialog = new ProgressDialog(AddTaskActivity.this);
                    pDialog.setMessage("Loading...");
                    pDialog.show();

                    params = new HashMap<String, String>();
                    params.put("taskname", tTaskName);
                    params.put("duration", tDuration);
                    params.put("startdate", sStartDate);
                    params.put("finishdate", sEndDate);
                    params.put("predecessors", sPredecessors);
                    params.put("resources", sResources);
                    params.put("status", String.valueOf(cTaskStatus));
                    params.put("type", String.valueOf(cTaskType));
                    params.put("estcost",sTotCost);
                    //params.put("name", "abc@abc.com");
                    //params.put("password", "abc123");

                    //JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    JSONObjectRequest jsonObjReq = new JSONObjectRequest(Request.Method.POST,
                            url, params,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
try{
                                    Log.v("VOLLEY", response.toString());
                                   // Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                                    if(response.getInt("success")==1) {
                                    form.setVisibility(View.GONE);
                                    header.setText("Inserted Successfully");
                                    pDialog.hide();}
                                    else
                                    {
                                        form.setVisibility(View.GONE);
                                        header.setText("Oops!");
                                        pDialog.hide();
                                    }}
catch (JSONException e){}
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
                            //Log.v("VOLLEY", error.getMessage());
                            if(error instanceof NoConnectionError) {
                                Toast.makeText(getApplicationContext(), "No internet Access, Check your internet connection.", Toast.LENGTH_LONG).show();}
                            pDialog.hide();
                            //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                            //pDialog.hide();
                        }
                    }); /*{

                    @Override
                    protected Map<String, String> getParams() {
                        params = new HashMap<String, String>();
                        params.put("name", "abc@abc.com");
                        params.put("password", "abc123");

                        return params;
                    }

                };*/

// Adding request to request queue
                    AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter Required Fields",Toast.LENGTH_LONG);
                }
            }
        });
                // Tag used to cancel the request

        textTopic=(TextView)findViewById(R.id.section_label);

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
                //Toast.makeText(getActivity(), "End Button Click", Toast.LENGTH_SHORT).show();
                DatePicker();

            }
        });
    }
    private void setupNavigationView(){

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.sign_out:
                PreferencesHelper.signOut(this);
               /* Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("mgmt.construction.constructionmgmt.Activity.AddTaskActivity");
                broadcastIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(broadcastIntent);*/
                Intent intent = new Intent(this, LoginActivity.class);
               // intent.putExtra("finish", true); // if you are checking for this in your other Activities
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                Log.d("onBroadcast", "Logout in progress");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void setupToolbar(){
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setTitle("Add Task");
        ab.setDisplayUseLogoEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
       // ab.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        //ab.setDisplayUseLogoEnabled(true);
       // ab.setDisplayShowTitleEnabled(true);
        // ab.setLogo(R.drawable.ic_launcher);
        //ab.setIcon(R.drawable.ic_launcher);
       // ab.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;

    }
  /*  class GetProductDetails extends AsyncTask<Void, Void, String> {

        private final String mEmail;
        private final String mPassword;

        GetProductDetails(String email, String password) {
            mEmail = email;
            mPassword = password;
        }
        /**
         * Before starting background thread Show Progress Dialog
         **
         @Override
         protected void onPreExecute() {
         super.onPreExecute();
         pDialog = new ProgressDialog(AddTaskActivity.this);
         pDialog.setMessage("Loading product details. Please wait...");
         pDialog.setIndeterminate(false);
         pDialog.setCancelable(true);
         pDialog.show();
         }

        /**
         * Getting product details in background thread
         **
        protected String doInBackground(Void... params) {

            // updating UI from Background Thread
           // runOnUiThread(new Runnable() {
             //   public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                        params1.add(new BasicNameValuePair("username", mEmail));
                        //Log.v("USERLOGIN",mEmail+" "+mPassword);
                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        Log.v("USERLOGIN","no"+mEmail);
                                JSONObject json = jsonParser.makeHttpRequest(url_product_detials, "GET", params1);

                        // check your log for json response
                        Log.v("USERLOGIN", "["+json+"]");

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received product details
                            JSONArray productObj = json
                                    .getJSONArray(TAG_USERLOGIN); // JSON Array

                            // get first product object from JSON Array
                            JSONObject product = productObj.getJSONObject(0);

                            // product with this pid found
                            //Toast.makeText(getActivity().getApplicationContext(), product.getString(TAG_USERLOGIN)+" "+product.getString(TAG_PASSWORD), Toast.LENGTH_SHORT).show();
                             Log.v("USERLOGIN", product.getString(TAG_USERLOGIN) + " " + product.getString(TAG_PASSWORD));
                            //textTopic.setText(product.getString(TAG_PASSWORD));
                        } else {
                            // product with pid not found
                            //textTopic.setText("NOT FOUND");
                            Log.v("USERLOGIN","NOT FOUND");
                        }
                    } catch (JSONException e) {
                        Log.v("USERLOGIN", e.getMessage());
                    }
               // }
           // });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
           // showProgress(false);
           // mTask=null;
        }
    }*/

    @Override
    protected void onDestroy()
    {super.onDestroy();
       // LocalBroadcastManager.getInstance(this).unregisterReceiver(signOutBroadcastReceiver);

    }

    @Override
    protected void onStart() {
        super.onStart();
        taskType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String tempTaskType=taskType.getSelectedItem().toString();
                TextView txt=(TextView) findViewById(R.id.sub_section_label3);
                if(tempTaskType.compareToIgnoreCase("new task")==0)
                {
                    cTaskType='T';
                    if(predecessors.getVisibility()==View.GONE)
                        predecessors.setVisibility(View.VISIBLE);
                    if(resources.getVisibility()==View.GONE)
                        resources.setVisibility(View.VISIBLE);
                    if(txt.getVisibility()==View.GONE)
                        txt.setVisibility(View.VISIBLE);
                }
                else if(tempTaskType.compareToIgnoreCase("new stage")==0)
                {
                    cTaskType='S';
                    predecessors.setVisibility(View.GONE);
                    resources.setVisibility(View.GONE);
                    txt.setVisibility(View.GONE);
                }
                else if(tempTaskType.compareToIgnoreCase("new project")==0)
                {
                    cTaskType='P';
                    predecessors.setVisibility(View.GONE);
                    resources.setVisibility(View.GONE);
                    txt.setVisibility(View.GONE);
                }
                else
                {
                    cTaskType='U';
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

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


                        //Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
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
        sStartDate=syear+"-"+(smonthofyear + 1)+"-"+sdayofmonth;
        //Toast.makeText(getActivity(), "STArt Date set", Toast.LENGTH_SHORT).show();
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
        sEndDate=eyear+"-"+(emonthofyear + 1)+"-"+edayofmonth;
        //Toast.makeText(getActivity(), "End Date set", Toast.LENGTH_SHORT).show();
        int days=totalNoOfDays();

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
    }

}
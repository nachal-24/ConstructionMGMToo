package mgmt.construction.constructionmgmt.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mgmt.construction.constructionmgmt.Adapters.OrdersListRecyclerViewAdapter;
import mgmt.construction.constructionmgmt.Classes.AppController;
import mgmt.construction.constructionmgmt.Classes.DummyContent;
import mgmt.construction.constructionmgmt.Classes.JSONObjectRequest;
import mgmt.construction.constructionmgmt.Classes.PreferencesHelper;
import mgmt.construction.constructionmgmt.Fragment.CalendarViewResultFragment;
import mgmt.construction.constructionmgmt.Fragment.NonInstEOQResultFragment;
import mgmt.construction.constructionmgmt.R;

public class CalendarViewActivity extends RootActivity  {
    DrawerLayout drawerLayout;

    CaldroidFragment caldroidFragment;
    Map<String,List<String>> mapDates;
    List<String> dateList;
    String sitemName;
    String stotQuantity;
    String scostPerOrder;
    String scarryingCost;
    //String stotDays;
    String soptOrderSize;
    //String stotMinCost;
    String snoOfOrders;
    String sordersCycle;
    String sstartDate;
    String sendDate;
    //String smaxInvLvl;
    //String sprodRate;
    String sSelectedDate;
    String sData;
    WebView webview;
    String htmlData;
    ProgressDialog pDialog;
    TextView textTopic;
    ListView listView;
    ArrayList<String> idsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     /*   caldroidFragment = new CaldroidFragment();
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        else{
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            //args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);
            caldroidFragment.setArguments(args);
        }*/
        setSupportActionBar(toolbar);
        setupToolbar();
        setupNavigationView();
        Bundle b = getIntent().getExtras();
        if(b!=null) {
            idsList = b.getStringArrayList("idlist");
            sSelectedDate = b.getString("dt");
        }
        String data="<html><body>";
        String tData="(";
        int i=1;
        for(String tempId:idsList) {
            //Log.v("TID",tempId);

            if(i==idsList.size())
                tData=tData+tempId+")";
            else
                tData=tData+tempId+",";
            //Log.v("TDATA",tData);
            // data=data+tData;
            // Log.v("FDATA",data);
            i++;
        }
        Log.v("IDSIZE",tData+" "+idsList.size());
        insertNonInstEOQ(tData);
        webview = (WebView)findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.window_bg));

        if(htmlData!=null)
            webview.loadDataWithBaseURL("", htmlData, "text/html", "UTF-8", "");

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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
                Intent intent = new Intent(this, LoginActivity.class);
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
        ab.setTitle("Calendar");
        ab.setDisplayUseLogoEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;

    }

    public void insertNonInstEOQ(String pid)
    {
        sData="";
        htmlData="<html><body>";
        String tag_json_obj = "json_obj_req";
        String url = "http://ivapapps.16mb.com/order_calendar_testing.php";
        Map<String, String> params;

        pDialog = new ProgressDialog(CalendarViewActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        Log.v("PID", pid);
        params = new HashMap<String, String>();
        params.put("id", pid);


        //JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
        JSONObjectRequest jsonObjReq = new JSONObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("orders");
                            Log.v("VOLLEY", response.toString());
                          /*  JSONArray ja = response.getJSONArray("orders");
                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jo = ja.getJSONObject(i);
                                //stTopic=jo.getString("taskname");
                                Log.v("VOLLEY", "INSIDELOOP");
                                sitemName = jo.getString("item");
                                stotQuantity = jo.getString("totalquantity");
                                scostPerOrder = jo.getString("costperorder");
                                scarryingCost = jo.getString("carryingcost");
                                stotDays = jo.getString("totaldays");
                                soptOrderSize = jo.getString("optimumordersize");
                                stotMinCost = jo.getString("totinvcost");
                                snoOfOrders = jo.getString("nooforders");
                                sordersCycle = jo.getString("orderscycle");
                                sstartDate = jo.getString("startdate");
                                sendDate = jo.getString("enddate");
                                smaxInvLvl = jo.getString("prodrate");
                                sprodRate = jo.getString("maxinvlevel");
                            }*/
                            for (int in = 0; in < ja.length(); in++) {

                                JSONObject jo = ja.getJSONObject(in);

                                sitemName = jo.getString("item");

                                scostPerOrder = jo.getString("costperorder");
                                scarryingCost = jo.getString("carryingcost");
                                //stotDays = response.getString("totaldays");
                                soptOrderSize = jo.getString("optimumordersize");
                                //stotMinCost = response.getString("totinvcost");
                                snoOfOrders = jo.getString("nooforders");
                                sordersCycle = jo.getString("orderscycle");
                                sstartDate = jo.getString("startdate");
                                sendDate = jo.getString("enddate");
                                stotQuantity = jo.getString("totalquantity");
                                //smaxInvLvl = response.getString("prodrate");
                                //sprodRate = response.getString("maxinvlevel");

                                String sType = jo.getString("type");
                                String sTypeShort = "";
                                if (sType.compareToIgnoreCase("E") == 0) {
                                    sTypeShort = "EOQ";

                                } else if (sType.compareToIgnoreCase("N") == 0) {
                                    sTypeShort = "Non Inst.EOQ";

                                }
                                sstartDate = sstartDate + " 01:01:01";
                                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                                DateTime sdt = formatter.parseDateTime(sstartDate);
                                double dNoOfOrders = Double.parseDouble(snoOfOrders);
                            /*if 2.1 means 2 orders optimum quantity and last one small so 3 orders*/
                                int noOfOrdersCeil = (int) Math.ceil(dNoOfOrders);

                                double dOrdersCycle = Double.parseDouble(sordersCycle);
                            /* if 100.5 days 100*/
                                int ordersCycleFloor = (int) Math.floor(dOrdersCycle);
                                double dTotalQuantity = Double.parseDouble(stotQuantity);
                                double tempQuantity = dTotalQuantity;
                                double dOptOrderSize = Double.parseDouble(soptOrderSize);
                                String orderList = "";
                                int orderNo;
                                DateTime sdt1 = sdt;
                                for (int i = 0; i < noOfOrdersCeil; i++) {
                                    Date date = sdt1.toDate();
                                    Log.v("TESTDATE", sdt1.getDayOfMonth() + "/" + sdt1.getMonthOfYear() + "/" + sdt1.getYear());
                                    //orderList=orderList+"Order "+(i+1)+" "+sdt1.getDayOfMonth()+"/"+sdt1.getMonthOfYear()+"/"+sdt1.getYear();
                                    if (tempQuantity > dOptOrderSize) {
                                        orderList = orderList + " " + dOptOrderSize + "\n";
                                        tempQuantity = tempQuantity - dOptOrderSize;
                                    } else {
                                        orderList = orderList + " " + String.format("%.2f", tempQuantity) + "\n";
                                    }
                                    String sDate = sdt1.getYear() + "-" + sdt1.getMonthOfYear() + "-" + sdt1.getDayOfMonth();
                                    if (sDate.compareToIgnoreCase(sSelectedDate) == 0) {
                                        Log.v("DATACOM", sDate + " " + sSelectedDate);
                                        if (sType.compareToIgnoreCase("E") == 0) {
                                            //tTopic.setText("Economic Order Quantity");
                                            sData = sData + "<h3 align=\"center\">Economic Order Quantity</h3>";
                                        } else if (sType.compareToIgnoreCase("N") == 0) {
                                            //tTopic.setText("Non Instantaneous Economic Order Quantity");
                                            sData = sData + "<h3 align=\"center\">Non Instantaneous Economic Order Quantity</h3>";
                                        }
                                        //itemName.setText(sitemName);
                                        sData = sData + "<h3 align=\"left\">" + sitemName + "</h3><br/>";
                                        orderNo = i + 1;
                                        sData = sData + "<table align=\"center\"><tr><td><span style=\"font-weight:bold\">Order No " + orderNo + "</span></td><td><b>" + sSelectedDate +"</b></td></b></tr>";
                                        sData = sData + "<tr><td>Quantity</td><td>" + String.format("%.2f", tempQuantity) + "</td></tr>";
                                        sData = sData + "<tr><td>Cost per Order</td><td>" + scostPerOrder + "</td></tr>";
                                        sData = sData + "<tr><td>Carrying Cost</td><td>" + scarryingCost + "</td></tr></table>";
                                        Log.v("DATACOM", sData);
                                        break;
                                    }
                                    sdt1 = sdt1.plusDays(ordersCycleFloor);

                                }
                                //tOrdersList.setText(orderList);
                                //Toast.makeText(getActivity(), sdt1.toString(), Toast.LENGTH_LONG).show();
                            }
                            Log.v("DATACOM", sData);
                            htmlData=htmlData+sData+"</body></html>";
                            Log.v("HTMLCOM", htmlData);
                            webview.loadDataWithBaseURL("", htmlData, "text/html", "UTF-8", "");
                            pDialog.hide();
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            pDialog.hide();}
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.getMessage()!=null)
                    VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
                //Log.v("VOLLEY", error.getMessage());
                if(error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "No internet Access, Check your internet connection.", Toast.LENGTH_LONG).show();}
                pDialog.hide();
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
       /* Log.v("DATACOM", sData);
        htmlData=htmlData+sData+"</body></html>";
        Log.v("HTMLCOM", htmlData);
        webview.loadDataWithBaseURL("", htmlData, "text/html", "UTF-8", "");*/


    }
  /*  public void calendarView()
    {


        Calendar cali = Calendar.getInstance();
        getEOQ(cali.get(Calendar.MONTH) + 1, cali.get(Calendar.YEAR));
        Log.v("DATENOW",cali.getTime().toString());

        caldroidFragment.setBackgroundResourceForDate(R.color.colorAccent, cali.getTime());
        caldroidFragment.setTextColorForDate(R.color.white, cali.getTime());
        caldroidFragment.refreshView();
        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.fragmentParentViewGroup, caldroidFragment);
        t.commit();

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
              /*  Toast.makeText(getApplicationContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();

                caldroidFragment.setBackgroundResourceForDate(R.color.colorAccent, date);
                caldroidFragment.setTextColorForDate(R.color.white, date);
                caldroidFragment.refreshView();*/

             /*   Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
//Add one to month {0 - 11}
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String dt=year+"-"+month+"-"+day;
               // Log.v("ID", a1);
                List<String> tempList=mapDates.get(dt);
                ArrayList<String> idList=new ArrayList<String>();
                idList.addAll(tempList);
                String id="";
                for(String a1:tempList) {
                    Log.v("ID", a1);
                    id=id+"-"+a1;

                }
                id=id.substring(1);
                Toast.makeText(getApplicationContext(), id,
                        Toast.LENGTH_SHORT).show();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentParentViewGroup, CalendarViewResultFragment.newInstance(idList,dt))
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
               // Toast.makeText(getApplicationContext(), text,
                   //     Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
               // Toast.makeText(getApplicationContext(),
                     //   "Long click " + formatter.format(date),
                      //  Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                   // Toast.makeText(getApplicationContext(),
                       //     "Caldroid view is created", Toast.LENGTH_SHORT)
                         //   .show();
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

    }*/
    /**
     * Save current states of the Caldroid here
     */
  /*  @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }



    }*/
   /* public void getEOQ(int month,int year)
    {
        String tag_json_obj = "json_obj_req";
        Log.v("VOLLEY", month + " " + year);
        String url = "http://ivapapps.16mb.com/order_calendar.php";
        Map<String, String> params =new HashMap<String,String>();
        params.put("month",String.valueOf(month));
        params.put("year",String.valueOf(year));
        dateList=new ArrayList<String>();
        mapDates=new HashMap<String,List<String>>();
        pDialog = new ProgressDialog(CalendarViewActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        //JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
        JSONObjectRequest jsonObjReq = new JSONObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.v("VOLLEY", response.toString());
                            JSONArray ordersObj = response
                                    .getJSONArray("orders");
                            Log.v("VOLLEY","orders len"+ordersObj.length());
                            for (int i = 0; i < ordersObj.length(); i++) {
                                Log.v("VOLLEY","INSIDE LOOP");
                                JSONObject jo = ordersObj.getJSONObject(i);
                                Log.v("VOLLEY","INSIDE LOOP2");
                                Log.v("VOLLEY", jo.getString("id")+ " " +
                                        jo.getString("nooforders")+" "+ jo.getString("startdate"));

                                Log.v("VOLLEY","ORDERSCYCLE"+jo.getString("orderscycle"));
                                String sid=jo.getString("id");
                                String sstartDate=jo.getString("startdate");
                                String snoOfOrders=jo.getString("nooforders");
                                String sOrdersCycle=jo.getString("orderscycle");

                                sstartDate=sstartDate+" 01:01:01";

                                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

                                DateTime sdt = formatter.parseDateTime(sstartDate);
                                double dNoOfOrders=Double.parseDouble(snoOfOrders);
                                double dOrdersCycle=Double.parseDouble(sOrdersCycle);
                            *if 2.1 means 2 orders optimum quantity and last one small so 3 orders*
                                int noOfOrdersCeil=(int) Math.ceil(dNoOfOrders);
                            * if 100.5 days 100*
                                int ordersCycleFloor=(int) Math.floor(dOrdersCycle);
                                Log.v("VOLLEY","After Conv");
                                        String orderList="";
                                DateTime sdt1 = sdt;
                                for(int j=0;j<noOfOrdersCeil;j++) {
                                   // Date date=sdt1.toDate();
                                    Log.v("TESTDATE", "ID " + sid + " " + sdt1.getDayOfMonth() + "/" + sdt1.getMonthOfYear() + "/" + sdt1.getYear());
                                    String sdate=sdt1.getYear()+"-"+sdt1.getMonthOfYear()+"-"+sdt1.getDayOfMonth();

                                    dateList.add(sdate);
                                    List<String> temp=mapDates.get(sdate);
                                    if(temp!=null)
                                        temp.add(sid);
                                    else{
                                        temp=new ArrayList<String>();
                                        temp.add(sid);
                                    }

                                    mapDates.put(sdate, temp);

                                    orderList=orderList+"Order "+(j+1)+" "+sdt1.getDayOfMonth()+"/"+sdt1.getMonthOfYear()+"/"+sdt1.getYear();
                                    sdt1=sdt1.plusDays(ordersCycleFloor);
                                }

                            }

                            //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                            for(String a:dateList) {
                                Log.v("DATELIST", a);
                                  String[] parts = a.split("-");
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])-1, Integer.parseInt(parts[2])); //Year, month and day of month
            Date date = cal.getTime();
            Log.v("DATENEW",a+" "+cal.getTime().toString());
            caldroidFragment.setBackgroundResourceForDate(R.color.mdtp_accent_color, date);
            caldroidFragment.setTextColorForDate(R.color.white, date);
                            }
                            caldroidFragment.refreshView();
                          /*  String dt="2016-2-2";
                            List<String> tempList=mapDates.get(dt);
                            for(String a1:tempList)
                                Log.v("ID", a1);*
                            pDialog.hide();
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            pDialog.hide();
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.getMessage()!=null)
                    VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
                //Log.v("VOLLEY", error.getMessage());
                //Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                if(error instanceof NoConnectionError) {
                    Toast.makeText(getBaseContext(), "No internet Access, Check your internet connection.", Toast.LENGTH_LONG).show();}
                pDialog.hide();
            }
        }); /*{

                    @Override
                    protected Map<String, String> getParams() {
                        params = new HashMap<String, String>();
                        params.put("name", "abc@abc.com");
                        params.put("password", "abc123");

                        return params;
                    }

                };*

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        for(String a:dateList) {
            Log.v("DATENEW",a);
          /*  String[] parts = a.split("-");
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])); //Year, month and day of month
            Date date = cal.getTime();
            Log.v("DATENEW",a+" "+cal.getTime().toString());
            caldroidFragment.setBackgroundResourceForDate(R.color.mdtp_accent_color, date);
            caldroidFragment.setTextColorForDate(R.color.white, date);*

        }
        //caldroidFragment.refreshView();

    }*/
    @Override
    public void onResume()
    {
        super.onResume();
        Log.v("RESUMECHECK", "ONRESUME");
        if(htmlData!=null)
            webview.loadDataWithBaseURL("", htmlData, "text/html", "UTF-8", "");

    }
}

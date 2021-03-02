package mgmt.construction.constructionmgmt.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.github.mikephil.charting.charts.LineChart;
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

import mgmt.construction.constructionmgmt.Activity.AddTaskActivity;
import mgmt.construction.constructionmgmt.Activity.CalendarViewActivity;
import mgmt.construction.constructionmgmt.Classes.AppController;
import mgmt.construction.constructionmgmt.Classes.JSONObjectRequest;
import mgmt.construction.constructionmgmt.R;

/**
 * Created by Recluse on 10/28/2015.
 */


public class CalendarViewFragment extends BaseChartFragment {

    public static Fragment newInstance() {
        return new CalendarViewFragment();
    }

    private LineChart mChart;
    private Button taskButton;
    DrawerLayout drawerLayout;
    private ProgressDialog pDialog;
    CaldroidFragment caldroidFragment;
    Map<String,List<String>> mapDates;
    List<String> dateList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      /*  View v = inflater.inflate(R.layout.frag_simple_line, container, false);
        taskButton=(Button) v.findViewById(R.id.email_sign_in_button);
        taskButtonClickListener();
        mChart = (LineChart) v.findViewById(R.id.lineChart1);

        mChart.setDescription("");

        mChart.setDrawGridBackground(false);

        mChart.setData(getComplexity());
        mChart.animateX(3000);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"OpenSans-Light.ttf");

        Legend l = mChart.getLegend();
        l.setTypeface(tf);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);

        mChart.getAxisRight().setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);*/
        View v = inflater.inflate(R.layout.frag_simple_line, container, false);
        taskButton=(Button) v.findViewById(R.id.email_sign_in_button);
        taskButtonClickListener();
        caldroidFragment = new CaldroidFragment();
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
        }
        calendarView();
        return v;
    }
    private void taskButtonClickListener()
    {
        taskButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent taskIntent =
                        new Intent(getActivity(), AddTaskActivity.class);
                startActivity(taskIntent);

            }

        });
    }

    public void calendarView()
    {


        Calendar cali = Calendar.getInstance();
        getEOQ(cali.get(Calendar.MONTH) + 1, cali.get(Calendar.YEAR));
        Log.v("DATENOW", cali.getTime().toString());

        caldroidFragment.setBackgroundResourceForDate(R.color.colorAccent, cali.getTime());
        caldroidFragment.setTextColorForDate(R.color.white, cali.getTime());
        caldroidFragment.refreshView();
        android.support.v4.app.FragmentTransaction t = getFragmentManager().beginTransaction();
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

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
//Add one to month {0 - 11}
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String dt=year+"-"+month+"-"+day;
                // Log.v("ID", a1);

                List<String> tempList=mapDates.get(dt);
                ArrayList<String> idList=new ArrayList<String>();
                if(tempList!=null)
                {
                idList.addAll(tempList);
                String id="";
                for(String a1:tempList) {
                    Log.v("ID", a1);
                    id=id+"-"+a1;

                }
                id=id.substring(1);
                //Toast.makeText(getActivity(), id,
                       // Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), CalendarViewActivity.class);
                Bundle b = new Bundle();
                b.putStringArrayList("idlist", idList); //Your id
                b.putString("dt", dt);
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);}
               /* getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentParentViewGroup, CalendarViewResultFragment.newInstance(idList,dt))
                        .addToBackStack(null)
                        .commit();*/
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

    }
    /**
     * Save current states of the Caldroid here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }



    }
    public void getEOQ(int month,int year)
    {
        String tag_json_obj = "json_obj_req";
        Log.v("VOLLEY", month + " " + year);
        String url = "http://ivapapps.16mb.com/order_calendar.php";
        Map<String, String> params =new HashMap<String,String>();
        params.put("month",String.valueOf(month));
        params.put("year",String.valueOf(year));
        dateList=new ArrayList<String>();
        mapDates=new HashMap<String,List<String>>();
        pDialog = new ProgressDialog(getActivity());
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
                            /*if 2.1 means 2 orders optimum quantity and last one small so 3 orders*/
                                int noOfOrdersCeil=(int) Math.ceil(dNoOfOrders);
                            /* if 100.5 days 100*/
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
                                Log.v("ID", a1);*/
                            pDialog.hide();
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity(), "No internet Access, Check your internet connection.", Toast.LENGTH_LONG).show();}
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

        for(String a:dateList) {
            Log.v("DATENEW",a);
          /*  String[] parts = a.split("-");
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])); //Year, month and day of month
            Date date = cal.getTime();
            Log.v("DATENEW",a+" "+cal.getTime().toString());
            caldroidFragment.setBackgroundResourceForDate(R.color.mdtp_accent_color, date);
            caldroidFragment.setTextColorForDate(R.color.white, date);*/

        }
        //caldroidFragment.refreshView();

    }
}

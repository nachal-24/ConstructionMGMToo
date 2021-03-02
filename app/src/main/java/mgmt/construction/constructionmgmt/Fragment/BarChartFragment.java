package mgmt.construction.constructionmgmt.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mgmt.construction.constructionmgmt.Activity.AddTaskActivity;
import mgmt.construction.constructionmgmt.Activity.TaskListActivity;
import mgmt.construction.constructionmgmt.Classes.AppController;
import mgmt.construction.constructionmgmt.Classes.JSONObjectRequest;
import mgmt.construction.constructionmgmt.Classes.MyMarkerView;
import mgmt.construction.constructionmgmt.R;

/**
 * Created by Recluse on 10/28/2015.
 */

public class BarChartFragment extends BaseChartFragment implements OnChartGestureListener,OnChartValueSelectedListener {

    public static Fragment newInstance() {
        return new BarChartFragment();
    }

    private BarChart mChart;
    private Button taskButton;
    ProgressDialog pDialog;
    String sid;
    String sTaskName;
    String sStatus;
    String sstartDate;
    String sendDate;
    String sTotDays;;
    ArrayList<String> taskList;
    ArrayList<BarEntry> entries;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_simple_bar, container, false);
        taskButton=(Button) v.findViewById(R.id.add_new_task);
        taskButtonClickListener();
        // create a new chart object
        mChart = new BarChart(getActivity());
        mChart.setDescription("");
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.animateY(3000);

        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);

        mChart.setMarkerView(mv);

        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"OpenSans-Light.ttf");

        //mChart.setData(generateBarData(1, 100, 4));

        Legend l = mChart.getLegend();
        //l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        l.setTypeface(tf);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);

        mChart.getAxisRight().setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);
        insertNonInstEOQ();
        // programatically add the chart
        FrameLayout parent = (FrameLayout) v.findViewById(R.id.parentLayout);
        parent.addView(mChart);

        return v;
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START");
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END");
        mChart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }
private void taskButtonClickListener()
{
    taskButton.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {

            Intent taskIntent =
                    new Intent(getActivity(), TaskListActivity.class);
            startActivity(taskIntent);

        }

    });
}
    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        // start new activity
        FragmentManager fm = getFragmentManager();
        TaskProgressDialogFragment dialogFragment =TaskProgressDialogFragment.newInstance(taskList.get(e.getXIndex()),e.getVal()+"% of Work Completed");
        dialogFragment.show(fm, "Sample Fragment");
       // Toast.makeText(getActivity(),e.getVal()+" "+taskList.get(e.getXIndex()),Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(){}

    public void insertNonInstEOQ()
    {
        String tag_json_obj = "json_obj_req";
        String url = "http://ivapapps.16mb.com/task_data_barchart.php";

        entries = new ArrayList<BarEntry>();
        taskList=new ArrayList<String>();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();



        //JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
        JSONObjectRequest jsonObjReq = new JSONObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("task");
                            Log.v("VOLLEY", response.toString());

                            for (int in = 0; in < ja.length(); in++) {

                                JSONObject jo = ja.getJSONObject(in);

                                sid = jo.getString("id");
                                sTaskName = jo.getString("taskname");
                                sStatus = jo.getString("status");
                                sstartDate = jo.getString("startdate");
                                sendDate = jo.getString("finishdate");
                                sTotDays= jo.getString("duration");

                                taskList.add(sTaskName);

                                int iDuration=Integer.parseInt(sTotDays);
                                sstartDate = sstartDate + " 01:01:01";
                                sendDate = sendDate + " 01:01:01";

                                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                                DateTime sdt = formatter.parseDateTime(sstartDate);

                                Calendar cal=Calendar.getInstance();
                                Date a = cal.getTime();
                                DateTime edt=new DateTime(a);
                                int currdays=Days.daysBetween(sdt.withTimeAtStartOfDay(), edt.withTimeAtStartOfDay()).getDays();
                                float YVal;
                                Log.v("VALUECHECK",iDuration+" "+currdays);
                                if(iDuration<=currdays)
                                {
                                    YVal=100;
                                    Log.v("VALUECHECK","INIF");
                                }
                                else
                                {
                                    YVal=(currdays*100)/iDuration;
                                    Log.v("VALUECHECK","INELSE"+YVal);
                                }
                                YVal=Float.valueOf(jo.getString("statuspercent"));
                                Log.v("STATUSPERCENT",YVal+"");
                                entries.add(new BarEntry(YVal, in));

                            }
                            if(entries.size()>0 && taskList.size()>0)
                            mChart.setData(generateBarData(1, 100, 4,entries,taskList));
                            else
                                Toast.makeText(getActivity(),"No Data",Toast.LENGTH_LONG).show();
                            pDialog.hide();
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            pDialog.hide();}
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.getMessage()!=null)
                    VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
                //Log.v("VOLLEY", error.getMessage());
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
       /* Log.v("DATACOM", sData);
        htmlData=htmlData+sData+"</body></html>";
        Log.v("HTMLCOM", htmlData);
        webview.loadDataWithBaseURL("", htmlData, "text/html", "UTF-8", "");*/


    }
}
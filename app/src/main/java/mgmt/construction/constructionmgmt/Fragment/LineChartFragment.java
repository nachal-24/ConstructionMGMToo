package mgmt.construction.constructionmgmt.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.github.mikephil.charting.charts.LineChart;
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
import java.util.List;

import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import mgmt.construction.constructionmgmt.Classes.AppController;
import mgmt.construction.constructionmgmt.Classes.JSONObjectRequest;
import mgmt.construction.constructionmgmt.R;


public class LineChartFragment extends BaseChartFragment implements OnChartGestureListener,OnChartValueSelectedListener {

    public static Fragment newInstance() {
        return new LineChartFragment();
    }

    private LineChart mChart;
    ProgressDialog pDialog;
    int iCost;
    int iSCost;
    ArrayList<Entry> entries;
    ArrayList<String> taskList;

    ArrayList<Entry> actualentries;
    ArrayList<String> actualtaskList;

    ArrayList<Entry> eventries;
    ArrayList<String> evtaskList;

    HashMap<Integer,Float> entriesHash=new HashMap<Integer,Float>();;
    HashMap<Integer,Float> actualentriesHash=new HashMap<Integer,Float>();;
    HashMap<Integer,Float> eventriesHash=new HashMap<Integer,Float>();;

    String sEndDate;
String sStartDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_line_chart, container, false);

        mChart = (LineChart) v.findViewById(R.id.lineChart1);

        mChart.setDescription("");

        mChart.setDrawGridBackground(false);
        //mChart.setClickable(true);
        mChart.setOnChartValueSelectedListener(this);
       /* mChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"TOAST ",Toast.LENGTH_LONG).show();
            }
        });*/
        insertNonInstEOQ();
        //mChart.setData(getComplexity());
        mChart.animateX(3000);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"OpenSans-Light.ttf");

        Legend l = mChart.getLegend();
        l.setTypeface(tf);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);

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

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        // start new activity
        //FragmentManager fm = getFragmentManager();
        //TaskProgressDialogFragment dialogFragment =TaskProgressDialogFragment.newInstance(taskList.get(e.getXIndex()),e.getVal());
        //dialogFragment.show(fm, "Sample Fragment");
        Log.i("VALUESELECTCHART", "Chart selected.");
        //mChart.highlightValue(10,1);
        int ind=0;
        if(e!=null)
        ind=e.getXIndex();
        else ind=0;

        float earnedValue=0;
        float actualCost=0;
        float plannedValue=0;
        if(eventriesHash.get(ind)!=null)
        earnedValue=eventriesHash.get(ind);
        if(actualentriesHash.get(ind)!=null)
        actualCost=actualentriesHash.get(ind);
        if(entriesHash.get(ind)!=null)
        plannedValue=entriesHash.get(ind);
        float costVariance=earnedValue-actualCost;
        float scheduleVariance=earnedValue-plannedValue;
        float costPerformaceIndex=earnedValue/actualCost;
        float schedulePerformanceIndex=earnedValue/plannedValue;
       // Toast.makeText(getActivity(),"Ind"+ind+"\nEV "+earnedValue+"\nAC "+actualCost+"\nPV"+plannedValue+"\nCost Variance "+costVariance+"\nSchedule Variance "+scheduleVariance+
             //   "\nCost Performance Index "+costPerformaceIndex+"\nSchedule Performance Index "+schedulePerformanceIndex,
             //   Toast.LENGTH_LONG).show();
        FragmentManager fm = getFragmentManager();
        String valueString="<b>Cost Variance</b> " + costVariance + "<br/><b>Schedule Variance</b> " + scheduleVariance +
                "<br/><b>Cost Performance Index</b> " + costPerformaceIndex + "<br/><b>Schedule Performance Index</b> " + schedulePerformanceIndex;
        TaskProgressDialogFragment dialogFragment =TaskProgressDialogFragment.newInstance("EVM", ""+Html.fromHtml(valueString));
        dialogFragment.show(fm, "Sample Fragment");
    }
    @Override
    public void onNothingSelected(){}

    public void insertNonInstEOQ()
    {
        String tag_json_obj = "json_obj_req";
        String url = "http://ivapapps.16mb.com/task_data_linechart.php";

        entries = new ArrayList<Entry>();
        taskList=new ArrayList<String>();
        //entriesHash=new HashMap<Integer,Float>();

        actualentries = new ArrayList<Entry>();
        actualtaskList=new ArrayList<String>();
       // actualentriesHash=new HashMap<Integer,Float>();

        eventries = new ArrayList<Entry>();
        evtaskList=new ArrayList<String>();
        //eventriesHash=new HashMap<Integer,Float>();

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
                            JSONObject jotemp = ja.getJSONObject(0);

                            sStartDate=jotemp.getString("enddate");
                            String startdate=sStartDate;

                            iSCost = jotemp.getInt("cost");
                            for (int in = 1; in < ja.length(); in++) {

                                JSONObject jo = ja.getJSONObject(in);

                                sEndDate = jo.getString("enddate");
                                iCost = jo.getInt("cost")+iSCost;


                                fillGapDates(entries.size(),sStartDate,iSCost,sEndDate,iCost);

                                sStartDate=sEndDate;
                                iSCost=iCost;

                            }
                            entries.add(new Entry(iCost, entries.size()));
                            entriesHash.put(entries.size(),(float)iCost);

                            ja = response.getJSONArray("actual");
                            Log.v("VOLLEY", response.toString());
                            jotemp = ja.getJSONObject(0);

                            sStartDate=jotemp.getString("actualenddate");
                            iSCost = jotemp.getInt("actualcost");
                            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

                            DateTime sdt = formatter.parseDateTime(startdate+ " 01:01:01");
                            DateTime edt = formatter.parseDateTime(sStartDate+ " 01:01:01");
                            if(DateDiff(sdt,edt)!=0)
                            actualFillGapDates(actualentries.size(),startdate,0,sStartDate,iSCost);

                            for (int in = 1; in < ja.length(); in++) {

                                JSONObject jo = ja.getJSONObject(in);

                                sEndDate = jo.getString("actualenddate");
                                iCost = jo.getInt("actualcost")+iSCost;

                                actualFillGapDates(actualentries.size(), sStartDate, iSCost, sEndDate, iCost);

                                sStartDate=sEndDate;
                                iSCost=iCost;

                            }
                            actualentries.add(new Entry(iCost, actualentries.size()));
                            actualentriesHash.put(actualentries.size(), (float) iCost);

                            ja = response.getJSONArray("earnedvalue");
                            Log.v("VOLLEY", response.toString());
                            jotemp = ja.getJSONObject(0);

                            sStartDate=jotemp.getString("eaenddate");
                            iSCost = jotemp.getInt("eacost");
                            //DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

                            DateTime sdt1 = formatter.parseDateTime(startdate+ " 01:01:01");
                            DateTime edt1 = formatter.parseDateTime(sStartDate+ " 01:01:01");
                            if(DateDiff(sdt1,edt1)!=0)
                                evFillGapDates(eventries.size(),startdate,0,sStartDate,iSCost);

                            for (int in = 1; in < ja.length(); in++) {

                                JSONObject jo = ja.getJSONObject(in);

                                sEndDate = jo.getString("eaenddate");
                                iCost = jo.getInt("eacost")+iSCost;

                                evFillGapDates(eventries.size(),sStartDate,iSCost,sEndDate,iCost);

                                sStartDate=sEndDate;
                                iSCost=iCost;

                            }
                            eventries.add(new Entry(iCost, eventries.size()));
                            eventriesHash.put(eventries.size(), (float) iCost);

                            if(entries.size()>0 && taskList.size()>0 && actualentries.size()>0 && eventries.size()>0) {
                                mChart.setData(getComplexity(entries, taskList, actualentries,eventries));

                            }
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

    public void fillGapDates(int arraysize,String startDate,int startCost,String endDate,int endCost)
    {
        int i=0;
        taskList.add(startDate);
        startDate = startDate + " 01:01:01";
        endDate = endDate + " 01:01:01";
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        DateTime sdt = formatter.parseDateTime(startDate);
        DateTime edt = formatter.parseDateTime(endDate);
Log.v("INWHILELOOPDATE",startDate+" "+endDate);
        /*
        * m=(y2-y1)/(x2-x1)
        * y-y1=(m)(x-x1)
        */
        int xDiff= DateDiff(sdt,edt);
        float yDiff=endCost-startCost;
        Log.v("INWHILELOOPCOST",startCost+" "+endCost);
        float slope=0;
        try {
            if(xDiff!=0)
            slope = yDiff / xDiff;
            else
                slope=yDiff;
        }
        catch (Exception e){}
        Log.v("INWHILELOOPPARAM", xDiff + " " + yDiff + " " + slope + "");
        float yVal=0;
        int xVal=0;
        DateTime tempDate=sdt;

            entries.add(new Entry(startCost, arraysize));
            entriesHash.put(arraysize,(float)startCost);
            taskList.add(startDate);
            sdt= sdt.plusDays(1);
            arraysize++;




        while(!sdt.withTimeAtStartOfDay().isEqual(edt.withTimeAtStartOfDay()))
        {

            //tempDate=sdt;
            yVal=(slope*(DateDiff(tempDate,sdt)))+ startCost;
            entries.add(new Entry(yVal, arraysize));
            entriesHash.put(arraysize, yVal);
            arraysize++;

            taskList.add("");
            Log.v("INWHILELOOP", tempDate.toString() + " " + sdt.toString() + " " + yVal);
            sdt= sdt.plusDays(1);
            if(sdt==edt)
            {
                taskList.add(endDate);
            }
        }
        //entries.add(new Entry(endCost, arraysize));


    }

    public void actualFillGapDates(int arraysize,String startDate,int startCost,String endDate,int endCost)
    {
        int i=0;
        actualtaskList.add(startDate);
        startDate = startDate + " 01:01:01";
        endDate = endDate + " 01:01:01";
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        DateTime sdt = formatter.parseDateTime(startDate);
        DateTime edt = formatter.parseDateTime(endDate);
        Log.v("ACTUALINWHILELOOPDATE",startDate+" "+endDate);
        /*
        * m=(y2-y1)/(x2-x1)
        * y-y1=(m)(x-x1)
        */
        int xDiff= DateDiff(sdt,edt);
        float yDiff=endCost-startCost;
        Log.v("ACTUALINWHILELOOPCOST",startCost+" "+endCost);
        float slope=yDiff/xDiff;
        Log.v("ACTUALINWHILELOOPPARAM",xDiff+" "+yDiff+" "+slope+"");
        float yVal=0;
        int xVal=0;
        DateTime tempDate=sdt;
        //if(i==0) {
        actualentries.add(new Entry(startCost, arraysize));
        actualentriesHash.put(arraysize, (float) startCost);
        actualtaskList.add(startDate);
//i++;
        sdt= sdt.plusDays(1);
        arraysize++;//}
        while(!sdt.withTimeAtStartOfDay().isEqual(edt.withTimeAtStartOfDay()))
        {

            //tempDate=sdt;
            yVal=(slope*(DateDiff(tempDate,sdt)))+ startCost;
            actualentries.add(new Entry(yVal, arraysize));
            actualentriesHash.put(arraysize, yVal);
            arraysize++;
            //i++;
            actualtaskList.add("");
            Log.v("ACTUALINWHILELOOP", tempDate.toString() + " " + sdt.toString() + " " + yVal);
            sdt= sdt.plusDays(1);
            if(sdt==edt)
            {
                actualtaskList.add(endDate);
            }
        }
        //actualentries.add(new Entry(endCost, arraysize));


    }

    public void evFillGapDates(int arraysize,String startDate,int startCost,String endDate,int endCost)
    {
       int i=0;
        evtaskList.add(startDate);
        startDate = startDate + " 01:01:01";
        endDate = endDate + " 01:01:01";
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        DateTime sdt = formatter.parseDateTime(startDate);
        DateTime edt = formatter.parseDateTime(endDate);
        Log.v("EVINWHILELOOPDATE",startDate+" "+endDate);
        /*
        * m=(y2-y1)/(x2-x1)
        * y-y1=(m)(x-x1)
        */
        int xDiff= DateDiff(sdt,edt);
        float yDiff=endCost-startCost;
        Log.v("EVINWHILELOOPCOST",startCost+" "+endCost);
        float slope=yDiff/xDiff;
        Log.v("EVINWHILELOOPPARAM",xDiff+" "+yDiff+" "+slope+"");
        float yVal=0;
        int xVal=0;
        DateTime tempDate=sdt;
        //if(i==0) {
        eventries.add(new Entry(startCost, arraysize));
        eventriesHash.put(arraysize, (float)startCost);
        evtaskList.add(startDate);
//i++;
        sdt= sdt.plusDays(1);
        arraysize++;//}
        int chk=0;
        while(!sdt.withTimeAtStartOfDay().isEqual(edt.withTimeAtStartOfDay()))
        {

            //tempDate=sdt;
            yVal=(slope*(DateDiff(tempDate,sdt)))+ startCost;
            eventries.add(new Entry(yVal, arraysize));
            eventriesHash.put(arraysize,yVal);
            arraysize++;
            //i++;
            evtaskList.add("");
            Log.v("EVINWHILELOOP", tempDate.toString() + " " + sdt.toString() + " " + yVal);
            sdt= sdt.plusDays(1);
            if(sdt==edt)
            {
                evtaskList.add(endDate);
            }
            chk++;
            if(chk==10)
                break;
        }
        //eventries.add(new Entry(endCost, arraysize));


    }
    public int DateDiff(DateTime pstartDate,DateTime pendDate)
    {

        int days=Days.daysBetween(pstartDate.withTimeAtStartOfDay(), pendDate.withTimeAtStartOfDay()).getDays();
        if(days<0)
            return (-1)*days;
        else
            return days;


    }

}
/*
public  class LineChartFragment extends Fragment {

    private LineChartView chart;
    private LineChartData data;
    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 12;

    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;

    public static Fragment newInstance() {
        return new LineChartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_line_chart, container, false);

        chart = (LineChartView) rootView.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());

        // Generate some randome values.
        generateValues();

        generateData();

        // Disable viewpirt recalculations, see toggleCubic() method for more info.
        chart.setViewportCalculationEnabled(false);

        resetViewport();

        return rootView;
    }
/*
    // MENU
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.line_chart, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_reset) {
            reset();
            generateData();
            return true;
        }
        if (id == R.id.action_add_line) {
            addLineToData();
            return true;
        }
        if (id == R.id.action_toggle_lines) {
            toggleLines();
            return true;
        }
        if (id == R.id.action_toggle_points) {
            togglePoints();
            return true;
        }
        if (id == R.id.action_toggle_cubic) {
            toggleCubic();
            return true;
        }
        if (id == R.id.action_toggle_area) {
            toggleFilled();
            return true;
        }
        if (id == R.id.action_point_color) {
            togglePointColor();
            return true;
        }
        if (id == R.id.action_shape_circles) {
            setCircles();
            return true;
        }
        if (id == R.id.action_shape_square) {
            setSquares();
            return true;
        }
        if (id == R.id.action_shape_diamond) {
            setDiamonds();
            return true;
        }
        if (id == R.id.action_toggle_labels) {
            toggleLabels();
            return true;
        }
        if (id == R.id.action_toggle_axes) {
            toggleAxes();
            return true;
        }
        if (id == R.id.action_toggle_axes_names) {
            toggleAxesNames();
            return true;
        }
        if (id == R.id.action_animate) {
            prepareDataAnimation();
            chart.startDataAnimation();
            return true;
        }
        if (id == R.id.action_toggle_selection_mode) {
            toggleLabelForSelected();

            Toast.makeText(getActivity(),
                    "Selection mode set to " + chart.isValueSelectionEnabled() + " select any point.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_toggle_touch_zoom) {
            chart.setZoomEnabled(!chart.isZoomEnabled());
            Toast.makeText(getActivity(), "IsZoomEnabled " + chart.isZoomEnabled(), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_zoom_both) {
            chart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
            return true;
        }
        if (id == R.id.action_zoom_horizontal) {
            chart.setZoomType(ZoomType.HORIZONTAL);
            return true;
        }
        if (id == R.id.action_zoom_vertical) {
            chart.setZoomType(ZoomType.VERTICAL);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*
    private void generateValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random() * 100f;
            }
        }
    }

    private void reset() {
        numberOfLines = 1;

        hasAxes = true;
        hasAxesNames = true;
        hasLines = true;
        hasPoints = true;
        shape = ValueShape.CIRCLE;
        isFilled = false;
        hasLabels = false;
        isCubic = false;
        hasLabelForSelected = false;
        pointsHaveDifferentColor = false;

        chart.setValueSelectionEnabled(hasLabelForSelected);
        resetViewport();
    }

    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = 100;
        v.left = 0;
        v.right = numberOfPoints - 1;
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
    }

    private void generateData() {

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
           // for (int j = 0; j < numberOfPoints; ++j) {
           //     values.add(new PointValue(j, randomNumbersTab[i][j]));
          //  }
            values.add(new PointValue(0,2));
            values.add(new PointValue(2,5));
            values.add(new PointValue(2,7));
            values.add(new PointValue(3,10));

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        data = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);

    }

    /**
     * Adds lines to data, after that data should be set again with
     * {@link LineChartView#setLineChartData(LineChartData)}. Last 4th line has non-monotonically x values.
     *
    private void addLineToData() {
        if (data.getLines().size() >= maxNumberOfLines) {
            Toast.makeText(getActivity(), "Samples app uses max 4 lines!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ++numberOfLines;
        }

        generateData();
    }

    private void toggleLines() {
        hasLines = !hasLines;

        generateData();
    }

    private void togglePoints() {
        hasPoints = !hasPoints;

        generateData();
    }

    private void toggleCubic() {
        isCubic = !isCubic;

        generateData();

        if (isCubic) {
            // It is good idea to manually set a little higher max viewport for cubic lines because sometimes line
            // go above or below max/min. To do that use Viewport.inest() method and pass negative value as dy
            // parameter or just set top and bottom values manually.
            // In this example I know that Y values are within (0,100) range so I set viewport height range manually
            // to (-5, 105).
            // To make this works during animations you should use Chart.setViewportCalculationEnabled(false) before
            // modifying viewport.
            // Remember to set viewport after you call setLineChartData().
            final Viewport v = new Viewport(chart.getMaximumViewport());
            v.bottom = -5;
            v.top = 105;
            // You have to set max and current viewports separately.
            chart.setMaximumViewport(v);
            // I changing current viewport with animation in this case.
            chart.setCurrentViewportWithAnimation(v);
        } else {
            // If not cubic restore viewport to (0,100) range.
            final Viewport v = new Viewport(chart.getMaximumViewport());
            v.bottom = 0;
            v.top = 100;

            // You have to set max and current viewports separately.
            // In this case, if I want animation I have to set current viewport first and use animation listener.
            // Max viewport will be set in onAnimationFinished method.
            chart.setViewportAnimationListener(new ChartAnimationListener() {

                @Override
                public void onAnimationStarted() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationFinished() {
                    // Set max viewpirt and remove listener.
                    chart.setMaximumViewport(v);
                    chart.setViewportAnimationListener(null);

                }
            });
            // Set current viewpirt with animation;
            chart.setCurrentViewportWithAnimation(v);
        }

    }

    private void toggleFilled() {
        isFilled = !isFilled;

        generateData();
    }

    private void togglePointColor() {
        pointsHaveDifferentColor = !pointsHaveDifferentColor;

        generateData();
    }

    private void setCircles() {
        shape = ValueShape.CIRCLE;

        generateData();
    }

    private void setSquares() {
        shape = ValueShape.SQUARE;

        generateData();
    }

    private void setDiamonds() {
        shape = ValueShape.DIAMOND;

        generateData();
    }

    private void toggleLabels() {
        hasLabels = !hasLabels;

        if (hasLabels) {
            hasLabelForSelected = false;
            chart.setValueSelectionEnabled(hasLabelForSelected);
        }

        generateData();
    }

    private void toggleLabelForSelected() {
        hasLabelForSelected = !hasLabelForSelected;

        chart.setValueSelectionEnabled(hasLabelForSelected);

        if (hasLabelForSelected) {
            hasLabels = false;
        }

        generateData();
    }

    private void toggleAxes() {
        hasAxes = !hasAxes;

        generateData();
    }

    private void toggleAxesNames() {
        hasAxesNames = !hasAxesNames;

        generateData();
    }

    /**
     * To animate values you have to change targets values and then call {@link //Chart#startDataAnimation()}
     * method(don't confuse with View.animate()). If you operate on data that was set before you don't have to call
     * {@link LineChartView#setLineChartData(LineChartData)} again.
     *
    private void prepareDataAnimation() {
        for (Line line : data.getLines()) {
            for (PointValue value : line.getValues()) {
                // Here I modify target only for Y values but it is OK to modify X targets as well.
                value.setTarget(value.getX(), (float) Math.random() * 100);
            }
        }
    }

    private class ValueTouchListener implements LineChartOnValueSelectListener {

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
}*/
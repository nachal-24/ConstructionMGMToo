package mgmt.construction.constructionmgmt.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mgmt.construction.constructionmgmt.Activity.AddTaskActivity;
import mgmt.construction.constructionmgmt.Activity.OrdersListActivity;
import mgmt.construction.constructionmgmt.Adapters.ImageAdapter;
import mgmt.construction.constructionmgmt.Adapters.OrdersListRecyclerViewAdapter;
import mgmt.construction.constructionmgmt.Classes.AppController;
import mgmt.construction.constructionmgmt.Classes.DummyContent;
import mgmt.construction.constructionmgmt.Classes.JSONObjectRequest;
import mgmt.construction.constructionmgmt.R;

/**
 * Created by Recluse on 10/28/2015.
 */
public class PieChartFragment extends BaseChartFragment {
    private PieChart mChart;
    private Button taskButton;
    ProgressDialog pDialog;
    ArrayList<String> xVals;
    ArrayList<Entry> entries1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState); }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_simple_pie, parentViewGroup, false);
        taskButton=(Button) v.findViewById(R.id.email_sign_in_button);
        taskButtonClickListener();
     /*   View rootView = inflater.inflate(R.layout.pie_chart, parentViewGroup, false);
        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getActivity().getApplicationContext()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });*/
        mChart = (PieChart) v.findViewById(R.id.pieChart1);
        mChart.setDescription("");

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        mChart.setCenterTextTypeface(tf);
        mChart.setCenterText(generateCenterText());
        mChart.setCenterTextSize(10f);
        mChart.setCenterTextTypeface(tf);
        mChart.animateX(3000);
        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);

        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.RIGHT_OF_CHART);
        insertNonInstEOQ();
        //if(xVals.size()>0 && entries1.size()>0)
       // mChart.setData(generatePieData(xVals,entries1));
       // else
       // Toast.makeText(getActivity(),"No Data",Toast.LENGTH_LONG).show();
        return v;
    }
    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Inv.Cost\nin Rs.");
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }
    private void taskButtonClickListener()
    {
        taskButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent taskIntent =
                        new Intent(getActivity(), OrdersListActivity.class);
                startActivity(taskIntent);

            }

        });
    }
    public void insertNonInstEOQ()
    {
        String tag_json_obj = "json_obj_req";
        String url = "http://alamaari.com/construction/inv_cost_piechart.php";

        entries1 = new ArrayList<Entry>();
        xVals = new ArrayList<String>();

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
                            Log.v("VOLLEY", response.toString());
                            JSONArray ordersObj = response
                                    .getJSONArray("orders");
                            for (int i = 0; i < ordersObj.length(); i++) {

                                JSONObject jo = ordersObj.getJSONObject(i);
                                String typeEOQ=jo.getString("type");
                                Log.v("VOLLEY", jo.getString("id") + " " + jo.getString("item") + " " + typeEOQ);
                                xVals.add(jo.getString("item"));
                                double invCost=Double.parseDouble(jo.getString("totinvcost"));

                                int iInvCost = (int) invCost;
                                entries1.add(new Entry(iInvCost , i));

                            }
                            pDialog.hide();
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();}
                        if(xVals.size()>0 && entries1.size()>0)
                            mChart.setData(generatePieData(xVals,entries1));
                        else
                            Toast.makeText(getActivity(),"No Data",Toast.LENGTH_LONG).show();
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
                Log.v("VOLLEY", error.getMessage());
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


    }
}

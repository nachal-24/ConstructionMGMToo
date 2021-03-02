package mgmt.construction.constructionmgmt.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import mgmt.construction.constructionmgmt.Adapters.OrdersListRecyclerViewAdapter;
import mgmt.construction.constructionmgmt.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //CalendarViewResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarViewResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mgmt.construction.constructionmgmt.Adapters.OrderArrayAdapter;
import mgmt.construction.constructionmgmt.Classes.AppController;
import mgmt.construction.constructionmgmt.Classes.Child;
import mgmt.construction.constructionmgmt.Classes.DummyContent;
import mgmt.construction.constructionmgmt.Classes.JSONObjectRequest;
import mgmt.construction.constructionmgmt.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrdersListResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrdersListResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarViewResultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    // TODO: Rename and change types of parameters
    private String id;
    private String content;
    private String details;
    TextView tTopic;
    TextView itemName;
    TextView totQuantity;
    TextView costPerOrder;
    TextView carryingCost;
    TextView totDays;
    TextView optOrderSize;
    TextView totMinCost;
    TextView noOfOrders;
    TextView ordersCycle;
    TextView startDate;
    TextView endDate;
    TextView maxInvLvl;
    TextView prodRate;
    TextView tOrdersList;
    String stTopic;
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
    //private OnFragmentInteractionListener mListener;

    public CalendarViewResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param //param1 Parameter 1.
     * @param //param2 Parameter 2.
     * @return A new instance of fragment CalendarViewResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarViewResultFragment newInstance(ArrayList<String> idList,String dt) {
        CalendarViewResultFragment fragment = new CalendarViewResultFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, idList);
        args.putString(ARG_PARAM2, dt);
        //args.putString(ARG_PARAM3, d.details);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idsList = getArguments().getStringArrayList(ARG_PARAM1);
            sSelectedDate = getArguments().getString(ARG_PARAM2);
            //details = getArguments().getString(ARG_PARAM3);
        }
        //pDialog = new ProgressDialog(getActivity());
        //pDialog.setMessage("Loading...");
        //pDialog.show();
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
        //pDialog.hide();
        insertNonInstEOQ(tData);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_orders_list_result, container, false);

        //itemName=   (TextView) rootView.findViewById(R.id.itemName);
        //tTopic=     (TextView) rootView.findViewById(R.id.textViewTopic);
        webview = (WebView)rootView.findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.window_bg));
       /* totQuantity=    (TextView) rootView.findViewById(R.id.totalQuantity);
        costPerOrder=   (TextView) rootView.findViewById(R.id.costPerOrder);
        carryingCost=   (TextView) rootView.findViewById(R.id.carryingCost);
        totDays=        (TextView) rootView.findViewById(R.id.totDays);
        optOrderSize=   (TextView) rootView.findViewById(R.id.optOrderSize);
        totMinCost=     (TextView) rootView.findViewById(R.id.totMinCost);
        noOfOrders=     (TextView) rootView.findViewById(R.id.noOfOrders);
        ordersCycle=        (TextView) rootView.findViewById(R.id.ordersCycle);
        startDate=      (TextView) rootView.findViewById(R.id.startDate);
        endDate=        (TextView) rootView.findViewById(R.id.endDate);
        maxInvLvl=      (TextView) rootView.findViewById(R.id.maxInvLvl);
        prodRate=       (TextView) rootView.findViewById(R.id.prodRate);
        tOrdersList = (TextView) rootView.findViewById(R.id.orders_list);*/

       // data=data+"<html><body><h1>HAI</h1></body></html>";


        if(htmlData!=null)
            webview.loadDataWithBaseURL("", htmlData, "text/html", "UTF-8", "");



        return rootView;
    }

    public void insertNonInstEOQ(String pid)
    {
        sData="";
        htmlData="<html><body>";
        String tag_json_obj = "json_obj_req";
        String url = "http://ivapapps.16mb.com/order_calendar_testing.php";
        Map<String, String> params;

        pDialog = new ProgressDialog(getActivity());
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
    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCalendarViewResultFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/
    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCalendarViewResultFragmentInteraction(Uri uri);
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

package mgmt.construction.constructionmgmt.Fragment;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
public class OrdersListResultFragment extends Fragment {
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
    TextView stockInv;
    TextView tOrdersList;
    String stTopic;
    String sitemName;
    String stotQuantity;
    String scostPerOrder;
    String scarryingCost;
    String stotDays;
    String soptOrderSize;
    String stotMinCost;
    String snoOfOrders;
    String sordersCycle;
    String sstartDate;
    String sendDate;
    String sStock;
    String smaxInvLvl;
    String sprodRate;
    ProgressDialog pDialog;
    EditText newDelivery;
    EditText todaysUsage;
    Button updateInv;
    TextView textTopic;
    TextView taskDesc;
    LinearLayout lTrailer;
    LinearLayout header;
    ListView listView;
    private OnFragmentInteractionListener mListener;

    public OrdersListResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param //param1 Parameter 1.
     * @param //param2 Parameter 2.
     * @return A new instance of fragment OrdersListResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersListResultFragment newInstance(DummyContent.DummyItem d) {
        OrdersListResultFragment fragment = new OrdersListResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, d.id);
        args.putString(ARG_PARAM2, d.content);
        args.putString(ARG_PARAM3, d.details);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ARG_PARAM1);
            content = getArguments().getString(ARG_PARAM2);
            details = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View rootView=inflater.inflate(R.layout.fragment_orders_list_result, container, false);
        insertNonInstEOQ(id);
        itemName=   (TextView) rootView.findViewById(R.id.itemName);
        tTopic=     (TextView) rootView.findViewById(R.id.textViewTopic);
        totQuantity=    (TextView) rootView.findViewById(R.id.totalQuantity);
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

        taskDesc=       (TextView) rootView.findViewById(R.id.task_desc);
        stockInv=       (TextView) rootView.findViewById(R.id.status);

        newDelivery =(EditText) rootView.findViewById(R.id.new_delivery);
        todaysUsage=(EditText) rootView.findViewById(R.id.resource_usage);
        updateInv=(Button) rootView.findViewById(R.id.updateInv);
        lTrailer=(LinearLayout)rootView.findViewById(R.id.trailer);

        header=(LinearLayout) rootView.findViewById(R.id.task_detail);

        updateInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatus(id);
            }
        });
        itemName.setText(content);


         tOrdersList = (TextView) rootView.findViewById(R.id.orders_list);



        return rootView;
    }

    public void updateStatus(String pid){
        //Toast.makeText(getContext(),sk.getProgress()+" selected",Toast.LENGTH_LONG).show();

        String tag_json_obj = "json_obj_req";
        String url = "http://ivapapps.16mb.com/update_order.php";
        Map<String, String> params;

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        Calendar now = Calendar.getInstance();
        String updateDate=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH) + 1)+"-"+now.get(Calendar.DAY_OF_MONTH);
        params = new HashMap<String, String>();

                if(sStock==null || sStock.compareToIgnoreCase("")==0)
                    sStock="0";
        String tempusage=todaysUsage.getText().toString();
        if(tempusage==null || tempusage.compareToIgnoreCase("")==0)
            tempusage="0";
        double Stock=0;
        String newdel=newDelivery.getText().toString();
        if(newdel==null || newdel.compareToIgnoreCase("")==0)
            newdel="0";

            Stock = Integer.valueOf(sStock)+Integer.valueOf(newdel) - Integer.valueOf(tempusage);
        params.put("id", pid);
        params.put("updatedate", updateDate);
        params.put("stock", Stock+"");


        //JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
        JSONObjectRequest jsonObjReq = new JSONObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.v("VOLLEYUPDATE", response.toString());
                            // Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();

                            tTopic.setText("Updated Successfully");
                            header.setVisibility(View.GONE);
                            lTrailer.setVisibility(View.GONE);
                            itemName.setVisibility(View.GONE);
                            pDialog.hide();
                        }
                        catch (Exception e)
                        {Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();}
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
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

    }
    public void insertNonInstEOQ(String pid)
    {
        String tag_json_obj = "json_obj_req";
        String url = "http://ivapapps.16mb.com/select_order_detail.php";
        Map<String, String> params;


        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        params = new HashMap<String, String>();
        params.put("id", pid);


        //JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
        JSONObjectRequest jsonObjReq = new JSONObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
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
                            sitemName = response.getString("item");
                            stotQuantity = response.getString("totalquantity");
                            scostPerOrder = response.getString("costperorder");
                            scarryingCost = response.getString("carryingcost");
                            stotDays = response.getString("totaldays");
                            soptOrderSize = response.getString("optimumordersize");
                            stotMinCost = response.getString("totinvcost");
                            snoOfOrders = response.getString("nooforders");
                            sordersCycle = response.getString("orderscycle");
                            sstartDate = response.getString("startdate");
                            sendDate = response.getString("enddate");
                            smaxInvLvl = response.getString("maxinvlevel");
                            sprodRate = response.getString("prodrate");
                            sStock= response.getString("stock");
                            String sType=response.getString("type");
                            if(sType.compareToIgnoreCase("E")==0){
                                //String tempTaskName="Economic Order Quantity";
                                //String s1 = tempTaskName.substring(0, 1).toUpperCase();
                               // String nameCapitalized = s1 + tempTaskName.substring(1);
                                tTopic.setText("Economic Order Quantity");
                                itemName.setText(sitemName);
                                totQuantity.setText("Total Quantity "+stotQuantity);
                                costPerOrder.setText("Cost per Order Rs."+scostPerOrder);
                                carryingCost.setText("Carrying Cost Rs."+scarryingCost);
                                totDays.setText("Task Total Days "+stotDays+" Days");
                                optOrderSize.setText("Optimum Order Size "+soptOrderSize);
                                totMinCost.setText("Total Inventory Cost Rs."+stotMinCost);
                                noOfOrders.setText("Number of Orders per Cycle "+snoOfOrders);
                                ordersCycle.setText("Orders Cycle "+sordersCycle+" Store Days");
                                startDate.setText("Task Start Date "+sstartDate);
                                endDate.setText("Task End Date "+sendDate);
                                stockInv.setText("Stock in Inv. "+sStock);
                                taskDesc.setText(response.getString("taskdetail"));

                            }
                            else if(sType.compareToIgnoreCase("N")==0){
                                tTopic.setText("Non Instant Economic Order Quantity");
                                itemName.setText(sitemName);
                                totQuantity.setText("Total Quantity "+stotQuantity);
                                costPerOrder.setText("Cost per Order Rs."+scostPerOrder);
                                carryingCost.setText("Carrying Cost Rs."+scarryingCost);
                                totDays.setText("Task Total Days "+stotDays+" Days");
                                optOrderSize.setText("Optimum Order Size "+soptOrderSize);
                                totMinCost.setText("Total Inventory Cost Rs."+stotMinCost);
                                noOfOrders.setText("Number of Orders per Cycle "+snoOfOrders);
                                ordersCycle.setText("Production Run "+sordersCycle+" Days per order");
                                startDate.setText("Task Start Date "+sstartDate);
                                endDate.setText("Task End Date "+sendDate);
                                maxInvLvl.setText("Maximum Inventory Level "+smaxInvLvl);
                                maxInvLvl.setVisibility(View.VISIBLE);
                                prodRate.setText("Production Rate " + sprodRate);
                                prodRate.setVisibility(View.VISIBLE);
                                lTrailer.setVisibility(View.GONE);
                                taskDesc.setText(response.getString("taskdetail"));

                            }
                            sstartDate=sstartDate+" 01:01:01";
                            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                            DateTime sdt = formatter.parseDateTime(sstartDate);
                            double dNoOfOrders=Double.parseDouble(snoOfOrders);
                            /*if 2.1 means 2 orders optimum quantity and last one small so 3 orders*/
                            int noOfOrdersCeil=(int) Math.ceil(dNoOfOrders);

                            double dOrdersCycle=Double.parseDouble(sordersCycle);
                            /* if 100.5 days 100*/
                            int ordersCycleFloor=(int) Math.floor(dOrdersCycle);
                            double dTotalQuantity=Double.parseDouble(stotQuantity);
                            double tempQuantity=dTotalQuantity;
                            double dOptOrderSize=Double.parseDouble(soptOrderSize);
                            String orderList="";
                            DateTime sdt1 = sdt;
                            for(int i=0;i<noOfOrdersCeil;i++) {
                            Date date=sdt1.toDate();
                                Log.v("TESTDATE",sdt1.getDayOfMonth()+"/"+sdt1.getMonthOfYear()+"/"+sdt1.getYear());
                                orderList=orderList+"Order "+(i+1)+" "+sdt1.getDayOfMonth()+"/"+sdt1.getMonthOfYear()+"/"+sdt1.getYear();
                                if(tempQuantity>dOptOrderSize) {
                                    orderList = orderList + " " +dOptOrderSize+"\n";
                                    tempQuantity=tempQuantity-dOptOrderSize;
                                }
                                else
                                {
                                    orderList = orderList + " " +String.format("%.2f",tempQuantity)+"\n";
                                }
                                sdt1=sdt1.plusDays(ordersCycleFloor);
                            }
                            tOrdersList.setText(orderList);
                             //Toast.makeText(getActivity(), sdt1.toString(), Toast.LENGTH_LONG).show();
                            pDialog.hide();
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();}
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
               // Log.v("VOLLEY", error.getMessage());
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
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onOrdersListResultFragmentInteraction(uri);
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onOrdersListResultFragmentInteraction(Uri uri);
    }
}

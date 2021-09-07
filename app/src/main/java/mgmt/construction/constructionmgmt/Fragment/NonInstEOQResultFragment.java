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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mgmt.construction.constructionmgmt.Classes.AppController;
import mgmt.construction.constructionmgmt.Classes.JSONObjectRequest;
import mgmt.construction.constructionmgmt.Classes.NonInstantEconomicOrderQuantity;
import mgmt.construction.constructionmgmt.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NonInstEOQResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NonInstEOQResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NonInstEOQResultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";
    private static final String ARG_PARAM8 = "param8";
    private static final String ARG_PARAM9 = "param9";
    private static final String ARG_PARAM10 = "param10";
    private static final String ARG_PARAM11 = "param11";
    private static final String ARG_PARAM12 = "param12";
    private static final String ARG_PARAM13 = "param13";
    private static final String ARG_PARAM14 = "param14";
    private static final String ARG_PARAM15 = "param15";
    private ProgressDialog pDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView totMinCost;
    TextView optOrderSize;
    TextView noOfOrders;
    TextView ordersCycle;
    TextView maxInventoryLevel;
    TextView itemDet;
    TextView workDet;
    TextView tTopic;
    String soptimumOrderSize;
    String sProdRunsPerCycle;
    String sProdRun;
    String stotMinCost;
    String sItem;
    Button saveDetail;
    String sCarryingCost;
    String  sCostPerOrder;
     String  sTotalQty;
     String sTotalDays;
     String sStartDate;
     String sFinishDate;
     String sType;
     String sMaxInvLevel;
     String sProdRate;
    private String sWorkDetail;
    private OnFragmentInteractionListener mListener;

    public NonInstEOQResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param //param1 Parameter 1.
     * @param //param2 Parameter 2.
     * @return A new instance of fragment NonInstEOQResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NonInstEOQResultFragment newInstance(NonInstantEconomicOrderQuantity eoq) {
        NonInstEOQResultFragment fragment = new NonInstEOQResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, String.format("%.2f", eoq.getOptimumOrderSize()));
        args.putString(ARG_PARAM2, String.format("%.2f",eoq.getNoOfProductionRunsPerCycle()));
        args.putString(ARG_PARAM3, String.format("%.2f",eoq.getProductionRun()));
        args.putString(ARG_PARAM4, String.format("%.2f",eoq.getTotalInventoryCost()));
        args.putString(ARG_PARAM5, ""+eoq.getItemName());
        args.putString(ARG_PARAM6, String.format("%.2f", eoq.getCarryingCost()));
        args.putString(ARG_PARAM7, String.format("%.2f", eoq.getCostPerOrder()));
        args.putString(ARG_PARAM8, String.format("%.2f", eoq.getTotalQuantity()));
        args.putString(ARG_PARAM9, String.format("%.2f", eoq.getTotalDays()));
        args.putString(ARG_PARAM10, eoq.getStartDate());
        args.putString(ARG_PARAM11, eoq.getEndDate());
        args.putString(ARG_PARAM12, String.valueOf(eoq.getType()));
        args.putString(ARG_PARAM13, String.format("%.2f", eoq.getMaximumInventoryLevel()));
        args.putString(ARG_PARAM14, String.format("%.2f", eoq.getProductionRate()));
        args.putString(ARG_PARAM15, eoq.getWorkDetail());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            soptimumOrderSize = getArguments().getString(ARG_PARAM1);
            sProdRunsPerCycle = getArguments().getString(ARG_PARAM2);
            sProdRun = getArguments().getString(ARG_PARAM3);
            stotMinCost = getArguments().getString(ARG_PARAM4);
            sItem=getArguments().getString(ARG_PARAM5);
            sCarryingCost=getArguments().getString(ARG_PARAM6);
            sCostPerOrder=getArguments().getString(ARG_PARAM7);
            sTotalQty=getArguments().getString(ARG_PARAM8);
            sTotalDays=getArguments().getString(ARG_PARAM9);
            sStartDate=getArguments().getString(ARG_PARAM10);
            sFinishDate=getArguments().getString(ARG_PARAM11);
            sType=getArguments().getString(ARG_PARAM12);
            sMaxInvLevel=getArguments().getString(ARG_PARAM13);
            sProdRate=getArguments().getString(ARG_PARAM14);
            sWorkDetail=getArguments().getString(ARG_PARAM15);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_non_inst_eoqresult, container, false);
        tTopic=(TextView) view.findViewById(R.id.textViewTopic);
        itemDet=(TextView) view.findViewById(R.id.itemDetail);
        optOrderSize=(TextView) view.findViewById(R.id.optOrderSize);
        totMinCost=(TextView) view.findViewById(R.id.totMinCost);
        noOfOrders=(TextView) view.findViewById(R.id.noOfOrders);
        ordersCycle=(TextView) view.findViewById(R.id.ordersCycle);
        maxInventoryLevel=(TextView) view.findViewById(R.id.maxInvLvl);
        workDet=(TextView) view.findViewById(R.id.work_detail);

        itemDet.setText(sItem);
        workDet.setText(sWorkDetail);
        optOrderSize.setText("Optimal Order Size "+soptimumOrderSize);
        totMinCost.setText("Total Minimum Annual Inventory Cost Rs."+stotMinCost);
        noOfOrders.setText("Production Run "+sProdRun+" days per order");
        ordersCycle.setText("Number of Production Run "+sProdRunsPerCycle);
        maxInventoryLevel.setText("Maximum Inventory Level "+sMaxInvLevel);

        saveDetail=(Button) view.findViewById(R.id.saveEOQ);
        saveDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                insertNonInstEOQ();

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onNonInstEOQResultFragmentInteraction(uri);
        }
    }
    public void insertNonInstEOQ()
    {
        String tag_json_obj = "json_obj_req";
        String url = "http://alamaari.com/construction/insert_noninst_eoq.php";
        Map<String, String> params;


        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        Log.v("VALUECHECK","NOOFORDERS"+sProdRunsPerCycle+"ORDERSCYCLE"+sProdRun);
        params = new HashMap<String, String>();
        params.put("optordersize", soptimumOrderSize);
        params.put("nooforders",sProdRunsPerCycle );
        params.put("startdate", sStartDate);
        params.put("finishdate", sFinishDate);
        params.put("orderscycle",sProdRun );
        params.put("totmincost", stotMinCost);
        params.put("item", sItem);
        params.put("type", sType);
        params.put("carryingcost", sCarryingCost);
        params.put("costperorder", sCostPerOrder);
        params.put("totalqty", sTotalQty);
        params.put("totaldays", sTotalDays);
        params.put("maxinvlevel", sMaxInvLevel);
        params.put("prodrate", sProdRate);
        params.put("taskdetail", sWorkDetail);
        //params.put("name", "abc@abc.com");
        //params.put("password", "abc123");

        //JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
        JSONObjectRequest jsonObjReq = new JSONObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.v("VOLLEY", response.toString());
                        Toast.makeText(getActivity(), "Order data Entered", Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
                Log.v("VOLLEY", error.getMessage());
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onNonInstEOQResultFragmentInteraction(Uri uri);
    }
}

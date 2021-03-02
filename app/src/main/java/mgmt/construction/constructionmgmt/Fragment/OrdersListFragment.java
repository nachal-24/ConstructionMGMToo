package mgmt.construction.constructionmgmt.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mgmt.construction.constructionmgmt.Adapters.OrdersListRecyclerViewAdapter;
import mgmt.construction.constructionmgmt.Classes.AppController;
import mgmt.construction.constructionmgmt.Classes.Child;

import mgmt.construction.constructionmgmt.Classes.JSONObjectRequest;
import mgmt.construction.constructionmgmt.Classes.OrderListItem;
import mgmt.construction.constructionmgmt.Classes.SimpleDividerItemDecoration;
import mgmt.construction.constructionmgmt.R;
import mgmt.construction.constructionmgmt.Classes.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class OrdersListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    ProgressDialog pDialog;
    RecyclerView recyclerView;
    private boolean Paused;
    private OrdersListRecyclerViewAdapter adapter;
    ArrayList<OrderListItem> orderListItems;
    private Bundle savedState = null;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OrdersListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OrdersListFragment newInstance(int columnCount) {
        OrdersListFragment fragment = new OrdersListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
        Log.v("STEPBYSTEP", "ONCREATE");
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        if(savedInstanceState != null && savedState == null) {
            savedState = savedInstanceState.getBundle("ordersListSaveIns");
        }
        if(savedState != null) {
            orderListItems=savedState.getParcelableArrayList("ordersList");
            //OrderListItem object1 = t.get(0);
            DummyContent.ITEMS.clear();
            Log.v("STEPBYSTEP", "ONCREATESAVED");
            for(OrderListItem object1:orderListItems) {
                DummyContent.DummyItem a = new DummyContent.DummyItem(object1.getId(), object1.getContent(), object1.getDetails());

                DummyContent.ITEMS.add(a);
            }
            adapter = new OrdersListRecyclerViewAdapter(DummyContent.ITEMS, mListener);
//            recyclerView.setAdapter(adapter);

        }
        savedState = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);


        //insertNonInstEOQ("1");
        Log.v("STEPBYSTEP", "ONCREATEVIEW");
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            Log.v("RESUMECHECK", "ONCREATEVIEW");
            //Toast.makeText(getContext(),"ONCREATEVIEW",Toast.LENGTH_LONG).show();
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
            Log.v("RESUMECHECK", "ONCREATEVIEWADAPTER");

            if(adapter==null) {
                Log.v("STEPBYSTEP", "ONCREATEVIEWEOQ");
                if(savedInstanceState != null && savedState == null) {
                    savedState = savedInstanceState.getBundle("ordersListSaveIns");
                }
                if(savedState != null) {
                    orderListItems = savedState.getParcelableArrayList("ordersList");
                    //OrderListItem object1 = t.get(0);
                    DummyContent.ITEMS.clear();
                    Log.v("STEPBYSTEP", "ONCREATESAVED");
                    for (OrderListItem object1 : orderListItems) {
                        DummyContent.DummyItem a = new DummyContent.DummyItem(object1.getId(), object1.getContent(), object1.getDetails());

                        DummyContent.ITEMS.add(a);
                    }
                    adapter = new OrdersListRecyclerViewAdapter(DummyContent.ITEMS, mListener);
                    recyclerView.setAdapter(adapter);
                }
                else
                    getEOQ();

                savedState = null;
            }
            else
                {

                    Log.v("STEPBYSTEP", "ONCREATEVIEWELSE");
                    //adapter = new OrdersListRecyclerViewAdapter(DummyContent.ITEMS, mListener);
                    recyclerView.setAdapter(adapter);
                }
            //if(pDialog!=null)
           //pDialog.hide();
        }
        return view;
    }
    public void getEOQ()
    {
        String tag_json_obj = "json_obj_req";
        String url = "http://ivapapps.16mb.com/orders_list_expand.php";

        orderListItems=new ArrayList<OrderListItem>();
        Log.v("STEPBYSTEP", "EOQ");
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
                            DummyContent.ITEMS.clear();
                            orderListItems.clear();
                            Log.v("VOLLEY", response.toString());
                            JSONArray ordersObj = response
                                    .getJSONArray("orders");
                            for (int i = 0; i < ordersObj.length(); i++) {

                                JSONObject jo = ordersObj.getJSONObject(i);
                                String typeEOQ=jo.getString("type");
                                Log.v("VOLLEY", jo.getString("id")+" "+jo.getString("item")+" "+typeEOQ);

                                if(typeEOQ.compareToIgnoreCase("E")==0)
                                    typeEOQ="Economic Order Quantity";
                                else if(typeEOQ.compareToIgnoreCase("E")==0)
                                    typeEOQ="Non Instant Economic Order Quantity";
                               // String tempTaskName=jo.getString("item");
                               // String s1 = tempTaskName.substring(0, 1).toUpperCase();
                               // String nameCapitalized = s1 + tempTaskName.substring(1);
                                DummyContent.DummyItem a=new DummyContent.DummyItem(jo.getString("id"),jo.getString("item"),typeEOQ);
                                OrderListItem o=new OrderListItem(jo.getString("id"),jo.getString("item"),typeEOQ);

                                DummyContent.ITEMS.add(a);
                                orderListItems.add(o);
                            }

                             //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                           if(adapter==null)
                                if(DummyContent.ITEMS!=null) {
                            adapter = new OrdersListRecyclerViewAdapter(DummyContent.ITEMS, mListener);
                            recyclerView.setAdapter(adapter);}
                            pDialog.hide();
                        }
                        catch (JSONException e)
                        {
                            pDialog.hide();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();}
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
              //  Log.v("VOLLEY", error.getMessage());
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
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(DummyContent.DummyItem mItem) {
        if (mListener != null) {
            mListener.onOrdersListFragmentInteraction(mItem);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onOrdersListFragmentInteraction(DummyContent.DummyItem mItem);
    }
    @Override
    public void onPause()
    {
        super.onPause();
        this.Paused = true;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.v("STEPBYSTEP", "RESUME");
        //Log.v("RESUMECHECK", "ONRESUME");
     /*   if(adapter==null){
        //if(DummyContent.ITEMS!=null) {
            Log.v("RESUMECHECK","ONRESUMEADAPTER");
            getEOQ();
            Log.v("STEPBYSTEP", "RESUMEEOQ");

        //}
    }else{
            //adapter = new OrdersListRecyclerViewAdapter(DummyContent.ITEMS, mListener);
            recyclerView.setAdapter(adapter);
            Log.v("STEPBYSTEP", "RESUMEELSE");
//            pDialog.hide();
        }*/

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v("STEPBYSTEP","ONDESTROYVIEW");
        savedState = saveState(); /* vstup defined here for sure */
        adapter = null;
    }

    private Bundle saveState() { /* called either from onDestroyView() or onSaveInstanceState() */
        Bundle state = new Bundle();
        Log.v("STEPBYSTEP","SAVESTATE");
        ArrayList<OrderListItem> tempOrderList=new ArrayList<OrderListItem>();
        tempOrderList.addAll(orderListItems);
        state.putParcelableArrayList("ordersList", tempOrderList);
        return state;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        Log.v("STEPBYSTEP", "SAVEINSTANCE");
     /* If onDestroyView() is called first, we can use the previously savedState but we can't call saveState() anymore */
        /* If onSaveInstanceState() is called first, we don't have savedState, so we need to call saveState() */
        /* => (?:) operator inevitable! */
        outState.putBundle("ordersListSaveIns", (savedState != null) ? savedState : saveState());


    }
}

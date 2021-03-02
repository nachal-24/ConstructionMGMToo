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
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import mgmt.construction.constructionmgmt.Adapters.ExpandListAdapter;
import mgmt.construction.constructionmgmt.Adapters.OrdersListRecyclerViewAdapter;
import mgmt.construction.constructionmgmt.Classes.AppController;
import mgmt.construction.constructionmgmt.Classes.Child;
import mgmt.construction.constructionmgmt.Classes.DummyContent;
import mgmt.construction.constructionmgmt.Classes.Group;
import mgmt.construction.constructionmgmt.Classes.OrderListItem;
import mgmt.construction.constructionmgmt.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpTaskListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExpTaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpTaskListFragment extends Fragment implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<Group> list;
    ArrayList<Child> ch_list;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String url = "http://ivapapps.16mb.com/task_list_expand.php";
    ProgressDialog PD;
    TextView tProjectName;
    private ExpandListAdapter ExpAdapter;
    private ExpandableListView ExpandList;
    private OnFragmentInteractionListener mListener;
    private Bundle savedState = null;
    public ExpTaskListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpTaskListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpTaskListFragment newInstance(String param1, String param2) {
        ExpTaskListFragment fragment = new ExpTaskListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("STEPBYSTEP", "ONCREATE");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if(savedInstanceState != null && savedState == null) {
            savedState = savedInstanceState.getBundle("tasksListSaveIns");
        }
        if(savedState != null) {
            list=savedState.getParcelableArrayList("taskList");
            Log.v("STEPBYSTEP", "ONCREATESAVED");
            //OrderListItem object1 = t.get(0);
           /* DummyContent.ITEMS.clear();
            Log.v("STEPBYSTEP", "ONCREATESAVED");
            for(OrderListItem object1:orderListItems) {
                DummyContent.DummyItem a = new DummyContent.DummyItem(object1.getId(), object1.getContent(), object1.getDetails());

                DummyContent.ITEMS.add(a);
            }*/

            if(list!=null)
                ExpAdapter = new ExpandListAdapter(
                        getActivity(), list);
            //ExpandList.setAdapter(ExpAdapter);
//            recyclerView.setAdapter(adapter);

        }
        savedState = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_exp_task_list, container, false);
        Log.v("STEPBYSTEP", "ONCREATEVIEW");
        ExpandList = (ExpandableListView) view.findViewById(R.id.exp_list);
        ExpandList.setOnChildClickListener(this);
        ExpandList.setOnGroupClickListener(this);
        tProjectName=(TextView) view.findViewById(R.id.textViewTopic);

        if(ExpAdapter==null) {
            Log.v("STEPBYSTEP", "ONCREATEVIEWEOQ");
            if(savedInstanceState != null && savedState == null) {
                savedState = savedInstanceState.getBundle("tasksListSaveIns");
            }
            if(savedState != null) {
                list = savedState.getParcelableArrayList("taskList");
                Log.v("STEPBYSTEP", "ONCREATESAVED");
                //OrderListItem object1 = t.get(0);
                //DummyContent.ITEMS.clear();
              /*  Log.v("STEPBYSTEP", "ONCREATESAVED");
                for (OrderListItem object1 : orderListItems) {
                    DummyContent.DummyItem a = new DummyContent.DummyItem(object1.getId(), object1.getContent(), object1.getDetails());

                    DummyContent.ITEMS.add(a);
                }
                adapter = new OrdersListRecyclerViewAdapter(DummyContent.ITEMS, mListener);
                recyclerView.setAdapter(adapter);*/
                ExpAdapter = new ExpandListAdapter(
                        getActivity(), list);
                ExpandList.setAdapter(ExpAdapter);
            }
            else
                makejsonobjreq();

            savedState = null;
        }
        else
        {

            Log.v("STEPBYSTEP", "ONCREATEVIEWELSE");
            //adapter = new OrdersListRecyclerViewAdapter(DummyContent.ITEMS, mListener);
            ExpandList.setAdapter(ExpAdapter);
        }


        return view;
    }
    private void makejsonobjreq() {
        PD = new ProgressDialog(getActivity());

        PD.setMessage("Loading.....");
        PD.setCancelable(false);
        PD.show();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                list = new ArrayList<Group>();
                list.clear();

                try {
                    Iterator<String> key = response.keys();
                    while (key.hasNext()) {
                        String k = key.next();
                        if (k.compareToIgnoreCase("task") == 0) {

                            JSONArray ja = response.getJSONArray(k);

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jo = ja.getJSONObject(i);

                                Child ch = new Child();

                                ch.setName(jo.getString("taskname"));
                                ch.setType(jo.getString("type"));
                                ch.setStatus(jo.getString("status"));
                                ch.setId(jo.getString("id"));
                                String tempTaskName=jo.getString("taskname");
                                String s1 = tempTaskName.substring(0, 1).toUpperCase();
                                String nameCapitalized = s1 + tempTaskName.substring(1);
                                tProjectName.setText(nameCapitalized);

                            } // for loop end

                        }else{
                            Group gru = new Group();
                            String tempTaskName=k;
                            String s1 = tempTaskName.substring(0, 1).toUpperCase();
                            String nameCapitalized = s1 + tempTaskName.substring(1);
                            gru.setName(nameCapitalized);

                            ch_list = new ArrayList<Child>();
                            ch_list.clear();
                            JSONArray ja = response.getJSONArray(k);

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jo = ja.getJSONObject(i);

                                Child ch = new Child();
                                String tempTaskName1=jo.getString("taskname");
                                String s11 = tempTaskName1.substring(0, 1).toUpperCase();
                                String nameCapitalized1 = s11 + tempTaskName1.substring(1);
                                ch.setName(nameCapitalized1);
                                ch.setType(jo.getString("type"));
                                ch.setStatus(jo.getString("status"));
                                ch.setId(jo.getString("id"));

                                ch_list.add(ch);
                            } // for loop end
                            gru.setItems(ch_list);
                            list.add(gru);
                        }
                        } // while loop end


                    ExpAdapter = new ExpandListAdapter(
                            getActivity(), list);
                    ExpandList.setAdapter(ExpAdapter);

                /*    ExpandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                        @Override
                        public boolean onChildClick(
                                ExpandableListView parent, View v,
                                int groupPosition, int childPosition,
                                long id) {
                            //GoCategory(mainMenusList.get(groupPosition)
                            //   .getPagesList().get(childPosition));
                            Toast.makeText(getActivity(), list.get(groupPosition).getName()+" "+ch_list.get(childPosition).getName(), Toast.LENGTH_LONG).show();
                            return false;
                        }
                    });*/
                    PD.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), "No internet Access, Check your internet connection.", Toast.LENGTH_LONG).show();}
                PD.dismiss();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jreq");
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Child c) {
        if (mListener != null) {
            mListener.onExpTaskListFragmentInteraction(c);
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onExpTaskListFragmentInteraction(Child c);
    }
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Log.d("CabinetListFragment", "onChildClick");
        //mCallbacks.onChildSelected(groupPosition, childPosition);
        //Toast.makeText(getActivity(), "new "+list.get(groupPosition).getName()+" "+list.get(groupPosition).getItems().get(childPosition).getName(), Toast.LENGTH_LONG).show();
        onButtonPressed(list.get(groupPosition).getItems().get(childPosition));
        return true;
    }
    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        Log.d("CabinetListFragment", "onGroupClick");
       // mCallbacks.onGroupSelected(groupPosition);
        return false;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        Log.v("STEPBYSTEP", "ONRESUME");
        //makejsonobjreq();
      /*  if(list!=null)
        ExpAdapter = new ExpandListAdapter(
                getActivity(), list);
        ExpandList.setAdapter(ExpAdapter);*/
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v("STEPBYSTEP","ONDESTROYVIEW");
        savedState = saveState(); /* vstup defined here for sure */
        ExpAdapter = null;
    }

    private Bundle saveState() { /* called either from onDestroyView() or onSaveInstanceState() */
        Bundle state = new Bundle();
        Log.v("STEPBYSTEP","SAVESTATE");
        //ArrayList<OrderListItem> tempOrderList=new ArrayList<OrderListItem>();
        //tempOrderList.addAll(list);
        state.putParcelableArrayList("taskList", list);
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
        outState.putBundle("tasksListSaveIns", (savedState != null) ? savedState : saveState());


    }
}

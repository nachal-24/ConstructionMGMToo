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
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mgmt.construction.constructionmgmt.Adapters.ExpandListAdapter;
import mgmt.construction.constructionmgmt.Classes.AppController;
import mgmt.construction.constructionmgmt.Classes.Child;
import mgmt.construction.constructionmgmt.Classes.Group;
import mgmt.construction.constructionmgmt.Classes.JSONObjectRequest;
import mgmt.construction.constructionmgmt.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpTaskListResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExpTaskListResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpTaskListResultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    ProgressDialog PD;
    String url = "http://ivapapps.16mb.com/task_list_expand.php";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String taskName;
    String id;
    String status;
    String type;
    TextView tTaskName;
    TextView tStartDate;
    TextView tEndDate;
    TextView tDuration;
    TextView tResources;
    TextView tStatus;
    TextView ttaskDetail;
    ProgressDialog pDialog;
    Button updateButton;
    SeekBar sk;
    int progressStatus=0;
    LinearLayout header;
    LinearLayout trailer;

    private OnFragmentInteractionListener mListener;

    public ExpTaskListResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param //param1 Parameter 1.
     * @param //param2 Parameter 2.
     * @return A new instance of fragment ExpTaskListResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpTaskListResultFragment newInstance(Child c) {
        ExpTaskListResultFragment fragment = new ExpTaskListResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, c.getId());
        args.putString(ARG_PARAM2, c.getName());
        args.putString(ARG_PARAM3, c.getStatus());
        args.putString(ARG_PARAM4, c.getType());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ARG_PARAM1);
            taskName = getArguments().getString(ARG_PARAM2);
            status = getArguments().getString(ARG_PARAM3);
            type = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_exp_task_list_result, container, false);
        tTaskName=(TextView) rootView.findViewById(R.id.task_name);
        tStartDate=(TextView) rootView.findViewById(R.id.startDate);
        tEndDate=(TextView) rootView.findViewById(R.id.endDate);
        tStatus=(TextView) rootView.findViewById(R.id.status);
        tResources=(TextView) rootView.findViewById(R.id.resources);
        tDuration=(TextView) rootView.findViewById(R.id.duration);
        ttaskDetail=(TextView) rootView.findViewById(R.id.taskDetail);
        header=(LinearLayout) rootView.findViewById(R.id.task_detail1);
        trailer=(LinearLayout) rootView.findViewById(R.id.trailer);

        updateButton=(Button) rootView.findViewById(R.id.updateTask);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatus(id);
            }
        });
        tTaskName.setText(taskName);

        sk=(SeekBar) rootView.findViewById(R.id.seekBar1);
        seekBarIntial();

        //makejsonobjreq(id);
        insertNonInstEOQ(id);
        return rootView;
    }
    public void updateStatus(String pid){
        //Toast.makeText(getContext(),sk.getProgress()+" selected",Toast.LENGTH_LONG).show();

        String tag_json_obj = "json_obj_req";
        String url = "http://ivapapps.16mb.com/update_task.php";
        Map<String, String> params;

        int statuspercent=sk.getProgress();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        Calendar now = Calendar.getInstance();
        String updateDate=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH) + 1)+"-"+now.get(Calendar.DAY_OF_MONTH);
        params = new HashMap<String, String>();
        params.put("id", pid);
        params.put("actualenddate", updateDate);
        params.put("statuspercent", statuspercent+"");
        if(statuspercent==100)
            params.put("status","D");
        else if(statuspercent==0)
            params.put("status","N");
        else
        params.put("status","O");

        //JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
        JSONObjectRequest jsonObjReq = new JSONObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.v("VOLLEYUPDATE", response.toString());
                            // Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();

                            ttaskDetail.setText("Updated Successfully");
                            header.setVisibility(View.GONE);
                            trailer.setVisibility(View.GONE);
                            tTaskName.setVisibility(View.GONE);
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
    public void seekBarIntial(){
        sk.setMax(100);
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int p = 0;

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                //if (p < 30) {
                //   p = 30;
                //    sk.setProgress(p);
                // }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                p = progress;

                tStatus.setText(p + " % Completed");
            }
        });
    }


    public void insertNonInstEOQ(String pid)
    {
        String tag_json_obj = "json_obj_req";
        String url = "http://ivapapps.16mb.com/select_task_detail.php";
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
                            String sDuration=response.getString("duration");
                            String sTaskName=response.getString("taskname");
                            String sStartDate=response.getString("startdate");
                            String sEndDate=response.getString("finishdate");
                            String sResources=response.getString("resources");
                            String sStatus=response.getString("status");
                            if (sDuration != null && sDuration.compareToIgnoreCase("") != 0
                                    && sTaskName != null && sTaskName.compareToIgnoreCase("") != 0
                                    && sStartDate != null && sStartDate.compareToIgnoreCase("") != 0
                                    && sEndDate != null && sEndDate.compareToIgnoreCase("") != 0
                                    && sResources != null && sResources.compareToIgnoreCase("") != 0
                                    && sStatus != null && sStatus.compareToIgnoreCase("") != 0) {
                                tDuration.setText("Task Duration " + sDuration + " Days");
                                tTaskName.setText(sTaskName);
                                tStartDate.setText("Task Start Date " + sStartDate);
                                tEndDate.setText("Task End Date " + sEndDate);
                                tResources.setText("Resources needed " + sResources);
                                String status = sStatus;
                                String statusPercent = response.getString("statuspercent");
                                //int progressStatus = 0;
                                if (statusPercent != null && statusPercent.compareToIgnoreCase("") != 0)
                                    progressStatus = Integer.valueOf(response.getString("statuspercent"));
                                if (status.compareToIgnoreCase("O") == 0) {

                                    sk.setProgress(progressStatus);
                                    tStatus.setText(progressStatus + "% Completed");
                                } else if (status.compareToIgnoreCase("D") == 0) {
                                    sk.setProgress(progressStatus);
                                    tStatus.setText(progressStatus + "% Completed");
                                } else if (status.compareToIgnoreCase("N") == 0) {
                                    sk.setProgress(progressStatus);
                                    tStatus.setText(progressStatus + "% Completed");
                                } else {
                                    sk.setProgress(0);
                                    tStatus.setText("0% Completed");
                                }
                                // Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                                pDialog.hide();
                            }
                            else
                            {
                                tTaskName.setText("Error");
                            }
                            }
                            catch(JSONException e)
                            {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }


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
    private void makejsonobjreq(String pid) {
        String tag_json_obj = "json_obj_req";
        String url = "http://ivapapps.16mb.com/select_task_detail.php";
        PD = new ProgressDialog(getActivity());
        PD.setMessage("Loading...");
        PD.show();

        Map<String, String> params2;
        params2 = new HashMap<String, String>();
        params2.put("id", pid);
        params2.put("value", pid);

        JSONObject params = new JSONObject();
try {

    params.put("id", id);
}catch (JSONException e) {
    e.printStackTrace();
}

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url,
                params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {




                try {
                    Log.v("VOLLEY", response.toString());
                    //JSONObject jo=response;
                    //Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();

                    JSONObject params1=new JSONObject();
                    params1.put("id", "param1");
                  /*  Iterator<String> key = response.keys();
                    while (key.hasNext()) {
                        String k = key.next();


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
                                //tProjectName.setText(nameCapitalized);

                            } // for loop end



                    } // while loop end*/
                    PD.dismiss();

                } catch (JSONException e) {
                   e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jreq");
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onExpTaskListResultFragmentInteraction(uri);
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
        void onExpTaskListResultFragmentInteraction(Uri uri);
    }
}

package mgmt.construction.constructionmgmt.Fragment;

/**
 * Created by Recluse on 2/1/2016.
 */

import android.support.v4.app.Fragment;

        import android.content.Context;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

        import org.joda.time.DateTime;
        import org.joda.time.Days;

        import java.util.Calendar;

import mgmt.construction.constructionmgmt.Activity.EconomicOrderQuantityActivity;
import mgmt.construction.constructionmgmt.Classes.EconomicOrderQuantity;
        import mgmt.construction.constructionmgmt.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EOQFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EOQFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EOQFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText totQuan;
    EditText costPerOrd;
    EditText carCost;
    EditText totDays;
    EditText workDetail;
    EditText item;
    Button mEmailSignInButton;
   /* TextView totMinCost;
    TextView optOrderSize;
    TextView noOfOrders;
    TextView ordersCycle;*/
    TextView endDate;
    TextView startDate;
    ImageButton startDatePicker;
    ImageButton endDatePicker;
    boolean sDtPicker=false;
    boolean eDtPicker=false;
    int syear;
    int smonthofyear;
    int sdayofmonth;
    int eyear;
    int emonthofyear;
    int edayofmonth;
    String soptimumOrderSize="";
    String stotMinCost="";
    String snoOfOrders="";
    String sordersCycle="";
    String sItemDetail="";
    String sStartDate="";
    String sEndDate="";
    EconomicOrderQuantity eoq;
    private OnFragmentInteractionListener mListener;

    public EOQFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EOQFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EOQFragment newInstance(String param1, String param2) {
        EOQFragment fragment = new EOQFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_eoq_main, container, false);

        startDate=(TextView) rootView.findViewById(R.id.startDateTV);
        endDate=(TextView) rootView.findViewById(R.id.endDateTV);
        item=(EditText) rootView.findViewById(R.id.itemDetail);
        workDetail=(EditText) rootView.findViewById(R.id.work_detail);
        startDatePicker  = (ImageButton)rootView.findViewById(R.id.startDate);
        startDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.v("DATEPICKER","button click");
                sDtPicker = true;
                DatePicker();
            }
        });
        endDatePicker  = (ImageButton)rootView.findViewById(R.id.endDate);
        endDatePicker  = (ImageButton)rootView.findViewById(R.id.endDate);
        endDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("DATEPICKER", "end button click");
                // endDatePicker();
                sDtPicker=false;
                //Toast.makeText(getActivity(), "End Button Click", Toast.LENGTH_SHORT).show();
                DatePicker();

            }
        });

        mEmailSignInButton = (Button)rootView.findViewById(R.id.calculateEOQ);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calculateEOQ();
                calculateEOQ();
//                Log.v("RESULTCHECK", "fragEOQ" + eoq.getOptimumOrderSize());
               // onButtonPressed(soptimumOrderSize,snoOfOrders,sordersCycle,stotMinCost,sItemDetail);

            }
        });
        totQuan = (EditText)rootView.findViewById(R.id.totalQuantity);
        costPerOrd = (EditText)rootView.findViewById(R.id.costPerOrder);
        carCost = (EditText)rootView.findViewById(R.id.carryingCost);
        totDays = (EditText)rootView.findViewById(R.id.totDays);

       /* optOrderSize=(TextView) rootView.findViewById(R.id.optOrderSize);
        totMinCost=(TextView) rootView.findViewById(R.id.totMinCost);
        noOfOrders=(TextView) rootView.findViewById(R.id.noOfOrders);
        ordersCycle=(TextView) rootView.findViewById(R.id.ordersCycle);*/
        totDays = (EditText)rootView.findViewById(R.id.totDays);
        // Inflate the layout for this fragment
        return rootView;

    }

 /*   // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String soptimumOrderSize,String snoOfOrders,String sordersCycle,String stotMinCost,String sItem) {
        if (mListener != null) {
            mListener.onEOQFragmentInteraction(soptimumOrderSize,snoOfOrders,sordersCycle,stotMinCost,sItem);
        }
    }*/
// TODO: Rename method, update argument and hook method into UI event
 public void onButtonPressed(EconomicOrderQuantity eoq) {
     if (mListener != null) {
         mListener.onEOQFragmentInteraction(eoq);
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
  /*  public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onEOQFragmentInteraction(String soptimumOrderSize,String snoOfOrders,String sordersCycle,String stotMinCost,String sItem);
    }*/
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onEOQFragmentInteraction(EconomicOrderQuantity eoq);
    }
    public void calculateEOQ(){
        EconomicOrderQuantity tempEOQ=new EconomicOrderQuantity();
        try {
            if(item.getText().toString().compareTo("")!=0 && item.getText().toString()!=null )
            sItemDetail=item.getText().toString();
            else
            sItemDetail="Item Name";

            double totalQuantity=0;
            if(totQuan.getText().toString().compareTo("")!=0 && totQuan.getText().toString()!=null )
                totalQuantity = Double.parseDouble(totQuan.getText().toString());

            double costPerOrder=0;

            if(costPerOrd.getText().toString().compareTo("")!=0 && costPerOrd.getText().toString()!=null)
                costPerOrder = Double.parseDouble(costPerOrd.getText().toString());

            double carryingCost=0;

            if(carCost.getText().toString().compareTo("")!=0 && carCost.getText().toString()!=null)
                carryingCost = Double.parseDouble(carCost.getText().toString());

            double totalDays=0;

            if(totDays.getText().toString().compareTo("")!=0 && totDays.getText().toString()!=null)
                totalDays = Double.parseDouble(totDays.getText().toString());

            if(sStartDate==null || sStartDate.compareToIgnoreCase("")==0)
                sStartDate="1900-1-1";
            if(sEndDate==null || sEndDate.compareToIgnoreCase("")==0)
                sEndDate="1900-1-1";

String sWorkDetail=workDetail.getText().toString();
            if(sWorkDetail==null)
                sWorkDetail="";


            if(carryingCost!=0 && costPerOrder!=0 && totalQuantity!=0 && totalDays!=0 && sItemDetail.compareToIgnoreCase("Item Name")!=0) {


                tempEOQ=new EconomicOrderQuantity(carryingCost,costPerOrder,totalQuantity,totalDays,sStartDate
                ,sEndDate,sItemDetail,sWorkDetail);

                // NonInstantEconomicOrderQuantity eoq=new NonInstantEconomicOrderQuantity(carryingCost,costPerOrder,totalQuantity,totalDays,150);
                double optimumOrderSize=tempEOQ.optimalOrderSize();


                //optOrderSize.setText("Optimum Order Size " + String.valueOf(optimumOrderSize));
                //optOrderSize.setVisibility(View.VISIBLE);
                soptimumOrderSize="Optimum Order Size " + String.format("%.2f",optimumOrderSize);


                //totMinCost.setText("Total Inventory Cost Rs." + String.valueOf(eoq.totalAnnualMinimumInventoryCost()));
               // totMinCost.setVisibility(View.VISIBLE);
                stotMinCost="Total Inventory Cost Rs." + String.format("%.2f", tempEOQ.totalAnnualMinimumInventoryCost());

                //noOfOrders.setText("No of Orders " + String.valueOf(eoq.noOfOrdersCycle()));
                // noOfOrders.setText("No of Orders " + String.valueOf(eoq.noOfProductionRunsPerCycle()));
                //noOfOrders.setVisibility(View.VISIBLE);
                snoOfOrders="No of Orders " + String.format("%.2f", tempEOQ.noOfOrdersCycle());



                //ordersCycle.setText("Orders Cycle " + String.valueOf(eoq.ordersCycleTime()) + " Days");
                // ordersCycle.setText("Orders Cycle " + String.valueOf(eoq.productionRun()) + " Days" + eoq.maximumInventoryLevel());
                //ordersCycle.setVisibility(View.VISIBLE);
                sordersCycle="Orders Cycle " + String.format("%.2f", tempEOQ.ordersCycleTime()) + " Days";
                eoq=tempEOQ;
                if(Double.isNaN(optimumOrderSize)  || Double.isInfinite(optimumOrderSize))
                {
                    Toast.makeText(getActivity(),"Unrealistic data \n Check the data entered",Toast.LENGTH_LONG).show();
                }
                else {
                    onButtonPressed(eoq);
                }
            }
            else {
                if(sItemDetail.compareToIgnoreCase("Item Name")==0)
                {
                    item.setError("This field is required");
                    item.requestFocus();
                }
                else if(totalQuantity==0)
                {
                    totQuan.setError("This field is required");
                    totQuan.requestFocus();
                }
                else if(costPerOrder==0)
                {
                    costPerOrd.setError("This field is required");
                    costPerOrd.requestFocus();
                }
                else if(carryingCost==0)
                {
                    carCost.setError("This field is required");
                    carCost.requestFocus();
                }


                else if(totalDays==0)
                {
                    totDays.setError("This field is required");
                    totDays.requestFocus();
                }
                else
                {
                    totQuan.setText("Enter Necessary details");
                    totQuan.setVisibility(View.VISIBLE);
                }

            }
        }
        catch (Exception e){}

    }
    public  void DatePicker()
    {
        Log.v("DATEPICKER", "In satrrt method");
        //Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String date = "You picked the following State date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

                        if(sDtPicker==true) {

                            setStartDate(year, monthOfYear, dayOfMonth);
                        }
                        else
                        {

                            setEndDate(year, monthOfYear,dayOfMonth);
                        }


                        //Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
                    }

                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.vibrate(false);
        // dpd.showYearPickerFirst(true);
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }
    public void setStartDate(int year,int monthOfYear, int dayOfMonth)
    {
        sDtPicker=false;
        syear=year;
        smonthofyear=monthOfYear;
        sdayofmonth=dayOfMonth;
        startDate.setText(sdayofmonth + "/" + (smonthofyear + 1) + "/" + syear);
        startDate.setVisibility(View.VISIBLE);
        sStartDate=syear+"-"+(smonthofyear + 1)+"-"+sdayofmonth;
        //Toast.makeText(getActivity(), "STArt Date set", Toast.LENGTH_SHORT).show();
    }

    public DateTime getStartDate()
    {
        DateTime startDate;
        if(eyear!=0 && emonthofyear>=0 && edayofmonth!=0)
            startDate = new DateTime(syear, smonthofyear+1, sdayofmonth, 12, 0);
        else
            startDate = new DateTime(1900, 1, 1, 12, 0);
        return startDate;
    }
    public void setEndDate(int year,int monthOfYear, int dayOfMonth)
    {
        eDtPicker=false;
        eyear=year;
        emonthofyear=monthOfYear;
        edayofmonth=dayOfMonth;
        endDate.setText(edayofmonth + "/" + (emonthofyear + 1) + "/" + eyear);
        endDate.setVisibility(View.VISIBLE);
        sEndDate=eyear+"-"+(emonthofyear + 1)+"-"+edayofmonth;
        //Toast.makeText(getActivity(), "End Date set", Toast.LENGTH_SHORT).show();
        int days=totalNoOfDays();

        totDays.setText(days + "");
    }
    public DateTime getEndDate()
    {
        DateTime endDate;
        if(eyear!=0 && emonthofyear>=0 && edayofmonth!=0)
            endDate = new DateTime(eyear, emonthofyear+1, edayofmonth, 12, 0);
        else
            endDate = new DateTime(1900, 1, 1, 12, 0);
        return endDate;
    }

    public int totalNoOfDays()
    {

        int days = Days.daysBetween(getStartDate().withTimeAtStartOfDay(), getEndDate().withTimeAtStartOfDay()).getDays();
        Log.v("DATEPICKER", "totoalnoofdays" + days + " " + getStartDate() + " " + getEndDate());
        if(days>35000 || days<0)
            days=0;
        return  days;
    }

}


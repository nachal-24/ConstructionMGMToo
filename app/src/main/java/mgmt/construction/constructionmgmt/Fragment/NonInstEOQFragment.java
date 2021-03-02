package mgmt.construction.constructionmgmt.Fragment;

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

import mgmt.construction.constructionmgmt.Classes.EconomicOrderQuantity;
import mgmt.construction.constructionmgmt.Classes.NonInstantEconomicOrderQuantity;
import mgmt.construction.constructionmgmt.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NonInstEOQFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NonInstEOQFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NonInstEOQFragment extends Fragment {
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
    EditText prodRate;
    EditText workDetail;
    EditText eItemDetail;
    Button mEmailSignInButton;
   /* TextView totMinCost;
    TextView optOrderSize;
    TextView noOfOrders;
    TextView ordersCycle;
    TextView maxInventoryLevel;*/
    TextView endDate;
    TextView startDate;
    String sItemDetail="";
    String sStartDate="";
    String sEndDate="";
    String sOrdersCycle="";
String sOptimumOrderSize="";
    String sTotMinCost="";
    String sNoOfOrders="";
    String sMaxInventoryLevel="";
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
    NonInstantEconomicOrderQuantity eoq;
    private OnFragmentInteractionListener mListener;

    public NonInstEOQFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NonInstEOQFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NonInstEOQFragment newInstance(String param1, String param2) {
        NonInstEOQFragment fragment = new NonInstEOQFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_non_inst_eoq, container, false);
        mEmailSignInButton = (Button) view.findViewById(R.id.calculateEOQ);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateEOQ();
            }
        });
        startDatePicker  = (ImageButton)view.findViewById(R.id.startDate);
        startDate=(TextView) view.findViewById(R.id.startDateTV);
        endDate=(TextView) view.findViewById(R.id.endDateTV);
        eItemDetail=(EditText) view.findViewById(R.id.itemDetail);
        totQuan = (EditText)view.findViewById(R.id.totalQuantity);
        costPerOrd = (EditText)view.findViewById(R.id.costPerOrder);
        carCost = (EditText)view.findViewById(R.id.carryingCost);
        prodRate = (EditText)view.findViewById(R.id.prodRate);
        workDetail=(EditText) view.findViewById(R.id.work_detail);
        totDays = (EditText)view.findViewById(R.id.totDays);
        startDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.v("DATEPICKER","button click");
                sDtPicker=true;
                DatePicker();
            }
        });
        endDatePicker  = (ImageButton)view.findViewById(R.id.endDate);
        endDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("DATEPICKER", "end button click");
                // endDatePicker();
                sDtPicker=false;
                Toast.makeText(getActivity(), "End Button Click", Toast.LENGTH_SHORT).show();
                DatePicker();

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(NonInstantEconomicOrderQuantity eoq) {
        if (mListener != null) {
            mListener.onNonInstEOQFragmentInteraction(eoq);
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
        void onNonInstEOQFragmentInteraction(NonInstantEconomicOrderQuantity eoq);
    }
    public void calculateEOQ(){
        try {
            NonInstantEconomicOrderQuantity tempEOQ=new NonInstantEconomicOrderQuantity();
            if(eItemDetail.getText().toString().compareTo("")!=0 && eItemDetail.getText().toString()!=null )
                sItemDetail=eItemDetail.getText().toString();
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


            double productionRate=0;

            if(prodRate.getText().toString().compareTo("")!=0 && prodRate.getText().toString()!=null)
                productionRate = Double.parseDouble(prodRate.getText().toString());



            if(sStartDate==null || sStartDate.compareToIgnoreCase("")==0)
                sStartDate="1900-1-1";
            if(sEndDate==null || sEndDate.compareToIgnoreCase("")==0)
                sStartDate="1900-1-1";
            String sWorkDetail=workDetail.getText().toString();
            if(sWorkDetail==null)
                sWorkDetail="";

            if(carryingCost!=0 && costPerOrder!=0 && totalQuantity!=0 && totalDays!=0 && productionRate!=0 && sItemDetail.compareToIgnoreCase("Item Name")!=0) {

                //EconomicOrderQuantity eoq=new EconomicOrderQuantity(carryingCost,costPerOrder,totalQuantity,totalDays);

                tempEOQ=new NonInstantEconomicOrderQuantity(carryingCost,costPerOrder,totalQuantity,totalDays,
                        productionRate,sStartDate,sEndDate,sItemDetail,sWorkDetail);
                double optimumOrderSize=tempEOQ.optimalOrderSize();

                //optOrderSize.setText("Optimum Order Size " + String.valueOf(optimumOrderSize));
                //optOrderSize.setVisibility(View.VISIBLE);
                sOptimumOrderSize="Optimum Order Size " + String.valueOf(optimumOrderSize);

                //totMinCost.setText("Total Inventory Cost Rs." + String.valueOf(tempEOQ.totalAnnualMinimumInventoryCost()));
                //totMinCost.setVisibility(View.VISIBLE);
                sTotMinCost="Total Inventory Cost Rs." + String.valueOf(tempEOQ.totalAnnualMinimumInventoryCost());


                //noOfOrders.setText("No of Orders " + String.valueOf(eoq.noOfOrdersCycle()));
                //noOfOrders.setText("No of Orders " + String.valueOf(tempEOQ.noOfProductionRunsPerCycle()));
                //noOfOrders.setVisibility(View.VISIBLE);
                sNoOfOrders="No of Orders " + String.valueOf(tempEOQ.noOfProductionRunsPerCycle());


                //ordersCycle.setText("Orders Cycle " + String.valueOf(eoq.ordersCycleTime()) + " Days");
                //ordersCycle.setText("Orders Cycle " + String.valueOf(tempEOQ.productionRun()) + " Days");
                //ordersCycle.setVisibility(View.VISIBLE);
                sOrdersCycle="Orders Cycle " + String.valueOf(tempEOQ.productionRun()) + " Days";

                //maxInventoryLevel.setText(String.valueOf("Maximum Inventory Level " + tempEOQ.maximumInventoryLevel()));
                //maxInventoryLevel.setVisibility(View.VISIBLE);
                sMaxInventoryLevel=String.valueOf("Maximum Inventory Level " + tempEOQ.maximumInventoryLevel());

                eoq=tempEOQ;
                if(Double.isNaN(optimumOrderSize) || Double.isInfinite(optimumOrderSize))
                {
                    Toast.makeText(getActivity(),"Unrealistic data \n Check the data entered",Toast.LENGTH_LONG).show();
                }
                else    {
                onButtonPressed(eoq);}
            }
            else {
                if(sItemDetail.compareToIgnoreCase("Item Name")==0)
                {
                    eItemDetail.setError("This field is required");
                    eItemDetail.requestFocus();
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
                else if(productionRate==0)
                {
                    prodRate.setError("This field is required");
                    prodRate.requestFocus();
                }
                else
                {
                    eItemDetail.setText("Enter Necessary details");
                    eItemDetail.requestFocus();
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


                        Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
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
        startDate.setText(sdayofmonth+"/"+(smonthofyear+1)+"/"+syear);
        sStartDate=syear+"-"+(smonthofyear + 1)+"-"+sdayofmonth;
        startDate.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), "STArt Date set", Toast.LENGTH_SHORT).show();
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
        endDate.setText(edayofmonth+"/"+(emonthofyear+1)+"/"+eyear);
        sEndDate=eyear+"-"+(emonthofyear + 1)+"-"+edayofmonth;
        endDate.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), "End Date set", Toast.LENGTH_SHORT).show();
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
    @Override
    public void onResume() {
        super.onResume();

        DatePickerDialog dpd = (DatePickerDialog) getActivity().getFragmentManager().findFragmentByTag("Datepickerdialog");
        if(dpd != null) dpd.setOnDateSetListener( new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear + 1) + "/" + year;

                if(sDtPicker==true)
                    setStartDate(year,monthOfYear,dayOfMonth);
                else
                    setEndDate(year, monthOfYear,dayOfMonth);

                Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
            }

        });
    }
}

package mgmt.construction.constructionmgmt.Fragment;

/**
 * Created by Recluse on 10/28/2015.
 */
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.charts.ScatterChart.ScatterShape;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.FileUtils;

import java.util.ArrayList;

public abstract class BaseChartFragment extends Fragment {

    private Typeface tf;

    public BaseChartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected BarData generateBarData(int dataSets, float range, int count) {

        ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();

        for(int i = 0; i < dataSets; i++) {

            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

            //entries = FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "stacked_bars.txt");

            for(int j = 0; j < count; j++) {
                entries.add(new BarEntry((float) (Math.random() * range) + range / 4, j));
            }

            BarDataSet ds = new BarDataSet(entries, getLabel(i));
            ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
            sets.add(ds);
        }

        BarData d = new BarData(ChartData.generateXVals(0, count), sets);
        d.setValueTypeface(tf);
        return d;
    }
    protected BarData generateBarData(int dataSets, float range, int count,ArrayList<BarEntry> ent,ArrayList<String> taskL) {

        ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();
ArrayList<String> xVals=new ArrayList<String>();
        for(int i = 0; i < dataSets; i++) {

            ArrayList<BarEntry> entries = ent;
            xVals=taskL;
            //entries = FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "stacked_bars.txt");

           // for(int j = 0; j < count; j++) {
           //     entries.add(new BarEntry((float) (Math.random() * range) + range / 4, j));
           // }

            BarDataSet ds = new BarDataSet(entries, xVals.get(i));
            ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
            sets.add(ds);
        }

        BarData d = new BarData(ChartData.generateXVals(0, count), sets);
        d.setValueTypeface(tf);
        return d;
    }
    protected ScatterData generateScatterData(int dataSets, float range, int count) {

        ArrayList<ScatterDataSet> sets = new ArrayList<ScatterDataSet>();

        ScatterShape[] shapes = ScatterChart.getAllPossibleShapes();

        for(int i = 0; i < dataSets; i++) {

            ArrayList<Entry> entries = new ArrayList<Entry>();

            for(int j = 0; j < count; j++) {
                entries.add(new Entry((float) (Math.random() * range) + range / 4, j));
            }

            ScatterDataSet ds = new ScatterDataSet(entries, getLabel(i));
            ds.setScatterShapeSize(12f);
            ds.setScatterShape(shapes[i % shapes.length]);
            ds.setColors(ColorTemplate.COLORFUL_COLORS);
            ds.setScatterShapeSize(9f);
            sets.add(ds);
        }

        ScatterData d = new ScatterData(ChartData.generateXVals(0, count), sets);
        d.setValueTypeface(tf);
        return d;
    }

    /**
     * generates less data (1 DataSet, 4 values)
     * @return
     */
    protected PieData generatePieData() {

        int count = 4;

        ArrayList<Entry> entries1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("Ground Floor Interior");
        xVals.add("First Floor Tiling");
        xVals.add("Second Floor Masonry");
        xVals.add("Third Floor Ceiling");

        for(int i = 0; i < count; i++) {
            xVals.add("entry" + (i+1));

            entries1.add(new Entry((int) (Math.random() * 60) + 40, i));
        }

        PieDataSet ds1 = new PieDataSet(entries1, "Man Power");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.BLACK);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(xVals, ds1);
        d.setValueTypeface(tf);

        return d;
    }

    protected PieData generatePieData(ArrayList<String> xVals1,ArrayList<Entry> entries2) {

        //int count = 4;
        Log.v("PIECHART", "IN PIEDATA");
        ArrayList<Entry> entries1 = entries2;
        ArrayList<String> xVals = xVals1;

        /*xVals.add("Ground Floor Interior");
        xVals.add("First Floor Tiling");
        xVals.add("Second Floor Masonry");
        xVals.add("Third Floor Ceiling");

        for(int i = 0; i < count; i++) {
            xVals.add("entry" + (i+1));

            entries1.add(new Entry((int) (Math.random() * 60) + 40, i));
        }*/
        Log.v("PIECHART", "IN PIEDATA" + entries1.size() + " " + xVals.size());
        PieDataSet ds1 = new PieDataSet(entries1, "Inv. Cost");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.BLACK);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(xVals, ds1);
        d.setValueTypeface(tf);

        return d;
    }

    protected LineData generateLineData() {

//        DataSet ds1 = new DataSet(n, "O(n)");
//        DataSet ds2 = new DataSet(nlogn, "O(nlogn)");
//        DataSet ds3 = new DataSet(nsquare, "O(n\u00B2)");
//        DataSet ds4 = new DataSet(nthree, "O(n\u00B3)");

        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();

        LineDataSet ds1 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "sine.txt"), "Sine function");
        LineDataSet ds2 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "cosine.txt"), "Cosine function");

        ds1.setLineWidth(2f);
        ds2.setLineWidth(2f);

        ds1.setDrawCircles(false);
        ds2.setDrawCircles(false);

        ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);

        // load DataSets from textfiles in assets folders
        sets.add(ds1);
        sets.add(ds2);

//        sets.add(FileUtils.dataSetFromAssets(getActivity().getAssets(), "n.txt"));
//        sets.add(FileUtils.dataSetFromAssets(getActivity().getAssets(), "nlogn.txt"));
//        sets.add(FileUtils.dataSetFromAssets(getActivity().getAssets(), "square.txt"));
//        sets.add(FileUtils.dataSetFromAssets(getActivity().getAssets(), "three.txt"));

        int max = Math.max(sets.get(0).getEntryCount(), sets.get(1).getEntryCount());

        LineData d = new LineData(ChartData.generateXVals(0, max),  sets);
        d.setValueTypeface(tf);
        return d;
    }

    protected LineData getComplexity() {

        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();

      /*  LineDataSet ds1 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "n.txt"), "Estimate");
        LineDataSet ds2 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "nlogn.txt"), "Actual");
        //LineDataSet ds3 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "square.txt"), "O(n\u00B2)");
      //  LineDataSet ds4 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "three.txt"), "O(n\u00B3)");

        ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
       // ds3.setColor(ColorTemplate.VORDIPLOM_COLORS[2]);
       // ds4.setColor(ColorTemplate.VORDIPLOM_COLORS[3]);

        ds1.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        ds2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[1]);
      //  ds3.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[2]);
       // ds4.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[3]);

        ds1.setLineWidth(2.5f);
        ds1.setCircleSize(3f);
        ds2.setLineWidth(2.5f);
        ds2.setCircleSize(3f);
        //ds3.setLineWidth(2.5f);
      //  ds3.setCircleSize(3f);
      //  ds4.setLineWidth(2.5f);
      //  ds4.setCircleSize(3f);


        // load DataSets from textfiles in assets folders
        sets.add(ds1);
        sets.add(ds2);
       // sets.add(ds3);
       // sets.add(ds4);

       LineData d = new LineData(ChartData.generateXVals(0, ds1.getEntryCount()), sets);
        d.setValueTypeface(tf);
*/

        // creating list of entry
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));
        ArrayList<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(0f, 0));
        entries1.add(new Entry(2f, 1));
        entries1.add(new Entry(10f, 2));
        LineDataSet dataset = new LineDataSet(entries, "# of Calls");
        dataset.setDrawCircles(false);
        dataset.setDrawCubic(true);
        dataset.setDrawValues(false);
        LineDataSet dataset1 = new LineDataSet(entries1, "# of Calls");
        dataset1.setDrawCircleHole(false);
        dataset1.setDrawCircles(false);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        // creating labels
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        LineData d = new LineData(labels, dataset);
        d.addDataSet(dataset1);
        d.setValueTypeface(tf);
        return d;
    }
    protected LineData getComplexity(ArrayList<Entry> entries,ArrayList<String> labels,ArrayList<Entry> actualentries,ArrayList<Entry> eventries) {

        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();

        LineDataSet dataset = new LineDataSet(entries, "Estimate");
        dataset.setDrawCircles(false);
        dataset.setDrawCubic(false);
        dataset.setDrawValues(false);
        dataset.setColor(ColorTemplate.VORDIPLOM_COLORS[4]);
        dataset.setLineWidth(2);

        //dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        //dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        // creating labels
        LineDataSet actualdataset = new LineDataSet(actualentries, "Actual Cost");
        actualdataset.setDrawCircles(false);
        actualdataset.setDrawCubic(false);
        actualdataset.setDrawValues(false);
        actualdataset.setColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        actualdataset.setLineWidth(2);
       // actualdataset.setColor(Color.BLUE);

        //actualdataset.setColors(ColorTemplate.VORDIPLOM_COLORS);

       LineDataSet evdataset = new LineDataSet(eventries, "Earned Value");
        evdataset.setDrawCircles(false);
        evdataset.setDrawCubic(false);
        evdataset.setDrawValues(false);
        evdataset.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        evdataset.setLineWidth(2);


        LineData d = new LineData(labels, dataset);

        d.addDataSet(actualdataset);
        d.addDataSet(evdataset);
        d.setValueTypeface(tf);
        return d;
    }
    private String[] mLabels = new String[] { "Ground Floor Interior", "First Floor Tiling", "Second Floor Masonry", "Third Floor Ceiling", "Company E", "Company F" };
//    private String[] mXVals = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec" };

    private String getLabel(int i) {
        return mLabels[i];
    }
}
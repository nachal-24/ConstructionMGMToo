package mgmt.construction.constructionmgmt.Fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import mgmt.construction.constructionmgmt.R;

/**
 * Created by Recluse on 2/7/2016.
 */
public class TaskProgressDialogFragment extends DialogFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static TaskProgressDialogFragment newInstance(String taskName,String percent) {
        TaskProgressDialogFragment fragment = new TaskProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, taskName);
        args.putString(ARG_PARAM2, percent);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_progress_dialog, container, false);
        getDialog().setTitle("Work Progress");
        Button dismiss = (Button) rootView.findViewById(R.id.dismiss);
        String taskName=getArguments().getString(ARG_PARAM1);
        String percent=getArguments().getString(ARG_PARAM2);
        TextView tTextName=(TextView)rootView.findViewById(R.id.task_name);
        TextView tPercent=(TextView)rootView.findViewById(R.id.complete);
        ImageView img=(ImageView) rootView.findViewById(R.id.image);
        String nameCapitalized="";
        if(taskName.compareToIgnoreCase("EVM")==0) {
            img.setVisibility(View.GONE);
            nameCapitalized="";
            getDialog().setTitle("EVM");
        }
        else
        {
            String s1 = taskName.substring(0, 1).toUpperCase();
            nameCapitalized = s1 + taskName.substring(1);
        }


        tTextName.setText(nameCapitalized);

        tPercent.setText(percent);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;
    }
}

package mgmt.construction.constructionmgmt.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mgmt.construction.constructionmgmt.R;

/**
 * Created by Recluse on 10/22/2015.
 */
public class RootActivity extends AppCompatActivity {
    int onStartCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.animator.anim_slide_in_left,
                    R.animator.anim_slide_out_left);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.animator.anim_slide_in_right,
                    R.animator.anim_slide_out_right);

        } else if (onStartCount == 1) {
            onStartCount++;
        }

    }

}
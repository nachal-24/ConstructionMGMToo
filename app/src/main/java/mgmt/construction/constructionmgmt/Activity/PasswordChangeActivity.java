package mgmt.construction.constructionmgmt.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import mgmt.construction.constructionmgmt.Classes.AppController;
import mgmt.construction.constructionmgmt.Classes.JSONObjectRequest;
import mgmt.construction.constructionmgmt.Classes.PreferencesHelper;
import mgmt.construction.constructionmgmt.Classes.User;
import mgmt.construction.constructionmgmt.R;

public class PasswordChangeActivity extends RootActivity {
    DrawerLayout drawerLayout;
    TextView topic;
    EditText tPass;
    EditText tConfPass;
    String password;
    String cPassword;
    Button bChangePass;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        topic=(TextView) findViewById(R.id.change_pass);
        tPass=(EditText) findViewById(R.id.password);
        tConfPass=(EditText) findViewById(R.id.confirm_password);

        bChangePass=(Button) findViewById(R.id.email_sign_in_button);

        setupToolbar();
        setupNavigationView();


        bChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password=tPass.getText().toString();
                cPassword=tConfPass.getText().toString();
                if (password == null || password.compareToIgnoreCase("") == 0) {
                    tPass.setError("This field is required");
                    tPass.requestFocus();
                } else if (cPassword == null || cPassword.compareToIgnoreCase("") == 0) {
                    tConfPass.setError("This field is required");
                    tConfPass.requestFocus();
                } else {
                    if (password.compareTo(cPassword) == 0)
                        updatePassword();
                    else
                        Toast.makeText(getApplicationContext(), "Password and Confirm Password Not Matching", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void updatePassword()
    {
        User user=PreferencesHelper.getUser(this);
        String email="";
        if(user!=null)
        email=user.getFirstName();
Log.v("UPDATEEMAIL",email);
        String tag_json_obj = "json_obj_req";
        String url = "http://alamaari.com/construction/update_password.php";
        Map<String, String> params;

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);


        //JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
        JSONObjectRequest jsonObjReq = new JSONObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.v("VOLLEYUPDATE", response.toString());
                            // Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
if(response.getInt("success")==1) {
    topic.setText("Updated Successfully");
    tPass.setVisibility(View.GONE);
    tConfPass.setVisibility(View.GONE);
    bChangePass.setVisibility(View.GONE);
    pDialog.hide();
}
                            else {
    topic.setText("Oops!");
    tPass.setVisibility(View.GONE);
    tConfPass.setVisibility(View.GONE);
    bChangePass.setVisibility(View.GONE);
    pDialog.hide();
}
                        }
                        catch (JSONException e)
                        {Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();}
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
                //Log.v("VOLLEY", error.getMessage());
                if(error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "No internet Access, Check your internet connection.", Toast.LENGTH_LONG).show();}
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
    private void setupToolbar(){
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setTitle("Change Password");
        ab.setDisplayUseLogoEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);

    }
    private void setupNavigationView(){

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.sign_out:
                PreferencesHelper.signOut(this);
                Intent intent = new Intent(this, LoginActivity.class);
                // intent.putExtra("finish", true); // if you are checking for this in your other Activities
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}

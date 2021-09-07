package mgmt.construction.constructionmgmt.Activity;

import android.app.ProgressDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.content.Intent;

import android.os.Bundle;

import mgmt.construction.constructionmgmt.Fragment.LoginFragment;
import mgmt.construction.constructionmgmt.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends RootActivity {




    // UI references.
    DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ProgressDialog pDialog;
    // single product url
    private static final String url_product_detials = "http://alamaari.com/construction/get_password.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USERLOGIN = "userlogin";
    private static final String TAG_PID = "pid";
    private static final String TAG_ID = "username";
    private static final String TAG_PASSWORD = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        // Set up the login form.


        //setupNavigationView();
        setupToolbar();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentParentViewGroup, new LoginFragment())
                    .commit();
        }
    }
    private void setupToolbar(){
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);

        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowTitleEnabled(true);
        // ab.setLogo(R.drawable.ic_launcher);
        //ab.setIcon(R.drawable.ic_launcher);
        ab.setDisplayHomeAsUpEnabled(true);


    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(drawerLayout != null)
                    drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    /*private void setupNavigationView() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.EOQ:
                        //Toast.makeText(getApplicationContext(), "Inbox Selected", Toast.LENGTH_SHORT).show();
                        Intent taskIntent =
                                new Intent(getApplicationContext(), EconomicOrderQuantityActivity.class);
                        startActivity(taskIntent);
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.NonInstantEOQ:
                        //Toast.makeText(getApplicationContext(),"Stared Selected",Toast.LENGTH_SHORT).show();
                        Intent nonInstantEOQ =
                                new Intent(getApplicationContext(), NonInstantEOQActivity.class);
                        startActivity(nonInstantEOQ);
                        return true;
                    case R.id.sent_mail:
                        Toast.makeText(getApplicationContext(), "Send Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drafts:
                        Toast.makeText(getApplicationContext(), "Drafts Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.allmail:
                        Toast.makeText(getApplicationContext(), "All Mail Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.trash:
                        Toast.makeText(getApplicationContext(), "Trash Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.sign_out:
                        finish();
                        //Toast.makeText(getApplicationContext(),"Spam Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();






    }

*/


    public void successStatus()
    {
        Intent currentstatus =
                new Intent(getApplicationContext(), CurrentStatusActivity.class);
        startActivity(currentstatus);
        finish();
       // getSupportFragmentManager().beginTransaction()
        //        .replace(R.id.fragmentParentViewGroup, new CurrentStatusFragment())
        //        .commit();

    }

/*
public void status()
{
    new GetProductDetails("abc@abc.com", "abc123").execute((Void) null);
    /**
     * Background Async Task to Get complete product details
     * */

//}
 /*   class GetProductDetails extends AsyncTask<Void, Void, String> {

        private final String mEmail;
        private final String mPassword;

        GetProductDetails(String email, String password) {
            mEmail =email ;
            mPassword =password ;
        }
        /**
         * Before starting background thread Show Progress Dialog
         **/
     /*   @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getApplicationContext());
            pDialog.setMessage("Loading product details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         *
        protected String doInBackground(Void... params) {

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("username", mEmail));

                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONParser jsonParser = new JSONParser();
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_product_detials, "GET", params);

                        // check your log for json response
                        Log.d("Single Product Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received product details
                            JSONArray productObj = json
                                    .getJSONArray(TAG_USERLOGIN); // JSON Array

                            // get first product object from JSON Array
                            JSONObject product = productObj.getJSONObject(0);

                            // product with this pid found
                            Toast.makeText(getApplicationContext(), product.getString(TAG_USERLOGIN) + " " + product.getString(TAG_PASSWORD), Toast.LENGTH_SHORT).show();

                        } else {
                            // product with pid not found
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
            //showProgress(false);
            //mTask=null;
        }
    }*/
}


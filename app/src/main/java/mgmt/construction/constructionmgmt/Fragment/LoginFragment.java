package mgmt.construction.constructionmgmt.Fragment;

/**
 * Created by Recluse on 10/26/2015.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TooManyListenersException;
import mgmt.construction.constructionmgmt.Classes.PreferencesHelper;
import mgmt.construction.constructionmgmt.Activity.LoginActivity;
import mgmt.construction.constructionmgmt.Classes.JSONParser;
import mgmt.construction.constructionmgmt.Classes.User;
import mgmt.construction.constructionmgmt.R;

import static android.Manifest.permission.READ_CONTACTS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USERLOGIN = "product";
    private static final String TAG_PID = "pid";
    private static final String TAG_ID = "username";
    private static final String TAG_PASSWORD = "password";

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "abc@abc.com:abc123", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    //private GetProductDetails mTask=null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    DrawerLayout drawerLayout;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    String pid;

    // Progress Dialog
    private ProgressDialog pDialog;
    // single product url
    private static final String url_product_detials = "http://ivapapps.16mb.com/get_password.php";


    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.activity_login, parentViewGroup, false);
            mEmailView = (AutoCompleteTextView) rootView.findViewById(R.id.email);
            populateAutoComplete();
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
       // StrictMode.setThreadPolicy(policy);
            mPasswordView = (EditText) rootView.findViewById(R.id.password);
            Log.e("DEBUG", "onCreateView of LoginFragment");
            if(mPasswordView.getText().toString()!=null || mPasswordView.getText().toString().length()!=0)
            {
                mPasswordView.setText("");
            }
            mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
                        attemptLogin();
                        return true;
                    }
                    return false;
                }
            });

            Button mEmailSignInButton = (Button) rootView.findViewById(R.id.email_sign_in_button);
            mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptLogin();
                }
            });

            mLoginFormView = rootView.findViewById(R.id.login_form);
            mProgressView = rootView.findViewById(R.id.login_progress);
            return rootView;
        }


    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }


    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (getActivity().checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            //mAuthTask = new UserLoginTask(email, password);
            //mAuthTask.execute((Void) null);


          //commented for tesing  new GetAsync().execute(email, password);

            successStatus();


           // mTask = new GetProductDetails(email, password);
           // Log.v("USERLOGIN",email);
           // mTask.execute((Void) null);


        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new android.support.v4.content.CursorLoader(getActivity(),
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }



    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }



    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
       // ArrayAdapter<String> adapter =
     //           new ArrayAdapter<>(this,getActivity().getApplicationContext(),
      //                  android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

      //  mEmailView.setAdapter(adapter);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);


            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {

                successStatus();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
    private void successStatus()
    {
        ((LoginActivity)getActivity()).successStatus();

    }
    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of LoginFragment");
        if(mPasswordView.getText().toString()!=null || mPasswordView.getText().toString().length()!=0)
        {
            mPasswordView.setText("");
        }
        super.onResume();
    }
    /**
     * Background Async Task to Get complete product details
     *
    class GetProductDetails extends AsyncTask<Void, Void, String> {

        private final String mEmail;
        private final String mPassword;

        GetProductDetails(String email, String password) {
            mEmail = email;
            mPassword = password;
        }
        /**
         * Before starting background thread Show Progress Dialog
         *
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading product details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
*/
        /**
         * Getting product details in background thread
         **
        protected String doInBackground(Void... params) {

            // updating UI from Background Thread
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("username", mEmail));
                        //Log.v("USERLOGIN",mEmail+" "+mPassword);
                        // getting product details by making HTTP request
                        // Note that product details url will use GET request

                            JSONObject json = jsonParser.makeHttpRequest(
                                    url_product_detials, "GET", params);

                            // check your log for json response
                            //Log.v("USERLOGIN", json.toString());

                            // json success tag
                            success = json.getInt(TAG_SUCCESS);
                            if (success == 1) {
                                // successfully received product details
                                JSONArray productObj = json
                                        .getJSONArray(TAG_USERLOGIN); // JSON Array

                                // get first product object from JSON Array
                                JSONObject product = productObj.getJSONObject(0);

                                // product with this pid found
                                //Toast.makeText(getActivity().getApplicationContext(), product.getString(TAG_USERLOGIN)+" "+product.getString(TAG_PASSWORD), Toast.LENGTH_SHORT).show();
                                // Log.v("USERLOGIN",product.getString(TAG_USERLOGIN)+" "+product.getString(TAG_PASSWORD));

                        } else {
                            // product with pid not found
                        }
                    } catch (JSONException e) {
                        Log.v("USERLOGIN",e.getMessage());
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
            showProgress(false);
            mTask=null;
        }
    }*/
        class GetAsync extends AsyncTask<String, String, JSONObject> {

            JSONParser jsonParser = new JSONParser();

            private ProgressDialog pDialog;

            private static final String LOGIN_URL = "http://ivapapps.16mb.com/get_new_password.php";
            //private static final String LOGIN_URL = "http://10.0.2.2/get_new_password.php";

            private static final String TAG_SUCCESS = "success";
            private static final String TAG_MESSAGE = "message";
            private String userId;
            private String password;

            @Override
            protected void onPreExecute() {
               /* pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Attempting login...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();*/
                showProgress(true);
            }

            @Override
            protected JSONObject doInBackground(String... args) {

                try {

                    HashMap<String, String> params = new HashMap<>();
                    params.put("name", args[0]);
                    params.put("password", args[1]);

                    userId=args[0];
                    password=args[1];

                    //int  = Log.d("request", "starting");

                    JSONObject json = jsonParser.makeHttpRequest(
                            LOGIN_URL, "GET", params);

                    if (json != null) {
                        Log.d("JSON result", json.toString());

                        return json;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            protected void onPostExecute(JSONObject json) {

                int success = 0;
                int userAuth=3;
                String message = "";

              //  if (pDialog != null && pDialog.isShowing()) {
              //      pDialog.dismiss();
               // }

                if (json != null) {
                   // Toast.makeText(getActivity(), json.toString(),
                           // Toast.LENGTH_LONG).show();

                    try {
                        success = json.getInt(TAG_SUCCESS);
                        message = json.getString(TAG_MESSAGE);
                        // json success tag
                        if (success == 1) {
                            // successfully received product details
                            JSONArray productObj = json
                                    .getJSONArray(TAG_USERLOGIN); // JSON Array

                            // get first product object from JSON Array
                            JSONObject product = productObj.getJSONObject(0);

                            // product with this pid found
                            Log.v("USERLOGIN", product.getString(TAG_ID) + " " + product.getString(TAG_PASSWORD));
                            //textTopic.setText(product.getString(TAG_PASSWORD));
                            String passwordDB=product.getString(TAG_PASSWORD);
                            if(passwordDB.compareTo(password)==0) {
                                userAuth=0;
                                Log.v("SUCCESSTATUS",mEmailView.getText().toString());
                                User user = new User(mEmailView.getText().toString(), mEmailView.getText().toString());
                                PreferencesHelper.writeToPreferences(getActivity(), user);
                                Toast.makeText(getActivity(), message,
                                        Toast.LENGTH_LONG).show();
                            }
                            else{
                                userAuth=1;
                                Toast.makeText(getActivity(),"Incorrect Password" ,
                                        Toast.LENGTH_LONG).show();
                            }

                        } else {
                            userAuth=2;
                            // product with pid not found
                            //textTopic.setText("NOT FOUND");
                            Log.v("USERLOGIN","NOT FOUND");
                            Toast.makeText(getActivity(),"Incorrect UserID" ,
                                    Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (userAuth==0) {
                    successStatus();
                } else if(userAuth==1) {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }
                else if(userAuth==2) {
                    mEmailView.setError(getString(R.string.error_invalid_email));
                    mEmailView.requestFocus();
                }
                else{
                    if(message.compareTo("")==0){
                    Toast.makeText(getActivity(), "Connection Error.\nCheck your Internet Connection",
                            Toast.LENGTH_LONG).show();}
                    else{
                        Toast.makeText(getActivity(), message,
                                Toast.LENGTH_LONG).show();
                    }
                }
                if (success == 1) {
                    Log.d("Success!", message);
                }else{
                    Log.d("Failure", message);
                }
                showProgress(false);
            }

        }
}


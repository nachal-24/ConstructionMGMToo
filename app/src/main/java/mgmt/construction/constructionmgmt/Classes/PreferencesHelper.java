/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mgmt.construction.constructionmgmt.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;



/**
 * Easy storage and retrieval of preferences.
 */
public class PreferencesHelper {

    private static final String USER_PREFERENCES = "userPreferences";
    private static final String PREFERENCE_FIRST_NAME = USER_PREFERENCES + ".firstName";
    private static final String PREFERENCE_LAST_INITIAL = USER_PREFERENCES + ".lastInitial";
    private PreferencesHelper() {
        //no instance
    }

    /**
     * Writes a {@link User} to preferences.
     *
     * @param context The Context which to obtain the SharedPreferences from.
     * @param user  The {@link User} to write.
     */
    public static void writeToPreferences(Context context, User user) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(PREFERENCE_FIRST_NAME, user.getFirstName());
        editor.putString(PREFERENCE_LAST_INITIAL, user.getLastInitial());
        editor.apply();
    }

    /**
     * Retrieves a {@link User} from preferences.
     *
     * @param context The Context which to obtain the SharedPreferences from.
     * @return A previously saved USER or <code>null</code> if none was saved previously.
     */
    public static User getUser(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        final String firstName = preferences.getString(PREFERENCE_FIRST_NAME, null);
        final String lastInitial = preferences.getString(PREFERENCE_LAST_INITIAL, null);


        if (null == firstName || null == lastInitial) {
            return null;
        }
        return new User(firstName, lastInitial);
    }

    /**
     * Signs out a USER by removing all it's data.
     *
     * @param context The context which to obtain the SharedPreferences from.
     */
    public static void signOut(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(PREFERENCE_FIRST_NAME);
        editor.remove(PREFERENCE_LAST_INITIAL);
        editor.apply();
    }

    /**
     * Checks whether a USER is currently signed in.
     *
     * @param context The context to check this in.
     * @return <code>true</code> if login data exists, else <code>false</code>.
     */
    public static boolean isSignedIn(Context context) {
        final SharedPreferences preferences = getSharedPreferences(context);
        return preferences.contains(PREFERENCE_FIRST_NAME) &&
                preferences.contains(PREFERENCE_LAST_INITIAL) ;
    }

    /**
     * Checks whether the USER's input data is valid.
     *
     * @param firstName   The USER's first name to be examined.
     * @param lastInitial The USER's last initial to be examined.
     * @return <code>true</code> if both strings are not null nor 0-length, else <code>false</code>.
     */
    public static boolean isInputDataValid(CharSequence firstName, CharSequence lastInitial) {
        return !TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastInitial);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }

}

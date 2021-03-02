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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Stores values to identify the subject that is currently attempting to solve quizzes.
 */
public class User implements Parcelable {

        public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private final String mFirstName;
    private final String mLastInitial;


    public User(String firstName, String lastInitial) {
        mFirstName = firstName;
        mLastInitial = lastInitial;

    }

    protected User(Parcel in) {
        mFirstName = in.readString();
        mLastInitial = in.readString();

    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastInitial() {
        return mLastInitial;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFirstName);
        dest.writeString(mLastInitial);

    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (!mFirstName.equals(user.mFirstName)) {
            return false;
        }
        if (!mLastInitial.equals(user.mLastInitial)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = mFirstName.hashCode();
        result = 31 * result + mLastInitial.hashCode();
        return result;
    }
}

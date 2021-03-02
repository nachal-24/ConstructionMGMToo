package mgmt.construction.constructionmgmt.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Recluse on 2/2/2016.
 */
public class Group implements Parcelable {

    private String Name;
    private ArrayList<Child> Items;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public ArrayList<Child> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Child> Items) {
        this.Items = Items;
    }
    public Group(){}
    public Group(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Group> CREATOR = new Creator<Group>() {
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        public Group[] newArray(int size) {

            return new Group[size];
        }

    };

    public void readFromParcel(Parcel in) {
        this.Name = in.readString();
        this.Items = in.readArrayList(null);


    }
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        try {
            dest.writeString(this.Name);
            dest.writeList(this.Items);
        }
    catch (   Exception e){}
    }

}
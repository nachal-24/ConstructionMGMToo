package mgmt.construction.constructionmgmt.Classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Recluse on 2/2/2016.
 */
public class Child implements Parcelable {

    private String Name;
    private String Id;
    private String Type;
    private String Status;

    public Child(){}
    public Child(Parcel in) {
        super();
        readFromParcel(in);
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStatus() {
        return Status;
    }

    public String getType() {
        return Type;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setType(String type) {
        Type = type;
    }

    public void readFromParcel(Parcel in) {
        this.Name = in.readString();
        this.Id = in.readString();
        this.Type = in.readString();
        this.Status = in.readString();
    }

    public static final Parcelable.Creator<Child> CREATOR = new Creator<Child>() {
        public Child createFromParcel(Parcel in) {
            return new Child(in);
        }

        public Child[] newArray(int size) {

            return new Child[size];
        }

    };
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        try {
            dest.writeString(this.Name);
            dest.writeString(this.Id);
            dest.writeString(this.Type);
            dest.writeString(this.Status);
        }
        catch (   Exception e){}
    }
}
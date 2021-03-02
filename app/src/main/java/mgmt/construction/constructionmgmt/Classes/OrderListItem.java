package mgmt.construction.constructionmgmt.Classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Recluse on 2/8/2016.
 */
public class OrderListItem implements Parcelable{
    String id;
    String content;
    String details;
    public OrderListItem(String id, String content, String details) {
        this.id = id;
        this.content = content;
        this.details = details;
    }

       public OrderListItem(Parcel in) {
            super();
            readFromParcel(in);
        }

        public static final Parcelable.Creator<OrderListItem> CREATOR = new Parcelable.Creator<OrderListItem>() {
            public OrderListItem createFromParcel(Parcel in) {
                return new OrderListItem(in);
            }

            public OrderListItem[] newArray(int size) {

                return new OrderListItem[size];
            }

        };

        public void readFromParcel(Parcel in) {
            this.id = in.readString();
            this.content = in.readString();
            this.details = in.readString();

        }
        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.content);
            dest.writeString(this.details);
        }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getDetails() {
        return details;
    }
}





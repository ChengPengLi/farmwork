package com.ky3h.farmwork.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class YueYaoItem implements Parcelable{
    public final static int NOT_ORDERED = 0;
    public final static int NOT_DOWNLOAD = 1;
    public final static int DOWNLOADING = 2;
    public final static int STOP = 3;
    public final static int PLAYING = 4;
    public final static int NOT_PAY = 5;

    private int id;
    private String title;
    private String source;
    private String price;
    private long orderDate;
    private String localStoragePath;

    private int downloadedSize;
    private int totalSize;
    private int status;

    public YueYaoItem() {
    }
    
    @Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel pa, int flags) {
		pa.writeInt(id);
		pa.writeString(title);
		pa.writeString(source);
		pa.writeString(price);
		pa.writeLong(orderDate);
		pa.writeString(localStoragePath);
	}
	
	public YueYaoItem(Parcel in){
		id = in.readInt();
		title = in.readString();
		source = in.readString();
		price = in.readString();
		orderDate = in.readLong();
		localStoragePath = in.readString();
		
	}

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getDownloadedSize() {
        return downloadedSize;
    }

    public void setDownloadedSize(int downloadedSize) {
        this.downloadedSize = downloadedSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public String getLocalStoragePath() {
        return localStoragePath;
    }

    public void setLocalStoragePath(String localStoragePath) {
        this.localStoragePath = localStoragePath;
    }

    @Override
    public String toString() {
        return "YueYaoItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", price='" + price + '\'' +
                ", orderDate=" + orderDate +
                ", localStoragePath='" + localStoragePath + '\'' +
                ", downloadedSize=" + downloadedSize +
                ", totalSize=" + totalSize +
                ", status=" + status +
                '}';
    }
}

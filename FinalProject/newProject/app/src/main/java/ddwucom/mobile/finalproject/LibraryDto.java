package ddwucom.mobile.finalproject;

import android.text.Html;
import android.text.Spanned;

import androidx.appcompat.app.AppCompatActivity;

public class LibraryDto extends AppCompatActivity {
    private int _id;
    private String libCode;
    private String libName;
    private String address;
    private String tel;
    private String homepage;
    private String operatingTime;
    private String closed;
    private String xcnts;
    private String ydnts;

    public String getXcnts() {
        return xcnts;
    }

    public void setXcnts(String xcnts) {
        this.xcnts = xcnts;
    }

    public String getYdnts() {
        return ydnts;
    }

    public void setYdnts(String ydnts) {
        this.ydnts = ydnts;
    }

    public LibraryDto() {

    }

    public LibraryDto(String libCode, String libName, String address, String tel, String homepage, String operatingTime, String closed) {
        this.libCode = libCode;
        this.libName = libName;
        this.address = address;
        this.tel = tel;
        this.homepage = homepage;
        this.operatingTime = operatingTime;
        this.closed = closed;
    }

    public LibraryDto(String libCode, String libName, String address, String tel, String homepage, String operatingTime, String closed, String xcnts, String ydnts) {
        this.libCode = libCode;
        this.libName = libName;
        this.address = address;
        this.tel = tel;
        this.homepage = homepage;
        this.operatingTime = operatingTime;
        this.closed = closed;
        this.xcnts = xcnts;
        this.ydnts = ydnts;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getLibCode() {
        return libCode;
    }

    public void setLibCode(String libCode) {
        this.libCode = libCode;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(String operatingTime) {
        this.operatingTime = operatingTime;
    }

    public String getClosed() {
        Spanned spanned = Html.fromHtml(closed);
        return spanned.toString();
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }
}

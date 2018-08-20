package ziaetaiba.com.zia_e_magazine.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PreviousMagazineData_Model {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("thumbnailPath")
    @Expose
    private String thumbnailPath;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}

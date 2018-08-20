package ziaetaiba.com.zia_e_magazine.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product_Model {

    @SerializedName("appName")
    @Expose
    private String appName;
    @SerializedName("Details")
    @Expose
    private List<HomeData_Model> details = null;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<HomeData_Model> getDetails() {
        return details;
    }

    public void setDetails(List<HomeData_Model> details) {
        this.details = details;
    }
}

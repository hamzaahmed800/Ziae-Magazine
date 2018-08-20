package ziaetaiba.com.zia_e_magazine.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HAMI on 01/08/2018.
 */

public class Home_Model {

    @SerializedName("appName")
    @Expose
    private String appName;
    @SerializedName("Products Details")
    @Expose
    private List<HomeData_Model> productsDetails = null;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<HomeData_Model> getProductsDetails() {
        return productsDetails;
    }

    public void setProductsDetails(List<HomeData_Model> productsDetails) {
        this.productsDetails = productsDetails;
    }
}

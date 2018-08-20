package ziaetaiba.com.zia_e_magazine.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HAMI on 07/08/2018.
 */

public class PreviousMagazine_Model {

    @SerializedName("appName")
    @Expose
    private String appName;
    @SerializedName("Yearly")
    @Expose
    private List<PreviousMagazineData_Model> yearly = null;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<PreviousMagazineData_Model> getYearly() {
        return yearly;
    }

    public void setYearly(List<PreviousMagazineData_Model> yearly) {
        this.yearly = yearly;
    }


}

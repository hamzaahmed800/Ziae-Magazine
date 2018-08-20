package ziaetaiba.com.zia_e_magazine.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Menu_Model {

    @SerializedName("appName")
    @Expose
    private String appName;
    @SerializedName("Menus")
    @Expose
    private List<MenuData_Model> menus = null;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<MenuData_Model> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuData_Model> menus) {
        this.menus = menus;
    }

}

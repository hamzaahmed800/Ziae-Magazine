package ziaetaiba.com.zia_e_magazine.Models;

/**
 * Created by HAMI on 18/09/2018.
 */

public class ShareModel {

    private String name;
    private String description;

    public ShareModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

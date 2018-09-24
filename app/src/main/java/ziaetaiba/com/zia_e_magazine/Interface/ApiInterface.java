package ziaetaiba.com.zia_e_magazine.Interface;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ziaetaiba.com.zia_e_magazine.API.Api_URLS;
import ziaetaiba.com.zia_e_magazine.Models.Home_Model;
import ziaetaiba.com.zia_e_magazine.Models.Menu_Model;
import ziaetaiba.com.zia_e_magazine.Models.PreviousMagazine_Model;
import ziaetaiba.com.zia_e_magazine.Models.Product_Model;

/**
 * Created by HAMI on 17/07/2018.
 */

public interface ApiInterface {

    @GET(Api_URLS.PACKAGE_API + Api_URLS.MENU_API + Api_URLS.EXTENSION_API)
    Call<Menu_Model> getMenu(@Query("lang")String language);
    @GET(Api_URLS.PACKAGE_API + Api_URLS.MENU_API + Api_URLS.EXTENSION_API)
    Call<Menu_Model> getMenu(@Query("lang")String language,@Query("year")String year,@Query("month")String month);
    @GET(Api_URLS.PACKAGE_API + Api_URLS.HOMEDATA_API + Api_URLS.EXTENSION_API)
    Call<Home_Model> getHomeData(@Query("lang") String language,@Query("year") String s,@Query("month") String s1);
    @GET(Api_URLS.PACKAGE_API + Api_URLS.PREVIOUS_MAGAZINE + Api_URLS.EXTENSION_API)
    Call<PreviousMagazine_Model> getPreviousMagazine(@Query("lang") String language, @Query("year") String s, @Query("month") String s1);
    @GET(Api_URLS.PACKAGE_API + Api_URLS.PRODUCT_DETAILS + Api_URLS.EXTENSION_API)
    Call<Product_Model> getDetails(@Query("lang")String language,@Query("year") String year,@Query("month") String month,@Query("slug") String mTab);


}

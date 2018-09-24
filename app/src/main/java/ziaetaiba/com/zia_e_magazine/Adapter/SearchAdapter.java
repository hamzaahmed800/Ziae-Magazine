package ziaetaiba.com.zia_e_magazine.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ziaetaiba.com.zia_e_magazine.Calls.homedetailListener;
import ziaetaiba.com.zia_e_magazine.Fragments.SearchFragment;
import ziaetaiba.com.zia_e_magazine.Globals.Constants;
import ziaetaiba.com.zia_e_magazine.Models.HomeData_Model;
import ziaetaiba.com.zia_e_magazine.R;

/**
 * Created by HAMI on 16/09/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private List<HomeData_Model> homeDataModels;
    private float textSize = 20;
    private homedetailListener homedetailListener;


    public SearchAdapter(Context context,List<HomeData_Model> homeDataModels){

        this.context = context;
        this.homeDataModels = homeDataModels;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_search_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(homeDataModels.get(position).getDescription().length() > 0 && !homeDataModels.get(position).getId().equals("")
                                                                      &&  !homeDataModels.get(position).getName().equals("")){
            String textString = String.valueOf(Html.fromHtml(homeDataModels.get(position).getDescription()));
            String modifiedString = null;
            int start = textString.indexOf(SearchFragment.searchString) - 25;
            int end =   textString.indexOf(SearchFragment.searchString) + 25;

            if(start < 0 && end < 0){
                modifiedString = textString.substring(textString.indexOf(SearchFragment.searchString),
                                                      textString.lastIndexOf(SearchFragment.searchString));
            }else if(start < 0 && end > 0){
                modifiedString = textString.substring(textString.indexOf(SearchFragment.searchString),end);
            }else if(start > 0 && end < 0){
                modifiedString = textString.substring(start,
                        textString.lastIndexOf(SearchFragment.searchString));
            }else if(start > 0 && end > 0 && end < textString.length()){
                modifiedString = textString.substring(start,end);
            }

            if(modifiedString != null){
                String textToHighlight = SearchFragment.searchString;
                String replacedWith = "<font color='yellow'>" + textToHighlight + "</font>";
                String modString = modifiedString.replaceAll(textToHighlight,replacedWith);
                holder.searchDescription.setText(Html.fromHtml("..."+modString+"..."));

            }

        }
//        else{
//            holder.searchDescription.setText(homeDataModels.get(position).getDescription());
//        }

    }

    @Override
    public int getItemCount() {
        if(homeDataModels.size() > 0){
            return homeDataModels.size();
        }
        return 0;
    }

    public void setItemClickListener(homedetailListener homedetailListener) {
        this.homedetailListener = homedetailListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final Typeface DescriptionFont;
        TextView searchDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            searchDescription = itemView.findViewById(R.id.search_text);
            searchDescription.setTextSize(textSize);
            if(Constants.language.equals("ur")){
                DescriptionFont = Typeface.createFromAsset(context.getAssets(), "Mehr Nastaliq.ttf");
                searchDescription.setTypeface(DescriptionFont);
                searchDescription.setGravity(Gravity.RIGHT);
            }else{
                DescriptionFont = Typeface.createFromAsset(context.getAssets(), "Times New Roman.ttf");
                searchDescription.setTypeface(DescriptionFont);
                searchDescription.setGravity(Gravity.LEFT);
            }
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(homedetailListener != null){
                HomeData_Model homeDataModel = homeDataModels.get(getAdapterPosition());
                homedetailListener.getHomeDetails(view,homeDataModel);
                Constants.backStack = true;
            }
        }
    }
}

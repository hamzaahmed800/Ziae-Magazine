package ziaetaiba.com.zia_e_magazine.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import ziaetaiba.com.zia_e_magazine.Calls.homedetailListener;
import ziaetaiba.com.zia_e_magazine.Globals.Constants;
import ziaetaiba.com.zia_e_magazine.Models.HomeData_Model;
import ziaetaiba.com.zia_e_magazine.R;


public class AllContent_Adapter extends RecyclerView.Adapter<AllContent_Adapter.ViewHolder> {

    private Context context;
    private List<HomeData_Model> homeData_modelList;
    private Typeface urduFont = null;
    private homedetailListener homedetailListener;

    public AllContent_Adapter(Context context, List<HomeData_Model> homeData_modelList) {
        this.context = context;
        this.homeData_modelList = homeData_modelList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.all_magazinecontent_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final HomeData_Model data_model = homeData_modelList.get(position);
        Glide.with(context).load(data_model.getThumbnailPath()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.topic_image);

            holder.short_desc.setText(data_model.getName());




    }

    @Override
    public int getItemCount() {
        if (homeData_modelList.size() > 0){
            return homeData_modelList.size();
        }else{
         return 0;
        }
    }

    public void setItemClickListener(homedetailListener homedetailListener) {
        this.homedetailListener = homedetailListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView topic_image;
        private TextView short_desc;
        private ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressBar);
            topic_image = itemView.findViewById(R.id.topic_image);
            short_desc = itemView.findViewById(R.id.short_description);
            if(Constants.language.equals("ur")){
                urduFont = Typeface.createFromAsset(context.getAssets(), "Jameel_Noori_Nastaleeq.ttf");
                short_desc.setGravity(Gravity.RIGHT);
                short_desc.setTypeface(urduFont);
            }else{
                urduFont = Typeface.createFromAsset(context.getAssets(), "Times New Roman.ttf");
                short_desc.setGravity(Gravity.LEFT);
                short_desc.setTypeface(urduFont);
            }
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(homedetailListener != null){
                HomeData_Model homeDataModel = homeData_modelList.get(getAdapterPosition());
                homedetailListener.getHomeDetails(view,homeDataModel);
                Constants.backStack = true;
            }
        }
    }
}

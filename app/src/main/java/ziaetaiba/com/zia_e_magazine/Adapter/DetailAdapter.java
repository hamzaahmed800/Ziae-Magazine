package ziaetaiba.com.zia_e_magazine.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
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

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import ziaetaiba.com.zia_e_magazine.Globals.Constants;
import ziaetaiba.com.zia_e_magazine.Models.HomeData_Model;
import ziaetaiba.com.zia_e_magazine.R;


public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    private List<HomeData_Model> homeData_model;
    private Context context;

    public DetailAdapter(Context context,List<HomeData_Model> homeData_model) {
        this.homeData_model = homeData_model;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_detail_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.headText.setText(homeData_model.get(position).getName());
        Glide.with(context).load(homeData_model.get(position).getThumbnailPath()).listener(new RequestListener<String, GlideDrawable>() {
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
        }).into(holder.headingImage);
        holder.descriptionText.setHtml(homeData_model.get(position).getDescription(),new HtmlHttpImageGetter(holder.descriptionText));


    }

    @Override
    public int getItemCount() {
        if(homeData_model != null){
            return homeData_model.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView headText;
        HtmlTextView descriptionText;
        ImageView headingImage,imageView1,imageView2,imageView3,imageView4,imageView5;
        ProgressBar progressBar;


        public ViewHolder(View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.detailprogressBar);

            headText = itemView.findViewById(R.id.detailheadingText);
            headingImage = itemView.findViewById(R.id.headImage);

            descriptionText = itemView.findViewById(R.id.descriptionText);
//            imageView1 = itemView.findViewById(R.id.articleImage1);
//            imageView2 = itemView.findViewById(R.id.articleImage2);
//            imageView3 = itemView.findViewById(R.id.articleImage3);
//            imageView4 = itemView.findViewById(R.id.articleImage4);
//            imageView5 = itemView.findViewById(R.id.articleImage5);


            // For Heading
            Typeface headingFont,DescriptionFont;
            if(Constants.language.equals("ur")){
                headingFont = Typeface.createFromAsset(context.getAssets(), "Aslam.ttf");
                headText.setTypeface(headingFont);
            }else{
                headingFont = Typeface.createFromAsset(context.getAssets(), "Arial.ttf");
                headText.setTypeface(headingFont);
            }

            //For Description
            if(Constants.language.equals("ur")){
                DescriptionFont = Typeface.createFromAsset(context.getAssets(), "Jameel_Noori_Nastaleeq.ttf");
                descriptionText.setTypeface(DescriptionFont);
            }else{
                DescriptionFont = Typeface.createFromAsset(context.getAssets(), "Times New Roman.ttf");
                descriptionText.setTypeface(DescriptionFont);
            }

        }
    }
}

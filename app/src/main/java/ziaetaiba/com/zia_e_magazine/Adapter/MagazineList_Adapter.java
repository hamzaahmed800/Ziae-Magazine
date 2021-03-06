package ziaetaiba.com.zia_e_magazine.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import ziaetaiba.com.zia_e_magazine.Calls.setOtherMagazine;
import ziaetaiba.com.zia_e_magazine.Globals.Constants;
import ziaetaiba.com.zia_e_magazine.Models.PreviousMagazineData_Model;
import ziaetaiba.com.zia_e_magazine.R;



public class MagazineList_Adapter extends RecyclerView.Adapter<MagazineList_Adapter.ViewHolder> {

    private Context context;
    private List<PreviousMagazineData_Model> previousMagazineDataModels;
    private setOtherMagazine setOtherMagazine;
    private SparseBooleanArray selectedItems;

    public MagazineList_Adapter(Context context, List<PreviousMagazineData_Model> previousMagazineDataModels) {
        this.context = context;
        this.previousMagazineDataModels = previousMagazineDataModels;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.magazinelist_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        PreviousMagazineData_Model previousMagazineData_model = previousMagazineDataModels.get(position);
        holder.magazine_name.setText(previousMagazineData_model.getName());

        Glide.with(context).load(previousMagazineData_model.getThumbnailPath()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
        //        holder.progressBar.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.magazine_img);


    }

    @Override
    public int getItemCount() {
        if (previousMagazineDataModels.size() > 0) {
            return previousMagazineDataModels.size();
        } else {
            return 0;
        }
    }

    public void setItemClickListener(setOtherMagazine homedetailListener) {
        this.setOtherMagazine = homedetailListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView magazine_name;
        ImageView magazine_img;
        ProgressBar progressBar;
        RelativeLayout relativeLayout,mainLayot;


        public ViewHolder(View itemView) {
            super(itemView);

            magazine_name = itemView.findViewById(R.id.magazine_name);
            magazine_img = itemView.findViewById(R.id.magazine_image);

            progressBar = itemView.findViewById(R.id.previousmagazine_progressBar);
            mainLayot = itemView.findViewById(R.id.mainLayout);
            relativeLayout = itemView.findViewById(R.id.view_listMagazine);
            itemView.setOnClickListener(this);
            RelativeLayout.LayoutParams mainparams = (RelativeLayout.LayoutParams) mainLayot.getLayoutParams();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();

            Typeface DescriptionFont;
            if (Constants.language.equals("ur")) {
                mainparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

                DescriptionFont = Typeface.createFromAsset(context.getAssets(), "Aslam.ttf");
                magazine_name.setTypeface(DescriptionFont);
            } else {
                mainparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                DescriptionFont = Typeface.createFromAsset(context.getAssets(), "Times New Roman.ttf");
                magazine_name.setTypeface(DescriptionFont);
            }
            mainLayot.setLayoutParams(mainparams);
            relativeLayout.setLayoutParams(params);
            mainLayot.bringToFront();

        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View view) {

            if (setOtherMagazine != null) {
                relativeLayout.setBackgroundColor(Color.TRANSPARENT);
                relativeLayout.setBackgroundColor(R.color.colorPrimary);
                PreviousMagazineData_Model previousMagazineData_model = previousMagazineDataModels.get(getAdapterPosition());
                setOtherMagazine.onClick(view, previousMagazineData_model);
            }
        }
    }
}

package ziaetaiba.com.zia_e_magazine.GestureListener;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import ziaetaiba.com.zia_e_magazine.Adapter.DetailAdapter;

/**
 * Created by HAMI on 16/09/2018.
 */

//Gesture Listener class
public class simpleOnScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    float size;
    float product;
    float factor;
    DetailAdapter detailAdapter;
    RecyclerView recyclerView;
    public simpleOnScaleGestureListener(DetailAdapter adapter,RecyclerView recyclerView){

        this.recyclerView = recyclerView;
        this.detailAdapter = adapter;

    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {

        size = ((HtmlTextView)((RelativeLayout)(((LinearLayout)((RelativeLayout) (recyclerView
                .getChildAt(0))).getChildAt(0)).getChildAt(2))).getChildAt(0))
                .getTextSize();
        Log.e("TextSizeStart", String.valueOf(size));
        //
        factor = detector.getScaleFactor();
        //    Log.d("Factor", "TextSize! Factor" + String.valueOf(factor));
        //
        product = (size * factor);
        if(product > 30 && product < 150){
            detailAdapter.setTextSize(product);
            Log.e("ProductTextSize", String.valueOf(product * factor));
            detailAdapter.notifyDataSetChanged();
        }else{}

        return true;
    }

}

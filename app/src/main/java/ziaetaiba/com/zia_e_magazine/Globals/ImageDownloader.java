package ziaetaiba.com.zia_e_magazine.Globals;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by HAMI on 03/09/2018.
 */

public class ImageDownloader extends Activity {

    Context context;
    private  static File storageDir;

      public ImageDownloader(final Context context, String url, final String image_name){

//          this.context = context;
//        //  requestStoragePermission(context);
//          Glide.with(context)
//                  .load(url)
//                  .asBitmap()
//                  .into(new SimpleTarget<Bitmap>(100,100) {
//                      @Override
//                      public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation)  {
//                          //  File removable = ContextCompat.getExternalFilesDirs(context, null)[0];
//
//                          //  if(new MainActivity().isStoragePermissionGranted()){
//                          //      Toast.makeText(context,"Granted",Toast.LENGTH_SHORT).show();
//                          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                              if (ContextCompat.checkSelfPermission(context,
//                                      Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//
//                                  saveImage(resource,image_name);
//
//
//                              } else {
//                                 // requestStoragePermission(context,resource,image_name);
//                              }
//                          } else {
//
//                          }
//
//                      }
//                  });


      }

    public static void saveImage(Context context,Bitmap image, String image_name) {
        String savedImagePath = null;
        String imageFileName =  image_name + ".png";
        File storageDir = new File(Environment.getExternalStorageDirectory()
                + "/Ziae Magazine");
        boolean success = true;
        if(GlobalCalls.retriveMonth(context,GlobalCalls.MONTH_KEY) != Constants.month){
            storageDir.delete();
        }
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
       //     savedImagePath = imageFile.getAbsolutePath();
            if(imageFile.exists()){
                imageFile.delete();
            }
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    public static String retriveImagePath(String image_name){

          return Environment.getExternalStorageDirectory()
                  + "/Ziae Magazine/" + image_name + ".png";

    }









}

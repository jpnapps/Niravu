package com.jpndev.niravu.utility;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.jpndev.niravu.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;


public class ImageZoomDialogFragment extends DialogFragment {

    private String imageurl;

  //  boolean isGlobalUrlcheck;

    ImageViewTouch mImage;

    Bitmap bitmap=null;


    public static  void showImageDialog(Context context, String image, boolean isGlobalUrl) {
        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();

        // Empty hoja_id => Register new header
        ImageZoomDialogFragment newFragment = ImageZoomDialogFragment.newInstance(image,isGlobalUrl);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null).commit();
    }
    public static  void showImageDialog(Activity context, String image, boolean isGlobalUrl) {
        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();

        // Empty hoja_id => Register new header
        ImageZoomDialogFragment newFragment = ImageZoomDialogFragment.newInstance(image,isGlobalUrl);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null).commit();
    }

    public static  void showImageDialog(Activity context, Bitmap image, boolean isGlobalUrl) {
        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();

        // Empty hoja_id => Register new header
        ImageZoomDialogFragment newFragment = ImageZoomDialogFragment.newInstance(image,isGlobalUrl);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null).commit();
    }
    public static  void showImageDialog(Context context, Bitmap image, boolean isGlobalUrl) {
        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();

        // Empty hoja_id => Register new header
        ImageZoomDialogFragment newFragment = ImageZoomDialogFragment.newInstance(image,isGlobalUrl);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null).commit();
    }


//    /* Process: com.beeone.techbank, PID: 27604
//    java.lang.ClassCastException: com.beeone.techbank.TechBankApplication cannot be cast to androidx.fragment.app.FragmentActivity
//        at com.beeone.techbank.ui.ImageZoomDialogFragment.showImageDialog(ImageZoomDialogFragment.java:86)
//        at com.beeone.techbank.sign.actions.AdsAdapter$onBindViewHolder$1$onBitmapLoaded$1.onClick(AdsAdapter.kt:51)*/
    public static ImageZoomDialogFragment newInstance(Bitmap imageurl, boolean isGlobalUrl) {
        ImageZoomDialogFragment f = new ImageZoomDialogFragment();

        Bundle args = new Bundle();

       // args.putBoolean("isGlobalUrl",isGlobalUrl);
        args.putParcelable("BitmapImage",imageurl);
        f.setArguments(args);

        return f;
    }

    public static ImageZoomDialogFragment newInstance(String imageurl, boolean isGlobalUrl) {
        ImageZoomDialogFragment f = new ImageZoomDialogFragment();

        Bundle args = new Bundle();
        args.putString("imageurl", imageurl);
       // args.putBoolean("isGlobalUrl",isGlobalUrl);

        f.setArguments(args);

        return f;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageurl = getArguments().getString("imageurl");
       // isGlobalUrlcheck = getArguments().getBoolean("isGlobalUrl");

        bitmap = getArguments().getParcelable("BitmapImage");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_image_zoom, container, false);

        ImageView closeImv = (ImageView) view.findViewById(R.id.close_imv);

        mImage = (ImageViewTouch) view.findViewById(R.id.image);

        // set the default image display type
        mImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

        imageLoad();

//        Picasso.with(getContext()).load(NetworkService.GLOBAL_IMAGE_URL+hoja_id).placeholder(R.drawable.image_placeholder).into(mImage);


        closeImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    private void imageLoad() {
        try{

            if(bitmap!=null)
            {
                mImage.setImageBitmap(bitmap);
            }
            else {
                String loadimage = imageurl;


             /*   if(isGlobalUrlcheck) {
                    loadimage = NetworkService.GLOBAL_IMAGE_URL + imageurl;
                } else {
                    loadimage = imageurl;
                }
*/

                Picasso.with(getContext()).load(loadimage).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                     //   Constants.re_annotation_image = bitmap;
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        LogUtils.LOGD("jp", "SILA  bitmap.getWidth()" + width);
                        LogUtils.LOGD("jp", "SILA  bitmap.getHeight()" + height);
                        //cellHolder.cell_image_arlay.getImageView().setHeightWidthRatio(width,height);

                        mImage.setImageBitmap(bitmap);
                        int device_width = Resources.getSystem().getDisplayMetrics().widthPixels;
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // inflater.inflate(R.menu.save_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

}
package com.jpndev.utilitylibrary.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.google.android.material.textfield.TextInputLayout;
import com.jpndev.utilitylibrary.CustomEditText;
import com.jpndev.utilitylibrary.CustomFontTextView;
import com.jpndev.utilitylibrary.DeviceFitImageView;
import com.jpndev.utilitylibrary.LogUtilsutility;
import com.jpndev.utilitylibrary.R;
import com.jpndev.utilitylibrary.ToastHandler;
import com.jpndev.utilitylibrary.expandview.Utils;
import com.jpndev.utilitylibrary.interfaces.HighLightEditTextHintListner;
import com.jpndev.utilitylibrary.network.UtilityNetworkService;
import com.jpndev.utilitylibrary.utils.PrefManager;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Created by ceino on 28/12/16.
 */

public class BaseAppCompactActivity extends AppCompatActivity {


    public static String last_fragment_name = "";
    /**
     * is Valid
     */
    public KProgressHUD hud_progress = null;
    public KProgressHUD hud_status = null;
    public KProgressHUD hud_status2 = null;
    public static String runningActivty = "";

    public static String getRunningActivty() {
        return runningActivty;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void setRunningActivty(String runningActivty) {
        BaseAppCompactActivity.runningActivty = runningActivty;
    }

    public BaseAppCompactActivity() {
    }

    public PrefManager getPrefManager() {

        return PrefManager.getInstance(getApplicationContext());

    }

    public PrefManager getPrefManager(Context cOntext) {

        return PrefManager.getInstance(cOntext);

    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void resetSharedValues() {
        try {
            PrefManager.getInstance(getApplicationContext()).clear();
        } catch (Exception e) {
            LogUtilsutility.LOGD("log", "container resetSharedValues exce " + e.getMessage());
        }
    }

    public void setLoading() {
        try {
            hideProgress();
            //Please wait
            hud_progress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setAnimationSpeed(2).setCancellable(true).setMaxProgress(100).setDimAmount(0.5f);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void setLoading(String message) {
        try {
            hideProgress();
            hud_progress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("" + message).setAnimationSpeed(2).setCancellable(true).setMaxProgress(100).setDimAmount(0.5f);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void setLoading(boolean isCancelable) {
        try {
            hideProgress();
            hud_progress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setAnimationSpeed(2).setCancellable(isCancelable).setMaxProgress(100).setDimAmount(0.5f);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void setLoading(boolean isCancelable, String message) {
        try {
            hideProgress();
            hud_progress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("" + message).setAnimationSpeed(2).setCancellable(isCancelable).setMaxProgress(100).setDimAmount(0.5f);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void setStatus() {
        try {
            hideStatus();
            hud_status = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("Please wait").setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);
        } catch (Exception e) {
            e.getMessage();
        }
    }


    public void setLoadingWait() {
        try {
            hideProgress();
            hud_progress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("Please wait").setAnimationSpeed(2).setCancellable(true).setMaxProgress(100).setDimAmount(0.5f);
        } catch (Exception e) {
            e.getMessage();
        }
    }


/*    public void setLoading() {

        hud_progress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setAnimationSpeed(2).setCancellable(true).setMaxProgress(100).setDimAmount(0.5f);


        }
    public void setLoading(boolean isCancelable) {
        hud_progress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setAnimationSpeed(2).setCancellable(isCancelable).setMaxProgress(100).setDimAmount(0.5f);

    }
    public void setLoadingWait() {
        hud_progress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("Please wait").setAnimationSpeed(2).setCancellable(true).setMaxProgress(100).setDimAmount(0.5f);

    }

    public void setLoading(String message) {
        hud_progress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel(""+message).setAnimationSpeed(2).setCancellable(true).setMaxProgress(100).setDimAmount(0.5f);

    }*/

    public void hideProgress() {
        try {
            if (isValid(hud_progress)) if (hud_progress.isShowing()) hud_progress.dismiss();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void showProgress() {
        try {
            hideProgress();
            if (isValid(hud_progress)) if (!hud_progress.isShowing()) hud_progress.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }
    public boolean isShowProgress() {
        boolean isShow=false;
        try {

            if (isValid(hud_progress)) if (!hud_progress.isShowing()) isShow=true;
        } catch (Exception e) {
            e.getMessage();
        }
        return isShow;
    }
    public void showStatus() {
        try {
            if (hud_status != null) if (!hud_status.isShowing()) hud_status.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void showStatus2() {
        try {
            if (hud_status2 != null) if (!hud_status2.isShowing()) hud_status2.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void hideStatus2() {
        try {
            if (hud_status2 != null) if (hud_status2.isShowing()) hud_status2.dismiss();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void hideStatus() {
        try {
            if (hud_status != null) if (hud_status.isShowing()) hud_status.dismiss();
        } catch (Exception e) {
            e.getMessage();
        }
    }


    public boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }






    public void setStatus(String status, int color) {
        View view = LayoutInflater.from(this).inflate(R.layout.status_dialog, null, false);
        CustomFontTextView status_dtxv = (CustomFontTextView) view.findViewById(R.id.status_dtxv);
        status_dtxv.setText("" + status);
        hideStatus();
        hud_status = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(this.getResources().getColor(color)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);

    }
    public void setErrorStatus(String status, int color) {
        View view = LayoutInflater.from(this).inflate(R.layout.status_dialog, null, false);
        CustomFontTextView status_dtxv = (CustomFontTextView) view.findViewById(R.id.status_dtxv);
        DeviceFitImageView status_dimv = (DeviceFitImageView) view.findViewById(R.id.status_dimv);
        status_dimv.setVisibility(View.INVISIBLE);
        status_dtxv.setText("" + status);
        hideStatus();
        hud_status = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(this.getResources().getColor(color)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);

    }
    public void setStatus(String status, int textcolor, int color) {
        View view = LayoutInflater.from(this).inflate(R.layout.status_dialog, null, false);
        CustomFontTextView status_dtxv = (CustomFontTextView) view.findViewById(R.id.status_dtxv);
        status_dtxv.setText("" + status);
        status_dtxv.setTextColor(textcolor);
        hideStatus();
        hud_status = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(this.getResources().getColor(color)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);

    }

    public void setStatus(String status) {
        View view = LayoutInflater.from(this).inflate(R.layout.status_dialog, null, false);
        CustomFontTextView status_dtxv = (CustomFontTextView) view.findViewById(R.id.status_dtxv);
        status_dtxv.setText("" + status);
        hideStatus();
        hud_status = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(this.getResources().getColor(R.color.md_green_400)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);

    }

    public void setAlertStatusInBlue(String title, String desc, View.OnClickListener oklistner) {
        View view = LayoutInflater.from(this).inflate(R.layout.alert_yes_no, null, false);
        CustomFontTextView title_dtxv = (CustomFontTextView) view.findViewById(R.id.title_dtxv);
        CustomFontTextView desc_dtxv = (CustomFontTextView) view.findViewById(R.id.desc_dtxv);
        CustomFontTextView dismis_dtxv = (CustomFontTextView) view.findViewById(R.id.dismis_dtxv);
        CustomFontTextView ok_dtxv = (CustomFontTextView) view.findViewById(R.id.ok_dtxv);
        defSetText(title_dtxv, title);
        defSetText(desc_dtxv, desc);
        dismis_dtxv.setText(" No ");
        ok_dtxv.setText(" Yes ");
        hideStatus();
        hud_status = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(this.getResources().getColor(R.color.transparent)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);
        showStatus();
        ok_dtxv.setOnClickListener(oklistner);
        dismis_dtxv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideStatus();


            }
        });
    }

    public void showDelayStatus(String save) {
        try {
            setStatus(save);
            showStatus();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideStatus();
                }
            }, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDelayStatus(String save, long time) {
        try {
          /*  setLoading(save);
            showProgress();*/
            setStatus(save);
            showStatus();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideStatus();
                    //hideProgress();
                }
            }, time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAlertOKStatusInBlue(String title, String desc, String ok) {
        //View.OnClickListener oklistner
        View view = LayoutInflater.from(this).inflate(R.layout.alert_ok, null, false);
        CustomFontTextView title_dtxv = (CustomFontTextView) view.findViewById(R.id.title_dtxv);
        CustomFontTextView desc_dtxv = (CustomFontTextView) view.findViewById(R.id.desc_dtxv);
        CustomFontTextView ok_dtxv = (CustomFontTextView) view.findViewById(R.id.ok_dtxv);

        if (isValid(title)) defSetText(title_dtxv, title);
        else title_dtxv.setVisibility(View.GONE);
        defSetText(desc_dtxv, desc);
        ok_dtxv.setText(ok);
        hideStatus();
        hud_status = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(this.getResources().getColor(R.color.transparent)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);
        showStatus();

        ok_dtxv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideStatus();


            }
        });
    }

    public void setAlertOKStatusInBlue(String title, String desc, String ok, boolean autohide) {
        //View.OnClickListener oklistner
        View view = LayoutInflater.from(this).inflate(R.layout.alert_ok, null, false);
        CustomFontTextView title_dtxv = (CustomFontTextView) view.findViewById(R.id.title_dtxv);
        CustomFontTextView desc_dtxv = (CustomFontTextView) view.findViewById(R.id.desc_dtxv);
        CustomFontTextView ok_dtxv = (CustomFontTextView) view.findViewById(R.id.ok_dtxv);

        if (isValid(title)) defSetText(title_dtxv, title);
        else title_dtxv.setVisibility(View.GONE);
        defSetText(desc_dtxv, desc);
        ok_dtxv.setText(ok);

        if (autohide) hideStatus();

        hud_status = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(this.getResources().getColor(R.color.transparent)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);
        showStatus();

        ok_dtxv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideStatus();


            }
        });
    }

    public void setAlertOKStatusInBlueStatus2(String title, String desc, String ok) {
        //View.OnClickListener oklistner
        View view = LayoutInflater.from(this).inflate(R.layout.alert_ok, null, false);
        CustomFontTextView title_dtxv = (CustomFontTextView) view.findViewById(R.id.title_dtxv);
        CustomFontTextView desc_dtxv = (CustomFontTextView) view.findViewById(R.id.desc_dtxv);
        CustomFontTextView ok_dtxv = (CustomFontTextView) view.findViewById(R.id.ok_dtxv);

        if (isValid(title)) defSetText(title_dtxv, title);
        else title_dtxv.setVisibility(View.GONE);
        defSetText(desc_dtxv, desc);
        ok_dtxv.setText(ok);


        hud_status2 = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(this.getResources().getColor(R.color.transparent)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);
        showStatus2();

        ok_dtxv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideStatus2();


            }
        });
    }

    public void setAlertOKStatusInBlueStatus2(String title, String desc, String ok, boolean autohide) {
        //View.OnClickListener oklistner
        View view = LayoutInflater.from(this).inflate(R.layout.alert_ok, null, false);
        CustomFontTextView title_dtxv = (CustomFontTextView) view.findViewById(R.id.title_dtxv);
        CustomFontTextView desc_dtxv = (CustomFontTextView) view.findViewById(R.id.desc_dtxv);
        CustomFontTextView ok_dtxv = (CustomFontTextView) view.findViewById(R.id.ok_dtxv);

        if (isValid(title)) defSetText(title_dtxv, title);
        else title_dtxv.setVisibility(View.GONE);
        defSetText(desc_dtxv, desc);
        ok_dtxv.setText(ok);

        if (autohide) hideStatus2();

        hud_status2 = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(this.getResources().getColor(R.color.transparent)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);
        showStatus2();

        ok_dtxv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideStatus2();


            }
        });
    }

    public void setAlertOKStatusInBlueStatus2(String title, String desc, String ok, boolean autohide, View.OnClickListener clickListener) {
        //View.OnClickListener oklistner
        View view = LayoutInflater.from(this).inflate(R.layout.alert_ok, null, false);
        CustomFontTextView title_dtxv = (CustomFontTextView) view.findViewById(R.id.title_dtxv);
        CustomFontTextView desc_dtxv = (CustomFontTextView) view.findViewById(R.id.desc_dtxv);
        CustomFontTextView ok_dtxv = (CustomFontTextView) view.findViewById(R.id.ok_dtxv);

        if (isValid(title)) defSetText(title_dtxv, title);
        else title_dtxv.setVisibility(View.GONE);
        defSetText(desc_dtxv, desc);
        ok_dtxv.setText(ok);

        if (autohide) hideStatus2();

        hud_status2 = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(this.getResources().getColor(R.color.transparent)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);
        showStatus2();

        ok_dtxv.setOnClickListener(clickListener);
    }
    public void setAlertOKStatusInCcrbStatus2(String title, String desc, String ok,  View.OnClickListener clickListener,boolean isCancel) {
        //View.OnClickListener oklistner
        View view = LayoutInflater.from(this).inflate(R.layout.alert_ok_dismiss, null, false);
        CustomFontTextView title_dtxv = (CustomFontTextView) view.findViewById(R.id.title_dtxv);
        CustomFontTextView desc_dtxv = (CustomFontTextView) view.findViewById(R.id.desc_dtxv);
        CustomFontTextView ok_dtxv = (CustomFontTextView) view.findViewById(R.id.ok_dtxv);
        RelativeLayout close_rlay = (RelativeLayout) view.findViewById(R.id.close_rlay);
        if (isValid(title)) defSetText(title_dtxv, title);
        else title_dtxv.setVisibility(View.GONE);
        defSetText(desc_dtxv, desc);
        ok_dtxv.setText(ok);
        hideStatus2();

        desc_dtxv.setTextColor(getResources().getColor(R.color.ccrb_text_medium_dark));
        title_dtxv.setTextColor(getResources().getColor(R.color.ccrb_text_dark));
        //if (autohide) hideStatus2();

        hud_status2 = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(this.getResources().getColor(R.color.transparent)).setCancellable(isCancel).setAnimationSpeed(2).setDimAmount(0.5f);
        showStatus2();

        ok_dtxv.setOnClickListener(clickListener);
        close_rlay.setVisibility(View.VISIBLE);
        close_rlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideStatus2();


            }
        });
    }


    public void setAlertStatusInBlue2(String title, String desc, String ok, String dismiss, View.OnClickListener oklistner) {
        View view = LayoutInflater.from(this).inflate(R.layout.alert_yes_no, null, false);
        CustomFontTextView title_dtxv = (CustomFontTextView) view.findViewById(R.id.title_dtxv);
        CustomFontTextView desc_dtxv = (CustomFontTextView) view.findViewById(R.id.desc_dtxv);
        CustomFontTextView dismis_dtxv = (CustomFontTextView) view.findViewById(R.id.dismis_dtxv);
        CustomFontTextView ok_dtxv = (CustomFontTextView) view.findViewById(R.id.ok_dtxv);
        if (isValid(title)) defSetText(title_dtxv, title);
        else title_dtxv.setVisibility(View.GONE);
        defSetText(desc_dtxv, desc);

        defSetText(dismis_dtxv, dismiss, "NO");
        defSetText(ok_dtxv, ok, "Yes");

        hideStatus();
        hideStatus2();
        hud_status2 = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(this.getResources().getColor(R.color.transparent)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);
        showStatus2();
        ok_dtxv.setOnClickListener(oklistner);
        dismis_dtxv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideStatus2();


            }
        });
    }

    /* public void setAlertOKStatusInBlue(String title,String desc,String ok) {
		 //View.OnClickListener oklistner
		 View view = LayoutInflater.from(this).inflate(R.layout.alert_ok, null, false);
		 CustomFontTextView title_dtxv = (CustomFontTextView) view.findViewById(R.id.title_dtxv);
		 CustomFontTextView desc_dtxv = (CustomFontTextView) view.findViewById(R.id.desc_dtxv);
		 CustomFontTextView ok_dtxv = (CustomFontTextView) view.findViewById(R.id.ok_dtxv);

		 if(isValid(title))
			 defSetText(title_dtxv,title);
		 else
			 title_dtxv.setVisibility(View.GONE);
		 defSetText(desc_dtxv,desc);
		 ok_dtxv.setText(ok);
		 hideStatus();
		 hud_status = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(this.getResources().getColor(R.color.transparent)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);
		 showStatus();
		 ok_dtxv.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 hideStatus();


			 }
		 });
	 }*/
    public String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    public long getElapsedMinutes(Date date2) {

        Date nowdate = new Date();
        long mili = 0;
        long elapsedminutes = 0;
        try {
            //   LogUtils.LOGD("jithish", "  TACA date2 time " + date2.getTime());
            //  LogUtils.LOGD("jithish", "  TACA nowdate time " + nowdate.getTime());
            long different = date2.getTime() - nowdate.getTime();
            //  LogUtils.LOGD("jithish", "  TACA different time " + different);
//return differents;
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            elapsedminutes = different / minutesInMilli;
        } catch (Exception e) {
            //LogUtils.LOGD("exception","  TACA time "+e.getMessage());
        }
        return elapsedminutes;
    }
/* public void setAlertStatus() {
        View view = LayoutInflater.from(this).inflate(R.layout.alert_yes_no, null, false);
        CustomFontTextView title_dtxv = (CustomFontTextView) view.findViewById(R.id.title_dtxv);
        title_dtxv.setText("Do you want to log out ?");
        CustomFontTextView dismis_dtxv = (CustomFontTextView) view.findViewById(R.id.dismis_dtxv);
        dismis_dtxv.setText(" No ");
        CustomFontTextView ok_dtxv = (CustomFontTextView) view.findViewById(R.id.ok_dtxv);
        ok_dtxv.setText(" Yes ");
        hud_status = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(getActivity().getResources().getColor(R.color.transparent)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);
        showStatus();
        ok_dtxv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {hideStatus(); }
        });
        dismis_dtxv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideStatus();


            }
        });
    }*/


    public Boolean isValid(String text) {
        if (text != null) if (!text.trim().equalsIgnoreCase("")) return true;
        return false;

    }

    public Boolean isValid(String text, int size) {
        if (isValid(text)) if (text.length() >= size) return true;
        return false;

    }

    public Boolean isValid(List list) {
        if (list != null) if (list.size() > 0) return true;
        return false;

    }

    public Boolean isValid(List list, int pos) {
        if (isValid(list)) if (list.size() >= pos) return true;
        return false;

    }

    public Boolean isListSize(List list, int size) {
        if (isValid(list)) if (list.size() == size) return true;
        return false;

    }

    public Boolean isValidPos(List list, int pos) {
        if (isValid(list)) if (list.size() >= pos) return true;
        return false;
    }

    public Boolean isValid(String[] list, int size) {

        if (isValid(list)) if (list.length >= size) return true;
        return false;

    }

    public Boolean isValidNotNull(List list) {
        if (list != null)
            // if(list.size()>0)
            return true;
        return false;

    }

    public Boolean isSessionExpired(AjaxStatus object) {
        boolean flag = false;
        try {
            if (object == null) flag = false;
            else if (object.getCode() == 401) flag = true;

            return flag;
        } catch (Exception e) {

        }
        return flag;
    }

    public Boolean isValid(Object object) {
        if (object != null) return true;
        return false;

    }
    public Boolean isValidBoolean(Object object) {
        boolean isObj=false;
        if(isValid(object))
        {

          try{
              isObj= (boolean) object;
          }catch (Exception e)
          {
              isObj=false;
          }
          return isObj;
        }
       return isObj;
    }


    public Boolean isErrorStatus(String object) {
        if (isValid(object)) if (object.trim().equalsIgnoreCase("error")) return true;
        return false;

    }

    public Boolean isValidDecimalLong(long object) {
        if (object > 0) return true;
        return false;

    }

    public Boolean isValidDecimal(int object) {
        if (object > 0) return true;
        return false;

    }

    public Boolean isValidLessSize(int object, int size) {
        if ((object + 1) < size) return true;
        return false;

    }

    public Boolean isValidSize(List list) {
        if (list != null) if (list.size() > 0) return true;
        return false;

    }

    public Boolean isValidMiddleSize(List list, int pos) {
        if (isValid(list)) if ((list.size() - 1) > pos && pos > 0) return true;
        return false;

    }

    public Boolean isValid(boolean object, String error) {
        if (object) return true;
        ToastHandler.newInstance(this).mustShowToast("" + error);
        return false;

    }

    public Boolean isLastItem(List list, int pos) {
        if (isValid(list)) if ((list.size() - 1) == pos) return true;
        return false;

    }

    /* public Boolean isFirst(List list,int pos) {
		 if (isValid(list))
			 if (list.size() == pos+1)
				 return true;
		 return false;

	 }*/
    public Boolean isValid(Object object, String error) {
        if (object != null) return true;
        ToastHandler.newInstance(this).mustShowToast("" + error);
        return false;

    }

    public Boolean isValid(long object, String error) {
        if (object > 0) return true;
        ToastHandler.newInstance(this).mustShowToast("" + error);
        return false;

    }
    public Boolean isValid(double object, String error) {
        if (object > 0) return true;
        ToastHandler.newInstance(this).mustShowToast("" + error);
        return false;

    }

/*    public Boolean isValid(RadioGroup radioGroup, String error) {
        if (radioGroup.getCheckedRadioButtonId() == -1)
        {
            ToastHandler.newInstance(getApplicationContext()).mustShowToast("" + error);
            return false;
        }
        else
        {
            return true;
        }

    }*/


    public Boolean isValid(List object, String error) {

        if (isValid(object)) return true;
        ToastHandler.newInstance(this).mustShowToast("" + error);
        return false;

    }

    public boolean isValidMobile(EditText phone_tv, String error) {
        if (phone_tv != null) if (isValid(phone_tv.getText() + ""))
            if (isValidMobile(phone_tv.getText().toString())) return true;
        phone_tv.setError(error + "");
        phone_tv.requestFocus();
        return false;

        //String phone=phone_tv.getText().toString();
        //return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public boolean isValidMobile(TextInputLayout usernameWrapper, EditText editext, String error) {
        if (usernameWrapper != null)
            if (isValid(editext.getText() + "")) if (isValidMobile(editext.getText().toString())) {
                usernameWrapper.setErrorEnabled(false);
                return true;
            }
        usernameWrapper.setError(error + "");
        usernameWrapper.setErrorEnabled(true);
        editext.requestFocus();
        return false;

        //String phone=phone_tv.getText().toString();
        //return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public boolean isValidMobile(CustomEditText phone_tv, String error) {
        if (phone_tv != null) if (isValid(phone_tv.getText() + ""))
            if (isValidMobile(phone_tv.getText().toString())) return true;
        phone_tv.setError(error + "");
        phone_tv.requestFocus();
        return false;

        //String phone=phone_tv.getText().toString();
        //return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public boolean isValid(TextInputLayout usernameWrapper, EditText editext, String error) {
        if (usernameWrapper != null)
            if (isValid(editext.getText() + "")) if (isValidMobile(editext.getText().toString())) {
                usernameWrapper.setErrorEnabled(false);
                return true;
            }
        usernameWrapper.setError(error + "");
        usernameWrapper.setErrorEnabled(true);
        editext.requestFocus();
        return false;

        //String phone=phone_tv.getText().toString();
        //return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public boolean isValidMobile(String txt) {
        return android.util.Patterns.PHONE.matcher(txt).matches();
    }

    public Object checkedObject(Object object) {
        if (object != null) return object;
        return object;

    }
    public Boolean isValidNotNull(Object text) {
        if (text != null)  return true;

        //text.requestFocus();
        return false;

    }
    public Boolean isValid(EditText text, String error) {
        if (text != null) if (isValid(text.getText() + "")) return true;
        text.setError(error + "");
        text.requestFocus();
        return false;

    }
    public Boolean isValid(EditText text) {
        if (text != null) if (isValid(text.getText() + "")) return true;

        //text.requestFocus();
        return false;

    }
    public Boolean isValid(EditText text, TextView texv, String error) {
        if (text != null) if (isValid(text.getText() + "")) return true;
        texv.setText(error + "");
        texv.setVisibility(View.VISIBLE);
        text.requestFocus();
        return false;

    }

    public Boolean isValid(RadioGroup radioGroup, String error) {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            ToastHandler.newInstance(getApplicationContext()).mustShowToast("" + error);
            return false;
        } else {
            return true;
        }

    }

    public Boolean isValid(TextView text, String error) {
        if (text != null) if (isValid(text.getText() + "")) return true;

        ToastHandler.newInstance(getApplicationContext()).mustShowToast("" + error);
        text.requestFocus();
        return false;

    }

    public Boolean isValid(String text, String error) {
        if (text != null) if (!text.trim().equalsIgnoreCase("")) return true;
        ToastHandler.newInstance(getApplicationContext()).mustShowToast("" + error);
        return false;

    }

    public Boolean isValid(String text,String text2,String text3, String error) {
        if( isValid(text)|| isValid(text2)|| isValid(text3))
         return true;
        ToastHandler.newInstance(getApplicationContext()).mustShowToast("" + error);
        return false;

    }

    public Boolean isDefaultDate(boolean text, String error) {
        if (!text) return true;
        ToastHandler.newInstance(getApplicationContext()).mustShowToast("" + error);
        return false;

    }

    public Boolean isValid(Date date) {
        if (date != null) return true;
        return false;

    }

    public Boolean isValid(String pwd, String pwd2, String error) {

        if (pwd2.equals(pwd)) return true;

        ToastHandler.newInstance(getApplicationContext()).mustShowToast(error);
        return false;

    }

    public Boolean isMatchesError(String pwd, String pwd2, String error) {

        if (!pwd2.equals(pwd)) return true;

        ToastHandler.newInstance(getApplicationContext()).mustShowToast(error);
        return false;

    }

    public Boolean isNOtMatches(String pwd, String pwd2) {

        if (!pwd2.equals(pwd)) return true;


        return false;

    }

    public Boolean isMatchesNull(String pwd, String pwd2) {

        if (!isValid(pwd)) return false;
        if (!isValid(pwd2)) return false;
        if (pwd2.equals(pwd)) return true;


        return false;

    }

    public Boolean isMatches(String pwd, String pwd2) {

        if (!isValid(pwd)) return false;
        if (!isValid(pwd2)) return false;
        if (pwd2.equals(pwd)) return true;


        return false;

    }

    public Boolean isMatchesForPut(String pwd, String pwd2) {

       /* if(!isValid(pwd))
            return false;*/
        if (!isValid(pwd2)) return false;
        if (pwd2.equals(pwd)) return true;


        return false;

    }

    public Boolean isMatchesCase(String pwd, String pwd2) {

        if (pwd2.equalsIgnoreCase(pwd)) return true;


        return false;

    }

    public Boolean isMatchesExceptNull(String pwd, String pwd2) {

        if (!isValid(pwd2)) return true;
        if (pwd2.equals(pwd)) return true;


        return false;

    }

    public Boolean isSame(String pwd, String pwd2) {

        if (pwd2.equals(pwd)) return true;


        return false;

    }
    public Boolean isGreater(EditText text, long bid) {
        boolean isGreater=false;
        try {
            long enterbid = Long.parseLong(text.getText() + "");
            if (enterbid >= bid) isGreater= true;

        }catch (Exception e)
        {
            isGreater=false;
        }
        return isGreater;

    }

    public Boolean isGreater(EditText text, long bid, String error) {
        long enterbid = Long.parseLong(text.getText() + "");
        if (enterbid >= bid) return true;
        ToastHandler.newInstance(getApplicationContext()).mustShowToast(error);
        return false;

    }
    public Boolean isGreaterDouble(EditText text, long bid, String error) {
        double enterbid = Double.parseDouble(text.getText() + "");
        if (enterbid >= bid) return true;
        ToastHandler.newInstance(getApplicationContext()).mustShowToast(error);
        return false;

    }
    public Boolean isGreaterDouble(TextView text, long bid, String error) {
        double enterbid = Double.parseDouble(text.getText() + "");
        if (enterbid >= bid) return true;
        ToastHandler.newInstance(getApplicationContext()).mustShowToast(error);
        return false;

    }
    public Boolean isValidEmail(EditText text, String error) {
        if (text != null)
            if (isValid(text.getText() + "")) if (isValidEmail(text.getText() + "")) return true;
        text.setError(error + "");
        return false;

    }

 /*   public boolean isValidMobile(CustomEditText phone_tv, String error) {
        if (phone_tv != null)
            if (isValid(phone_tv.getText() + ""))
                if (isValidMobile(phone_tv.getText().toString()))
                    return true;
        phone_tv.setError(error + "");
        return false;

        //String phone=phone_tv.getText().toString();
        //return android.util.Patterns.PHONE.matcher(phone).matches();
    }
    public boolean isValidMobile(String txt)
    {
        return android.util.Patterns.PHONE.matcher(txt).matches();
    }*/

    public Boolean isValidPassword(EditText text, String error) {
        if (text != null)
            if (isValid(text.getText() + "")) if (isValidPassword(text.getText() + "")) return true;
        text.setError(error + "");
        return false;

    }

    // validating email id
    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    public boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 3) {
            return true;
        }
        return false;
    }

    public String defString(EditText text, String def) {
        if (text != null) if (isValid(text.getText() + "")) return text.getText() + "";
        return def;

    }

    public String defString(EditText text) {
        if (text != null) if (isValid(text.getText() + "")) return text.getText() + "";
        return "";

    }
    public String defString(TextView text) {
        if (text != null) if (isValid(text.getText() + "")) return text.getText() + "";
        return "";

    }
    public long defStringLong(EditText text) {
        if (text != null)
            if (isValid(text.getText() + "")) return Long.parseLong(text.getText() + "");
        return 0;

    }
    public double defStringDouble(EditText text) {

        if (text != null)
            if (isValid(text.getText() + "")) return Double.parseDouble(text.getText() + "");
        return 0.0;
    }

    public int defStringInt(EditText text) {
        if (text != null)
            if (isValid(text.getText() + "")) return Integer.parseInt(text.getText() + "");
        return 0;

    }

    public String defString(String text, String def) {
        if (text != null) return text;
        return def;

    }

    /*public String defString(Object text) {
        if (text != null)
            return text;
        return "";

    }*/
    public String defString(String text) {
        String tt="";
        if (text != null)
            tt= text;

          return tt;

    }

    public String defStringWithPrefix(Object text,String postfix) {
        if (isValid(text)) return postfix+text;
        return "";

    }
    public String defStringWithPrefix(String text,String postfix) {
        if (text != null) return postfix+text;
        return "";

    }
    public String defStringWithPostfix(Object text,String postfix) {
        if (isValid(text)) return text+postfix;
        return "";

    }
    public String defStringWithPostfix(String text,String postfix) {
        if (text != null) return text+postfix;
        return "";

    }
    public String defStringWithComma(String text) {
        if (text != null) return text+", ";
        return "";

    }
    public Object defString(Object text) {
        if (text != null) return text;
        return "";

    }
    public String minusZero(String text) {
        if (text.startsWith("-")||text.startsWith("_")) return "0";
        return text;

    }
    public void showFragment(Fragment fragment, int id) {
        last_fragment_name = fragment.getClass().getName();
        getSupportFragmentManager().beginTransaction().replace(id, fragment).addToBackStack("" + fragment).commit();
// fragmentShowActionBar(fragment);

    }

    public void showFragment(Fragment fragment, Bundle bundle, int id) {
        last_fragment_name = fragment.getClass().getName();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(id, fragment).addToBackStack("" + fragment).commit();
// fragmentShowActionBar(fragment);
    }

    public void showFragment(Fragment fragment) {
        last_fragment_name = fragment.getClass().getName();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("" + fragment).commit();
// fragmentShowActionBar(fragment);

    }

    public void showFragment(Fragment fragment, Bundle bundle) {
        last_fragment_name = fragment.getClass().getName();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("" + fragment).commit();
// fragmentShowActionBar(fragment);
    }

    public void showActionBar() {
        if (!getSupportActionBar().isShowing()) {
            getSupportActionBar().show();

        }
        getSupportActionBar().setElevation(0);
    }

    public void hideActionBar() {
        getSupportActionBar().hide();
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }


    }


    public void showKeyboard(final EditText edt) {

        if (edt != null) {
            /*InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);*/
            edt.requestFocus();
            edt.postDelayed(new Runnable(){
                                @Override public void run(){
                                    InputMethodManager keyboard=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    keyboard.showSoftInput(edt,0);
                                }
                            }
                    ,200);
        }


    }
    public void onPopUpFragment() {
        hideKeyboard();
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            //fragmentShowActionBar(getSupportFragmentManager().findFragmentById(R.id.container));
        } else {
            // showFragment(new HomeFragment());
        }
    }

    public void onBackClicked() {
        hideKeyboard();
        //showFragment(new HomeFragment());
    }

    public void setAqueryImage(AQuery aqueryImage, String urllast, int drawable) {
        try {
            aqueryImage.image(UtilityNetworkService.GLOBAL_IMAGE_URL + urllast, true, true, 0, 0, drawableToBitmap(getApplicationContext().getResources().getDrawable(drawable)), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAqueryImage(AQuery aqueryImage, String urllast, int drawable, Boolean cached) {
        try {
            aqueryImage.image(UtilityNetworkService.GLOBAL_IMAGE_URL + urllast, cached, cached, 0, 0, drawableToBitmap(getApplicationContext().getResources().getDrawable(drawable)), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setAqueryImage(AQuery aqueryImage, ImageView imageView, String urllast, int drawable) {
        try {
            aqueryImage.id(imageView).image(UtilityNetworkService.GLOBAL_IMAGE_URL + urllast, true, true, 0, 0, drawableToBitmap(getApplicationContext().getResources().getDrawable(drawable)), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAqueryImage(ImageView img, String urllast, int drawable) {

        try {
            AQuery aQuery = new AQuery(img);
            if (!isValid(urllast))
                aQuery.image(drawableToBitmap(getApplicationContext().getResources().getDrawable(drawable)));
            else
                aQuery.image(UtilityNetworkService.GLOBAL_IMAGE_URL + urllast, true, true, 0, 0, drawableToBitmap(getApplicationContext().getResources().getDrawable(drawable)), 0);
        } catch (Exception e) {
            //Crashlytics.logException(e);
            e.printStackTrace();
        }
    }

    public void setAqueryImage(ImageView img, String urllast, int drawable, Boolean cached) {

        try {
            AQuery aqueryImage = new AQuery(img);
            if (!isValid(urllast))
                aqueryImage.image(drawableToBitmap(getApplicationContext().getResources().getDrawable(drawable)));
            else
                aqueryImage.image(UtilityNetworkService.GLOBAL_IMAGE_URL + urllast, cached, cached, 0, 0, drawableToBitmap(getApplicationContext().getResources().getDrawable(drawable)), 0);
        } catch (Exception e) {
            //Crashlytics.logException(e);
            e.printStackTrace();
        }

    }

    public void setAqueryImage(ImageView img, ImageView imageView, String urllast, int drawable) {
        try {
            AQuery aqueryImage = new AQuery(img);
            if (!isValid(urllast))
                aqueryImage.image(drawableToBitmap(getApplicationContext().getResources().getDrawable(drawable)));
            else
                aqueryImage.id(imageView).image(UtilityNetworkService.GLOBAL_IMAGE_URL + urllast, true, true, 0, 0, drawableToBitmap(getApplicationContext().getResources().getDrawable(drawable)), 0);
        } catch (Exception e) {
            //Crashlytics.logException(e);
            e.printStackTrace();
        }
    }

    public void defSetText(TextView textv, String text, String def) {
        if (isValid(textv)) textv.setText(defString(text, def));

    }

    public void defSetText(TextView textv, String text) {
        if (isValid(textv)) textv.setText(defString(text));

    }

    public void defSetText(TextView textv, SpannableString text) {
        if (isValid(textv)) textv.setText(defString(text));

    }

    public SpannableString defString(SpannableString text) {
        if (text != null) return text;
        return new SpannableString("");

    }




    public void defSetText(TextView textv, Object text) {
        if (isValid(textv)) textv.setText(defString(text) + "");

    }
    public void defSetText(TextView textv, Spanned text) {
        if (isValid(textv)) textv.setText(defString(text) + "");

    }

    public void defSetHtmlText(TextView textv, String text) {
        if (isValid(textv)) {
            textv.setText(Html.fromHtml(defString(text) + ""));
            textv.setMovementMethod(LinkMovementMethod.getInstance());
        }

    }

    public void setTextWithVisbility(TextView textv, String text) {
        if (isValid(textv)) if (isValid(text)) textv.setText(defString(text));
        else textv.setVisibility(View.GONE);
    }

    public void setHTMLTextWithVisbility(TextView textv, String text) {
        if (isValid(textv)) if (isValid(text)) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                textv.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
            } else {
                textv.setText(Html.fromHtml(text));
            }
            textv.setMovementMethod(LinkMovementMethod.getInstance());
        } else textv.setVisibility(View.GONE);
    }

    public void defSetText(EditText textv, String text, String def) {
        if (isValidNotNull(textv)) textv.setText(defString(text, def));

    }

    public void  defSetText(EditText textv, String text) {
        if (isValidNotNull(textv))
            textv.setText(defString(text));

    }

    public void defSetText(EditText textv) {
        if (isValidNotNull(textv)) textv.setText("");

    }

    public void defBlinkSetText(final TextView textv, String text) {
        if (isValid(textv)) {
            textv.setText(defString(text));
            textv.setVisibility(View.VISIBLE);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isValid(textv)) textv.setVisibility(View.INVISIBLE);
            }
        }, 4000);
    }

    public long millisecondCalc(String s) {
        try {
            if (s.equalsIgnoreCase("5min")) {
                return TimeUnit.MINUTES.toMillis(5); // 23 minutes to milliseconds.
            } else if (s.equalsIgnoreCase("15min")) {
                return TimeUnit.MINUTES.toMillis(15); // 23 minutes to milliseconds.
            } else if (s.equalsIgnoreCase("30min")) {
                return TimeUnit.MINUTES.toMillis(30); // 23 minutes to milliseconds.
            } else if (s.equalsIgnoreCase("1hr")) {
                return TimeUnit.HOURS.toMillis(1);    // 4 hours to milliseconds.
            } else if (s.equalsIgnoreCase("2hr")) {
                return TimeUnit.HOURS.toMillis(2);    // 4 hours to milliseconds.
            } else if (s.equalsIgnoreCase("1day")) {
                return TimeUnit.DAYS.toMillis(1);     // 1 day to milliseconds.
            } else if (s.equalsIgnoreCase("2day")) {
                return TimeUnit.DAYS.toMillis(2);     // 1 day to milliseconds.
            } else if (s.equalsIgnoreCase("1week")) {
                return TimeUnit.DAYS.toMillis(7);              // 7 Days to milliseconds.
            } else if (s.equalsIgnoreCase("none")) {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        TimeUnit.SECONDS.toMillis(96); // 96 seconds to milliseconds.
        return 0;
    }


    public String millisecondString(long s) {
        try {
            if (TimeUnit.MINUTES.toMillis(5) == s) {
                return "5 min"; // 23 minutes to milliseconds.
            } else if (TimeUnit.MINUTES.toMillis(15) == s) {
                return "15 min"; // 23 minutes to milliseconds.
            } else if (TimeUnit.MINUTES.toMillis(15) == s) {
                return "30 min"; // 23 minutes to milliseconds.
            } else if (TimeUnit.HOURS.toMillis(1) == s) {
                return "1 hour"; // 23 minutes to milliseconds.
            } else if (TimeUnit.HOURS.toMillis(2) == s) {
                return "2 hour"; // 23 minutes to milliseconds.
            } else if (TimeUnit.DAYS.toMillis(1) == s) {
                return "1 day"; // 23 minutes to milliseconds.
            } else if (TimeUnit.DAYS.toMillis(2) == s) {
                return "2 day"; // 23 minutes to milliseconds.
            } else if (TimeUnit.DAYS.toMillis(7) == s) {
                return "1 week"; // 23 minutes to milliseconds.
            } else {
                return "none";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        TimeUnit.SECONDS.toMillis(96); // 96 seconds to milliseconds.
        return "";
    }


    public void setTextColor(final TextView editText, final int textcolor) {
        editText.setTextColor(getResources().getColor(textcolor));
    }


    public void setTextInActiveColor(final TextView editText) {
        editText.setTextColor(getResources().getColor(R.color.grey_fit));
    }

    public void setTextActiveColor(final TextView editText) {
      //  editText.setTextColor(getResources().getColor(R.color.ccrbGreen));
        editText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    public void setEditTextLay(final EditText editText, final TextView textview) {


        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    textview.setVisibility(View.VISIBLE);
                    if (getCurrentFocus() == editText) {
                        textview.setTextColor(getResources().getColor(R.color.ccrbGreen));
                    } else textview.setTextColor(getResources().getColor(R.color.grey_fit));
                } else textview.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void setEditTextLay(final EditText editText, final TextView textview, final HighLightEditTextHintListner listner) {


        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    textview.setVisibility(View.VISIBLE);
                    if (getCurrentFocus() == editText) {
                        textview.setTextColor(getResources().getColor(R.color.ccrbGreen));
                        listner.onItemEdit(textview);
                    } else textview.setTextColor(getResources().getColor(R.color.grey_fit));
                } else textview.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void setEditTextVisibileLay(final EditText editText, final TextView textview, final HighLightEditTextHintListner listner) {


        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    textview.setVisibility(View.VISIBLE);
                    if (getCurrentFocus() == editText) {
                        textview.setTextColor(getResources().getColor(R.color.ccrbGreen));
                        listner.onItemEdit(textview);
                    } else textview.setTextColor(getResources().getColor(R.color.grey_fit));
                }
                //elseb
                   // textview.setTextColor(getResources().getColor(R.color.grey_fit));
                   // textview.setVisibility(View.INVISIBLE);
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    listner.onItemEdit(textview);
                } else {
                   // Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }


    private ValueAnimator createExpandAnimator(final int from, final int to, final long duration, final TimeInterpolator interpolator) {
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animator) {
              /*  if (isVertical()) {
                    getLayoutParams().height = (int) animator.getAnimatedValue();
                } else {
                    getLayoutParams().width = (int) animator.getAnimatedValue();
                }
                requestLayout();*/
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }
        });
        return valueAnimator;
    }
    public  int calculateNoOfGridColumns(Context context) {
        int noOfColumns=3;
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
           //noOfColumns = (int) (dpWidth / 80);
            noOfColumns = (int) (dpWidth / 100);
        }catch (Exception e)
        {

        }
        return noOfColumns;
    }
 /*   public  int calculateNoOfGridColumns(Context context,int noOfColumns) {
        int noOfColumns=3;
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            noOfColumns = (int) (dpWidth / 80);
        }catch (Exception e)
        {

        }
        return noOfColumns;
    }*/
}

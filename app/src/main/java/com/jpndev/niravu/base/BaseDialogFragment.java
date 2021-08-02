package com.jpndev.niravu.base;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.androidquery.AQuery;

import com.jpndev.niravu.R;
import com.jpndev.niravu.home.SplashActivity;
import com.jpndev.utilitylibrary.CustomFontTextView;
import com.jpndev.utilitylibrary.DeviceFitImageView;
import com.jpndev.utilitylibrary.ToastHandler;
import com.jpndev.utilitylibrary.utils.PrefManager;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.otto.Subscribe;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jpndev.utilitylibrary.network.UtilityNetworkService.GLOBAL_IMAGE_URL;

public abstract class BaseDialogFragment extends DialogFragment {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;


    public LinearLayout actionBarTopLlay;
    public RelativeLayout bottomPanel;
    public LinearLayout leftLlay;
    public DeviceFitImageView backImv;
    public CustomFontTextView leftTxv;
    public CustomFontTextView titleTxv;
    public LinearLayout rightLlay;
    public CustomFontTextView rightTxv;
    public DeviceFitImageView rightImv;


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //BusProvider.getInstance().register(this);

    }
    public void showAlert(Activity kycConfirmUserDetailActivity, String title, String desc) {
        try {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.alert_ccrb_ok, null);
            RelativeLayout topLlay = (RelativeLayout) view.findViewById(R.id.top_llay);
            LinearLayout innerTopLlay = (LinearLayout) view.findViewById(R.id.inner_top_llay);
            RelativeLayout actionBarRlay = (RelativeLayout) view.findViewById(R.id.action_bar_rlay);
            DeviceFitImageView dimv = (DeviceFitImageView) view.findViewById(R.id.dimv);
            CustomFontTextView descCftxv = (CustomFontTextView) view.findViewById(R.id.desc_cftxv);
            RelativeLayout okeyRlay = (RelativeLayout) view.findViewById(R.id.okey_rlay);
            CustomFontTextView okCftxv = (CustomFontTextView) view.findViewById(R.id.ok_cftxv);
            hud_status = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(getResources().getColor(R.color.transparent)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);

            defSetText(descCftxv, desc);
            topLlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            topLlay.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            okeyRlay.setVisibility(View.VISIBLE);

            okeyRlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    hideStatus();
                   // onBackFinish();

                    // finish();

                }
            });
            showStatus();
        } catch (Exception e) {
            hideStatus();
            e.getMessage();
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {


            onSetActionBar(view);
            onSetValues(view);
        }catch (Exception e)
        {
            e.getMessage();
        }
    }

/*    @Subscribe
    public abstract void onNotifyEvent(NotifyEvent event);*/

    // throws Exception
    public abstract void onSetValues(View view);


    public abstract void onSetActionBar(View view);
    @Override
    public void onDestroy() {
        super.onDestroy();
      //  BusProvider.getInstance().unregister(this);
    }

    public Boolean isValid(String text) {
        if (text != null) if (!text.trim().equalsIgnoreCase("")) return true;
        return false;

    }

    public Boolean isValid(List list) {
        if (list != null) if (list.size() > 0) return true;
        return false;

    }

    public Boolean isValid(List list, int pos) {
        if (isValid(list))
            if (list.size() >= pos)
                return true;
        return false;

    }

    public Boolean isValidPos(List list, int pos) {
        if (isValid(list))
            if (list.size() >= pos)
                return true;
        return false;

    }

    public Boolean isValidNotNull(List list) {
        if (list != null)
            // if(list.size()>0)
            return true;
        return false;

    }

    public Boolean isValidSize(List list) {
        if (list != null) if (list.size() > 0) return true;
        return false;

    }

    public Boolean isValid(Object object) {
        if (object != null) return true;
        return false;

    }

    public Boolean isValid(int object) {
        if (object > 0) return true;
        return false;

    }

    public Boolean isValidhtml(String text) {
        if (isValid(text)) if (text.contains("http") || text.contains("https")) return true;
        return false;

    }

    public Boolean isValid(Object object, String error) {
        if (object != null) return true;
        ToastHandler.newInstance(getActivity()).mustShowToast("" + error);
        return false;

    }

    public Boolean isValidAlert(Object object, String error) {
        if (object != null) return true;
        //  setAlertInWhiteBox("",error,"OK");
        return false;

    }

    public Boolean isValidAlert(List list, String error) {
        if (list != null) if (list.size() > 0) return true;
        // setAlertInWhiteBox("",error,"OK");
        return false;

    }

    public Object checkedObject(Object object) {
        if (object != null) return object;
        return object;

    }

    /*  public Boolean isValid(EditText text, String error) {
          if(text != null) if(isValid(text.getText() + "")) return true;
          text.setError(error + "");

          return false;

      }*/
    public Boolean isValid(EditText text, String error) {
        if (text != null) if (isValid(text.getText() + "")) return true;
        //  setAlertInWhiteBox("",error,"OK");
        // text.setError(error + "");

        return false;

    }

    public Boolean isValid(EditText text) {
        if (text != null) if (isValid(text.getText() + "")) return true;
        //  setAlertInWhiteBox("",error,"OK");
        // text.setError(error + "");

        return false;

    }
    public Boolean isValid(EditText text, String error, int colorPrimaryDark, int duartion) {
        if (text != null) if (isValid(text.getText() + "")) return true;
        errorStatusTimerShow("" + error, colorPrimaryDark, duartion);
        //ToastHandler.newInstance(getApplicationContext()).mustShowToast("" + error);
        text.requestFocus();
        return false;

    }
    public Boolean isValidNumber(EditText text, String error, int colorPrimaryDark, int duartion) {
        try {
            double d = Double.parseDouble(text.getText().toString());
            if (d > 0.0) return true;
            errorStatusTimerShow("" + error, colorPrimaryDark, duartion);

            text.requestFocus();
            return false;
        }catch (Exception e)
        {
            errorStatusTimerShow("" + error, colorPrimaryDark, duartion);

            text.requestFocus();
            return false;
        }

    }
    public Boolean isValidUptoNumber(double number,EditText text, String error, int colorPrimaryDark, int duartion) {
        try {
            double d = Double.parseDouble(text.getText().toString());
            if (d <= number) return true;
            errorStatusTimerShow("" + error, colorPrimaryDark, duartion);

            text.requestFocus();
            return false;
        }catch (Exception e)
        {
            errorStatusTimerShow("" + error, colorPrimaryDark, duartion);

            text.requestFocus();
            return false;
        }

    }
    public void errorStatusTimerShow(String message, int colorPrimaryDark, int duartion) {
        hideProgress();
        setErrorStatus(message, colorPrimaryDark);
        showStatus();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideStatus();

            }
        }, duartion);
    }

    public Boolean isValidPlusWarning(EditText text, String error) {
        if (text != null) if (isValid(text.getText() + "")) return true;
        //  setAlertStatusMessage("Warning!",error,"Ok");
        return false;

    }

    public Boolean isValid(String text, String error) {
        if (text != null) if (!text.trim().equalsIgnoreCase("")) return true;
        ToastHandler.newInstance(getActivity()).mustShowToast("" + error);
        return false;

    }

    public Boolean isValid(boolean text, String error) {
        if (text) return true;
        ToastHandler.newInstance(getActivity()).mustShowToast("" + error);
        return false;

    }

    public Boolean isValid(List list, String error) {
        if (list != null) if (list.size() > 0) return true;
        //  setAlertStatusMessage("Warning", error,"Ok");
        // ToastHandler.newInstance(getActivity()).mustShowToast(""+error);
        return false;

    }

    public Boolean isValid(Date date) {
        if (date != null) return true;
        return false;


    }


    public Boolean isEquals(String pwd, String pwd2) {

        if (pwd2.equals(pwd)) return true;


        return false;

    }

    public Boolean isValid(String pwd, String pwd2, String error) {

        if (pwd2.equals(pwd)) return true;

        ToastHandler.newInstance(getActivity()).mustShowToast(error);
        return false;

    }

    public Boolean isValidEmail(EditText text, String error) {
        if (text != null)
            if (isValid(text.getText() + "")) if (isValidEmail(text.getText() + "")) return true;
        text.setError(error + "");
        return false;

    }

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


    public Boolean isErrorStatus(String object) {
        if (isValid(object)) if (object.trim().equalsIgnoreCase("error")) return true;
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

    public String defString(String text, String def) {
        if (text != null) return text;
        return def;

    }

    public String defString(String text) {
        if (text != null) return text;
        return "";

    }


    public void defSetText(TextView textv, String text, String def) {
        if (isValid(textv))
            textv.setText(defString(text, def));

    }

    public void defSetText(TextView textv, String text) {
        if (isValid(textv))
            textv.setText(defString(text));

    }

    public void defSetText(EditText textv, String text, String def) {
        if (isValid(textv))
            textv.setText(defString(text, def));

    }

    public void defSetText(EditText textv, String text) {
        if (isValid(textv))
            textv.setText(defString(text));

    }

    public KProgressHUD hud_progress = null;
    public KProgressHUD hud_status = null;

    public void setLoading() {
        hud_progress = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setAnimationSpeed(2).setCancellable(true).setMaxProgress(100).setDimAmount(0.5f);

    }
    public void setLoading(boolean isCancelable) {
        hud_progress = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setAnimationSpeed(2).setCancellable(isCancelable).setMaxProgress(100).setDimAmount(0.5f);

    }
    public void setLoadingWait() {
        hud_progress = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("Please wait").setAnimationSpeed(2).setCancellable(true).setMaxProgress(100).setDimAmount(0.5f);

    }

    public void setLoading(String message) {
        hud_progress = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("" + message).setAnimationSpeed(2).setCancellable(true).setMaxProgress(100).setDimAmount(0.5f);

    }

    public void showStatus() {
        if (hud_status != null) if (!hud_status.isShowing()) hud_status.show();
    }

    public void hideStatus() {
        if (hud_status != null) if (hud_status.isShowing()) hud_status.dismiss();
    }

    public void hideProgress() {
        if (isValid(hud_progress)) if (hud_progress.isShowing()) hud_progress.dismiss();
    }

    public void showProgress() {
        if (isValid(hud_progress)) if (!hud_progress.isShowing()) hud_progress.show();
    }

    public void setStatus(String status, int color) {
        View view = LayoutInflater.from(getActivity()).inflate(com.jpndev.utilitylibrary.R.layout.status_dialog, null, false);
        CustomFontTextView status_dtxv = (CustomFontTextView) view.findViewById(com.jpndev.utilitylibrary.R.id.status_dtxv);
        status_dtxv.setText("" + status);
        hideStatus();
        hud_status = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(getActivity().getResources().getColor(color)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);

    }
    public void setErrorStatus(String status, int color) {
        View view = LayoutInflater.from(getActivity()).inflate(com.jpndev.utilitylibrary.R.layout.status_dialog, null, false);
        CustomFontTextView status_dtxv = (CustomFontTextView) view.findViewById(com.jpndev.utilitylibrary.R.id.status_dtxv);
        DeviceFitImageView status_dimv = (DeviceFitImageView) view.findViewById(com.jpndev.utilitylibrary.R.id.status_dimv);
        status_dimv.setVisibility(View.INVISIBLE);
        status_dtxv.setText("" + status);
        hideStatus();
        hud_status = KProgressHUD.create(getActivity()
        ).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(this.getResources().getColor(color)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);

    }

    public void setAqueryImage(AQuery aqueryImage, String urllast, int drawable) {
        try {
            aqueryImage.image(GLOBAL_IMAGE_URL + urllast, true, true, 0, 0, drawableToBitmap(getActivity().getResources().getDrawable(drawable)), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAqueryImage(AQuery aqueryImage, String urllast, int drawable, Boolean cached) {
        try {
            aqueryImage.image(GLOBAL_IMAGE_URL + urllast, cached, cached, 0, 0, drawableToBitmap(getActivity().getResources().getDrawable(drawable)), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setAqueryImage(AQuery aqueryImage, ImageView imageView, String urllast, int drawable) {
        try {
            aqueryImage.id(imageView).image(GLOBAL_IMAGE_URL + urllast, true, true, 0, 0, drawableToBitmap(getActivity().getResources().getDrawable(drawable)), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAqueryImage(ImageView img, String urllast, int drawable) {

        try {
            AQuery aQuery = new AQuery(img);
            if (!isValid(urllast))
                aQuery.image(drawableToBitmap(getActivity().getResources().getDrawable(drawable)));
            else
                aQuery.image(GLOBAL_IMAGE_URL + urllast, true, true, 0, 0, drawableToBitmap(getActivity().getResources().getDrawable(drawable)), 0);
        } catch (Exception e) {
            //Crashlytics.logException(e);
            e.printStackTrace();
        }
    }

    public void setAqueryImage(ImageView img, String urllast, int drawable, Boolean cached) {

        try {
            AQuery aqueryImage = new AQuery(img);
            if (!isValid(urllast))
                aqueryImage.image(drawableToBitmap(getActivity().getResources().getDrawable(drawable)));
            else
                aqueryImage.image(GLOBAL_IMAGE_URL + urllast, cached, cached, 0, 0, drawableToBitmap(getActivity().getResources().getDrawable(drawable)), 0);
        } catch (Exception e) {
            //Crashlytics.logException(e);
            e.printStackTrace();
        }

    }

    public void setAqueryImage(ImageView img, ImageView imageView, String urllast, int drawable) {
        try {
            AQuery aqueryImage = new AQuery(img);
            if (!isValid(urllast))
                aqueryImage.image(drawableToBitmap(getActivity().getResources().getDrawable(drawable)));
            else
                aqueryImage.id(imageView).image(GLOBAL_IMAGE_URL + urllast, true, true, 0, 0, drawableToBitmap(getActivity().getResources().getDrawable(drawable)), 0);
        } catch (Exception e) {
            //Crashlytics.logException(e);
            e.printStackTrace();
        }
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


    public String getUDID() {
        String android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }


    public void clearSharedAll() {
        if (!isValid(editor)) {
            if (!isValid(sharedpreferences))
                sharedpreferences = getActivity().getSharedPreferences(PrefManager.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
        }
        editor.clear().commit();
    }



    public PrefManager getPrefManager() {

        return PrefManager.getInstance(getApplicationContext());

    }

    public PrefManager getPrefManager(Context cOntext) {

        return PrefManager.getInstance(cOntext);

    }


    /**
     * Shared Preference
     */


    public boolean getSharedBoolean(String KEY, boolean defValue) {
        return PrefManager.getInstance(getApplicationContext()).getSharedBoolean(KEY, defValue);
    }


    public void putSharedBoolean(String KEY, boolean defValue) {
        PrefManager.getInstance(getApplicationContext()).putSharedBoolean(KEY, defValue);
    }

    public String getSharedString(String KEY, String defValue) {
        return PrefManager.getInstance(getApplicationContext()).getSharedString(KEY, defValue);
    }

    public void putSharedString(String KEY, String defValue) {
        PrefManager.getInstance(getApplicationContext()).putSharedString(KEY, defValue);
    }

    public String getPrefUDID() {
        return getSharedString(PrefManager.UDID, "");
    }

    public void saveUDID(String valude) {
        putSharedString(PrefManager.UDID, valude);
    }

    public void saveProfileImageURL(String url) {
        putSharedString(PrefManager.PROFILE_IMAGE_URL, url);

    }

    public String getProfileImageURL() {
        return getSharedString(PrefManager.PROFILE_IMAGE_URL, "");

    }

    public String getUserName() {
        return getUserFirstName() + " " + getUserLastName();

    }

    public String getUserFirstName() {
        return getSharedString(PrefManager.UserFirstName, "");

    }

    public String getUserLastName() {
        return getSharedString(PrefManager.UserLastName, "");

    }

    public String getUserID() {
        return getSharedString(PrefManager.USERID, "");

    }


    public KProgressHUD hud_status2 = null;

    public void showStatus2() {
        if (hud_status2 != null) if (!hud_status2.isShowing()) hud_status2.show();
    }

    public void hideStatus2() {
        if (hud_status2 != null) if (hud_status2.isShowing()) hud_status2.dismiss();
    }

    public Context getApplicationContext() {
        return getActivity().getApplicationContext();
    }





    public void postEventOnMainThread(final Object event) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                BusProvider.getInstance().post(event);
                // bus.post(event);
            }
        });

    }



    public String get2F( String addtocard_total_pound) {
        String text = null;
        try {
            text = String.format("%.2f", Double.parseDouble(addtocard_total_pound));
            if (isValid(text))
                text = "" + text;
        } catch ( Exception e) {
            text = null;
           // e.message
        }

        return text;
    }
   /* public void insertUserDB( final Context context, final MpUser object) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                DataBase dataBase = DataBase.getInstance(context);
                //  dataBase.getUserDao().
                dataBase.getUserDao().deleteRows();
                dataBase.getUserDao().insertUser(object);
                showProfileTabActivty();


            }
        };
        new Thread(runnable).start();
    }*/
}


package com.jpndev.utilitylibrary.base;

import android.app.Activity;

import android.content.Context;
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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.androidquery.AQuery;
import com.jpndev.utilitylibrary.CustomEditText;
import com.jpndev.utilitylibrary.CustomFontTextView;
import com.jpndev.utilitylibrary.R;
import com.jpndev.utilitylibrary.ToastHandler;
import com.jpndev.utilitylibrary.utils.PrefManager;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.jpndev.utilitylibrary.network.UtilityNetworkService.GLOBAL_IMAGE_URL;


/**
 * Created by jithish on 1/12/16.
 */

public abstract class BaseFragment extends Fragment {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //BusProvider.getInstance().register(this);

    }
   /* @Subscribe
    public abstract void onNotifyEvent(NotifyEvent event);*/

   public abstract void onSetValues(View view) throws Exception;
    public abstract void onSetAdapter() ;
    @Override
    public void onDestroy() {
        super.onDestroy();
        //BusProvider.getInstance().unregister(this);
    }
    public Boolean isValid(String text) {
        if(text != null) if(!text.trim().equalsIgnoreCase("")) return true;
        return false;

    }

    public Boolean isValid(List list) {
        if(list != null) if(list.size() > 0) return true;
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
        if(list != null)
            // if(list.size()>0)
            return true;
        return false;

    }

    public Boolean isValidSize(List list) {
        if(list != null) if(list.size() > 0) return true;
        return false;

    }

    public Boolean isValid(Object object) {
        if(object != null) return true;
        return false;

    }
//{"cards":[{"amount":10,"ccrb":5,"id":274}],"crypto":"BTC","currency":"GBP","email":"amal@ccrb.io","userid":"e349bbf63a01cd4f3f2b316cb60c232dd4bef59a"}
//{"message":"Sorry, card not available at the moment","status":false}
    public Boolean isValid(int object) {
        if(object > 0) return true;
        return false;

    }
    public Boolean isValidhtml(String text) {
        if(isValid(text)) if(text.contains("http")||text.contains("https")) return true;
        return false;

    }
    public Boolean isValid(Object object, String error) {
        if(object != null) return true;
        ToastHandler.newInstance(getActivity()).mustShowToast("" + error);
        return false;

    }
    public Boolean isValidAlert(Object object, String error) {
        if(object != null) return true;
      //  setAlertInWhiteBox("",error,"OK");
        return false;

    }
    public Boolean isValidAlert(List list, String error) {
        if(list != null) if(list.size() > 0) return true;
       // setAlertInWhiteBox("",error,"OK");
        return false;

    }

    public Object checkedObject(Object object) {
        if(object != null) return object;
        return object;

    }
    /*  public Boolean isValid(EditText text, String error) {
          if(text != null) if(isValid(text.getText() + "")) return true;
          text.setError(error + "");

          return false;

      }*/
    public Boolean isValid(EditText text, String error) {
        if(text != null) if(isValid(text.getText() + "")) return true;
      //  setAlertInWhiteBox("",error,"OK");
        // text.setError(error + "");

        return false;

    }
    public Boolean isValid(EditText text) {
        if(text != null) if(isValid(text.getText() + "")) return true;
        //  setAlertInWhiteBox("",error,"OK");
        // text.setError(error + "");

        return false;

    }
    public Boolean isValidPlusWarning(EditText text, String error) {
        if(text != null) if(isValid(text.getText() + "")) return true;
      //  setAlertStatusMessage("Warning!",error,"Ok");
        return false;

    }

    public Boolean isValid(String text, String error) {
        if(text != null) if(!text.trim().equalsIgnoreCase("")) return true;
        ToastHandler.newInstance(getActivity()).mustShowToast("" + error);
        return false;

    }

    public Boolean isValid(boolean text, String error) {
        if(text) return true;
        ToastHandler.newInstance(getActivity()).mustShowToast("" + error);
        return false;

    }

    public Boolean isValid(List list, String error) {
        if(list != null) if(list.size() > 0) return true;
      //  setAlertStatusMessage("Warning", error,"Ok");
        // ToastHandler.newInstance(getActivity()).mustShowToast(""+error);
        return false;

    }

    public Boolean isValid(Date date) {
        if(date != null) return true;
        return false;






    }




    public Boolean isEquals(String pwd, String pwd2) {

        if(pwd2.equals(pwd)) return true;


        return false;

    }

    public Boolean isValid(String pwd, String pwd2, String error) {

        if(pwd2.equals(pwd)) return true;

        ToastHandler.newInstance(getActivity()).mustShowToast(error);
        return false;

    }

    public Boolean isValidEmail(EditText text, String error) {
        if(text != null)
            if(isValid(text.getText() + "")) if(isValidEmail(text.getText() + "")) return true;
        text.setError(error + "");
        return false;

    }

    public Boolean isValidPassword(EditText text, String error) {
        if(text != null)
            if(isValid(text.getText() + "")) if(isValidPassword(text.getText() + "")) return true;
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
        if(pass != null && pass.length() > 3) {
            return true;
        }
        return false;
    }



    public Boolean isErrorStatus(String object) {
        if(isValid(object)) if(object.trim().equalsIgnoreCase("error")) return true;
        return false;

    }

    public String defString(EditText text, String def) {
        if(text != null) if(isValid(text.getText() + "")) return text.getText() + "";
        return def;

    }

    public String defString(EditText text) {
        if(text != null) if(isValid(text.getText() + "")) return text.getText() + "";
        return "";

    }

    public String defString(String text, String def) {
        if(text != null) return text;
        return def;

    }

    public String defString(String text) {
        if(text != null) return text;
        return "";

    }


    public void defSetText(TextView textv, String text, String def) {
        if(isValid(textv))
            textv.setText(defString(text,def));

    }
    public void defSetText(TextView textv, String text) {
        if(isValid(textv))
            textv.setText(defString(text));

    }

    public void defSetText(EditText textv, String text, String def) {
        if(isValid(textv))
            textv.setText(defString(text,def));

    }
    public void defSetText(EditText textv, String text) {
        if(isValid(textv))
            textv.setText(defString(text));

    }

    public KProgressHUD hud_progress = null;
    public KProgressHUD hud_status = null;
    public void setLoading() {
        hud_progress = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setAnimationSpeed(2).setCancellable(true).setMaxProgress(100).setDimAmount(0.5f);

    }

    public void setLoadingWait() {
        hud_progress = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel("Please wait").setAnimationSpeed(2).setCancellable(true).setMaxProgress(100).setDimAmount(0.5f);

    }

    public void setLoading(String message) {
        hud_progress = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel(""+message).setAnimationSpeed(2).setCancellable(true).setMaxProgress(100).setDimAmount(0.5f);

    }
    public void showStatus() {
        if(hud_status != null) if(!hud_status.isShowing()) hud_status.show();
    }

    public void hideStatus() {
        if(hud_status != null) if(hud_status.isShowing()) hud_status.dismiss();
    }
    public void hideProgress() {
        if(isValid(hud_progress))if(hud_progress.isShowing()) hud_progress.dismiss();
    }

    public void showProgress() {
        if(isValid(hud_progress)) if(!hud_progress.isShowing()) hud_progress.show();
    }
    public void setStatus(String status, int color) {
        View view = LayoutInflater.from(getActivity()).inflate(com.jpndev.utilitylibrary.R.layout.status_dialog, null, false);
        CustomFontTextView status_dtxv = (CustomFontTextView) view.findViewById(com.jpndev.utilitylibrary.R.id.status_dtxv);
        status_dtxv.setText("" + status);
        hideStatus();
        hud_status = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(getActivity().getResources().getColor(color)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);

    }




    public void setAqueryImage(AQuery aqueryImage, String urllast, int drawable)
    {
        try{
            aqueryImage.image(GLOBAL_IMAGE_URL + urllast,true,true,0,0,drawableToBitmap(getActivity().getResources().getDrawable(drawable)),0);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setAqueryImage(AQuery aqueryImage, String urllast, int drawable, Boolean cached)
    {
        try{
            aqueryImage.image(GLOBAL_IMAGE_URL+ urllast,cached,cached,0,0,drawableToBitmap(getActivity().getResources().getDrawable(drawable)),0);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setAqueryImage(AQuery aqueryImage, ImageView imageView, String urllast, int drawable)
    {
        try{
            aqueryImage.id(imageView).image(GLOBAL_IMAGE_URL + urllast,true,true,0,0,drawableToBitmap(getActivity().getResources().getDrawable(drawable)),0);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void setAqueryImage(ImageView img, String urllast, int drawable) {

        try {
            AQuery aQuery = new AQuery(img);
            if(!isValid(urllast))
                aQuery.image(drawableToBitmap(getActivity().getResources().getDrawable(drawable)));
            else
                aQuery.image(GLOBAL_IMAGE_URL + urllast, true, true, 0, 0, drawableToBitmap(getActivity().getResources().getDrawable(drawable)), 0);
        } catch (Exception e){
            //Crashlytics.logException(e);
            e.printStackTrace();
        }
    }

    public void setAqueryImage(ImageView img, String urllast, int drawable, Boolean cached) {

        try {
            AQuery aqueryImage = new AQuery(img);
            if(!isValid(urllast))
                aqueryImage.image(drawableToBitmap(getActivity().getResources().getDrawable(drawable)));
            else
                aqueryImage.image(GLOBAL_IMAGE_URL + urllast, cached, cached, 0, 0, drawableToBitmap(getActivity().getResources().getDrawable(drawable)), 0);
        } catch (Exception e){
            //Crashlytics.logException(e);
            e.printStackTrace();
        }

    }

    public void setAqueryImage(ImageView img, ImageView imageView, String urllast, int drawable) {
        try {
            AQuery aqueryImage = new AQuery(img);
            if(!isValid(urllast))
                aqueryImage.image(drawableToBitmap(getActivity().getResources().getDrawable(drawable)));
            else
                aqueryImage.id(imageView).image(GLOBAL_IMAGE_URL + urllast, true, true, 0, 0, drawableToBitmap(getActivity().getResources().getDrawable(drawable)), 0);
        } catch (Exception e){
            //Crashlytics.logException(e);
            e.printStackTrace();
        }
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
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
        if(!isValid(editor)) {
            if(!isValid(sharedpreferences))
                sharedpreferences = getActivity().getSharedPreferences(PrefManager.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
        }
        editor.clear().commit();
    }
//////////////////////////////////////////////

    public void clearServices() {
     //   clearLayerDatas();
      /*  NetworkService.mAllUsersRoot = null;
        NetworkService.mAllUsersRootPaged = null;
        NetworkService.mhangoutLive = null;*/
       /* Constants.m = null;
        Constants.question_image_data = null;
        Constants.question_title = null;
        Constants.annotedlist = null;
        Constants.sharedlist = null;
        profileimage = null;*/


    }



    public PrefManager getPrefManager()
    {

        return     PrefManager.getInstance(getApplicationContext());

    }
    public PrefManager getPrefManager(Context cOntext)
    {

        return     PrefManager.getInstance(cOntext);

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
        return getUserFirstName()+" "+ getUserLastName();

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
        if(hud_status2 != null) if(!hud_status2.isShowing()) hud_status2.show();
    }
    public void hideStatus2() {
        if(hud_status2 != null) if(hud_status2.isShowing()) hud_status2.dismiss();
    }

   public Context getApplicationContext()
   {
       return getActivity().getApplicationContext();
   }

    public void setAlertStatusInBlue2(String title, String desc, String ok, String dismiss, View.OnClickListener oklistner) {
        View view = LayoutInflater.from(getActivity()).inflate(com.jpndev.utilitylibrary.R.layout.alert_yes_no, null, false);
        CustomFontTextView title_dtxv = (CustomFontTextView) view.findViewById(com.jpndev.utilitylibrary.R.id.title_dtxv);
        CustomFontTextView desc_dtxv = (CustomFontTextView) view.findViewById(com.jpndev.utilitylibrary.R.id.desc_dtxv);
        CustomFontTextView dismis_dtxv = (CustomFontTextView) view.findViewById(com.jpndev.utilitylibrary.R.id.dismis_dtxv);
        CustomFontTextView ok_dtxv = (CustomFontTextView) view.findViewById(com.jpndev.utilitylibrary.R.id.ok_dtxv);
        if(isValid(title))
            defSetText(title_dtxv,title);
        else
            title_dtxv.setVisibility(View.GONE);
        defSetText(desc_dtxv,desc);

        defSetText(dismis_dtxv,dismiss,"NO");
        defSetText(ok_dtxv,ok,"Yes");

        hideStatus();
        hideStatus2();
        hud_status2 = KProgressHUD.create(getApplicationContext()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCustomView(view).setWindowColor(this.getResources().getColor(com.jpndev.utilitylibrary.R.color.transparent)).setCancellable(false).setAnimationSpeed(2).setDimAmount(0.5f);
        showStatus2();
        ok_dtxv.setOnClickListener(oklistner);
        dismis_dtxv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideProgress();
                hideStatus2();


            }
        });
    }



    public boolean isValidMobile(EditText phone_tv, String error) {
        if (phone_tv != null)
            if (isValid(phone_tv.getText() + ""))
                if (isValidMobile(phone_tv.getText().toString()))
                    return true;
        phone_tv.setError(error + "");
        return false;

        //String phone=phone_tv.getText().toString();
        //return android.util.Patterns.PHONE.matcher(phone).matches();
    }
    public boolean isValidMobile(CustomEditText phone_tv, String error) {
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
    }



    public void hideKeyboard(Activity context) {
        // Check if no view has focus:
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
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



    private void errorStatus() {
        setStatus("Error,Please try later", R.color.red);
        showStatus();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                hideStatus();
                hideStatus2();
            }
        }, 2000);
    }
    public void postEventOnMainThread(final Object event) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
              ///  BusProvider.getInstance().post(event);
                // bus.post(event);
            }
        });

    }


    public boolean onBackPressed() {
        return false;
    }
}
package com.jpndev.utilitylibrary.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jpndev.utilitylibrary.BuildConfig;
import com.jpndev.utilitylibrary.CustomFontTextView;
import com.jpndev.utilitylibrary.DeviceFitImageView;
import com.jpndev.utilitylibrary.LogUtilsutility;
import com.jpndev.utilitylibrary.R;
import com.jpndev.utilitylibrary.ToastHandler;
import com.jpndev.utilitylibrary.base.BaseAppCompactActivity;
import com.jpndev.utilitylibrary.utils.ConstantsUtility;


/**
 * Created by ceino on 23/7/15.
 */
public class WebActivity extends BaseAppCompactActivity {
    String url;
    final String mimeType = "text/html";
    WebView wv;
    final String encoding = "UTF-8";
   // ProgressDialog pd;
    ImageView backarrow;
    RelativeLayout backarrow_layout;
    public static final String URL="web_url";
    public static final String CLICK="interface";

    private LinearLayout actionBarTopLlay;
    private RelativeLayout bottomPanel;
    private LinearLayout leftLlay;
    private DeviceFitImageView backImv;
    private CustomFontTextView leftTxv;
    private CustomFontTextView titleTxv;
    private LinearLayout rightLlay;
    private DeviceFitImageView logoImv;
    private DeviceFitImageView rightImv;
    private DeviceFitImageView copy_dimv;
    private final String googleDocs = "https://docs.google.com/viewer?url=";


    public static void startWebActivity(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }


    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webactivityframe);
        Intent intent = getIntent();

     //   ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);


         setLoading();
      showProgress();
        url=intent.getStringExtra(URL);
        setActionBar();
       /* pd=new ProgressDialog(this);
        pd.show();*/
//url="https://api.soumya.beeone.co.uk/v1/tbappflyer?email=ajeesh@ccrb.io&userid=48d927e3609845d8642745f9e90f052cc78ca0d0";
        wv = (WebView) findViewById(R.id.webview1);

        wv.setInitialScale(0);


//		  wv.getSettings().setBuiltInZoomControls(true);
      wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
       // wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
      //  wv.setHorizontalScrollBarEnabled(false);
      //  wv.set
       // wv.getSettings().setLayo  utAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
       // wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
             /*   if(pd.isShowing()&&pd!=null)
                {
                    pd.hide();
                }*/
                hideProgress();
                if (url.endsWith(".pdf")) {
                    wv.loadUrl("javascript:(function() { " + "document.querySelector('[role=\"toolbar\"]').remove();})()");
                }

            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

      /*         // pd.show();
                // to kill activity
                view.loadUrl(url);
//	                pd.show();
               return true;
               // return(false);*/
                if(!isShowProgress())
                  showProgress();


                if (url.endsWith(".pdf")){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(url), "application/pdf");
                    try{
                        view.getContext().startActivity(intent);
                    } catch (Exception e) {
                        String pdfUrl = googleDocs + url;
                        view.loadUrl(pdfUrl);
                    }

					return false;
                } else {
                    view.loadUrl(url);
					return true;
                }



            }


          /*  @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view, String url) {
                return(false);
            }*/


        });
        wv.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
//				     activity.setProgress(progress * 1000);
                if (progress == 1)
                 showProgress();
             /*   pd.show();
                pd.setProgress(0);
                pd.setProgress(progress * 1000);

                pd.incrementProgressBy(progress);
                if (progress == 100 && pd.isShowing())
                    pd.dismiss();*/
                if (progress == 100)
                    hideProgress();


            }


            // For 3.0+ Devices (Start)
            // onActivityResult attached before constructor
            protected void openFileChooser(ValueCallback uploadMsg, String acceptType)
            {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            }


            // For Lollipop 5.0+ Devices
            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
            {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

                uploadMessage = filePathCallback;

                Intent intent = fileChooserParams.createIntent();
                try
                {
                    startActivityForResult(intent, REQUEST_SELECT_FILE);
                } catch (ActivityNotFoundException e)
                {
                    uploadMessage = null;
                    Toast.makeText(getApplicationContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

            //For Android 4.1 only
            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
            {
                mUploadMessage = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            protected void openFileChooser(ValueCallback<Uri> uploadMsg)
            {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }
        });
        wv.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv.getSettings().setJavaScriptEnabled(true);

        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setAppCacheEnabled(true);
        wv.getSettings().setLoadsImagesAutomatically(true);


        /*26-11-2019*/
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setAllowContentAccess(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wv.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        wv.loadUrl(url);
       // wv.loa
//		 wv.requestFocus(View.FOCUS_DOWN);
//		 wv.requestFocus();
        wv.requestFocus(View.FOCUS_DOWN|View.FOCUS_UP);
//		 wv.requestFocus(View.FOCUSABLES_ALL);


        WebSettings wbsettings = wv.getSettings();
       wbsettings.setBuiltInZoomControls(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            CookieManager cookieManager = CookieManager.getInstance();

            cookieManager.setAcceptThirdPartyCookies(wv, true);

        }


        //wbsettings.setBuiltInZoomControls(false);
    }
/*public boolean launchPDF(WebView view, String url) {
                if ( urlIsPDF(url)){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(url), "application/pdf");
                    try{
                        view.getContext().startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        //user does not have a pdf viewer installed
                    }
                } else {
                    webview.loadUrl(url);
                }
                return true;
            }*/



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (requestCode == REQUEST_SELECT_FILE)
            {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        }
        else if (requestCode == FILECHOOSER_RESULTCODE)
        {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
        else
            Toast.makeText(this.getApplicationContext(), "Failed to Upload Image", Toast.LENGTH_LONG).show();
    }
    private void setActionBar() {
        try{
            actionBarTopLlay = (LinearLayout) findViewById(R.id.action_bar_top_llay);
            bottomPanel = (RelativeLayout) findViewById(R.id.bottom_panel);
            leftLlay = (LinearLayout) findViewById(R.id.left_llay);
            backImv = (DeviceFitImageView) findViewById(R.id.back_imv);
            leftTxv = (CustomFontTextView) findViewById(R.id.left_txv);
            titleTxv = (CustomFontTextView) findViewById(R.id.title_txv);
            rightLlay = (LinearLayout) findViewById(R.id.right_llay);
            logoImv = (DeviceFitImageView) findViewById(R.id.logo_imv);
            rightImv = (DeviceFitImageView) findViewById(R.id.right_imv);
            copy_dimv = (DeviceFitImageView) findViewById(R.id.copy_dimv);
           // if(BuildConfig.s)
            copy_dimv.setVisibility(View.GONE);

            if(!ConstantsUtility.IS_LIVE) {
                copy_dimv.setVisibility(View.VISIBLE);
                copy_dimv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final android.content.ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("Url", url);
                        clipboardManager.setPrimaryClip(clipData);
                        ToastHandler.newInstance(WebActivity.this).mustShowToast("Copied Url "+url);
                    }
                });
            }




            rightLlay.setVisibility(View.VISIBLE);
         //   rightTxv.setVisibility(View.INVISIBLE);
            rightImv.setVisibility(View.VISIBLE);
            logoImv.setVisibility(View.GONE);
            titleTxv.setVisibility(View.INVISIBLE);
            leftLlay.setVisibility(View.VISIBLE);
            backImv.setVisibility(View.VISIBLE);
            leftTxv.setVisibility(View.INVISIBLE);

           // leftTxv.setText("Back");
           // leftTxv.setTextColor(getResources().getColor(R.color.color_scheme));
            //backImv.getDrawable().setColorFilter(getResources().getColor(R.color.color_scheme), PorterDuff.Mode.SRC_ATOP);
            backImv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                }
            });
            leftLlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                }
            });
            rightImv.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    wv.reload();

                }
            });
        }catch (Exception e)
        {
            LogUtilsutility.LOGD("exception"," webview exception "+e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        finish();
    }
}

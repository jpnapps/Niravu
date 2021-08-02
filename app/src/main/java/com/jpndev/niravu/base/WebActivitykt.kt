package com.jpndev.niravu.base

import android.annotation.SuppressLint

import android.content.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast

import com.jpndev.utilitylibrary.CustomFontTextView
import com.jpndev.utilitylibrary.DeviceFitImageView
import com.jpndev.utilitylibrary.LogUtilsutility
import com.jpndev.utilitylibrary.ToastHandler
import com.jpndev.utilitylibrary.activity.WebActivity
import com.jpndev.utilitylibrary.base.BaseAppCompactActivity
import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.jpndev.niravu.BuildConfig
import com.jpndev.niravu.MWeb
import com.jpndev.niravu.NiravuActivity
import com.jpndev.niravu.R
import kotlinx.android.synthetic.main.activity_web.*

class WebActivitykt : NiravuActivity() {

    internal var url: String ?=null
    val mimeType = "text/html"
    var wv: WebView? = null
    val encoding = "UTF-8"
    // ProgressDialog pd;
    var backarrow: ImageView? = null
    var backarrow_layout: RelativeLayout? = null

    val CLICK = "interface"


    private val googleDocs = "https://docs.google.com/viewer?url="

    companion object {
        val URL = "web_url"
        val WEB_PROPERTY = "web_property"
    fun startWebActivity(context: FragmentActivity?, url: String?,web_properties:MWeb?=null) {
        val intent = Intent(context, WebActivitykt::class.java)
        intent.putExtra(WebActivity.URL, url)
        intent.putExtra(WEB_PROPERTY, web_properties)
        context?.startActivity(intent)
    }
        fun startWebActivity2(context: FragmentActivity?, url: String?,web_properties:MWeb?=null) {
            val intent = Intent(context, WebActivitykt::class.java)
            intent.putExtra(URL, url)
            intent.putExtra(WEB_PROPERTY, web_properties)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context?.startActivity(intent)
        }
        fun startWebActivity(context: Context, url: String?,web_properties:MWeb?=null) {
            val intent = Intent(context, WebActivitykt::class.java)
            intent.putExtra(WebActivity.URL, url)
            intent.putExtra(WEB_PROPERTY, web_properties)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }
    }
    private var mUploadMessage: ValueCallback<Uri?>? = null
    var uploadMessage: ValueCallback<Array<Uri>>? = null
    val REQUEST_SELECT_FILE = 100
    private val FILECHOOSER_RESULTCODE = 1
    var web_properties:MWeb?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val intent = intent
        //   ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
        setLoading()
        showProgress()
        url = intent.getStringExtra(URL)
        web_properties = intent.getParcelableExtra(WEB_PROPERTY)

        setActionBar(web_properties)
        /* pd=new ProgressDialog(this);
        pd.show();*/
//url="https://api.soumya.beeone.co.uk/v1/tbappflyer?email=ajeesh@ccrb.io&userid=48d927e3609845d8642745f9e90f052cc78ca0d0";
        wv = findViewById<View>(R.id.webview1) as WebView
        wv!!.setInitialScale(0)
        //		  wv.getSettings().setBuiltInZoomControls(true);
        wv!!.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        // wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
//  wv.setHorizontalScrollBarEnabled(false);
//  wv.set
// wv.getSettings().setLayo  utAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
// wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        wv!!.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) { /*   if(pd.isShowing()&&pd!=null)
                {
                    pd.hide();
                }*/
                hideProgress()
                if (url.endsWith(".pdf")) {
                    wv!!.loadUrl("javascript:(function() { " + "document.querySelector('[role=\"toolbar\"]').remove();})()")
                }
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean { /*         // pd.show();
                // to kill activity
                view.loadUrl(url);
//	                pd.show();
               return true;
               // return(false);*/
                if (!isShowProgress()) showProgress()
                return if (url.endsWith(".pdf")) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(Uri.parse(url), "application/pdf")
                    try {
                        view.context.startActivity(intent)
                    } catch (e: Exception) {
                        val pdfUrl = googleDocs + url
                        view.loadUrl(pdfUrl)
                    }
                    false
                } else {
                    view.loadUrl(url)
                    true
                }
            } /*  @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view, String url) {
                return(false);
            }*/
        }
        wv!!.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) { // Activities and WebViews measure progress with different scales.
// The progress meter will automatically disappear when we reach 100%
//				     activity.setProgress(progress * 1000);
                if (progress == 1) showProgress()
                /*   pd.show();
                pd.setProgress(0);
                pd.setProgress(progress * 1000);

                pd.incrementProgressBy(progress);
                if (progress == 100 && pd.isShowing())
                    pd.dismiss();*/if (progress == 100) hideProgress()
            }

            // For 3.0+ Devices (Start)
// onActivityResult attached before constructor
            protected fun openFileChooser(uploadMsg: ValueCallback<Uri?>?, acceptType: String?) {
                mUploadMessage = uploadMsg
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE)
            }

            // For Lollipop 5.0+ Devices
            override fun onShowFileChooser(mWebView: WebView, filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams: FileChooserParams): Boolean {
                if (uploadMessage != null) {
                    uploadMessage!!.onReceiveValue(null)
                    uploadMessage = null
                }
                uploadMessage = filePathCallback
                val intent = fileChooserParams.createIntent()
                try {
                    startActivityForResult(intent, REQUEST_SELECT_FILE)
                } catch (e: ActivityNotFoundException) {
                    uploadMessage = null
                    Toast.makeText(applicationContext, "Cannot Open File Chooser", Toast.LENGTH_LONG).show()
                    return false
                }
                return true
            }

            //For Android 4.1 only
            protected fun openFileChooser(uploadMsg: ValueCallback<Uri?>?, acceptType: String?, capture: String?) {
                mUploadMessage = uploadMsg
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE)
            }

            protected fun openFileChooser(uploadMsg: ValueCallback<Uri?>?) {
                mUploadMessage = uploadMsg
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE)
            }
        }
        wv!!.settings.pluginState = WebSettings.PluginState.ON
        wv!!.settings.javaScriptEnabled = true
        wv!!.settings.domStorageEnabled = true
        wv!!.settings.setAppCacheEnabled(true)
        wv!!.settings.loadsImagesAutomatically = true
        /*26-11-2019*/wv!!.settings.allowFileAccess = true
        wv!!.settings.allowContentAccess = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wv!!.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        wv!!.loadUrl(url)
        // wv.loa
//		 wv.requestFocus(View.FOCUS_DOWN);
//		 wv.requestFocus();
        wv!!.requestFocus(View.FOCUS_DOWN or View.FOCUS_UP)
        //		 wv.requestFocus(View.FOCUSABLES_ALL);
        val wbsettings = wv!!.settings
        wbsettings.builtInZoomControls = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptThirdPartyCookies(wv, true)
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
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null) return
                uploadMessage!!.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent))
                uploadMessage = null
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
// Use RESULT_OK only if you're implementing WebView inside an Activity
            val result = if (intent == null || resultCode != Activity.RESULT_OK) null else intent.data
            mUploadMessage!!.onReceiveValue(result)
            mUploadMessage = null
        } else Toast.makeText(this.applicationContext, "Failed to Upload Image", Toast.LENGTH_LONG).show()
    }

    private fun setActionBar(mWeb: MWeb?) {
        try {

            action_bar.visibility=View.VISIBLE
            back_imv.visibility=View.VISIBLE
            right_imv.visibility=View.VISIBLE
            mWeb?.let {
               if(!it.actionbar)
               {
                   action_bar.visibility=View.GONE
               }
                if(!it.close_icon)
                {
                    back_imv.visibility=View.GONE
                }
                if(!it.refresh_icon)
                {
                    right_imv.visibility=View.GONE
                }
            }
            copy_dimv!!.visibility = View.GONE
            if (BuildConfig.isShowApi) {
                copy_dimv!!.visibility = View.VISIBLE
                copy_dimv!!.setOnClickListener {
                    var clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clipData = ClipData.newPlainText("Url", url)
                    clipboardManager?.setPrimaryClip(clipData)
                    ToastHandler.newInstance(this@WebActivitykt).mustShowToast("Copied Url $url")
                }
            }


            back_imv!!.setOnClickListener {
                finish()
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
            }
            left_llay!!.setOnClickListener {
                finish()
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
            }
            right_imv!!.setOnClickListener { wv!!.reload() }
        } catch (e: Exception) {
            LogUtilsutility.LOGD("exception", " webview exception " + e.message)
        }
    }

    override fun onBackPressed() { // super.onBackPressed();
        finish()
    }

    override fun onDismiss(obj: Any?) {
        TODO("Not yet implemented")
    }
}

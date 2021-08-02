package com.jpndev.niravu.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import com.jpndev.niravu.MApp
import com.jpndev.niravu.MBanner
import com.jpndev.niravu.R
import com.jpndev.niravu.ThemeType
import com.jpndev.niravu.base.WebActivitykt
import com.jpndev.niravu.utility.LogUtils
import com.jpndev.niravu.viewmodel.AppViewModel


import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rcv_item_home_data_1.view.*
import kotlinx.android.synthetic.main.rcv_item_home_data_5.view.*
import kotlinx.android.synthetic.main.silder_image_item.view.*
import java.util.*

class BannerAdapter (private val context: Context, val appViewModel: AppViewModel?, var items: List<MBanner>?, val clickListener: (MBanner?, Int) -> Unit) : SliderViewAdapter<BannerAdapter.SliderAdapterVH2>() {

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH2 {
        val inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.silder_image_item, null)
        return SliderAdapterVH2(inflate)
    }


    fun openBrowser( link: String?) {
        try {
            link?.let {
                val intent = Intent(Intent. ACTION_VIEW)
                intent.data = Uri.parse(link)

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.  startActivity(intent)
            }
        } catch (e: Exception) {
            e.message
        }

    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH2, position: Int) {

        var results: MBanner?=items?.get(position)
        var url: String? =results?.bg_1
        //text1_txv.visibility=View.VISIBLE
       results?.let {
           it.text1?.let {
               viewHolder.text1_txv.visibility = View.VISIBLE
               defSetText(viewHolder.text1_txv, it)
           } ?: let {
               viewHolder.text1_txv.visibility = View.INVISIBLE
           }
           it.bg_1?.let {
               viewHolder.logo_dimv.visibility = View.VISIBLE
               Picasso.with(context).load(Html.fromHtml(it).toString())
                   .placeholder(R.drawable.placeholder_image)
                   .error(R.drawable.placeholder_image).into(viewHolder.logo_dimv)
           } ?: let {
               viewHolder.logo_dimv.visibility = View.INVISIBLE
           }

         viewHolder.top_rlay.setOnClickListener(View.OnClickListener {
             results?.web_url?.let{
                 LogUtils.LOGD("niravu", "banner weburl  "+it)
                 try {
                     WebActivitykt. startWebActivity(context, it,results?.web_properties)
                 }catch(e:Exception)
                 {
                     LogUtils.LOGD("niravu", "banner Exception =  "+e.message)
                 }
             }?:let {
                 results.browser_url?.let {

                     openBrowser( it)

                 }?:let {
                     /*com.jpndev.niravu.utility.ComingSoonDialog.showDialog(context ,
                         com.jpndev.niravu.MBaseDialog(com.jpndev.niravu.utility.Constants.COMING_SOON)
                     )*/

                 }

             }
         })
           if(it.design_override) {
               it.bg_color?.let {
                   viewHolder.top_rlay.setBackgroundColor(Color.parseColor(it))
               }



               it.text1_color?.let {

                   viewHolder.text1_txv.setTextColor(Color.parseColor(it))
               }
           }
           else
           {

               setBackGrounds( it.type,appViewModel?.appRepository?.prefManager?.getAPPObject(), viewHolder.text1_txv,rlay = viewHolder.top_rlay)
           }





       }








    }


    private fun setTypeBackGrounds(themeType: ThemeType?, text1_txv:TextView?=null, text2_txv:TextView?=null, cardview: CardView?=null, llay: LinearLayout?=null,rlay: RelativeLayout?=null){

        themeType?.let {
            it.text_color_1?.let {

                text1_txv?.setTextColor(Color.parseColor(it))
            }
            it.text_color_2?.let {

                text2_txv?.setTextColor(Color.parseColor(it))
            }
            it.card_color_1?.let {

                cardview?.setCardBackgroundColor(Color.parseColor(it))
            }
            it.bg_color_1?.let {

                llay?.setBackgroundColor(Color.parseColor(it))
            }
            it.bg_color_1?.let {

                rlay?.setBackgroundColor(Color.parseColor(it))
            }
        }
    }
    private fun setBackGrounds(type:Int =1,app: MApp?, text1_txv:TextView?=null, text2_txv:TextView?=null, cardview: CardView?=null, llay: LinearLayout?=null,rlay: RelativeLayout?=null){

        app?.let {

            when(appViewModel?.getMode())
            {
                0->it.system_mode?.let {

                    setTypeBackGrounds(appViewModel?.getViewThemeType(type,it),text1_txv,text2_txv,cardview,llay,rlay)
                }
                1->it.light_mode?.let {
                    setTypeBackGrounds(appViewModel?.getViewThemeType(type,it),text1_txv,text2_txv,cardview,llay,rlay)
                }
                2->it.dark_mode?.let {
                    setTypeBackGrounds(appViewModel?.getViewThemeType(type,it),text1_txv,text2_txv,cardview,llay,rlay)
                }
                else->it.system_mode?.let {

                    setTypeBackGrounds(appViewModel?.getViewThemeType(type,it),text1_txv,text2_txv,cardview,llay,rlay)
                }
            }

        }
    }
    override fun getCount(): Int {
        //slider view count could be dynamic size
        return getItemCount()
    }
    fun getItemCount(): Int {
        if (items != null) {
            return items!!.size
        }
        else
            return 0;
    }

    fun isValid(text: String?): Boolean {
        if (text != null) if (!text.trim { it <= ' ' }.equals("", ignoreCase = true)) return true
        return false

    }

    fun isValid(text: String, size: Int): Boolean {
        if (isValid(text)!!) if (text.length >= size) return true
        return false

    }

    fun isValid(list: List<*>?): Boolean {
        if (list != null) if (list.size > 0) return true
        return false

    }

    fun isValid(list: List<*>, pos: Int): Boolean {
        if (isValid(list)!!) if (list.size >= pos) return true
        return false

    }

    fun getDateString(textdate: String?): String {
        var dates : String =""
        try{
            //"2018-11-30 10:45:20"
            val fmtIn = java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val date : Date = fmtIn.parse(textdate)
            val fmtOut = java.text.SimpleDateFormat("dd-MMM-yy")

            dates= fmtOut.format(date)

        }catch (e :java.lang.Exception)
        {

        }
        return  dates
    }
    fun getTimeString(textdate: String?): String {
        var dates : String =""
        try{
            //"2018-11-30 10:45:20"
            val fmtIn = java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val date : Date = fmtIn.parse(textdate)
            val fmtOut = java.text.SimpleDateFormat("hh:mm a")

            dates= fmtOut.format(date)

        }catch (e :java.lang.Exception)
        {

        }
        return  dates
    }

    fun isValidhtml(text: String?): Boolean {
        if (isValid(text))
            text?.let {
                if (text.contains("http") || text.contains("https")) return true
            }
        return false

    }
/*    fun isValid(list: List<*>?): Boolean {
        if (list != null)
            if (list.size > 0)
                return true
        return false

    }

    fun isValid(list: List<*>, position: Int): Boolean {
        if (isValid(list))
            if (list.size >= position)
                return true
        return false

    }*/

    fun isValid(`object`: Any?): Boolean {
        return if (`object` != null) true else false

    }

  /*  fun isValid(text: String?): Boolean {
        if (text != null) if (!text.trim { it <= ' ' }.equals("", ignoreCase = true)) return true
        return false

    }*/

    fun defString(text: EditText?, def: String): String {
        if (text != null) if (isValid(text.text.toString() + "")!!) return text.text.toString() + ""
        return def

    }

    fun defString(text: EditText?): String {
        if (text != null) if (isValid(text.text.toString() + "")!!) return text.text.toString() + ""
        return ""

    }

    fun defString(text: String?, def: String): String {
        return text ?: def

    }

    fun defString(text: String?): String {
        return text ?: ""

    }
    fun defStringSuffix(text: String?,text2: String): String {
        //return text ?: ""
        return  if(isValid(text))text+text2 else ""
    }
    fun defStringPrefix(text: String?,prefix: String): String {
        //return text ?: ""
        return  if(isValid(text))prefix+text else ""
    }
    fun defSetText(textv: TextView, text: String, def: String) {
        if (isValid(textv)!!)
            textv.text = defString(text, def)

    }

    fun defSetText(textv: TextView, text: String?) {
        if (isValid(textv)!!)
            textv.text = defString(text)

    }
    fun defSetHtmlText(textv: TextView, text: String) {
        if (isValid(textv)) {
            textv.text = Html.fromHtml(defString(text) + "")
            textv.movementMethod = LinkMovementMethod.getInstance()
        }

    }
    fun defSetText(textv: EditText, text: String, def: String) {
        if (isValid(textv)!!)
            textv.setText(defString(text, def))

    }

    fun defSetText(textv: EditText, text: String?) {
        if (isValid(textv)!!)
            textv.setText(defString(text))


    }


    inner class SliderAdapterVH2(var itemView2: View) : SliderViewAdapter.ViewHolder(itemView2) {
        var logo_dimv: ImageView
        var top_rlay: RelativeLayout
        //var textViewDescription: TextView
        var text1_txv: TextView
        init {
            logo_dimv = itemView2.findViewById(R.id.logo_dimv)
            top_rlay = itemView2.findViewById(R.id.top_rlay)
            text1_txv = itemView2.findViewById(R.id.text1_txv)
            text1_txv.visibility=View.VISIBLE
            //  textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider)
        }
    }
}
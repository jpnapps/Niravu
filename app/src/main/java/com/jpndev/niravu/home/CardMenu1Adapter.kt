package com.jpndev.niravu.home

import android.content.Context
import android.graphics.Color
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jpndev.niravu.*
import com.jpndev.niravu.base.BaseViewHolder
import com.jpndev.niravu.utility.LogUtils
import com.jpndev.niravu.viewmodel.AppViewModel
import com.jpndev.utilitylibrary.DeviceFitImageView
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.rcv_item_card_1.view.*
import kotlinx.android.synthetic.main.rcv_item_card_1.view.cardview
import kotlinx.android.synthetic.main.rcv_item_card_1.view.text1_txv
import kotlinx.android.synthetic.main.rcv_item_home_data_5.view.*
import kotlinx.android.synthetic.main.rcv_item_home_data_6.view.*

import java.util.*

class CardMenu1Adapter  (var items: List<MHomeData>?, val context: Context, val appViewModel: AppViewModel?,
                         val clickListener: (MHomeData?, Int, Int) -> Unit) : RecyclerView.Adapter<BaseViewHolder<MHomeData>>(),
    Filterable {

    private  var result_searchlist: List<MHomeData> ?=null
    //  private lateinit var valueFilter: ValueFilter
    /*  override fun getFilter(): Filter {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }*/
    init {
        this.result_searchlist = items
    }
   /* companion object {
        private const val TYPE_0 = 0
        private const val TYPE_1 = 1
        private const val TYPE_2 = 2
        private const val TYPE_3 = 3
    }*/

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    items = result_searchlist as MutableList<MHomeData>?
                } else {
                    val filteredList = ArrayList<MHomeData>()
                    if(isValid(result_searchlist)) {
                        for (row in result_searchlist!!) {
                            if (row.text1!!.toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row)
                            }
                        }
                    }else
                    {
                        items = result_searchlist as MutableList<MHomeData>?
                    }
                    items = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = items
                items?.let {
                    filterResults.count= items!!.size
                }
                return filterResults
            }
            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                items = filterResults.values as ArrayList<MHomeData>
                notifyDataSetChanged()
            }
        }
    }



    public fun clear() {
        try {


            items = emptyList()
        }catch (e:Exception)
        {
            e.message
        }
    }
    override fun getItemViewType(position: Int): Int {
        val mHomeData: MHomeData? = items?.get(position)
        return  mHomeData?.type?:-1
        /* return when (mHomeData.type) {
             "0" -> TYPE_0
             "2" -> TYPE_1
             "2" -> TYPE_2
             "" -> TYPE_3
             else -> throw IllegalArgumentException("Invalid type of data " + position)
         }*/
    }



    //--------onCreateViewHolder: inflate layout with view holder-------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MHomeData> {
        LogUtils.LOGD("niravu", "Adapter onCreateViewHolder  ")
        return when (viewType) {
            30,31,32,33,34,40,200 -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.rcv_item_card_1, parent, false)
                ViewHolderType1(view)
            }
          
         /*   10 -> {
                LogUtils.LOGD("niravu", "Adapter onCreateViewHolder 1000000 ")
                val view =
                    LayoutInflater.from(context).inflate(R.layout.rcv_item_banner_1, parent, false)
                BannerViewHolderType10(view)
            }
            11 -> {
                LogUtils.LOGD("niravu", "Adapter onCreateViewHolder 1000000 ")
                val view =
                    LayoutInflater.from(context).inflate(R.layout.rcv_item_banner_2, parent, false)
                BannerViewHolderType10(view)
            }*/
             else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    override fun onBindViewHolder(holder: BaseViewHolder<MHomeData>, position: Int) {
        try {
            (holder as BaseViewHolder).bind(items?.get(position),clickListener,context,position,appViewModel)

        }catch (e:Exception)
        {
            e.message
        }
    }


    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        if (items != null) {
            return items!!.size
        }
        else
            return 0;
    }
    /*fun isValid(list: List<*>?): Boolean {
        if (list != null)
            if (list.size > 0)
                return true
        return false

    }
*/



    private fun setTypeBackGrounds(themeType: ThemeType?, text1_txv: TextView?=null, text2_txv: TextView?=null, cardview: CardView?=null, llay: LinearLayout?=null,menu_bg_dimv:DeviceFitImageView?=null){

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
            LogUtils.LOGD("d_theme", "setTypeBackGrounds  bg_1  = "+it.bg_1)
            it.bg_1?.let {
               val bg1:String=it

                menu_bg_dimv?.let {
                    menu_bg_dimv?.visibility = View.VISIBLE
                    cardview?.setCardBackgroundColor(context.resources.getColor(R.color.transparent))
                    com.squareup.picasso.Picasso.with(context)
                    .load(android.text.Html.fromHtml(bg1).toString())
                    .into(it);
                          }
            } ?: let {
                menu_bg_dimv?.visibility = View.INVISIBLE
            }
        }
    }
     fun setBackGrounds(type:Int =1, app: MApp?, text1_txv: TextView?=null, text2_txv: TextView?=null, cardview: CardView?=null, llay: LinearLayout?=null,menu_bg_dimv:DeviceFitImageView?=null) {
        LogUtils.LOGD("d_theme", "setBackGrounds  mode  = "+appViewModel?.getMode())
        LogUtils.LOGD("d_theme", "setBackGrounds  app  = "+app?.app_name)
        app?.let {

            when(appViewModel?.getMode())
            {

                0->it.system_mode?.let {

                    setTypeBackGrounds(appViewModel?.getViewThemeType(type,it),text1_txv,text2_txv,cardview,llay,menu_bg_dimv)
                }
                1->it.light_mode?.let {
                    setTypeBackGrounds(appViewModel?.getViewThemeType(type,it),text1_txv,text2_txv,cardview,llay,menu_bg_dimv)
                }
                2->it.dark_mode?.let {
                    setTypeBackGrounds(appViewModel?.getViewThemeType(type,it),text1_txv,text2_txv,cardview,llay,menu_bg_dimv)
                }
                else->it.system_mode?.let {

                    setTypeBackGrounds(appViewModel?.getViewThemeType(type,it),text1_txv,text2_txv,cardview,llay,menu_bg_dimv)
                }
            }

        }
    }


    inner class ViewHolderType1 (view: View) : BaseViewHolder<MHomeData>(view) {
        override fun bind(
            item: MHomeData?,
            clickListener: (MHomeData?, Int, Int) -> Unit,
            context: Context,
            position: Int,
            appViewModel: AppViewModel?
        ) {
            try {

                item?.let {

                    defSetText(itemView.text1_txv, item.text1?.let { item.text1 } ?: let { "" })
                    //defSetText(itemView.text1_txv, item.text2?.let { item.text2 } ?: let { "" })
                    if(it.design_override) {
                        it.text_color_1?.let {

                            itemView.text1_txv.setTextColor(Color.parseColor(it))
                        }
                        /*it.text_color_2?.let {

                            itemView.text2_txv.setTextColor(Color.parseColor(it))
                        }*/
                        it.bg_color?.let {

                            itemView.cardview.setCardBackgroundColor(Color.parseColor(it))
                        }

                        it.bg_1?.let {
                            itemView.cardview.setCardBackgroundColor(context.resources.getColor(R.color.transparent))
                            itemView.menu_bg_dimv.visibility=View.VISIBLE
                            com.squareup.picasso.Picasso.with(context)
                                .load(android.text.Html.fromHtml(it).toString())
                                .into(itemView.menu_bg_dimv);
                        }?:let{
                            itemView.menu_bg_dimv.visibility=View.INVISIBLE
                        }
                    }
                    else
                    {
                        setBackGrounds( it.type,appViewModel?.appRepository?.prefManager?.getAPPObject(), itemView.text1_txv,null,itemView.cardview,menu_bg_dimv = itemView.menu_bg_dimv)



                    }


                    if(it.click) {
                        itemView.setOnClickListener {
                           // LogUtils.LOGD("niravu", "Adapter partItemClicked  ")
                            LogUtils.LOGD("niravu", "card Adapter partItemClicked  "+item.text1+" type = "+item.type )
                            clickListener(item, position, 1)
                        }
                    }



                }
            } catch (e: Exception) {
                e.message
            }
        }



    }



   

   /* //----------------BannerViewHolderType10 | Image-------------
    inner class BannerViewHolderType10(itemView: View) : BaseViewHolder<MHomeData>(itemView) {


        private fun setAdsDatas(img_slideview: SliderView, imagelist :List<MBanner>, context: Context, appViewModel: AppViewModel?) {
            try {


                val adapter: BannerAdapter = BannerAdapter(context,appViewModel, imagelist,{ maction : MBanner?, postion:Int-> partItemClicked(maction,postion) })
                img_slideview.setSliderAdapter(adapter)
                //  img_slideview.layout_constraintDimensionRatio
                img_slideview.setIndicatorAnimation(IndicatorAnimations.SWAP) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                //img_slideview.setSliderTransformAnimation(SliderAnimations.CUBEOUTDEPTHTRANSFORMATION)
                img_slideview.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
                img_slideview.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH)
                // img_slideview.setIndicatorSelectedColor(context.resources.getColor(R.color.colorPrimaryDark))
                // img_slideview.setIndicatorUnselectedColor(Color.WHITE)
                img_slideview.setScrollTimeInSec(6) //set scroll delay in seconds :
                img_slideview.setOnIndicatorClickListener(DrawController.ClickListener { position -> img_slideview.setCurrentPagePosition(position) })
                // img_slideview.
                img_slideview.startAutoCycle();
            }catch (e:Exception)
            {
                e.message
            }
        }

        override fun bind(
            item: MHomeData?,
            clickListener: (MHomeData?, Int, Int) -> Unit,
            context: Context,
            position: Int,
            appViewModel: AppViewModel?
        ) {


            try {
                LogUtils.LOGD("niravu", "BannerViewHolderType10 ")
                item?.let {
                    LogUtils.LOGD("niravu", "BannerViewHolderType10  ")
                    setAdsDatas(itemView.img_slideview,it!!.banner!!,context,appViewModel)
                    if(it.click) {
                        itemView.setOnClickListener {
                            //clickListener(item, position, 1)
                        }
                    }

                }
            } catch (e: Exception) {
                LogUtils.LOGD("niravu", "Adapter BannerViewHolderType10  ex = "+e.message)
            }
        }

        private fun partItemClicked(data: MBanner?, postion:Int) {

        }
    }
*/



   




    private fun isRequestCancel(actions: List<String>): Boolean {
        var booobj: Boolean = false
        try {

            if (actions.any { it .equals("6",true)  || it .equals("cancel",true) })
            {

            }


        } catch (e: Exception) {
            booobj = false
            e.message
        }

        return booobj
    }


    private fun get2F(addtocard_total_pound: String?): String? {
        var text: String? = null
        try {
            text = kotlin.String.format("%.2f", addtocard_total_pound?.toDouble())
            if (isValid(text))
                text = "" + text
        } catch (e: Exception) {
            text = null
            e.message
        }

        return text
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
    fun isValid(list: List<*>?): Boolean {
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

    }

    fun isValid(`object`: Any?): Boolean {
        return if (`object` != null) true else false

    }

    fun isValid(text: String?): Boolean {
        if (text != null) if (!text.trim { it <= ' ' }.equals("", ignoreCase = true)) return true
        return false

    }

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



}
package com.jpndev.niravu.quiz

import android.content.Context
import android.graphics.Color
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jpndev.niravu.MApp
import com.jpndev.niravu.MQuiz
import com.jpndev.niravu.R
import com.jpndev.niravu.ThemeType
import com.jpndev.niravu.base.BaseViewHolder
import com.jpndev.niravu.utility.LogUtils
import com.jpndev.niravu.viewmodel.AppViewModel
import com.jpndev.utilitylibrary.DeviceFitImageView
import kotlinx.android.synthetic.main.rcv_item_qs_no.view.*
import java.util.*

class QuestionIndexAdapter   (var items: List<MQuiz>?, val context: Context, val appViewModel: AppViewModel?,
                                               val clickListener: (MQuiz?, Int, Int) -> Unit) : RecyclerView.Adapter<BaseViewHolder<MQuiz>>(),
    Filterable {

    private  var result_searchlist: List<MQuiz> ?=null
    //  private lateinit var valueFilter: ValueFilter
    /*  override fun getFilter(): Filter {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }*/
    init {
        this.result_searchlist = items
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    items = result_searchlist as MutableList<MQuiz>?
                } else {
                    val filteredList = ArrayList<MQuiz>()
                    if(isValid(result_searchlist)) {
                        for (row in result_searchlist!!) {
                            if (row.question!!.toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row)
                            }
                        }
                    }else
                    {
                        items = result_searchlist as MutableList<MQuiz>?
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
                items = filterResults.values as ArrayList<MQuiz>
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
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getItemViewType(position: Int): Int {
        //val MQuiz: MQuiz? = items?.get(position)
       // return  MQuiz?.type?:-1
       // return 200
        return position

    }



    //--------onCreateViewHolder: inflate layout with view holder-------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MQuiz> {
        LogUtils.LOGD("niravu", "Adapter onCreateViewHolder  ")
    /*    return when (viewType) {
            200 -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.rcv_item_qs_no, parent, false)
                val  b: ViewHolderType1 =ViewHolderType1(view)
                b.onInit()
                b
               *//* ViewHolderType1(view)*//*
            }


            else -> throw IllegalArgumentException("Invalid view type")
        }*/
        val view = LayoutInflater.from(context)
            .inflate(R.layout.rcv_item_qs_no, parent, false)
        val  b: ViewHolderType1 =ViewHolderType1(view)
        b.onInit()

        return b
    }


    override fun onBindViewHolder(holder: BaseViewHolder<MQuiz>, position: Int) {
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




    private fun setTypeBackGrounds(themeType: ThemeType?, text1_txv: TextView?=null, text2_txv: TextView?=null, cardview: CardView?=null, llay: LinearLayout?=null, menu_bg_dimv: DeviceFitImageView?=null){

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
            LogUtils.LOGD("d_theme", "setBackGrounds  bg_1  = "+it.bg_1)
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
    fun setBackGrounds(type:Int =1, app: MApp?, text1_txv: TextView?=null, text2_txv: TextView?=null, cardview: CardView?=null, llay: LinearLayout?=null, menu_bg_dimv: DeviceFitImageView?=null) {
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


    inner class ViewHolderType1 (view: View) : BaseViewHolder<MQuiz>(view) {


        fun onInit()
        {
            itemView?.top_clay?.setSelected(false)
        }


        override fun bind(
            item: MQuiz?,
            clickListener: (MQuiz?, Int, Int) -> Unit,
            context: Context,
            position: Int,
            appViewModel: AppViewModel?
        ) {
            try {

                item?.let {

                    defSetHtmlText(itemView.index_txv, (position+1).toString())
                    itemView.top_clay.setSelected(false)
                    if((appViewModel?.qs_view_mode?:false))
                    {
                        LogUtils.LOGD("quiz_2", "QuestionIndexAdapter ViewHolderType1  viewmode if ")
                        if(isValid(item.your_answer)) {
                            if (item.correct_answer.equals(item.your_answer, true)) {
                              //  itemView.top_clay.setBackground(context.resources.getDrawable(R.drawable.shape2_true))

                                itemView.top_clay.setBackgroundResource(R.drawable.shape2_true)
                            } else {

                                 itemView.top_clay.setBackgroundResource(R.drawable.shape2_false)
                                //itemView.top_clay.setBackground(context.resources.getDrawable(R.drawable.shape2_false))

                              }
                        }
                       /* else{

                            LogUtils.LOGD("quiz_2", "QuestionIndexAdapter ViewHolderType1 viewmode else ")
                            //itemView.top_clay.setSelected(false)
                        }
*/

                    }
                    else {
                        LogUtils.LOGD("quiz_2", "QuestionIndexAdapter ViewHolderType1 if ")

                        itemView.top_clay.setSelected(false)
                        /*  itemView.option_txv.setSelected(false)
                          itemView.option_no_txv.setSelected(false)*/
                        if (it.your_answer?.isNotBlank() ?: false) {
                            //LogUtils.LOGD("quiz", "QS Index Adapter  setSelected pos=  "+(position).toString());
                            itemView.top_clay.setSelected(true)
                            /*   itemView.option_txv.setSelected(true)
                            itemView.option_no_txv.setSelected(true)*/
                        }

                    }

                    itemView.index_txv.setOnClickListener {

                        clickListener(item, position, 1)
                    }








                    if(it.design_override) {
                        it.question_text_color_1?.let {

                            itemView.index_txv.setTextColor(Color.parseColor(it))
                        }


                    }
                    else
                    {
                        setBackGrounds( it.type,appViewModel?.appRepository?.prefManager?.getAPPObject(), itemView.index_txv,null,null,menu_bg_dimv =null)



                    }








                }
            } catch (e: Exception) {
                e.message
                LogUtils.LOGD("quiz", "QS Index Adapter      e.message  "+   e.message)
            }
        }



    }













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
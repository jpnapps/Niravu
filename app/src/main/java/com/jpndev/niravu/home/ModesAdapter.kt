package com.jpndev.niravu.home

import android.content.Context
import android.graphics.Color
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpndev.niravu.MBanner
import com.jpndev.niravu.MModeData
import com.jpndev.niravu.R
import com.jpndev.niravu.base.BaseViewHolder
import com.jpndev.niravu.utility.LogUtils
import com.jpndev.niravu.viewmodel.AppViewModel


import kotlinx.android.synthetic.main.rcv_item_modes.view.*

import java.util.*

class ModesAdapter (var items: List<MModeData>?, val context: Context, val appViewModel: AppViewModel?, val clickListener: (MModeData?, Int, Int) -> Unit) : RecyclerView.Adapter<BaseViewHolder<MModeData>>(),
    Filterable {

    private  var result_searchlist: List<MModeData> ?=null
    //  private lateinit var valueFilter: ValueFilter
    /*  override fun getFilter(): Filter {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }*/
    init {
        this.result_searchlist = items
    }



    internal fun setItems(words: List<MModeData>?) {
        this.items = words
        notifyDataSetChanged()
    }
    internal fun getItems() : List<MModeData>?{
        return  items
    }




    public fun clear() {
        try {


            items= emptyList()
        }catch (e:Exception)
        {
            e.message
        }
    }

    
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    items = result_searchlist as MutableList<MModeData>?
                } else {
                    val filteredList = ArrayList<MModeData>()
                    if(isValid(result_searchlist)) {
                        for (row in result_searchlist!!) {
                            if (row.text1!!.toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row)
                            }
                        }
                    }else
                    {
                        items = result_searchlist as MutableList<MModeData>?
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
                items = filterResults.values as ArrayList<MModeData>
                notifyDataSetChanged()
            }
        }
    }



    override fun getItemViewType(position: Int): Int {
       // val mHomeData: MModeData? = items?.get(position)
       // return  mHomeData?.type?:-1
        return 1
        /* return when (mHomeData.type) {
             "0" -> TYPE_0
             "2" -> TYPE_1
             "2" -> TYPE_2
             "" -> TYPE_3
             else -> throw IllegalArgumentException("Invalid type of data " + position)
         }*/
    }



    //--------onCreateViewHolder: inflate layout with view holder-------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MModeData> {
        LogUtils.LOGD("Modes", "Adapter onCreateViewHolder  ")
        return when (viewType) {
            1 -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.rcv_item_modes, parent, false)
                ViewHolderType1(view)
            }


            else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    override fun onBindViewHolder(holder: BaseViewHolder<MModeData>, position: Int) {
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








    inner class ViewHolderType1 (view: View) : BaseViewHolder<MModeData>(view) {
        override fun bind(
            item: MModeData?,
            clickListener: (MModeData?, Int, Int) -> Unit,
            context: Context,
            position: Int,appViewModel:AppViewModel?
        ) {
            try {

                item?.let {
                    LogUtils.LOGD("Modes", "MAadater  ViewHolderType1 contents =  "+it?.text1)
                    defSetText(itemView.text1_txv, item.text1?.let { item.text1 } ?: let { "" })
                /*    it.text1_color?.let {

                        itemView.text1_txv.setTextColor(Color.parseColor(it))
                    }
                    it.bg1_color?.let {

                        itemView.bg_view.setBackgroundColor(Color.parseColor(it))
                    }*/
                    if(it.isSelected)
                      itemView?.select_dimv?.setImageDrawable(context.resources.getDrawable(R.drawable.icn_radio_button_checked))
                    else
                        itemView?.select_dimv?.setImageDrawable(context.resources.getDrawable(R.drawable.icn_radio_button_unchecked))
                    itemView.setOnClickListener {
                        LogUtils.LOGD("niravu", "Adapter partItemClicked  ")
                        clickListener(item, position, 1)
                    }

                  /*  defSetText(itemView.text2_txv, item.text2?.let { item.text2 } ?: let { "" })

                    it.text2_color?.let {

                        itemView.text2_txv.setTextColor(Color.parseColor(it))
                    }
                    it.bg_color?.let {

                        itemView.cardview.setCardBackgroundColor(Color.parseColor(it))
                    }
                    if(it.click) {
                        itemView.setOnClickListener {
                            LogUtils.LOGD("niravu", "Adapter partItemClicked  ")
                            clickListener(item, position, 1)
                        }
                    }*/
                }
            } catch (e: Exception) {

                LogUtils.LOGD("Modes", "MAadater  ViewHolderType1 exce =  "+ e.message)
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
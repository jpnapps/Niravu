package com.jpndev.niravu.quiz

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpndev.niravu.*
import com.jpndev.niravu.base.BaseViewHolder
import com.jpndev.niravu.home.CardMenu1Adapter
import com.jpndev.niravu.utility.LogUtils
import com.jpndev.niravu.utility.WrapContentLinearLayoutManager
import com.jpndev.niravu.viewmodel.AppViewModel
import com.jpndev.utilitylibrary.DeviceFitImageView
import com.jpndev.utilitylibrary.WrapLinearLayoutManager
import kotlinx.android.synthetic.main.rcv_item_qs.view.*
import kotlinx.android.synthetic.main.rcv_item_qs.view.chameleon
import kotlinx.android.synthetic.main.rcv_item_qs.view.rcv
import xyz.sangcomz.chameleon.Chameleon
import java.util.*

class QuizAdapter  (var items: List<MQuiz>?, val context: Context, val appViewModel: AppViewModel?,
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
    override fun getItemViewType(position: Int): Int {
        val MQuiz: MQuiz? = items?.get(position)
        return  MQuiz?.type?:-1
        /* return when (MQuiz.type) {
             "0" -> TYPE_0
             "2" -> TYPE_1
             "2" -> TYPE_2
             "" -> TYPE_3
             else -> throw IllegalArgumentException("Invalid type of data " + position)
         }*/
    }



    //--------onCreateViewHolder: inflate layout with view holder-------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MQuiz> {
        LogUtils.LOGD("niravu", "Adapter onCreateViewHolder  ")
        return when (viewType) {
            200 -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.rcv_item_qs, parent, false)
              val  b: ViewHolderType1 =ViewHolderType1(view)
                b.onInit()
                b
            }

         
            else -> throw IllegalArgumentException("Invalid view type")
        }
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
    /*fun isValid(list: List<*>?): Boolean {
        if (list != null)
            if (list.size > 0)
                return true
        return false

    }
*/



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
        override fun bind(
            item: MQuiz?,
            clickListener: (MQuiz?, Int, Int) -> Unit,
            context: Context,
            position: Int,
            appViewModel: AppViewModel?
        ) {
            try {

                item?.let {


               //   val layoutManager = WrapContentLinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

                    LogUtils.LOGD("quiz", "QuizAdapter onSetAdapter refresh "+position)
                    if(isValidList(item?.choices))
                    {
                      ///  itemView.clay.visibility=View.VISIBLE
                        itemView.chameleon.showState(Chameleon.STATE.CONTENT)
                        onSetAdapter(position,item,item?.choices,itemView.rcv)
                    }
                    else
                    {
                        itemView.chameleon.showState(Chameleon.STATE.EMPTY)
                        //itemView.clay.visibility=View.GONE
                    }

                    defSetHtmlText(itemView.question_txv, (position+1).toString()+". "+item.question?.let { item.question } ?: let { "" })
                    if(it.design_override) {
                        it.question_text_color_1?.let {

                            itemView.question_txv.setTextColor(Color.parseColor(it))
                        }

                      /*  it.bg_color?.let {

                            itemView.cardview.setCardBackgroundColor(Color.parseColor(it))
                        }

                        it.bg_1?.let {
                            itemView.cardview.setCardBackgroundColor(context.resources.getColor(R.color.transparent))
                            itemView.menu_bg_dimv.visibility= View.VISIBLE
                            com.squareup.picasso.Picasso.with(context)
                                .load(android.text.Html.fromHtml(it).toString())
                                .into(itemView.menu_bg_dimv);
                        }?:let{
                            itemView.menu_bg_dimv.visibility= View.INVISIBLE
                        }*/
                    }
                    else
                    {
                        setBackGrounds( it.type,appViewModel?.appRepository?.prefManager?.getAPPObject(), itemView.question_txv,null,null,menu_bg_dimv =null)



                    }



                    /*    itemView.setOnClickListener {
                            LogUtils.LOGD("niravu", "Adapter partItemClicked  ")
                            clickListener(item, position, 1)
                        }
*/



                }
            } catch (e: Exception) {
                e.message
            }
        }
        fun onInit()
        {
            val layoutManager = LinearLayoutManager(context)
            itemView. rcv.setLayoutManager(layoutManager)

              val mDivider: Drawable? = ContextCompat.getDrawable(context, R.drawable.divider_1)
              mDivider?.let{
                  val hItemDecoration = DividerItemDecoration(
                      context,
                      DividerItemDecoration.VERTICAL
                  )

                  hItemDecoration.setDrawable(it)
                  itemView. rcv.addItemDecoration(hItemDecoration)
              }
        }



    }


    fun isValidList(list: List<*>?): Boolean {
        if (list != null)
            if (list.size > 0)
                return true
        return false

    }

    var child_adapter : QzChoiceAdapter? =null
    fun onSetAdapter(pos:Int,mQuiz: MQuiz,items: List<String>?=null,rcv:RecyclerView) {
        LogUtils.LOGD("quiz", "QuizAdapter onSetAdapter "+pos)
        mQuiz.parent_postion=pos
        child_adapter = QzChoiceAdapter(mQuiz,items, context,appViewModel, { mQuiz: MQuiz,mgame : String?, postion:Int, purpose:Int  -> partItemClicked(mQuiz,mgame,postion,purpose) })
        child_adapter?.parent_postion=pos
        rcv.setAdapter(child_adapter)
    }



    private fun partItemClicked(mQuiz: MQuiz,item:  String?, postion:Int, purpose:Int=0) {
        item?.let {
            LogUtils.LOGD("quiz", "QuizAdapter partItemClicked  "+item+ " parent pos = "+mQuiz?.parent_postion)
            var isanswered:Boolean=false
            if(mQuiz.your_answer?.isNotBlank()?:false) {
                isanswered = true
                mQuiz.is_answered=true
            }


            mQuiz.your_answer=item
           // mQuiz.answered=(mQuiz.answered?:0)+1

         child_adapter?.notifyDataSetChanged()

       // child_adapter?.notifyItemChanged(child_adapter?.parent_postion?:0)
          //  if(!isanswered)
            clickListener(mQuiz,  mQuiz?.parent_postion?:-1, 2)
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
package com.jpndev.niravu.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jpndev.niravu.MBanner
import com.jpndev.niravu.MModeData
import com.jpndev.niravu.NiravuActivity
import com.jpndev.niravu.R
import com.jpndev.niravu.utility.LogUtils
import com.jpndev.niravu.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.activity_modes.*
import xyz.sangcomz.chameleon.Chameleon


class ModesActivity : NiravuActivity() {
    private lateinit var appViewModel: AppViewModel
    private fun initAppViewModel() {
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java!!)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modes)
        initAppViewModel()

        setValues()
        setClicks()
    }

    private fun setClicks() {
        close_dimv.setOnClickListener(View.OnClickListener {
            onBackFinish()
        })



    }

    override fun onBackPressed()
    {
        // super.onBackPressed();
        hideProgress()
        finish()
    }

    private fun setValues() {
        val layoutManager2 = LinearLayoutManager(this@ModesActivity)
        modes_rcv.setLayoutManager(layoutManager2)

        val mDivider: Drawable? = ContextCompat.getDrawable(this@ModesActivity, R.drawable.divider_2)
        mDivider?.let{
            val hItemDecoration = DividerItemDecoration(
                this@ModesActivity,
                DividerItemDecoration.VERTICAL
            )

            hItemDecoration.setDrawable(mDivider!!)
            modes_rcv.addItemDecoration(hItemDecoration)
        }


        onSetModeAdapter()
        appViewModel.appRepository.mld_mode_data.observe(this, Observer { items ->
            items?.let {

                modes_chameleon.showState(Chameleon.STATE.CONTENT)
                onSetModeAdapter(items)
            } ?: let {
                modes_chameleon.showState(Chameleon.STATE.EMPTY)
            }
        })
    }
    var modes_adapter : ModesAdapter? =null
    fun onSetModeAdapter(items: List<MModeData>?=null) {

        modes_adapter?.let {
            LogUtils.LOGD("Modes", "HA onSetModeAdapter updated=  "+items?.size)
            modes_adapter = ModesAdapter(items,this@ModesActivity,appViewModel, { mgame : MModeData?, postion:Int, purpose:Int  -> modeItemClicked(mgame,postion,purpose) })
            modes_rcv.setAdapter(modes_adapter)
        }?:let {
            LogUtils.LOGD("Modes", "HA onSetModeAdapter new =  "+items?.size)
            modes_adapter = ModesAdapter(items,this@ModesActivity,appViewModel, { mgame : MModeData?, postion:Int, purpose:Int  -> modeItemClicked(mgame,postion,purpose) })
            modes_rcv.setAdapter(modes_adapter)
        }
        modes_adapter?.getItems()?.let {
            LogUtils.LOGD("Modes", "HA  onSetModeAdapter contents =  "+it?.size)
            if( it.isNotEmpty()) {
                LogUtils.LOGD("Modes", "HA  onSetModeAdapter isNotEmpty =  "+it?.size)
                modes_chameleon.showState(Chameleon.STATE.CONTENT)
            }
            else
                modes_chameleon.showState(Chameleon.STATE.EMPTY)
        }




    }



private fun modeItemClicked(data: MModeData?, postion:Int,purpose:Int) {

}
    override fun onDismiss(obj: Any?) {

    }
}

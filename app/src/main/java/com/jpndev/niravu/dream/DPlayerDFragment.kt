package com.jpndev.niravu.dream

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.jpndev.niravu.MBaseDialog
import com.jpndev.niravu.base.BaseDialogFragment
import com.jpndev.niravu.viewmodel.AppViewModel

class DPlayerDFragment  : BaseDialogFragment() {





    private lateinit var appViewModel: AppViewModel
    private fun initAppViewModel() {
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java!!)

    }

    var mBaseDialog: MBaseDialog?=null
    var mContext: Activity?=null
    var from: String? = ""
    companion object {

        var closed = true
        fun newInstance(content: String): DPlayerDFragment {
            val f = DPlayerDFragment()
            val args = Bundle()
            f.arguments = args

            return f
        }
        fun newInstance(): DPlayerDFragment {
            val f = DPlayerDFragment()
            val args = Bundle()
            f.arguments = args

            return f
        }
        fun newInstance(mBaseDialog: MBaseDialog): DPlayerDFragment {
            val f = DPlayerDFragment()
            val args = Bundle()
            args.putParcelable("DFragment",mBaseDialog)
            f.arguments = args

            return f
        }
        fun newInstance(mBaseDialog: MBaseDialog, from:String): DPlayerDFragment {
            val f = DPlayerDFragment()
            val args = Bundle()
            args.putParcelable("DFragment",mBaseDialog)
            args.putString("from", from)
            f.arguments = args

            return f
        }
        fun showDialog(context: Activity) {
            try {

                if (!context.isFinishing && !context.isDestroyed) {
                    val fragmentManager = (context as FragmentActivity).supportFragmentManager
                    val newFragment = DPlayerDFragment.newInstance()
                    val transaction = fragmentManager.beginTransaction()
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    transaction.add(android.R.id.content, newFragment)
                        .addToBackStack(null).commit()
                }
            } catch (e: Exception) {
                e.message
            }

        }
        fun showDialog(context: Activity, mBaseDialog: MBaseDialog) {
            try {

                if (!context.isFinishing && !context.isDestroyed) {
                    val fragmentManager = (context as FragmentActivity).supportFragmentManager
                    val newFragment = DPlayerDFragment.newInstance(mBaseDialog)
                    val transaction = fragmentManager.beginTransaction()
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    transaction.add(android.R.id.content, newFragment)
                        .addToBackStack(null).commit()
                }
            } catch (e: Exception) {
                e.message
            }

        }
        fun showDialog(context: Activity, mBaseDialog: MBaseDialog, from:String) {
            try {
                if (!context.isFinishing && !context.isDestroyed) {
                    val fragmentManager = (context as FragmentActivity).supportFragmentManager
                    val newFragment = DPlayerDFragment.newInstance(mBaseDialog,from)
                    val transaction = fragmentManager.beginTransaction()
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    transaction.add(android.R.id.content, newFragment)
                        .addToBackStack(null).commit()
                }
            } catch (e: Exception) {
                e.message
            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBaseDialog = getArguments()?.getParcelable("DFragment")
        from= mBaseDialog?.from

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        /*getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FUpay_request_cftxvLLSCREEN);*/

        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.df_trade_wallets, container, false)
    }

    override fun onSetValues(view: View?) {
    }

    override fun onSetActionBar(view: View?) {
    }

   //var listener : OnTradeWalletSelectListner?=null
}
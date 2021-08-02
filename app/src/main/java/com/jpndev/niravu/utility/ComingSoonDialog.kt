package com.jpndev.niravu.utility

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.jpndev.niravu.MBaseDialog
import com.jpndev.niravu.R
import com.jpndev.niravu.interfaces.OnDismissListner
import com.jpndev.utilitylibrary.ToastHandler
import kotlinx.android.synthetic.main.activity_coming_soon_dialog.*


class ComingSoonDialog  : DialogFragment() {


    var mBaseDialog: MBaseDialog?=null
    var context: Activity?=null
    var from: String? =  Constants.COMING_SOON
    /*  class ComingSoonDialog()
      {

      }
  */
    companion object {

        var closed = true
        /**
         * Create a new instance of CustomDialogFragment, providing "num" as an
         * argument.
         */
        fun newInstance(content: String): ComingSoonDialog {
            val f = ComingSoonDialog()

            // Supply num input as an argument.
            val args = Bundle()
            // args.putString("content", content)
            f.arguments = args

            return f
        }
        fun newInstance(): ComingSoonDialog {
            val f = ComingSoonDialog()

            // Supply num input as an argument.
            val args = Bundle()
            // args.putString("content", content)
            f.arguments = args

            return f
        }
        fun newInstance(mBaseDialog: MBaseDialog): ComingSoonDialog {
            val f = ComingSoonDialog()

            // Supply num input as an argument.
            val args = Bundle()
            args.putParcelable("ccrb_pay_order",mBaseDialog)
            // args.putString("content", content)
            f.arguments = args

            return f
        }
        fun newInstance(mBaseDialog: MBaseDialog, from:String): ComingSoonDialog {
            val f = ComingSoonDialog()

            // Supply num input as an argument.
            val args = Bundle()
            args.putParcelable("ccrb_pay_order",mBaseDialog)
            args.putString("from", from)
            // args.putString("content", content)
            f.arguments = args

            return f
        }
        fun showDialog(context: Activity) {
            try {
                if (!context.isFinishing && !context.isDestroyed) {
                    val fragmentManager = (context as FragmentActivity).supportFragmentManager
                    val newFragment = ComingSoonDialog.newInstance()
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
                    val newFragment = ComingSoonDialog.newInstance(mBaseDialog)
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
                    val newFragment = ComingSoonDialog.newInstance(mBaseDialog,from)
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
        mBaseDialog = getArguments()?.getParcelable("ccrb_pay_order")
        //isGlobalUrlcheck = getArguments().getBoolean("isGlobalUrl");
        //from = getArguments()?.getString("from", Constants.COMING_SOON)

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


        return inflater?.inflate(R.layout.activity_coming_soon_dialog, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        closed = false
        mBaseDialog?.let {

            // mBaseDialog?.title?.let {   defSetText(pay_request_cftxv,mBaseDialog?.title)}?:let {  }
          

            if(mBaseDialog!!.close_icon!!)
                close_rlay.visibility= View.VISIBLE
            else
                close_rlay.visibility= View.GONE
        }?:let {

           
        }
        // amount_cftxv.text=""

             val listener = activity as OnDismissListner?
        top_rlay.setOnClickListener(View.OnClickListener { })
        top_rlay.setOnTouchListener(View.OnTouchListener { v, event -> false })
        close_rlay.setOnClickListener(View.OnClickListener {
            listener?.onDismiss()
            //  postEventOnMainThread(RefreshEvent("dismiss"))
            onBackFinish()

        })
       

    }

    fun redirectLogin(activity: Activity?) {
        activity?.let {
            ToastHandler.newInstance(activity).mustShowToastSessionExpired()
         /*   HomeSignTabActivity.tab_position = 4
            CCRBXActivity.class_name = activity?.javaClass
            val intent = Intent(activity, SignktActivity::class.java)
            startActivity(intent)*/
        }
    }

    fun onBackFinish() {
        // super.onBackPressed();
        //finish();
        closed = true
        dismiss()
    }
    /*@Subscribe
    override fun onNotifyEvent(event: NotifyEvent?) {
    }

    override fun onSetValues(view: View?) {
    }

    override fun onSetActionBar(view: View?) {
    }*/

}

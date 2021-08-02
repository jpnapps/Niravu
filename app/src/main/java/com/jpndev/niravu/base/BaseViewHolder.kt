package com.jpndev.niravu.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jpndev.niravu.MHomeData
import com.jpndev.niravu.MQuiz
import com.jpndev.niravu.viewmodel.AppViewModel

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //abstract fun bind(item: T)
    abstract fun bind(item: T?, clickListener: (T?, Int, Int) -> Unit, context: Context,position: Int,appViewModel:AppViewModel?)
}
abstract class BaseViewHolder2<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //abstract fun bind(item: T)
    abstract fun bind(item: T?, clickListener: (MQuiz, T?, Int, Int) -> Unit, context: Context, position: Int, appViewModel:AppViewModel?)

}
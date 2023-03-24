package com.zql.travelassistant.fragment

import androidx.fragment.app.Fragment
import com.zql.travelassistant.R
import dev.shreyaspatil.MaterialDialog.AbstractDialog
import dev.shreyaspatil.MaterialDialog.MaterialDialog

open class BaseFragment: Fragment() {
    var mDialog: AbstractDialog? = null

    fun showLoading() {
        mDialog = MaterialDialog.Builder(requireActivity()).setAnimation(R.raw.lottie_loading_animation).build()

        mDialog?.show()
    }

    fun closeLoading(){
        mDialog!!.dismiss()
    }
}
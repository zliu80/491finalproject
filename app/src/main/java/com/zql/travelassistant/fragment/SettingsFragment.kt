package com.zql.travelassistant.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.zql.travelassistant.MainActivity
import com.zql.travelassistant.ProfileActivity
import com.zql.travelassistant.R
import com.zql.travelassistant.TSApplication
import com.zql.travelassistant.adapter.SettingsRecyclearViewAdapter
import com.zql.travelassistant.bean.User
import com.zql.travelassistant.databinding.FragmentSettingsBinding
import com.zql.travelassistant.interfaces.OnItemClickListener
import com.zql.travelassistant.util.LocalManageUtils
import com.zql.travelassistant.view.RecyclerViewOrientationManager
import io.getstream.avatarview.coil.loadImage
import io.multimoon.colorful.Colorful
import io.multimoon.colorful.ThemeColor


class SettingsFragment : BaseFragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    private lateinit var binding:FragmentSettingsBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
//        initViews()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initViews()
    }

    private fun initViews(){
        // Get the user object
        val user: User? = TSApplication.userRecord


        binding.avatarView.loadImage(data = TSApplication.getAvatarHttpAddress())
        binding.nickname.text = user?.nickname
        binding.avatarView.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java))
        }

        binding.settingsRecyclerview.layoutManager = RecyclerViewOrientationManager.getLayoutManager(resources, requireContext())

        var adapter = SettingsRecyclearViewAdapter(context, mutableListOf(getString(R.string.theme), getString(R.string.language)))
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                if(position == 0){
                    popup_themes()
                } else{
                    popup_languages()
                }
            }
        })
        binding.settingsRecyclerview.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

    }

    fun reStart(context: Context, select: Int) {
        LocalManageUtils.setSelectLanguage(requireContext(), select)
        activity?.recreate()
    }

    fun popup_languages(){
        var list = arrayOf(getString(R.string.english), getString(R.string.japanese), getString(R.string.chinese), getString(R.string.spanish))
        var builder = AlertDialog.Builder(requireContext())

        builder.setTitle(R.string.setting_language).setNegativeButton(R.string.text_cancel, null)
            .setSingleChoiceItems(list, LocalManageUtils.getSelectLanguageInt(requireContext()), DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int ->
                if(LocalManageUtils.getSelectLanguageInt(requireContext()) != i){
                    reStart(requireContext(), i)
                }
                dialogInterface.dismiss()
            }).create()
        builder.show()
    }


    fun popup_themes(){
        val list = arrayOf("Light", "Night")
        val builder = AlertDialog.Builder(requireContext())
        val TAG = javaClass::class.java.name

        var sharedPreferences = activity?.getSharedPreferences(TAG, Activity.MODE_PRIVATE)
        builder.setTitle(R.string.setting_theme).setNegativeButton(R.string.text_cancel, null)
            .setSingleChoiceItems(list,sharedPreferences!!.getInt("mode", 0), DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int ->
                val item = list.get(i)
                if(item == list[0]){
                    Colorful().edit()
                        .setPrimaryColor(ThemeColor.GREEN)
                        .setAccentColor(ThemeColor.BLUE)
                        .setDarkTheme(false)
                        .setTranslucent(true)
                        .apply(requireContext()){
                            activity?.recreate()
                            dialogInterface.dismiss()
                        }
                    sharedPreferences!!.edit().putInt("mode", 0).commit()
                } else if (item == list[1]){
                    Colorful().edit()
                        .setPrimaryColor(ThemeColor.RED)
                        .setAccentColor(ThemeColor.BLUE)
                        .setDarkTheme(true)
                        .setTranslucent(true)
                        .apply(requireContext()){
                            activity?.recreate()
                            dialogInterface.dismiss()
                        }
                    sharedPreferences!!.edit().putInt("mode", 1).commit()

                }
//                Toast.makeText(requireContext(), item, Toast.LENGTH_SHORT).show()
            }).create()
        builder.show()
    }
}
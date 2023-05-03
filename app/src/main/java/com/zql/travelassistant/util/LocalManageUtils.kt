package com.zql.travelassistant.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.util.Log
import com.zql.travelassistant.R
import com.zql.travelassistant.util.SPUtils.Companion.getInstance
import java.util.*

/**
 * 本地管理工具类
 *
 * @author llw
 */
object LocalManageUtils {
    private const val TAG = "LocalManage"

    /**
     * 获取系统的 locale对象
     *
     * @param context 上下文参数
     * @return locale对象
     */
    fun getSystemLocal(context: Context?): Locale {
        return getInstance(context!!)!!.systemCurrentLocal
    }

    /**
     * 获取当前APP使用的语言
     *
     * @param context 上下文参数
     * @return 语言类型描述
     */
    fun getSelectLanguage(context: Context): String {
        //根据缓存中的语言返回当前设置的语言类型文字
        return when (getInstance(context)!!.language) {
            0 -> context.getString(R.string.english)
            1 -> context.getString(R.string.japanese)
            2 -> context.getString(R.string.chinese)
            else -> context.getString(R.string.chinese)
        }
    }

    fun getSelectLanguageInt(context: Context): Int {
        //根据缓存中的语言返回当前设置的语言类型文字
        return getInstance(context)!!.language
    }

    /**
     * 获取选择的语言设置
     *
     * @param context 上下文参数
     * @return locale
     */
    fun getSelectLanguageLocal(context: Context?): Locale {
        return when (getInstance(context!!)!!.language) {
            0 -> Locale.ENGLISH
            1 -> Locale.JAPANESE
            2 -> Locale.CHINESE
            3 -> Locale("es", "ES")
            else -> Locale.CHINESE //中文
        }
    }

    /**
     * 设置选中的语言
     *
     * @param context 上下文参数
     * @param select  选中的语言项
     */
    fun setSelectLanguage(context: Context, select: Int) {
        //放入缓存中
        getInstance(context)!!.language = select
        //设置APP语言
        setAppLanguage(context)
    }

    /**
     * 设置App语言
     *
     * @param context
     */
    fun setAppLanguage(context: Context) {
        //通过应用全局上下文获取资源对象
        val resources = context.applicationContext.resources
        //获取资源配置对象
        val config = resources.configuration
        //获取系统本地对象
        val locale = getSelectLanguageLocal(context)
        //配置本地资源
        config.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //Android7.0及以上
            //获取本地语言列表
            val localeList = LocaleList(locale)
            //设置默认语言列表
            LocaleList.setDefault(localeList)
            //设置语言环境列表
            config.setLocales(localeList)
            //创建配置系统的上下文参数
            context.applicationContext.createConfigurationContext(config)
            //设置默认语言
            Locale.setDefault(locale)
        }
        //更新资源配置
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    /**
     * 设置本地语言
     *
     * @param context 上下文参数
     * @return 设置后的对象
     */
    fun setLocale(context: Context): Context {
        return updateResource(context, getSelectLanguageLocal(context))
    }

    /**
     * 更新资源
     *
     * @param context 上下文参数
     * @param locale  本地语言
     * @return
     */
    private fun updateResource(context: Context, locale: Locale): Context {
        //设置默认语言
        var context = context
        Locale.setDefault(locale)
        //通过上下文获取资源对象
        val resources = context.resources
        //资源配置对象
        val config = Configuration(resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) { //Android4.2及以上
            //设置语言
            config.setLocale(locale)
            //获取设置之后的上下文参数
            context = context.createConfigurationContext(config)
        } else {
            //配置语言
            config.setLocale(locale)
            //更新资源配置
            resources.updateConfiguration(config, resources.displayMetrics)
        }
        return context
    }

    /**
     * 设置系统当前语言
     *
     * @param context
     */
    fun setSystemCurrentLanguage(context: Context?) {
        val locale: Locale
        locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //Android7.0及以上
            LocaleList.getDefault()[0]
        } else {
            Locale.getDefault()
        }
        Log.d(TAG, locale.language)
        //设置应用当前本地语言
        getInstance(context!!)!!.systemCurrentLocal = locale
    }

    /**
     * 更改应用配置
     * @param context 上下文参数
     */
    fun onConfigurationChanged(context: Context) {
        setSystemCurrentLanguage(context)
        setLocale(context)
        setAppLanguage(context)
    }
}
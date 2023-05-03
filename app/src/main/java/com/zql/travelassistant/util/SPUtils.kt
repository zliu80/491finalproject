package com.zql.travelassistant.util

import android.content.Context
import android.content.SharedPreferences
import java.util.*

/**
 * 语言缓存工具类
 * @author llw
 */
class SPUtils(context: Context) {

    companion object {
        @Volatile
        private var instance: SPUtils? = null

        /**
         * 获取实例
         *
         * @param context 上下文参数
         * @return SPUtils实例对象
         */
        @JvmStatic
        fun getInstance(context: Context): SPUtils? {
            if (instance == null) { //等于空则重新创建实例，不为空则直接返回
                synchronized(SPUtils::class.java) { //增加一个同步锁,如果已经有了实例则跳出
                    if (instance == null) {
                        //创建新的对象
                        instance = SPUtils(context)
                    }
                }
            }
            return instance
        }

    }

    private val SP_NAME = "language_setting" //缓存名称
    private val TAG_LANGUAGE = "language_select"
    private val mSharedPreferences: SharedPreferences
    /**
     * 获取系统当前本地
     *
     * @return 系统当前本地
     */
    /**
     * 设置系统当前本地
     *
     * @param local 本地对象
     */
    var systemCurrentLocal = Locale.CHINESE //系统当前本地语言为中文 初始值

    init {
        //通过上下文获取本地缓存
        mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }
    /**
     * 获取语言
     *
     * @return 从缓存中根据缓存名或者缓存值，如果没有，则返回默认值0
     *///缓存编辑者
    //放入保存的语言项
    //提交 之后 缓存语言项保存完毕
    /**
     * 设置语言
     *
     * @param select 选中的语言项
     */
    var language: Int
        get() = mSharedPreferences.getInt(TAG_LANGUAGE, 0)
        set(select) {
            //缓存编辑者
            val editor = mSharedPreferences.edit()
            //放入保存的语言项
            editor.putInt(TAG_LANGUAGE, select)
            //提交 之后 缓存语言项保存完毕
            editor.commit()
        }


}
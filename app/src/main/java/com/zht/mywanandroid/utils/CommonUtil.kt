package com.zht.mywanandroid.utils

import android.content.Context
import android.util.TypedValue

/**
 * 文件描述：
 * 作者：Mr.U
 * 创建时间：2019/6/11
 * 更改时间：2019/6/11
 * 版本号：1
 *
 * 给我一行代码，还你十个BUG
 */
object CommonUtil {
    init {

    }

    fun dp2px(context: Context,dpValue: Float):Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpValue,context.resources.displayMetrics).toInt()
    }

    fun sp2px(context: Context,spValue:Float):Int {

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spValue,context.resources.displayMetrics).toInt()
    }

    /**
     * 获取状态栏高度
     * */
    fun getStatusBarHeight(context: Context):Int{
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }
}
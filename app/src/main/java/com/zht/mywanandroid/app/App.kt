package com.zht.mywanandroid.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import com.cxz.wanandroid.utils.DisplayManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.BuildConfig
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.zht.mywanandroid.utils.SettingUtil
import org.litepal.LitePal
import java.util.*
import kotlin.properties.Delegates

/**
 * 文件描述：Application
 * 作者：Mr.U
 * 创建时间：2019/6/3
 * 更改时间：2019/6/3
 * 版本号：1
 *
 * 给我一行代码，还你十个BUG
 */
class App : MultiDexApplication() {
    private var refWatcher: RefWatcher? = null

    companion object {
        private val TAG = "App"
        var context: Context by Delegates.notNull()
            private set

        lateinit var instance: Application
        fun getRefWatcher(context: Context): RefWatcher? {
            val app = context.applicationContext as App
            return app.refWatcher
        }

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        refWatcher = setupLeakCanary()
        initConfig()
        DisplayManager.init(this)
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        initTheme()
        initLitePal()
        initBuggly()

    }

    private fun initBuggly() {
        if(BuildConfig.DEBUG){
            return
        }

        //获取当前包名
        val packageName = applicationContext.packageName
        //获取当前进程名

    }

    private fun initLitePal() {
        LitePal.initialize(this)
    }

    //初始化主题
    private fun initTheme() {

        //如果开启自动切换夜光模式
        if (SettingUtil.getIsAutoNightMode()) {
            val nightStartHour = SettingUtil.getNightStartHour().toInt()
            val nightStartMinute = SettingUtil.getNightStartMinute().toInt()
            val dayStartHour = SettingUtil.getDayStartHour().toInt()
            val dayStartMinute = SettingUtil.getDayStartMinute().toInt()

            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            val nightValue = nightStartHour * 60 + nightStartHour
            val dayValue = dayStartHour * 60 + dayStartMinute
            val currentValue = currentHour * 60 + currentMinute

            if (currentValue >= nightValue || currentValue <= dayValue) {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                SettingUtil.setIsNightMode(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                SettingUtil.setIsNightMode(false)
            }
        } else {
            //获取当前主题
            if (SettingUtil.getIsAutoNightMode()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    /**
     * 初始化配置
     * */
    private fun initConfig() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(0)
            .methodOffset(7)
            .tag("hao_zz")
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {

                return BuildConfig.DEBUG
            }
        })

    }

    private fun setupLeakCanary(): RefWatcher {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else LeakCanary.install(this)
    }

    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {
            Log.d("Mr.U", "onActivityPaused:" + activity?.componentName?.className)
        }

        override fun onActivityResumed(activity: Activity?) {
            Log.d("Mr.U", "onActivityResumed:" + activity?.componentName?.className)

        }

        override fun onActivityStarted(activity: Activity?) {
            Log.d("Mr.U", "onActivityStarted:" + activity?.componentName?.className)

        }

        override fun onActivityDestroyed(activity: Activity?) {
            Log.d("Mr.U", "onActivityDestroyed:" + activity?.componentName?.className)
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            Log.d("Mr.U", "onActivitySaveInstanceState:" + activity?.componentName?.className)
        }

        override fun onActivityStopped(activity: Activity?) {
            Log.d("Mr.U", "onActivityStopped:" + activity?.componentName?.className)
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            Log.d("Mr.U", "onActivityCreated:" + activity?.componentName?.className)
        }

    }
}
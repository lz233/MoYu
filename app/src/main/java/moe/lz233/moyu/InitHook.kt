package moe.lz233.moyu

import android.content.Context
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.init.InitFields
import com.github.kyuubiran.ezxhelper.utils.*
import com.sfysoft.android.xposed.shelling.XposedShelling
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import moe.lz233.moyu.util.LogUtil
import java.util.*

class InitHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == PACKAGE_NAME) {
            try {
                XposedShelling.runShelling(lpparam)
                XposedHelpers.findAndHookMethod(
                    "com.secneo.apkwrapper.AW",
                    lpparam.classLoader,
                    "attachBaseContext",
                    Context::class.java,
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            super.afterHookedMethod(param)
                            EzXHelperInit.initAppContext(param.args[0] as Context)
                            EzXHelperInit.setEzClassLoader(InitFields.appContext.classLoader)
                            EzXHelperInit.setHostPackageName(PACKAGE_NAME)
                            init()
                            LogUtil.d("((")
                        }
                    }
                )
            } catch (e: Throwable) {
                LogUtil.e("?!")
            }
        }
    }

    fun init() {
        findConstructor("w3.k\$a") {
            it.parameterTypes.size == 1 && it.parameterTypes[0] == Context::class.java
        }.hookAfter {
            val list =
                it.thisObject.getObjectAs<ArrayList<String>>("a")
            list.add("MoYu")
            val list2 =
                it.thisObject.getObjectAs<ArrayList<Long>>("c")
            list2.add(5L)
        }
        findMethod("w3.k\$a") {
            this.parameterTypes.size == 1 && this.parameterTypes[0] == Int::class.javaPrimitiveType && this.returnType == Long::class.javaPrimitiveType
        }.hookBefore {
            when (it.args[0] as Int) {
                3 -> {
                    LogUtil.toast("test")
                }
            }
        }
    }
}
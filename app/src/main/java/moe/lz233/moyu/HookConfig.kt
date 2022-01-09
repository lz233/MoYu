package moe.lz233.moyu

import android.content.Context
import com.github.kyuubiran.ezxhelper.init.InitFields

const val PACKAGE_NAME = "com.maimemo.android.momo"
const val SP_NAME="MoYu"
const val TAG = "MoYu"

object HookConfig {
    val sp by lazy { InitFields.appContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE) }
    val editor by lazy { sp.edit() }
}
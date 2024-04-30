package com.b1nd.alimo.presentation.utiles

import android.util.Log


object Dlog {
    const val TAG = "alimo"

    /** Log Level Error  */
    fun e(message: String?) {
        if (AlimoApplication.DEBUG) Log.e(TAG, buildLogMsg(message))
    }

    /** Log Level Warning  */
    fun w(message: String?) {
        if (AlimoApplication.DEBUG) Log.w(TAG, buildLogMsg(message))
    }

    /** Log Level Information  */
    fun i(message: String?) {
        if (AlimoApplication.DEBUG) Log.i(TAG, buildLogMsg(message))
    }

    /** Log Level Debug  */
    fun d(message: String?) {
        if (AlimoApplication.DEBUG) Log.d(TAG, buildLogMsg(message))
    }

    /** Log Level Verbose  */
    fun v(message: String?) {
        if (AlimoApplication.DEBUG) Log.v(TAG, buildLogMsg(message))
    }

    fun buildLogMsg(message: String?): String {
        val ste = Thread.currentThread().stackTrace[4]
        val sb = StringBuilder()
        sb.append("[")
        sb.append(ste.fileName.replace(".java", ""))
        sb.append("::")
        sb.append(ste.methodName)
        sb.append("]")
        sb.append(message)
        return sb.toString()
    }
}
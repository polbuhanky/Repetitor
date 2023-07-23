package com.platovco.repetitor.managers

import android.content.Context
import id.zelory.compressor.Compressor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.util.function.BiConsumer
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

object CompressorManager {
    @JvmOverloads
    fun <R> getContinuation(
        onFinished: BiConsumer<File?, Throwable?>,
        dispatcher: CoroutineDispatcher = Dispatchers.Default
    ): Continuation<R> {
        return object : Continuation<R> {
            override val context: CoroutineContext
                get() = dispatcher

            override fun resumeWith(result: Result<R>) {
                onFinished.accept(result.getOrNull() as File, result.exceptionOrNull())
            }
        }
    }
    suspend fun compress(context: Context, file: File): File {
        return Compressor.compress(context, file);
    }
}
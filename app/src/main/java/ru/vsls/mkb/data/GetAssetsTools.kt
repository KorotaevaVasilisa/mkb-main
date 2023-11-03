package ru.vsls.mkb.data

import android.content.Context
import android.content.res.AssetManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import javax.inject.Inject

class GetAssetsTools @Inject constructor(@ApplicationContext private val context: Context) {
    fun execute(nameFile: String): InputStream {
        val manager: AssetManager = context.assets
        return manager.open(nameFile)
    }
}

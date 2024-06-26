@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.arch.test

import android.annotation.SuppressLint
import android.content.Context
import io.tagd.arch.infra.CompressedResource
import io.tagd.arch.infra.ICompressedResource
import io.tagd.arch.infra.INamedResource
import io.tagd.arch.infra.UnifiedResource
import io.tagd.arch.infra.UnifiedResourceReader
import io.tagd.arch.infra.toFileNameParts
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference

actual class FakeUnifiedResourceReader actual constructor(context: Any?) : UnifiedResourceReader {

    private var weakContext: WeakReference<Context>? = null

    init {
        assert(context is Context)
        weakContext = WeakReference(context as Context)
    }

    override fun resource(
        id: Int,
        nameWithOrWithoutRelativePath: String?,
        pkg: String?
    ): UnifiedResource {

        return UnifiedResource(nameWithOrWithoutRelativePath, "raw", id, pkg)
    }

    @SuppressLint("DiscouragedApi")
    override fun readNamed(resource: INamedResource): String? {
        val pathAndNames = resource.toFileNameParts()

        val pkg = weakContext!!.get()!!.packageName
        val id = weakContext!!.get()!!.resources.getIdentifier(
            pathAndNames[1],
            pathAndNames[0],
            pkg
        )

        return readCompressed(CompressedResource(type = pathAndNames[0], identifier = id))
    }

    override fun readCompressed(resource: ICompressedResource): String? {
        return try {
            val fileId: Int = resource.identifier
            val `is`: InputStream = weakContext!!.get()!!.resources.openRawResource(fileId)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }
}
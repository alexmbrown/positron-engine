package org.positron.engine.core.util

import org.lwjgl.system.APIUtil.apiLog
import org.lwjgl.system.MemoryUtil
import java.io.BufferedInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.file.Paths

class ResourceUtils {
    companion object {

        fun load(path: String, name: String): ByteBuffer {
            val url = ResourceUtils.javaClass.getResource(Paths.get(path, name).toString())
                    ?: throw IOException("Resource not found: $path/$name")

            val resourceSize = url.openConnection().getContentLength()

            apiLog("bgfx: loading resource '" + url.getFile() + "' (" + resourceSize + " bytes)")

            val resource = MemoryUtil.memAlloc(resourceSize)

            BufferedInputStream(url.openStream()).use { bis ->
                var b: Int
                do {
                    b = bis.read()
                    if (b != -1) {
                        resource.put(b.toByte())
                    }
                } while (b != -1)
            }

            resource.flip()
            return resource
        }

    }

}
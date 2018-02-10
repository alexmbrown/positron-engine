package org.positron.engine.core.renderer

import org.joml.Matrix4f
import org.lwjgl.bgfx.BGFX
import org.lwjgl.bgfx.BGFX.BGFX_RENDERER_TYPE_COUNT
import org.lwjgl.bgfx.BGFXAllocatorInterface
import org.lwjgl.bgfx.BGFXAllocatorVtbl
import org.lwjgl.bgfx.BGFXCallbackInterface
import org.lwjgl.bgfx.BGFXCallbackVtbl
import org.lwjgl.bgfx.BGFXPlatform
import org.lwjgl.bgfx.BGFXPlatformData
import org.lwjgl.bgfx.BGFXReallocCallbackI
import org.lwjgl.glfw.GLFWNativeCocoa
import org.lwjgl.glfw.GLFWNativeWin32
import org.lwjgl.glfw.GLFWNativeX11
import org.lwjgl.opengl.GL11
import org.lwjgl.system.APIUtil.apiLog
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.system.MemoryUtil.getAllocator
import org.lwjgl.system.MemoryUtil.memUTF8
import org.lwjgl.system.Platform
import org.positron.engine.core.mesh.Mesh
import org.positron.engine.core.renderer.camera.Camera
import org.positron.engine.core.scene.VertexBuffer
import org.positron.engine.core.shader.Shaders
import org.positron.engine.core.system.AppSettings
import org.positron.engine.core.window.WindowSize
import java.nio.FloatBuffer
import kotlin.experimental.or


class Renderer(val window: Long): BGFXReallocCallbackI {

    companion object {
        var MAIN_RENDERER = BGFX_RENDERER_TYPE_COUNT
        private const val MAT_SIZE = 16
    }

    private var encoder: Long = -1

    private var viewBuffer: FloatBuffer = MemoryUtil.memAllocFloat(MAT_SIZE)
    private var projectionBuffer: FloatBuffer = MemoryUtil.memAllocFloat(MAT_SIZE)
    private var modelBuffer: FloatBuffer = MemoryUtil.memAllocFloat(MAT_SIZE)

    private var windowSize: WindowSize? = null

    fun init(stack: MemoryStack, settings: AppSettings) {
        val platformData = BGFXPlatformData.callocStack(stack)

        when (Platform.get()) {
            Platform.LINUX -> {
                platformData.ndt(GLFWNativeX11.glfwGetX11Display())
                platformData.nwh(GLFWNativeX11.glfwGetX11Window(window))
            }
            Platform.MACOSX -> {
                platformData.ndt(NULL)
                platformData.nwh(GLFWNativeCocoa.glfwGetCocoaWindow(window))
            }
            Platform.WINDOWS -> {
                platformData.ndt(NULL)
                platformData.nwh(GLFWNativeWin32.glfwGetWin32Window(window))
            }
            else -> {
                throw RuntimeException("Unsupported platform ${Platform.get()}")
            }
        }

        platformData.context(NULL)
        platformData.backBuffer(NULL)
        platformData.backBufferDS(NULL)

        BGFXPlatform.bgfx_set_platform_data(platformData)

        reportSupportedRenderers()

        if (
            !BGFX.bgfx_init(
                MAIN_RENDERER,
                BGFX.BGFX_PCI_ID_NONE.toInt(),
                0,
                createCallbacks(stack),
                createAllocator(stack)
            )
        ) {
            throw RuntimeException("Error initializing bgfx renderer")
        }

        if (MAIN_RENDERER == BGFX.BGFX_RENDERER_TYPE_COUNT) {
            MAIN_RENDERER = BGFX.bgfx_get_renderer_type()
        }

        val rendererName = BGFX.bgfx_get_renderer_name(MAIN_RENDERER)
        if ("NULL" == rendererName) {
            throw RuntimeException("Error identifying bgfx renderer")
        }

        apiLog("BGFX: using renderer '$rendererName'")

        windowSize = settings.windowSize
        BGFX.bgfx_reset(settings.windowSize.width, settings.windowSize.height, BGFX.BGFX_RESET_VSYNC)
        BGFX.bgfx_set_debug(BGFX.BGFX_DEBUG_STATS or BGFX.BGFX_DEBUG_TEXT)
//        BGFX.bgfx_set_debug(BGFX.BGFX_DEBUG_STATS or BGFX.BGFX_DEBUG_TEXT or BGFX.BGFX_DEBUG_WIREFRAME)
        BGFX.bgfx_set_view_clear(0, (BGFX.BGFX_CLEAR_COLOR or BGFX.BGFX_CLEAR_DEPTH).toInt(), 0x303030ff, 1.0f, 0)

        Shaders.init()
    }

    fun prepareFrame(camera: Camera) {

        camera.view.get(viewBuffer)
        camera.projection.get(projectionBuffer)

        val size = windowSize!!
        BGFX.bgfx_set_view_transform(0, viewBuffer, projectionBuffer)
        BGFX.bgfx_set_view_rect(0, 0, 0, size.width, size.height)

        BGFX.bgfx_dbg_text_clear(0, false)
        BGFX.bgfx_dbg_text_printf(0, 1, 0x4f, "bgfx/examples/01-cubes")
        BGFX.bgfx_dbg_text_printf(0, 2, 0x6f, "Description: Rendering simple static mesh")

        encoder = BGFX.bgfx_begin()
    }


    fun render(mesh: Mesh, model: Matrix4f) {
        model.get(modelBuffer)
        BGFX.bgfx_encoder_set_transform(encoder, modelBuffer)

        // material
        val material = mesh.material
        material.uniforms.forEach({ u -> u.bind(encoder) })

        // geometry
        val geometry = mesh.geometry
        BGFX.bgfx_encoder_set_vertex_buffer(encoder, 0, geometry.vertexBuffer?.ref!!, 0, geometry.vertexBuffer?.vertCount!!)
        BGFX.bgfx_encoder_set_index_buffer(encoder, geometry.indexBuffer?.ref!!, 0, geometry.indexBuffer?.faceCount!! * 3)

        BGFX.bgfx_encoder_set_state(encoder, BGFX.BGFX_STATE_DEFAULT, 0)

        // render with program
        BGFX.bgfx_encoder_submit(encoder, 0, mesh.material.program.ref, 0, false)
    }

    fun postFrame() {
        BGFX.bgfx_end(encoder)
        BGFX.bgfx_frame(false)
    }

    fun resize(size: WindowSize) {
        windowSize = size
        BGFX.bgfx_reset(size.width, size.height, BGFX.BGFX_RESET_VSYNC)
    }

    fun destroy() {
        MemoryUtil.memFree(viewBuffer)
        MemoryUtil.memFree(projectionBuffer)
        MemoryUtil.memFree(modelBuffer)

        BGFX.bgfx_shutdown()
    }

    private fun reportSupportedRenderers() {
        val rendererTypes = IntArray(BGFX.BGFX_RENDERER_TYPE_COUNT)
        val count = BGFX.bgfx_get_supported_renderers(rendererTypes)

        apiLog("[BGFX]: renderers supported")

        for (i in 0 until count) {
            apiLog("    " + BGFX.bgfx_get_renderer_name(rendererTypes[i]))
        }
    }

    private fun createCallbacks(stack: MemoryStack): BGFXCallbackInterface {
        val callbackVtbl = BGFXCallbackVtbl.callocStack(stack)

        callbackVtbl.fatal({_, code, str ->
            apiLog("[BGFX]: FATAL code: $code, $str")
        })

        callbackVtbl.trace_vargs({_, file, line, format, args ->
            apiLog("[BGFX]: TRACE_VARGS file: $file, line: $line, format: $format, args: $args")
        })

        callbackVtbl.cache_read_size({_, id ->
            apiLog("[BGFX]: CACHE_READ_SIZE id: $id")
            0
        })

        callbackVtbl.cache_read({_, id, data, size ->
            apiLog("[BGFX]: CACHE_READ id: $id, data: $data, size: $size")
            false
        })

        callbackVtbl.cache_write({ _, id, data, size ->
            apiLog("[BGFX]: CACHE_WRITE id: $id, data: $data, size: $size")
        })

        callbackVtbl.screen_shot({_, filePath, width, height, pitch, data, size, yflip ->
            apiLog("[BGFX] SCREEN_SHOT: filePath: $filePath, width: $width, height: $height, pitch: $pitch, data: $data, size: $size, yflip: $yflip")
        })

        callbackVtbl.capture_begin({_, width, height, pitch, format, yflip ->
            apiLog("[BGFX] CAPTURE_BEGIN: width: $width, height: $height, pitch: $pitch, format: $format, yflip: $yflip")
        })

        callbackVtbl.capture_end({_ ->
            apiLog("[BGFX] CAPTURE_END")
        })

        callbackVtbl.capture_frame({_, data, size ->
            apiLog("[BGFX] CAPTURE_FRAME: data: $data, size: $size")
        })

        val callbacks = BGFXCallbackInterface.callocStack(stack)
        callbacks.vtbl(callbackVtbl)

        return callbacks
    }

    private fun createAllocator(stack: MemoryStack): BGFXAllocatorInterface {
        val allocatorVtbl = BGFXAllocatorVtbl.callocStack(stack)
        allocatorVtbl.realloc(this)

        val allocator = BGFXAllocatorInterface.callocStack(stack)
        allocator.vtbl(allocatorVtbl)

        return allocator
    }

    override fun invoke(_this: Long, ptr: Long, size: Long, align: Long, file: Long, line: Int): Long {
        synchronized(Renderer::class.java) {
            val file = if (file != NULL) memUTF8(file) else "[n/a]"

            if (ptr == 0L && size > 0) {
                if (align != 0L) {
                    val pointer = getAllocator().aligned_alloc(align, size)

                    apiLog("[BGFX]: allocate " + size + " [" + align + "] bytes at address "
                            + java.lang.Long.toHexString(ptr) + " [" + file + " (" + line + ")]")

                    return pointer
                }
            }

            val ptr = getAllocator().realloc(ptr, size)

            apiLog("[BGFX]: (re-)allocate address " + ptr + " with " + size + " bytes at address "
                    + java.lang.Long.toHexString(ptr) + " [" + file + " (" + line + ")]")

            return ptr
        }
    }

}
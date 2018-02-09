package org.positron.engine.core.window

import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.joml.Vector2f
import org.joml.Vector2i
import org.joml.Vector4i
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.positron.engine.core.system.AppSettings

class Window {

    var created: Boolean = false
        protected set

    var onFocus: Subject<Boolean> = PublishSubject.create()
    var onClose: Subject<Boolean> = PublishSubject.create()
    var onResize: Subject<Boolean> = PublishSubject.create()
    var onFrameBufferResize: Subject<WindowSize> = PublishSubject.create()
    var onPositionChange: Subject<Vector2i> = PublishSubject.create()

    var windowRef: Long = -1
        private set

    fun close() {
        verifyWindowCreated()
        GLFW.glfwSetWindowShouldClose(windowRef, true)
    }

    fun create(stack: MemoryStack, settings: AppSettings) {
        // Configure GLFW
//        GLFW.glfwDefaultWindowHints()
//        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_TRUE)
//
//        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4)
//        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 1)
//        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE)
//        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE)
//
//        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
//        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)
        GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API)

        // Create the windowRef
        val size = settings.windowSize
        windowRef = GLFW.glfwCreateWindow(size.width, size.height, settings.title, MemoryUtil.NULL, MemoryUtil.NULL)
        if (windowRef < 0) {
            throw RuntimeException("Failed to create the GLFW windowRef")
        }

        // callbacks
        GLFW.glfwSetFramebufferSizeCallback(windowRef, {_, width, height ->
            onFrameBufferResize.onNext(WindowSize(width, height))
        })

//
//        val pWidth = stack.mallocInt(1)
//        val pHeight = stack.mallocInt(1)
//
//        GLFW.glfwGetWindowSize(windowRef, pWidth, pHeight)
//
//        val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())
//
//        GLFW.glfwSetWindowPos(
//                windowRef,
//                (vidmode.width() - pWidth.get(0)) / 2,
//                (vidmode.height() - pHeight.get(0)) / 2
//        )
//
//        GLFW.glfwMakeContextCurrent(windowRef)
//        GLFW.glfwSwapInterval(1)
        created = true
    }

    fun destroy() {
        GLFW.glfwDestroyWindow(windowRef)
    }

    fun focus() {
        verifyWindowCreated()
        GLFW.glfwFocusWindow(windowRef)
    }

    fun fullScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun hide() {
        verifyWindowCreated()
        GLFW.glfwHideWindow(windowRef)
    }

    fun maximize() {
        verifyWindowCreated()
        GLFW.glfwMaximizeWindow(windowRef)
    }

    fun minimize() {
        verifyWindowCreated()
        GLFW.glfwIconifyWindow(windowRef)
    }

    fun resize(width: Int, height: Int) {
        verifyWindowCreated()
        GLFW.glfwSetWindowSize(windowRef, width, height)
    }

    fun restore() {
        verifyWindowCreated()
        GLFW.glfwRestoreWindow(windowRef)
    }

    fun show() {
        verifyWindowCreated()
        GLFW.glfwShowWindow(windowRef)
    }

    fun shouldClose(): Boolean {
        return GLFW.glfwWindowShouldClose(windowRef)
    }

    fun setAspectRatio(numerator: Int, denominator: Int) {
        verifyWindowCreated()
        GLFW.glfwSetWindowAspectRatio(windowRef, numerator, denominator)
    }

    fun setTitle(title: String) {
        verifyWindowCreated()
        GLFW.glfwSetWindowTitle(windowRef, title)
    }

    fun getSize(): Vector2i {
        verifyWindowCreated()
        val width = IntArray(1)
        val height = IntArray(1)
        GLFW.glfwGetWindowSize(windowRef, width, height)
        return Vector2i(width[0], height[0])
    }

    fun getWidth(): Int {
        return getSize().x
    }

    fun getHeight(): Int {
        return getSize().y
    }

    fun getFrameSize(): Vector4i {
        verifyWindowCreated()
        val left = IntArray(1)
        val top = IntArray(1)
        val right = IntArray(1)
        val bottom = IntArray(1)
        GLFW.glfwGetWindowFrameSize(windowRef, left, top, right, bottom)
        return Vector4i(left[0], top[0], right[0], bottom[0])
    }

    fun getFrameBufferSize(): Vector2i {
        verifyWindowCreated()
        val width = IntArray(1)
        val height = IntArray(1)
        GLFW.glfwGetFramebufferSize(windowRef, width, height)
        return Vector2i(width[0], height[0])
    }

    fun getWindowPosition(): Vector2i {
        verifyWindowCreated()
        val xPos = IntArray(1)
        val yPos = IntArray(1)
        GLFW.glfwGetWindowPos(windowRef, xPos, yPos)
        return Vector2i(xPos[0], yPos[0])
    }

    fun isVisible(): Boolean {
        return GLFW.glfwGetWindowAttrib(windowRef, GLFW.GLFW_VISIBLE) > 0
    }

    fun isFocused(): Boolean {
        return GLFW.glfwGetWindowAttrib(windowRef, GLFW.GLFW_FOCUSED) > 0
    }

    private fun verifyWindowCreated() {
        if(!created || windowRef < 0) {
            throw IllegalStateException("Window has not been created yet")
        }
    }

}
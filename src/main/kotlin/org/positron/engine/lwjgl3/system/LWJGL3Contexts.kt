package org.positron.engine.lwjgl3.system

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.positron.engine.core.input.InputManager
import org.positron.engine.core.system.SystemContext
import org.positron.engine.lwjgl3.input.GLFWKeyboardInput
import org.positron.engine.lwjgl3.input.GLFWMouseInput


class LWJGL3Context: SystemContext() {

    private var window: Long = -1

    override fun close() {
        Callbacks.glfwFreeCallbacks(window)
        GLFW.glfwDestroyWindow(window)

        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null).free()

        onClose()
    }

    override fun init() {
        GLFWErrorCallback.createPrint(System.err).set()

        if (!GLFW.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)

        // Create the window
        println("pre window")
        window = GLFW.glfwCreateWindow(300, 300, "Hello World!", MemoryUtil.NULL, MemoryUtil.NULL)
        if (window < 0) {
            throw RuntimeException("Failed to create the GLFW window")
        }
        println("post window")

        GLFW.glfwSetKeyCallback(window) { window, key, _, action, _ ->
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(window, true)
            }
        }

        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            GLFW.glfwGetWindowSize(window, pWidth, pHeight)

            val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())

            GLFW.glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            )
        }

        GLFW.glfwMakeContextCurrent(window)
        GLFW.glfwSwapInterval(1)

        // keyboard
        val keyboard = GLFWKeyboardInput(window)
        val mouse = GLFWMouseInput(window)

        input = InputManager(keyboard, mouse)

        onInit()
    }

    override fun restart() {
        TODO("not implemented")
        onRestart()
    }

    override fun start() {
        GLFW.glfwShowWindow(window)
        timer.start()
        onStart()
        update()
    }

    override fun update() {
        GL.createCapabilities()

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        while (!GLFW.glfwWindowShouldClose(window)) {

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
            GLFW.glfwSwapBuffers(window)
            GLFW.glfwPollEvents()

            onUpdate(timer.tick())
        }

        this.close()
    }

}
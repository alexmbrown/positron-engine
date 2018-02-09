package org.positron.engine.core.system

import io.reactivex.Observable
import org.joml.Matrix4f
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GLUtil
import org.lwjgl.system.APIUtil
import org.lwjgl.system.Callback
import org.lwjgl.system.MemoryStack
import org.positron.engine.core.input.InputManager
import org.positron.engine.core.input.keyboard.KeyEvent
import org.positron.engine.core.input.keyboard.KeyUtils
import org.positron.engine.core.input.keyboard.KeyboardInput
import org.positron.engine.core.input.mouse.MouseButtonEvent
import org.positron.engine.core.input.mouse.MouseInput
import org.positron.engine.core.input.mouse.MouseUtils
import org.positron.engine.core.mesh.Mesh
import org.positron.engine.core.renderer.Renderer
import org.positron.engine.core.renderer.camera.Camera
import org.positron.engine.core.scene.Scene
import org.positron.engine.core.scene.SceneNode
import org.positron.engine.core.scene.SceneNodeIterator
import org.positron.engine.core.shader.Shaders
import org.positron.engine.core.window.Window

abstract class Application {

    var settings = AppSettings()

    protected var camera: Camera? = null
    private var started = false

    lateinit var input: InputManager
        private set

    lateinit var timer: Timer
        private set

    val window = Window()

    lateinit var renderer: Renderer
    val scene = Scene()

    // life cycle hooks
    open fun onClose() {}
    open fun onUpdate(tpf: Float) {}
    open fun onStart() {}
    open fun onInit() {}

    fun restart() {
        if (!started) {
            throw IllegalStateException("restart() cannot be called before an application has been started.")
        }

        // TODO
    }

    /**
     * PUBLIC METHODS
     */
    fun start() {
        MemoryStack.stackPush().use { stack ->
            init(stack)

            if (camera == null) {
                throw IllegalStateException("A camera must be initialized before starting the application")
            }

            window.show()
            timer.start()
            onStart()

            update()
        }
    }

    fun stop() {
        close()
    }

    /**
     * PRIVATE METHODS
     */
    private fun close() {
        Shaders.destroy()

        input.destroy()
        window.destroy()
        renderer.destroy()

        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null).free()

        onClose()
    }

    private fun init(stack: MemoryStack) {
        GLFWErrorCallback.createPrint(System.err).set()

        if (!GLFW.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        window.create(stack, settings)
        window.onFrameBufferResize.subscribe({ws -> renderer.resize(ws)})

        // input
        val keyboard = KeyboardInput(window.windowRef)
        val mouse = MouseInput(window.windowRef)

        input = InputManager(keyboard, mouse)
        timer = Timer()

        renderer = Renderer(window.windowRef)
        renderer.init(stack, settings)

        onInit()
    }

    private fun update() {
//        GL.createCapabilities()
//        val debugProc = GLUtil.setupDebugMessageCallback()
//        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        var iterator: SceneNodeIterator
        while (!window.shouldClose()) {
//            window.clear()
            input.update()

            renderer.prepareFrame(camera!!)

            onUpdate(timer.tick())

            iterator = scene.iterate()
            while(!iterator.complete) {
                iterator.next({node: SceneNode, model: Matrix4f ->

                    if (node is Mesh) {
                        renderer.render(node, model)
                    }

                })
            }

            renderer.postFrame()

//            val err = GL11.glGetError()
//            if (err != GL11.GL_NO_ERROR) {
//                APIUtil.apiLog("GL_ERROR: $err")
//            }
        }

//        if ( debugProc != null ) {
//            debugProc.free()
//        }

        close()
    }

    /**
     * Input Events
     */
    // keyboard
    fun onKeyPress(key: Int): Observable<KeyEvent> {
        return input.key.onEvent.filter(KeyUtils.onKeyPress(key))
    }

    fun onKeyRelease(key: Int): Observable<KeyEvent> {
        return input.key.onEvent.filter(KeyUtils.onKeyRelease(key))
    }

    fun onKeyRepeat(key: Int): Observable<KeyEvent> {
        return input.key.onEvent.filter(KeyUtils.onKeyRepeat(key))
    }

    // mouse
    fun onMouseButtonPress(key: Int): Observable<MouseButtonEvent> {
        return input.mouse.onButtonEvent.filter(MouseUtils.onMouseButtonPress(key))
    }

    fun onMouseButtonRelease(key: Int): Observable<MouseButtonEvent> {
        return input.mouse.onButtonEvent.filter(MouseUtils.onMouseButtonRelease(key))
    }

    fun onMouseButtonRepeat(key: Int): Observable<MouseButtonEvent> {
        return input.mouse.onButtonEvent.filter(MouseUtils.onMouseButtonRelease(key))
    }

}
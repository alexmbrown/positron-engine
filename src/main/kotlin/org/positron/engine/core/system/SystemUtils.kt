package org.positron.engine.core.system

import org.positron.engine.lwjgl3.system.LWJGL3Context

class SystemUtils {
    companion object {
        fun createContext(): SystemContext {
            return LWJGL3Context()
        }
    }
}

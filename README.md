# Positron Engine

A game engine inspired by three.js using LWJGL3 coded in Kotlin.

# Running Examples

Startup Arguments: -Dorg.lwjgl.util.Debug=true -XstartOnFirstThread -javaagent:./lib/lwjglx-debug-1.0.0.jar

### Compiling Custom Shaders
1. clone bgfx, bimg, and bx respositories
2. TODO: make bgfx, bimg, and bx
3. Update `SHADER_DIR` in `bgfx-master/script/shader.mk`
4. `make -f bgfx-master/scripts/shader.mk rebuild TARGET=4`
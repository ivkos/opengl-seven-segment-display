package com.ivkos.opengl.util;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GlWindow implements Runnable
{
   private final String title;
   private final int windowWidth;
   private final int windowHeight;
   private final GlDrawable drawable;

   private long windowHandle;

   public GlWindow(String title, int windowWidth, int windowHeight, GlDrawable drawable)
   {
      this.title = title;
      this.windowWidth = windowWidth;
      this.windowHeight = windowHeight;
      this.drawable = drawable;
   }

   @Override
   public void run()
   {
      try {
         init();
         loop();

         // Free the window callbacks and destroy the window
         glfwFreeCallbacks(windowHandle);
         glfwDestroyWindow(windowHandle);
      } finally {
         // Terminate GLFW and free the error callback
         glfwTerminate();
         glfwSetErrorCallback(null).free();
      }
   }

   private void init()
   {
      // Setup an error callback. The default implementation
      // will print the error message in System.err.
      GLFWErrorCallback.createPrint(System.err).set();

      // Initialize GLFW. Most GLFW functions will not work before doing this.
      if (!glfwInit()) {
         throw new IllegalStateException("Unable to initialize GLFW");
      }

      // Configure our window
      glfwDefaultWindowHints(); // optional, the current window hints are already the default
      glfwWindowHint(GLFW_VISIBLE, 0); // the window will stay hidden after creation
      glfwWindowHint(GLFW_RESIZABLE, 1); // the window will be resizable

      // Create the window
      windowHandle = glfwCreateWindow(windowWidth, windowHeight, title, NULL, NULL);
      if (windowHandle == NULL)
         throw new RuntimeException("Failed to create the GLFW window");

      // Setup a key callback. It will be called every time a key is pressed, repeated or released.
      glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
         if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
            glfwSetWindowShouldClose(window, true); // We will detect this in our rendering loop
      });

      glfwSetWindowRefreshCallback(windowHandle, window -> {
         int[] wBuf = new int[1];
         int[] hBuf = new int[1];
         glfwGetFramebufferSize(window, wBuf, hBuf);

         int w = wBuf[0];
         int h = hBuf[0];

         glViewport(0, 0, w, h);
         glMatrixMode(GL_PROJECTION);
         glLoadIdentity();
      });

      // Get the resolution of the primary monitor
      GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

      // Center our window
      glfwSetWindowPos(
            windowHandle,
            (vidmode.width() - windowWidth) / 2,
            (vidmode.height() - windowHeight) / 2
      );

      // Make the OpenGL context current
      glfwMakeContextCurrent(windowHandle);

      // Enable v-sync
      glfwSwapInterval(1);

      // Make the window visible
      glfwShowWindow(windowHandle);
   }

   private void loop()
   {
      GL.createCapabilities();

      while (!glfwWindowShouldClose(windowHandle)) {
         glClear(GL_COLOR_BUFFER_BIT);

         drawable.draw();

         glfwSwapBuffers(windowHandle);
         glfwPollEvents();
      }
   }
}

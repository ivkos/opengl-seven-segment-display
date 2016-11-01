package com.ivkos.opengl.util;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;

public class GlGroup implements AutoCloseable
{
   public GlGroup(int mode)
   {
      glBegin(mode);
   }

   @Override
   public void close()
   {
      glEnd();
   }
}

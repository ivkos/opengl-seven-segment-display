package com.ivkos.opengl.util;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

public class GlMatrix implements AutoCloseable
{
   public GlMatrix()
   {
      glPushMatrix();
   }

   @Override
   public void close()
   {
      glPopMatrix();
   }
}

package com.ivkos.opengl;

import com.ivkos.opengl.sevensegmentdisplay.SevenSegmentDisplayDrawable;
import com.ivkos.opengl.util.GlDrawable;
import com.ivkos.opengl.util.GlWindow;

public class Application
{
   public static void main(String[] args)
   {
      GlDrawable drawable = new SevenSegmentDisplayDrawable(1337, "#7EFF00");
      GlWindow ctx = new GlWindow("Seven Segment Display", 720, 720, drawable);

      new Thread(ctx).start();
   }
}

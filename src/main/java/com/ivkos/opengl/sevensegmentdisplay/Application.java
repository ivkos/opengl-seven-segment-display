package com.ivkos.opengl.sevensegmentdisplay;

import com.ivkos.opengl.sevensegmentdisplay.ssd.SevenSegmentDisplayDrawable;
import com.ivkos.opengl.sevensegmentdisplay.util.GlDrawable;
import com.ivkos.opengl.sevensegmentdisplay.util.GlWindow;

public class Application
{
   public static void main(String[] args)
   {
      GlDrawable drawable = new SevenSegmentDisplayDrawable(1337, "#7EFF00");
      GlWindow ctx = new GlWindow("Seven Segment Display", 720, 720, drawable);

      new Thread(ctx).start();
   }
}

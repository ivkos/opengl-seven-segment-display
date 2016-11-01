package com.ivkos.opengl;

import com.ivkos.opengl.sevensegmentdisplay.SevenSegmentDisplayDrawable;
import com.ivkos.opengl.util.GlDrawable;
import com.ivkos.opengl.util.GlWindow;

import static java.lang.Integer.parseInt;

public class Application
{
   public static void main(String[] args)
   {
      int number;
      try {
         number = parseInt(args[0]);
      } catch (NumberFormatException | IndexOutOfBoundsException e) {
         number = 1337;
      }

      GlDrawable drawable = new SevenSegmentDisplayDrawable(number, "#7EFF00");
      GlWindow ctx = new GlWindow("Seven Segment Display", 720, 720, drawable);

      new Thread(ctx).start();
   }
}

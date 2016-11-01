package com.ivkos.opengl.sevensegmentdisplay;

import com.ivkos.opengl.util.GlDrawable;
import com.ivkos.opengl.util.GlGroup;
import com.ivkos.opengl.util.GlMatrix;

import java.util.Arrays;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static org.lwjgl.opengl.GL11.*;

public class SevenSegmentDisplayDrawable implements GlDrawable
{
   public static final double COLOR_DARKEN_FACTOR = 0.15;

   public static final double SEGMENT_WIDTH = 1 / 4.0;
   public static final double SEGMENT_HEIGHT = 1 / 16.0;
   public static final double SEGMENT_SHORT_WIDTH_FACTOR = 2 / 3.0;

   public static final double DIGIT_WIDTH = SEGMENT_WIDTH + SEGMENT_HEIGHT;
   public static final double DIGIT_SPACING = DIGIT_WIDTH + DIGIT_WIDTH / 16.0;

   private final Integer[] digits;
   private final byte[] segmentColorOn;
   private final byte[] segmentColorOff;

   public SevenSegmentDisplayDrawable(int number, String colorHex)
   {
      this.digits = Arrays.stream(Integer.toString(number).split(""))
            .map(Integer::parseInt)
            .collect(toList())
            .toArray(new Integer[0]);

      int[] colors = parseColorHexString(colorHex);

      this.segmentColorOn = new byte[]{
            (byte) colors[0],
            (byte) colors[1],
            (byte) colors[2]
      };
      this.segmentColorOff = new byte[]{
            (byte) (colors[0] * COLOR_DARKEN_FACTOR),
            (byte) (colors[1] * COLOR_DARKEN_FACTOR),
            (byte) (colors[2] * COLOR_DARKEN_FACTOR)
      };
   }

   private static int[] parseColorHexString(String colorHex)
   {
      int hexOffset = colorHex.startsWith("#") ? 1 : 0;

      int red = parseInt(colorHex.substring(hexOffset, 2 + hexOffset), 16);
      int green = parseInt(colorHex.substring(2 + hexOffset, 4 + hexOffset), 16);
      int blue = parseInt(colorHex.substring(4 + hexOffset, 6 + hexOffset), 16);

      return new int[]{
            red,
            green,
            blue
      };
   }

   private void drawLedSegment(boolean active, double rotation, double x, double y)
   {
      byte[] color = active ? segmentColorOn : segmentColorOff;

      try (GlMatrix __ = new GlMatrix()) {
         glRotated(rotation, 0, 0, 1);
         glTranslated(x, y, 0);

         try (GlGroup ___ = new GlGroup(GL_POLYGON)) {
            glColor3ub(color[0], color[1], color[2]);

            glVertex2d((SEGMENT_WIDTH / 2) * SEGMENT_SHORT_WIDTH_FACTOR, SEGMENT_HEIGHT / 2);
            glVertex2d(SEGMENT_WIDTH / 2, 0);
            glVertex2d((SEGMENT_WIDTH / 2) * SEGMENT_SHORT_WIDTH_FACTOR, -SEGMENT_HEIGHT / 2);
            glVertex2d(-(SEGMENT_WIDTH / 2) * SEGMENT_SHORT_WIDTH_FACTOR, -SEGMENT_HEIGHT / 2);
            glVertex2d(-SEGMENT_WIDTH / 2, 0);
            glVertex2d(-(SEGMENT_WIDTH / 2) * SEGMENT_SHORT_WIDTH_FACTOR, SEGMENT_HEIGHT / 2);
         }
      }
   }

   private void drawDigit(int digit, double x, double y)
   {
      SevenSegmentDigit ssDigit = new SevenSegmentDigit(digit);

      try (GlMatrix __ = new GlMatrix()) {
         glTranslated(x, y, 0);

         drawLedSegment(ssDigit.a(), 0, 0, SEGMENT_WIDTH);
         drawLedSegment(ssDigit.b(), 90, SEGMENT_WIDTH / 2, -SEGMENT_WIDTH / 2);
         drawLedSegment(ssDigit.c(), 90, -SEGMENT_WIDTH / 2, -SEGMENT_WIDTH / 2);
         drawLedSegment(ssDigit.d(), 0, 0, -SEGMENT_WIDTH);
         drawLedSegment(ssDigit.e(), 90, -SEGMENT_WIDTH / 2, SEGMENT_WIDTH / 2);
         drawLedSegment(ssDigit.f(), 90, SEGMENT_WIDTH / 2, SEGMENT_WIDTH / 2);
         drawLedSegment(ssDigit.g(), 0, 0, 0);
      }
   }

   private double getTotalWidth()
   {
      return digits.length * DIGIT_WIDTH
            + (digits.length - 1) * (DIGIT_SPACING - DIGIT_WIDTH);
   }

   @Override
   public void draw()
   {
      glMatrixMode(GL_MODELVIEW);
      glLoadIdentity();

      glClearColor(.07f, .07f, .07f, 1);

      double offsetX = DIGIT_WIDTH / 2 - getTotalWidth() / 2;

      try (GlMatrix __ = new GlMatrix()) {
         glTranslated(offsetX, 0, 0);

         for (int i = 0; i < digits.length; i++) {
            drawDigit(digits[i], DIGIT_SPACING * i, 0);
         }
      }
   }
}

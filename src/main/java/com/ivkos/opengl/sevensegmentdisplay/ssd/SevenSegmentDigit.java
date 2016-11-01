package com.ivkos.opengl.sevensegmentdisplay.ssd;

public class SevenSegmentDigit
{
   private static final byte[] encodings = {
         0x7e,
         0x30,
         0x6d,
         0x79,
         0x33,
         0x5b,
         0x5f,
         0x70,
         0x7f,
         0x7b
   };

   private final byte encoding;

   public SevenSegmentDigit(int digit)
   {
      this.encoding = encodings[digit];
   }

   public boolean a()
   {
      return (encoding & 0b1000000) != 0;
   }

   public boolean b()
   {
      return (encoding & 0b100000) != 0;
   }

   public boolean c()
   {
      return (encoding & 0b10000) != 0;
   }

   public boolean d()
   {
      return (encoding & 0b1000) != 0;
   }

   public boolean e()
   {
      return (encoding & 0b100) != 0;
   }

   public boolean f()
   {
      return (encoding & 0b10) != 0;
   }

   public boolean g()
   {
      return (encoding & 0b1) != 0;
   }
}

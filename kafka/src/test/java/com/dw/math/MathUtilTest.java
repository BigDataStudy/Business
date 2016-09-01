package com.dw.math;
import static org.junit.Assert.*;
import org.junit.Test;
  public class MathUtilTest {
    @Test
    public void test_max_1_2_3() {
          assertEquals(3, MathUtil.max(1, 2, 3));
    }
}
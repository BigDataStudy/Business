package com.dw.math;

import static org.junit.Assert.*;
import org.junit.Test;

public class MathUtilTest {
	 @Test
	  public void test_max_1_2_3() {
	      assertEquals(3, MathUtil.max(1, 2, 3));
	  }
	  @Test
	  public void test_max_1_3_2() {
	      assertEquals(3, MathUtil.max(1, 3, 2));
	  }
	  @Test
	  public void test_max_3_2_1() {
	      assertEquals(3, MathUtil.max(3, 2, 1));
	  }
	  @Test
	  public void test_max_0_0_0(){
	      assertEquals(0, MathUtil.max(0, 0, 0));
	  }
	  @Test
	  public void test_max_0_1_0(){
	      assertEquals(1, MathUtil.max(0, 1, 0));
	  }
	  
	  @Test
	  public void test_max_2_1_3(){
	      assertEquals(3, MathUtil.max(2, 1, 3));
	  }
}
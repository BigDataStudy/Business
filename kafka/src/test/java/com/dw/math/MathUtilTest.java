package com.dw.math;

import static org.junit.Assert.*;
import org.junit.Test;

public class MathUtilTest {
	@Test
	public void test_max_1_2_3() {
		assertEquals(3, MathUtil.max(1, 2, 3));
	}

	@Test
	public void test_max_10_20_30() {
		assertEquals(30, MathUtil.max(10, 20, 30));
	}

	@Test
	public void test_max_100_200_300() {
		assertEquals(300, MathUtil.max(100, 200, 300));
	}
}
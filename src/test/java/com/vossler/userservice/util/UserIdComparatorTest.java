package com.vossler.userservice.util;

import static org.junit.Assert.assertEquals;
import static org.apache.commons.lang3.RandomStringUtils.*;

import org.junit.Test;

import com.vossler.userservice.dto.User;
import com.vossler.userservice.util.UserIdComparator;

public class UserIdComparatorTest {

	UserIdComparator comparator = new UserIdComparator();
	
	@Test
	public void testNullComparisons() {
		assertEquals(0, comparator.compare(new User(), new User()));
		assertEquals(-1, comparator.compare(new User(), new User().setId(randomNumeric(3))));
		assertEquals(1, comparator.compare(new User().setId(randomNumeric(3)), new User()));
	}
	
	@Test
	public void testIntegerValueComparisons() {
		assertEquals(0, comparator.compare(new User().setId("1"), new User().setId("1")));
		assertEquals(-1, comparator.compare(new User().setId("2"), new User().setId("10")));
		assertEquals(1, comparator.compare(new User().setId("25"), new User().setId("3")));
	}
	
	@Test
	public void testAlphabeticFallbackComparisons() {
		assertEquals(0, comparator.compare(new User().setId("abc"), new User().setId("abc")));
		assertEquals(-1, comparator.compare(new User().setId("1a"), new User().setId("1b")));
		assertEquals(1, comparator.compare(new User().setId("zip"), new User().setId("yak")));
	}
}

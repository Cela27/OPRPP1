package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UtilTests {

	@Test
	public void testHexToByteIllegalArgumentException1() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte(null));
	}

	@Test
	public void testHexToByteIllegalArgumentException2() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("ab123"));
	}

	@Test
	public void testHexToByteIllegalArgumentException3() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("abc."));
	}

	@Test
	public void testHexToBytetextIsSizeNull() {;
		byte[] arr = Util.hextobyte("");
		assertEquals(0, arr.length);
	}

	@Test
	public void testHexToByteWorks() {
		byte[] arr = Util.hextobyte("01Ae22");
		assertEquals(1, arr[0]);
		assertEquals(-82, arr[1]);
		assertEquals(34, arr[2]);
	}

	@Test
	public void testByteToHexArrayLengthIsZero() {
		byte[] arr = {};
		assertEquals("", Util.bytetohex(arr));
	}

	@Test
	public void testByteToHexWorks() {
		byte[] arr = {1, -82, 34};
		assertEquals("01ae22", Util.bytetohex(arr));
	}
}

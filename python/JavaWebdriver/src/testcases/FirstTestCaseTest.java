package testcases;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import org.junit.Test;

public class FirstTestCaseTest {

	@Test
	public void firstTest() {
		ArrayList<Object> list = new ArrayList<Object>();
		//ArrayList<Integer> intList = new ArrayList<Integer>();
		list.add(42);
		list.add(-3);
		list.add(17);
		list.add(99);

		assertEquals(4, list.size());
		assertEquals(17, list.get(2));
		assertTrue(list.contains(-3));
		assertFalse(list.isEmpty());
		
	}
	@Ignore
	public void secondTest(){
		System.out.println("secondTest");
		
	}

	}



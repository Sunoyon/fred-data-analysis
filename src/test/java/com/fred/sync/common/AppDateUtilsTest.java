package com.fred.sync.common;

import java.text.ParseException;

import org.junit.Test;

public class AppDateUtilsTest {
	
	@Test
	public void getLocalDateTest() throws ParseException {
		System.out.println(AppDateUtils.getLocalDate("2018-07-01"));
	}

}

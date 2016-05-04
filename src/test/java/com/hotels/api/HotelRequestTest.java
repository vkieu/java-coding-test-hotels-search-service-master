package com.hotels.api;

import java.sql.Date;

import junit.framework.Assert;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HotelRequestTest {
	
	HotelRequest request;
	Date checkInDate = new Date(System.currentTimeMillis());
	Date checkOutDate = new Date(System.currentTimeMillis() + 1);
	
	@BeforeMethod
	public void setUp() throws Exception {
		request = new HotelRequest("London", checkInDate, checkOutDate, 1);
	}

	@Test(expectedExceptions=NullPointerException.class)
	public void testHotelRequest() {
		new HotelRequest(null, null, null, 0);
	}

	@Test
	public void testGetNumberOfResultsToReturn() {		
		Assert.assertEquals(3, request.getNumberOfResultsToReturn());
	}
	@Test
	public void testSetNumberOfResultsToReturn() {
		request.setNumberOfResultsToReturn(10);
		Assert.assertEquals(10, request.getNumberOfResultsToReturn());
	}
	@Test
	public void testSetBreakfastOption() {		
		Assert.assertEquals(BreakfastOption.EITHER, request.getBreakfastOption());
		request.setBreakfastOption(BreakfastOption.BREAKFAST);
		Assert.assertEquals(BreakfastOption.BREAKFAST, request.getBreakfastOption());
	}
	@Test
	public void testGetStartPrice() {
		Assert.assertEquals(0, request.getStartPrice());
	}
	@Test
	public void testSetPriceRange() {
		Assert.assertEquals(false, request.isPriceRangeSet());
		request.setPriceRange(0, 1);
		Assert.assertEquals(0, request.getStartPrice());
		Assert.assertEquals(1, request.getEndPrice());
	}
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void testSetPriceRange_exception() {		
		request.setPriceRange(1, 0);		
	}
	@Test
	public void testGetEndPrice() {
		Assert.assertEquals(Integer.MAX_VALUE, request.getEndPrice());
	}

	@Test
	public void testGetDestination() {
		Assert.assertEquals("London", request.getDestination());
	}

	@Test
	public void testIsPriceRangeSet() {
		Assert.assertEquals(false, request.isPriceRangeSet());
		request.setPriceRange(0, 1);
		Assert.assertEquals(true, request.isPriceRangeSet());
	}

	@Test
	public void testGetCheckInDate() {
		Assert.assertEquals(checkInDate, request.getCheckIn());
	}

	@Test
	public void testGetCheckOut() {
		Assert.assertEquals(checkOutDate, request.getCheckOut());
	}

	@Test
	public void testGetNumberOfGuests() {
		Assert.assertEquals(1, request.getNumberOfGuests());
	}

}

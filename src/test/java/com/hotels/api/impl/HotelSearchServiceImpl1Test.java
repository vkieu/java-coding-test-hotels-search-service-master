package com.hotels.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hotels.api.BreakfastOption;
import com.hotels.api.HotelRequest;
import com.hotels.api.HotelSearchService;
import com.hotels.platform.availability.HotelDetails;
import com.hotels.platform.availability.PricingAndAvailabilityRequest;
import com.hotels.platform.availability.PricingAndAvailabilityService;


public class HotelSearchServiceImpl1Test {
	
	HotelRequest request;
	PricingAndAvailabilityService mockPricingAndAvailabilityService;
	
	@BeforeMethod
	public void setUp() throws Exception {
		Date checkIn = new Date();
		Date checkOut = new Date();	
		request = new HotelRequest("Paris", checkIn, checkOut, 1);
		mockPricingAndAvailabilityService = Mockito.mock(PricingAndAvailabilityService.class);
	}
	
	@AfterMethod
	public void cleanDown() throws Exception {
		
	}
	
	
	@Test
	public void testHotelSearchServiceImpl1() {
		new HotelSearchServiceImpl1(null);
	}
	
	@Test
	public void testSearch_emptyRequestParameter() {		
		Mockito.when(
				mockPricingAndAvailabilityService.getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject()))
				.thenReturn(Collections.<HotelDetails>emptyList());				
		HotelSearchService service = new HotelSearchServiceImpl1(mockPricingAndAvailabilityService);					
		Collection<HotelDetails> foundHotels = service.search(null);		
		Assert.assertEquals(0, foundHotels.size());
		Mockito.verify(mockPricingAndAvailabilityService, Mockito.times(0)).getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject());		
	}
	
	@Test
	public void testSearch_backendReturnNoResults() {		
		Mockito.when(
				mockPricingAndAvailabilityService.getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject()))
				.thenReturn(Collections.<HotelDetails>emptyList());				
		HotelSearchService service = new HotelSearchServiceImpl1(mockPricingAndAvailabilityService);
		Collection<HotelDetails> foundHotels = service.search(request);		
		Assert.assertEquals(0, foundHotels.size());
		Mockito.verify(mockPricingAndAvailabilityService, Mockito.times(1)).getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject());
		
	}
	
	@Test
	public void testSearch_Found1() {		
		HotelDetails hotel = new HotelDetails("Paris", 100, 2, false);		
		PricingAndAvailabilityService mockPricingAndAvailabilityService = Mockito.mock(PricingAndAvailabilityService.class);		
		Mockito.when(
				mockPricingAndAvailabilityService.getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject()))
				.thenReturn(Collections.singletonList(hotel));				
		HotelSearchService service = new HotelSearchServiceImpl1(mockPricingAndAvailabilityService);				
		Collection<HotelDetails> foundHotels = service.search(request);		
		Assert.assertEquals(1, foundHotels.size());
		Mockito.verify(mockPricingAndAvailabilityService, Mockito.times(1)).getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject());		
	}
	
	@Test
	public void testSearch_FoundManyButRestrictedTo3() {		
		List<HotelDetails> list = new ArrayList<HotelDetails>();
		list.add(new HotelDetails("Paris", 100, 2, false));
		list.add(new HotelDetails("London", 101, 3, false));
		list.add(new HotelDetails("NewYork", 102, 4, false));
		list.add(new HotelDetails("NewYork2", 103, 4, false));
		list.add(new HotelDetails("NewYork3", 103, 4, false));
		
		PricingAndAvailabilityService mockPricingAndAvailabilityService = Mockito.mock(PricingAndAvailabilityService.class);		
		Mockito.when(
				mockPricingAndAvailabilityService.getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject()))
				.thenReturn(list);				
		HotelSearchService service = new HotelSearchServiceImpl1(mockPricingAndAvailabilityService);				
		Collection<HotelDetails> foundHotels = service.search(request);		
		Assert.assertEquals(3, foundHotels.size());
		Mockito.verify(mockPricingAndAvailabilityService, Mockito.times(1)).getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject());		
	}
	
	@Test
	public void testSearch_FoundManyButRestrictedTo5() {		
		List<HotelDetails> list = new ArrayList<HotelDetails>();
		list.add(new HotelDetails("Paris", 100, 2, false));
		list.add(new HotelDetails("London", 101, 3, false));
		list.add(new HotelDetails("NewYork", 102, 4, false));
		list.add(new HotelDetails("NewYork2", 103, 4, false));
		list.add(new HotelDetails("NewYork3", 103, 4, false));
		
		PricingAndAvailabilityService mockPricingAndAvailabilityService = Mockito.mock(PricingAndAvailabilityService.class);		
		Mockito.when(
				mockPricingAndAvailabilityService.getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject()))
				.thenReturn(list);				
		HotelSearchService service = new HotelSearchServiceImpl1(mockPricingAndAvailabilityService);
		request.setNumberOfResultsToReturn(5);
		Collection<HotelDetails> foundHotels = service.search(request);		
		Assert.assertEquals(5, foundHotels.size());
		Mockito.verify(mockPricingAndAvailabilityService, Mockito.times(1)).getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject());		
	}
	
	@Test
	public void testSearch_SortByPopularity() {		
		List<HotelDetails> list = new ArrayList<HotelDetails>();
		list.add(new HotelDetails("Paris", 100, 2, false));
		list.add(new HotelDetails("London", 101, 3, false));
		list.add(new HotelDetails("NewYork3", 103, 1, false));//most popularity
		list.add(new HotelDetails("NewYork", 102, 4, false));
		list.add(new HotelDetails("NewYork2", 103, 4, false));
		
		PricingAndAvailabilityService mockPricingAndAvailabilityService = Mockito.mock(PricingAndAvailabilityService.class);		
		Mockito.when(
				mockPricingAndAvailabilityService.getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject()))
				.thenReturn(list);				
		HotelSearchService service = new HotelSearchServiceImpl1(mockPricingAndAvailabilityService);
		request.setNumberOfResultsToReturn(5);
		Collection<HotelDetails> foundHotels = service.search(request);		
		Assert.assertEquals(5, foundHotels.size());
		Mockito.verify(mockPricingAndAvailabilityService, Mockito.times(1)).getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject());
		HotelDetails mostPopula = foundHotels.iterator().next();
		Assert.assertEquals("NewYork3", mostPopula.getHotelId());		
	}
	
	@Test
	public void testSearch_restrictedByPrice() {		
		List<HotelDetails> list = new ArrayList<HotelDetails>();
		list.add(new HotelDetails("Paris", 100, 2, false));
		list.add(new HotelDetails("London", 101, 3, false));
		list.add(new HotelDetails("NewYork3", 103, 1, false));//most popularity
		list.add(new HotelDetails("NewYork", 102, 4, false));
		list.add(new HotelDetails("NewYork2", 103, 4, false));
		
		PricingAndAvailabilityService mockPricingAndAvailabilityService = Mockito.mock(PricingAndAvailabilityService.class);		
		Mockito.when(
				mockPricingAndAvailabilityService.getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject()))
				.thenReturn(list);				
		HotelSearchService service = new HotelSearchServiceImpl1(mockPricingAndAvailabilityService);

		request.setNumberOfResultsToReturn(5);
		request.setPriceRange(101, 105);
		
		Collection<HotelDetails> foundHotels = service.search(request);			
		Assert.assertEquals(4, foundHotels.size());
		
		Mockito.verify(mockPricingAndAvailabilityService, Mockito.times(1)).getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject());
				
	}
	
	@Test
	public void testSearch_breakfastOptionEither() {		
		List<HotelDetails> list = new ArrayList<HotelDetails>();
		list.add(new HotelDetails("Paris", 100, 2, false));
		list.add(new HotelDetails("London", 101, 3, false));
		list.add(new HotelDetails("NewYork3", 103, 1, true));//most popularity
		list.add(new HotelDetails("NewYork", 102, 4, false));
		list.add(new HotelDetails("NewYork2", 103, 4, true));
		
		PricingAndAvailabilityService mockPricingAndAvailabilityService = Mockito.mock(PricingAndAvailabilityService.class);		
		Mockito.when(
				mockPricingAndAvailabilityService.getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject()))
				.thenReturn(list);				
		HotelSearchService service = new HotelSearchServiceImpl1(mockPricingAndAvailabilityService);
		request.setNumberOfResultsToReturn(5);		
		
		Collection<HotelDetails> foundHotels = service.search(request);			
		Assert.assertEquals(5, foundHotels.size());
		Assert.assertEquals(BreakfastOption.EITHER, request.getBreakfastOption());
		Mockito.verify(mockPricingAndAvailabilityService, Mockito.times(1)).getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject());
				
	}
	
	@Test
	public void testSearch_breakfastOptionRequired() {		
		List<HotelDetails> list = new ArrayList<HotelDetails>();
		list.add(new HotelDetails("Paris", 100, 2, false));
		list.add(new HotelDetails("London", 101, 3, false));
		list.add(new HotelDetails("NewYork3", 103, 1, true));//most popularity
		list.add(new HotelDetails("NewYork", 102, 4, false));
		list.add(new HotelDetails("NewYork2", 103, 4, true));
		
		PricingAndAvailabilityService mockPricingAndAvailabilityService = Mockito.mock(PricingAndAvailabilityService.class);		
		Mockito.when(
				mockPricingAndAvailabilityService.getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject()))
				.thenReturn(list);				
		HotelSearchService service = new HotelSearchServiceImpl1(mockPricingAndAvailabilityService);
		request.setNumberOfResultsToReturn(5);
		request.setBreakfastOption(BreakfastOption.BREAKFAST);
		
		Collection<HotelDetails> foundHotels = service.search(request);					
		Assert.assertEquals(BreakfastOption.BREAKFAST, request.getBreakfastOption());
		Assert.assertEquals(2, foundHotels.size());
		HotelDetails hotel = foundHotels.iterator().next();
		Assert.assertEquals(true, hotel.isBreakfastIncluded());
		hotel = foundHotels.iterator().next();
		Assert.assertEquals(true, hotel.isBreakfastIncluded());
		Mockito.verify(mockPricingAndAvailabilityService, Mockito.times(1)).getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject());
				
	}
	
	@Test
	public void testSearch_breakfastOptionNotRequired() {		
		List<HotelDetails> list = new ArrayList<HotelDetails>();
		list.add(new HotelDetails("Paris", 100, 2, false));
		list.add(new HotelDetails("London", 101, 3, false));
		list.add(new HotelDetails("NewYork3", 103, 1, true));//most popularity
		list.add(new HotelDetails("NewYork", 102, 4, false));
		list.add(new HotelDetails("NewYork2", 103, 4, true));
		
		PricingAndAvailabilityService mockPricingAndAvailabilityService = Mockito.mock(PricingAndAvailabilityService.class);		
		Mockito.when(
				mockPricingAndAvailabilityService.getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject()))
				.thenReturn(list);				
		HotelSearchService service = new HotelSearchServiceImpl1(mockPricingAndAvailabilityService);
		request.setNumberOfResultsToReturn(5);
		request.setBreakfastOption(BreakfastOption.NO_BREAKFAST);
		
		Collection<HotelDetails> foundHotels = service.search(request);					
		Assert.assertEquals(BreakfastOption.NO_BREAKFAST, request.getBreakfastOption());
		Assert.assertEquals(3, foundHotels.size());
		HotelDetails hotel = foundHotels.iterator().next();
		Assert.assertEquals(false, hotel.isBreakfastIncluded());
		hotel = foundHotels.iterator().next();
		Assert.assertEquals(false, hotel.isBreakfastIncluded());
		hotel = foundHotels.iterator().next();
		Assert.assertEquals(false, hotel.isBreakfastIncluded());
		Mockito.verify(mockPricingAndAvailabilityService, Mockito.times(1)).getAvailableHotelDetails(Mockito.<PricingAndAvailabilityRequest>anyObject());				
	}
	
	

}

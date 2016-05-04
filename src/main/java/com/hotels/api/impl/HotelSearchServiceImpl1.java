package com.hotels.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.hotels.api.HotelRequest;
import com.hotels.api.HotelSearchService;
import com.hotels.platform.availability.HotelDetails;
import com.hotels.platform.availability.PricingAndAvailabilityRequest;
import com.hotels.platform.availability.PricingAndAvailabilityService;

public class HotelSearchServiceImpl1 implements HotelSearchService {

	private PricingAndAvailabilityService pricingAndAvailabilityService;

	public HotelSearchServiceImpl1(
			PricingAndAvailabilityService pricingAndAvailabilityService) {
		// constructor injection of the back end service
		this.pricingAndAvailabilityService = pricingAndAvailabilityService;
	}

	/*
	 * (non-Javadoc)
	 * @see com.hotels.api.HotelSearchService#search(com.hotels.api.HotelRequest)
	 */
	public Collection<HotelDetails> search(HotelRequest request) {
		if(request == null) {
			return Collections.emptyList();
		}		
		PricingAndAvailabilityRequest pricingAndAvailabilityRequest = 
				new PricingAndAvailabilityRequest(request.getDestination(),
						request.getCheckIn(),
						request.getCheckOut(),
						request.getNumberOfGuests());
		
		//api call to the back-end system
		Collection<HotelDetails> matchingHotels = pricingAndAvailabilityService
				.getAvailableHotelDetails(pricingAndAvailabilityRequest);
		
		List<HotelDetails> returnList = new ArrayList<HotelDetails>();		
		for(HotelDetails hotel : matchingHotels) {
			if(request.isPriceRangeSet()) {
				//additional feature, price range
				if(hotel.getNightlyPrice() < request.getStartPrice()
						|| hotel.getNightlyPrice() > request.getEndPrice()) {
					continue;
				}
			}
			//filter out breakfast option
			switch (request.getBreakfastOption()) {
				case BREAKFAST:
					if(!hotel.isBreakfastIncluded()) {
						continue;
					}
					break;
				case NO_BREAKFAST:
					if(hotel.isBreakfastIncluded()) {
						continue;
					}
					break;
				default://EITHER
					;
			}	
			returnList.add(hotel);
		}
		
		//always sortByPopularity
		sortByPopularity(returnList);
				
		//shorten the result if required
		if(returnList.size() < request.getNumberOfResultsToReturn()) {
			return returnList;
		}
		return returnList.subList(0, request.getNumberOfResultsToReturn());
	}
	
	/**
	 * Orders the list by Popularity
	 * 
	 * @param list
	 */
	private void sortByPopularity(List<HotelDetails> list) {
		Collections.sort(list, new Comparator<HotelDetails>() {
			public int compare(HotelDetails o1, HotelDetails o2) {				
				return o1.getPopularityIndex() < o2.getPopularityIndex() ? -1 : 1;
			}			
		});
	}
}

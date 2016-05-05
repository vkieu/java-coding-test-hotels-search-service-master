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
		
		//API call to the back-end system
		Collection<HotelDetails> matchingHotels = pricingAndAvailabilityService
				.getAvailableHotelDetails(pricingAndAvailabilityRequest);
		
		//filter results based on the request parameter
		List<HotelDetails> returnList = filterResults(matchingHotels, request);
		
		//always sortByPopularity
		sortByPopularity(returnList);
				
		return limitResults(returnList, request);
	}
	
	private List<HotelDetails> filterResults(Collection<HotelDetails> list, HotelRequest request) {
		List<HotelDetails> returnList = new ArrayList<HotelDetails>();		
		for(HotelDetails hotel : list) {
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
		return returnList;
	}
	
	private List<HotelDetails> limitResults(List<HotelDetails> list,
			HotelRequest request) {
		// shorten the result if required
		if (list.size() < request.getNumberOfResultsToReturn()) {
			return list;
		}
		return list.subList(0, request.getNumberOfResultsToReturn());
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

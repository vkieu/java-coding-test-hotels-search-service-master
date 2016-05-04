package com.hotels.api;

import java.util.Collection;

import com.hotels.platform.availability.HotelDetails;

public interface HotelSearchService {
	/**
	 * search and filter the hotels based on the request parameter
	 * 
	 * @param request
	 * @return collection of hotels matched the request
	 */
	Collection<HotelDetails> search(HotelRequest request);
}

package com.hotels.api;

import java.util.Date;
import static java.util.Objects.requireNonNull;

public class HotelRequest {

	private int numberOfResultsToReturn = 3;// default to as requested in the spec

	// available option are include breakfast, no breakfast, either
	private BreakfastOption breakfastOption = BreakfastOption.EITHER;

	// city location
	private String destination;

	// price range
	private boolean priceRangeSet = false;
	private int startPrice = 0;
	private int endPrice = Integer.MAX_VALUE;

	// dates occupied
	private Date checkIn;
	private Date checkOut;

	// how many guests
	private int numberOfGuests;

	/**
	 * Construct new instance
	 * 
	 * @param destination - The location the user would like to stay e.g. 'Paris'
	 * @param checkIn - hotel checks in date
	 * @param checkOut - hotel checks out date
	 * @param numberOfGuests - number of guest will be staying
	 */
	public HotelRequest(String destination, Date checkIn, Date checkOut, int numberOfGuests) {
		this.destination = requireNonNull(destination);
		this.checkIn = requireNonNull(checkIn);
		this.checkOut = requireNonNull(checkOut);
		this.numberOfGuests = numberOfGuests;
		if(checkIn.after(checkOut)){
			throw new IllegalArgumentException("checkIn date is after checkOut date");
		}
	}
	/**
	 * number of result to be returned
	 * @return number of result to be returned
	 */
	public int getNumberOfResultsToReturn() {
		return numberOfResultsToReturn;
	}

	/**
	 * restricted the number of result returned
	 * 
	 * @param numberOfResultsToReturn restricted the number of result returned
	 */
	public void setNumberOfResultsToReturn(int numberOfResultsToReturn) {
		this.numberOfResultsToReturn = numberOfResultsToReturn;
	}
	
	/**
	 * @return the breakfast option was set
	 */
	public BreakfastOption getBreakfastOption() {
		return breakfastOption;
	}

	/**
	 * set the matching breakfast option
	 * 
	 * @param breakfastOption set the matching breakfast option 
	 */
	public void setBreakfastOption(BreakfastOption breakfastOption) {
		this.breakfastOption = breakfastOption;
	}

	/**
	 * nightly price filter
	 * 
	 * @return start price filter
	 */
	public int getStartPrice() {
		return startPrice;
	}

	/**
	 * Set the range in which result matches
	 * 
	 * @param startPrice - start price filter
	 * @param endPrice - end price filter
	 */
	public void setPriceRange(int startPrice, int endPrice) {
		if(startPrice > endPrice) {
			throw new IllegalArgumentException("Incorrect price range specified");
		}
		priceRangeSet = true;
		this.startPrice = startPrice;
		this.endPrice = endPrice;
	}
	
	/**
	 * nightly price filter
	 * 
	 * @return end price filter
	 */
	public int getEndPrice() {
		return endPrice;
	}

	/**
	 * The location the user would like to stay e.g. 'Paris'
	 * 
	 * @return
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * Has price range been set
	 *  
	 * @return true if set else false
	 */
	public boolean isPriceRangeSet() {
		return priceRangeSet;
	}
	
	/**
	 * Check in date criteria 
	 * 
	 * @return Check in date criteria
	 */
	public Date getCheckIn() {
		return checkIn;
	}

	/**
	 * Check out date criteria 
	 * 
	 * @return Check out date criteria
	 */
	public Date getCheckOut() {
		return checkOut;
	}

	/**
	 * Number of guests criteria
	 * @return
	 */
	public int getNumberOfGuests() {
		return numberOfGuests;
	}
	
	 @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((breakfastOption == null) ? 0 : breakfastOption.hashCode());
		result = prime * result + ((checkIn == null) ? 0 : checkIn.hashCode());
		result = prime * result
				+ ((checkOut == null) ? 0 : checkOut.hashCode());
		result = prime * result
				+ ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + endPrice;
		result = prime * result + numberOfGuests;
		result = prime * result + numberOfResultsToReturn;
		result = prime * result + (priceRangeSet ? 1231 : 1237);
		result = prime * result + startPrice;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HotelRequest other = (HotelRequest) obj;
		if (breakfastOption != other.breakfastOption)
			return false;
		if (checkIn == null) {
			if (other.checkIn != null)
				return false;
		} else if (!checkIn.equals(other.checkIn))
			return false;
		if (checkOut == null) {
			if (other.checkOut != null)
				return false;
		} else if (!checkOut.equals(other.checkOut))
			return false;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (endPrice != other.endPrice)
			return false;
		if (numberOfGuests != other.numberOfGuests)
			return false;
		if (numberOfResultsToReturn != other.numberOfResultsToReturn)
			return false;
		if (priceRangeSet != other.priceRangeSet)
			return false;
		if (startPrice != other.startPrice)
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "HotelRequest{" +
                "destination='" + destination + '\'' +
                ", startPrice=" + startPrice +
                ", endPrice=" + endPrice +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", numberOfResultsToReturn=" + numberOfResultsToReturn +
                ", priceRangeSet=" + priceRangeSet +
                ", numberOfGuests=" + numberOfGuests +
                ", breakfastOption=" + breakfastOption + 
                '}';
    }
}


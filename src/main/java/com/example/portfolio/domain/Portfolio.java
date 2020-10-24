package com.example.portfolio.domain;


public class Portfolio {

	private int shortPosition;
	private int longPosition;
	private String side;
	private String securityId;
	private int currentPosition;
	private int currentPrice;

	public Portfolio(){
	}

	public Portfolio(int shortPosition, int longPosition, String side, String securityId, int currentPosition, int currentPrice) {
		this.shortPosition = shortPosition;
		this.longPosition = longPosition;
		this.side = side;
		this.securityId = securityId;
		this.currentPosition = currentPosition;
		this.currentPrice = currentPrice;
	}

	public int getShortPosition() {
		return shortPosition;
	}

	public void setShortPosition(int shortPosition) {
		this.shortPosition = shortPosition;
	}

	public int getLongPosition() {
		return longPosition;
	}

	public void setLongPosition(int longPosition) {
		this.longPosition = longPosition;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public int getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	@Override
	public String toString() {
		return "Portfolio{" +
				"shortPosition=" + shortPosition +
				", longPosition=" + longPosition +
				", side='" + side + '\'' +
				", securityId='" + securityId + '\'' +
				", currentPosition=" + currentPosition +
				", currentPrice=" + currentPrice +
				'}';
	}
}

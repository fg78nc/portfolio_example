package com.example.portfolio;

// Security ID, Current Position, and Current Price. Also user might have option to order data based on any of the field
public class PortfolioCriteria {

	private Integer shortPosition;
	private String side;
	private String securityId;
	private String orderBy;

	PortfolioCriteria() {
	}

	PortfolioCriteria(Integer shortPosition, String side, String securityId, String orderBy) {
		this.shortPosition = shortPosition;
		this.side = side;
		this.securityId = securityId;
		this.orderBy = orderBy;
	}

	public Integer getShortPosition() {
		return shortPosition;
	}

	public void setShortPosition(Integer shortPosition) {
		this.shortPosition = shortPosition;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public String toString() {
		return "PortfolioCriteria{" +
				"shortPosition=" + shortPosition +
				", side='" + side + '\'' +
				", securityId='" + securityId + '\'' +
				", orderBy='" + orderBy + '\'' +
				'}';
	}
}

package com.trinary.persistence;

import java.util.ArrayList;
import java.util.List;

public class OrderPair {
	protected String field;
	protected OrderDirection direction;
	
	public OrderPair(String field, OrderDirection direction) {
		super();
		this.field = field;
		this.direction = direction;
	}
	
	public OrderPair(String field, String direction) {
		super();
		this.field = field;
		if (direction.equals("asc")) {
			this.direction = OrderDirection.ASCENDING;
		} else {
			this.direction = OrderDirection.DESCENDING;
		}
	}
	
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}
	/**
	 * @return the direction
	 */
	public OrderDirection getDirection() {
		return direction;
	}
	/**
	 * @param direction the direction to set
	 */
	public void setDirection(OrderDirection direction) {
		this.direction = direction;
	}
	
	public static OrderPair generate(String orderPair) {
		String[] orderElements = orderPair.split(":");
		return new OrderPair(orderElements[0].trim(), orderElements[1].trim());
	}
	
	public static List<OrderPair> generateAll(String orderPairs) {
		List<OrderPair> pairs = new ArrayList<OrderPair>();
		if (orderPairs == null) {
			return null;
		}
		
		String[] orderPairList = orderPairs.split(",");
		
		for (String orderPair : orderPairList) {
			pairs.add(generate(orderPair));
		}
		
		return pairs;
	}
}
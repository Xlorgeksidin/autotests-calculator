package ru.neoflex.model;

import com.google.gson.annotations.SerializedName;

public class RequestPutPrice{

	@SerializedName("price")
	private Price price;

	public void setPrice(Price price){
		this.price = price;
	}

	public Price getPrice(){
		return price;
	}

	@Override
 	public String toString(){
		return 
			"RequestPutPrice{" + 
			"price = '" + price + '\'' + 
			"}";
		}
}
package beans;

import java.io.Serializable;

public class CurrencyDefault  implements Serializable {

	
	
	String currency_code;
	public String getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
	public int getRounding() {
		return rounding;
	}
	public void setRounding(int rounding) {
		this.rounding = rounding;
	}
	public String getIso_code() {
		return iso_code;
	}
	public void setIso_code(String iso_code) {
		this.iso_code = iso_code;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDefault_holiday() {
		return default_holiday;
	}
	public void setDefault_holiday(String default_holiday) {
		this.default_holiday = default_holiday;
	}
	public int getSpot_days() {
		return spot_days;
	}
	public void setSpot_days(int spot_days) {
		this.spot_days = spot_days;
	}
	public int getIs_precious_metal_b() {
		return is_precious_metal_b;
	}
	public void setIs_precious_metal_b(int is_precious_metal_b) {
		this.is_precious_metal_b = is_precious_metal_b;
	}
	public int getNon_deliverable_b() {
		return non_deliverable_b;
	}
	public void setNon_deliverable_b(int non_deliverable_b) {
		this.non_deliverable_b = non_deliverable_b;
	}
	int rounding = 0;
	String iso_code;
	String country;
	String default_holiday;
	int spot_days=0;
	int is_precious_metal_b =0;
	int non_deliverable_b =0;
	
}

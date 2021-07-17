package dataapp.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class BloodDataRequest {

	@Min(value = 0, message = "Value:New value must be 0 and more.")
	@Digits(integer = 3, fraction = 2, message = "Value:New value must be within 3 digit integer and 2 digit fraction.")
	private double newValue;

	@NotBlank(message = "Other:Invalid input error occured. Please retry or start over.")
	@Pattern(regexp = "TG|GTP|HDL|LDL|FPG", message = "Other:Invalid input error occured. Please retry or start over.")
	private String bloodName;

	@NotBlank(message = "Other:Invalid input error occured. Please retry or start over.")
	@Size(min = 1, max = 512, message = "Other:Invalid input error occured. Please retry or start over.")
	private String jwt;

	public double getNewvalue() {
		return newValue;
	}

	public void setNewvalue(double newValue) {
		this.newValue = newValue;
	}

	public String getBloodname() {
		return bloodName;
	}

	public void setBloodname(String bloodName) {
		this.bloodName = bloodName;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}

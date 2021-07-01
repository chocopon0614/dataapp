package dataapp.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class blooddatarequest {

	@Min(value = 0, message = "Value:New value must be 0 and more.")
	@Digits(integer = 3, fraction = 2, message = "Value:New value must be within 3 digit integer and 2 digit fraction.")
	private double newvalue;

	@NotBlank(message = "Other:Invalid input error occured. Please retry or start over.")
	@Pattern(regexp = "TG|GTP|HDL|LDL|FPG", message = "Other:Invalid input error occured. Please retry or start over.")
	private String bloodname;

	@NotBlank(message = "Other:Invalid input error occured. Please retry or start over.")
	@Size(min = 1, max = 512, message = "Other:Invalid input error occured. Please retry or start over.")
	private String jwt;

	public double getNewvalue() {
		return newvalue;
	}

	public void setNewvalue(double newvalue) {
		this.newvalue = newvalue;
	}

	public String getBloodname() {
		return bloodname;
	}

	public void setBloodname(String bloodname) {
		this.bloodname = bloodname;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}

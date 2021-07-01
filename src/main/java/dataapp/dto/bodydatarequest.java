package dataapp.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class bodydatarequest {

	@Min(value = 0, message = "Height:Height must be 0 and more.")
	@Digits(integer = 3, fraction = 2, message = "Height:Height must be within 3 digit integer and 2 digit fraction.")
	private double height;

	@Min(value = 0, message = "Weight:Weight must be 0 and more.")
	@Digits(integer = 3, fraction = 2, message = "Weight:Weight must be within 3 digit integer and 2 digit fraction.")
	private double weight;

	@NotBlank(message = "Other:Invalid input error occured. Please retry or start over.")
	@Size(min = 1, max = 512, message = "Other:Invalid input error occured. Please retry or start over.")
	private String jwt;

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}

package dataapp.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@SpringBootTest
public class BloodDataRequestTest {

	@Autowired
	private Validator validator;

	private BloodDataRequest blooddataRequest = new BloodDataRequest();
	private BindingResult bindingResult = new BindException(blooddataRequest, "BloodDataRequest");

	@BeforeEach
	public void setup() {
		blooddataRequest.setNewvalue(23.2);
		blooddataRequest.setBloodname("TG");
		blooddataRequest.setJwt("dummyJwt");
	}

	@Test
	void noError() {
		validator.validate(blooddataRequest, bindingResult);
		assertNull(bindingResult.getFieldError());
	}

	@Test
	void newvalueError_min() {
		blooddataRequest.setNewvalue(-0.1);
		validator.validate(blooddataRequest, bindingResult);

		assertEquals("newValue", bindingResult.getFieldError().getField());
		assertEquals("Value:New value must be 0 and more.", bindingResult.getFieldError().getDefaultMessage());

	}

	@Test
	void newvalueError_digit_1() {
		blooddataRequest.setNewvalue(1234.1);
		validator.validate(blooddataRequest, bindingResult);

		assertEquals("newValue", bindingResult.getFieldError().getField());
		assertEquals("Value:New value must be within 3 digit integer and 2 digit fraction.",
				bindingResult.getFieldError().getDefaultMessage());

	}

	@Test
	void newvalueError_digit_2() {
		blooddataRequest.setNewvalue(1.143);
		validator.validate(blooddataRequest, bindingResult);

		assertEquals("newValue", bindingResult.getFieldError().getField());
		assertEquals("Value:New value must be within 3 digit integer and 2 digit fraction.",
				bindingResult.getFieldError().getDefaultMessage());

	}

	@Test
	void bloodnameError_blank() {
		blooddataRequest.setBloodname("");
		validator.validate(blooddataRequest, bindingResult);

		assertEquals("bloodName", bindingResult.getFieldError().getField());
		assertEquals("Other:Invalid input error occured. Please retry or start over.",
				bindingResult.getFieldError().getDefaultMessage());

	}

	@Test
	void bloodnameError_pattern() {
		blooddataRequest.setBloodname("DUM");
		validator.validate(blooddataRequest, bindingResult);

		assertEquals("bloodName", bindingResult.getFieldError().getField());
		assertEquals("Other:Invalid input error occured. Please retry or start over.",
				bindingResult.getFieldError().getDefaultMessage());

	}

	@Test
	void jwtError_blank() {
		blooddataRequest.setJwt("");
		validator.validate(blooddataRequest, bindingResult);

		assertEquals("jwt", bindingResult.getFieldError().getField());
		assertEquals("Other:Invalid input error occured. Please retry or start over.",
				bindingResult.getFieldError().getDefaultMessage());

	}

	@Test
	void jwtError_max() {
		blooddataRequest.setJwt(
				"111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
		validator.validate(blooddataRequest, bindingResult);

		assertEquals("jwt", bindingResult.getFieldError().getField());
		assertEquals("Other:Invalid input error occured. Please retry or start over.",
				bindingResult.getFieldError().getDefaultMessage());

	}
}

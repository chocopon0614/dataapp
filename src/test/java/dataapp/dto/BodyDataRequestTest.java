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
public class BodyDataRequestTest {

	@Autowired
	private Validator validator;

	private BodyDataRequest bodydataRequest = new BodyDataRequest();
	private BindingResult bindingResult = new BindException(bodydataRequest, "BodyDataRequest");

	@BeforeEach
	public void setup() {
		bodydataRequest.setHeight(180.23);
		bodydataRequest.setWeight(67.2);
		bodydataRequest.setJwt("dummyJwt");
	}

	@Test
	void noError() {
		validator.validate(bodydataRequest, bindingResult);
		assertNull(bindingResult.getFieldError());
	}

	@Test
	void heightError_min() {
		bodydataRequest.setHeight(-0.2);
		validator.validate(bodydataRequest, bindingResult);

		assertEquals("height", bindingResult.getFieldError().getField());
		assertEquals("Height:Height must be 0 and more.", bindingResult.getFieldError().getDefaultMessage());

	}

	@Test
	void heightError_digit_1() {
		bodydataRequest.setHeight(1029.2);
		validator.validate(bodydataRequest, bindingResult);

		assertEquals("height", bindingResult.getFieldError().getField());
		assertEquals("Height:Height must be within 3 digit integer and 2 digit fraction.",
				bindingResult.getFieldError().getDefaultMessage());

	}

	@Test
	void heightError_digit_2() {
		bodydataRequest.setHeight(9.233);
		validator.validate(bodydataRequest, bindingResult);

		assertEquals("height", bindingResult.getFieldError().getField());
		assertEquals("Height:Height must be within 3 digit integer and 2 digit fraction.",
				bindingResult.getFieldError().getDefaultMessage());

	}

	@Test
	void weightError_min() {
		bodydataRequest.setWeight(-3);
		validator.validate(bodydataRequest, bindingResult);

		assertEquals("weight", bindingResult.getFieldError().getField());
		assertEquals("Weight:Weight must be 0 and more.", bindingResult.getFieldError().getDefaultMessage());

	}

	@Test
	void weightError_digit_1() {
		bodydataRequest.setWeight(4022.23);
		validator.validate(bodydataRequest, bindingResult);

		assertEquals("weight", bindingResult.getFieldError().getField());
		assertEquals("Weight:Weight must be within 3 digit integer and 2 digit fraction.",
				bindingResult.getFieldError().getDefaultMessage());

	}

	@Test
	void weightError_digit_2() {
		bodydataRequest.setWeight(3.123);
		validator.validate(bodydataRequest, bindingResult);

		assertEquals("weight", bindingResult.getFieldError().getField());
		assertEquals("Weight:Weight must be within 3 digit integer and 2 digit fraction.",
				bindingResult.getFieldError().getDefaultMessage());

	}

	@Test
	void jwtError_blank() {
		bodydataRequest.setJwt("");
		validator.validate(bodydataRequest, bindingResult);

		assertEquals("jwt", bindingResult.getFieldError().getField());
		assertEquals("Other:Invalid input error occured. Please retry or start over.",
				bindingResult.getFieldError().getDefaultMessage());

	}

	@Test
	void jwtError_max() {
		bodydataRequest.setJwt(
				"111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
		validator.validate(bodydataRequest, bindingResult);

		assertEquals("jwt", bindingResult.getFieldError().getField());
		assertEquals("Other:Invalid input error occured. Please retry or start over.",
				bindingResult.getFieldError().getDefaultMessage());

	}
}

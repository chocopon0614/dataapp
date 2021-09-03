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
		blooddataRequest.setBloodname("TG");
		blooddataRequest.setJwt("dummyJwt");
		blooddataRequest.setNewvalue(23.2);
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

}

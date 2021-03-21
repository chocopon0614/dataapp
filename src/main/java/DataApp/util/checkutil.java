package DataApp.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class checkutil {
	public static Map<String, String> validCheck(BindingResult result) {

		Map<String, String> map = new HashMap<>();

		for (ObjectError error : result.getAllErrors()) {
			String temp = error.getDefaultMessage();
			map.put(temp.split(":")[0], temp.split(":")[1]);
		}

		return map;
	}
}

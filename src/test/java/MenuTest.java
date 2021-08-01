
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import dataapp.Menu;
import dataapp.Util;
import dataapp.dao.UserDataDao;
import dataapp.dao.UserDatabloodDao;
import dataapp.dao.UserInformationDao;
import dataapp.dto.BodyDataRequest;
import dataapp.entity.UserData;
import dataapp.entity.UserDatablood;
import dataapp.entity.UserInformation;

@ExtendWith(MockitoExtension.class)
public class MenuTest {
	@Mock
	private Util util;

	@Mock
	private UserInformationDao daoUser;

	@Mock
	private UserDataDao daoData;

	@Mock
	private UserDatabloodDao daoBlood;

	@Mock
	BindingResult bindingResult;

	@InjectMocks
	private Menu menuController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void bodyDataTestNormal() {

		UserInformation user = new UserInformation();
		user.setUsername("dummyUsername");

		UserData data1 = new UserData();
		data1.setId(18);
		data1.setHeight(178.2);
		data1.setWeight(76.2);
		data1.setUserinformation(user);
		data1.setCreateTime(Timestamp.valueOf("2021-08-01 12:02:03.189"));

		UserData data2 = new UserData();
		data2.setId(133);
		data2.setHeight(177.3);
		data2.setWeight(74.9);
		data2.setUserinformation(user);
		data2.setCreateTime(Timestamp.valueOf("2021-09-12 06:12:33.923"));

		List<UserData> dataList = new ArrayList<>();
		dataList.add(data1);
		dataList.add(data2);

		try {
			doReturn("dummyUsername").when(util).varifyJwt(Mockito.anyString(), Mockito.anyString());
			doReturn(new UserInformation()).when(daoUser).findByUsername(Mockito.anyString());
			doReturn(dataList).when(daoData).findByUserid(Mockito.any());

			ResponseEntity<String> result = menuController.bodyData("dummyJwt");
			assertEquals(HttpStatus.OK, result.getStatusCode());
			assertNotNull(result.getBody());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void bodyDataTestError() {

		try {
			doReturn("dummyUsername").when(util).varifyJwt(Mockito.anyString(), Mockito.anyString());
			doReturn(new UserInformation()).when(daoUser).findByUsername(Mockito.anyString());
			doThrow(new NoResultException()).when(daoData).findByUserid(Mockito.any());

			ResponseEntity<String> result = menuController.bodyData("dummyJwt");
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void bloodDataTestNormal() {

		UserInformation user = new UserInformation();
		user.setUsername("dummyUsername");

		UserDatablood data1 = new UserDatablood();
		data1.setFpg(178);
		data1.setGtp(123.2);
		data1.setHdl(12.2);
		data1.setUserinformation(user);
		data1.setCreateTime(Timestamp.valueOf("2021-05-21 23:17:13.879"));

		try {
			doReturn("dummyUsername").when(util).varifyJwt(Mockito.anyString(), Mockito.anyString());
			doReturn(new UserInformation()).when(daoUser).findByUsername(Mockito.anyString());
			doReturn(data1).when(daoBlood).findByUserid(user);

			ResponseEntity<String> result = menuController.bodyData("dummyJwt");
			assertEquals(HttpStatus.OK, result.getStatusCode());
			assertNotNull(result.getBody());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void bloodDataTestError() {

		try {
			doReturn("dummyUsername").when(util).varifyJwt(Mockito.anyString(), Mockito.anyString());
			doReturn(new UserInformation()).when(daoUser).findByUsername(Mockito.anyString());
			doThrow(new NonUniqueResultException()).when(daoBlood).findByUserid(Mockito.any());

			ResponseEntity<String> result = menuController.bloodData("dummyJwt");
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void deleteDataTestNormal() {

		UserInformation user = new UserInformation();
		user.setUsername("dummyUsername");

		UserData data1 = new UserData();
		data1.setId(1292);
		data1.setHeight(178.2);
		data1.setWeight(76.2);
		data1.setUserinformation(user);
		data1.setCreateTime(Timestamp.valueOf("2021-08-01 12:02:03.189"));

		try {
			doReturn("dummyUsername").when(util).varifyJwt(Mockito.anyString(), Mockito.anyString());
			doReturn(data1).when(daoData).find(Mockito.anyInt());
			doNothing().when(daoData).remove(data1);

			ResponseEntity<String> result = menuController.deleteData("dummyJwt", 1292);
			assertEquals(HttpStatus.OK, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void deleteDataTestError1() {

		try {
			doReturn("dummyUsername").when(util).varifyJwt(Mockito.anyString(), Mockito.anyString());
			doReturn(null).when(daoData).find(Mockito.anyInt());

			ResponseEntity<String> result = menuController.deleteData("dummyJwt", 273);
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void deleteDataTestError2() {
		UserInformation user = new UserInformation();
		user.setUsername("dummyError");

		UserData data1 = new UserData();
		data1.setId(1292);
		data1.setHeight(178.2);
		data1.setWeight(76.2);
		data1.setUserinformation(user);
		data1.setCreateTime(Timestamp.valueOf("2021-08-01 12:02:03.189"));

		try {
			doReturn("dummyUsername").when(util).varifyJwt(Mockito.anyString(), Mockito.anyString());
			doReturn(data1).when(daoData).find(Mockito.anyInt());
			doNothing().when(daoData).remove(data1);

			ResponseEntity<String> result = menuController.deleteData("dummyJwt", 273);
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void deleteDataTestException() {
		UserInformation user = new UserInformation();
		user.setUsername("dummyUsername");

		UserData data1 = new UserData();
		data1.setId(12832);
		data1.setHeight(128.9);
		data1.setWeight(36);
		data1.setUserinformation(user);
		data1.setCreateTime(Timestamp.valueOf("2020-12-28 02:03:12.929"));

		try {
			doReturn("dummyUsername").when(util).varifyJwt(Mockito.anyString(), Mockito.anyString());
			doReturn(data1).when(daoData).find(Mockito.anyInt());
			doThrow(new EntityNotFoundException()).when(daoData).remove(data1);

			ResponseEntity<String> result = menuController.deleteData("dummyJwt", 12832);
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void insertDataTestNormal() {

		UserInformation user = new UserInformation();
		user.setUsername("dummyUsername");

		BodyDataRequest bodyData = new BodyDataRequest();
		bodyData.setHeight(123.9);
		bodyData.setWeight(43.2);
		bodyData.setJwt("dummyJwt");

		try {
			doReturn(false).when(bindingResult).hasErrors();
			doReturn("dummyUsername").when(util).varifyJwt(Mockito.anyString(), Mockito.anyString());
			doReturn(user).when(daoUser).findByUsername(Mockito.anyString());
			doNothing().when(daoData).persist(Mockito.any());

			ResponseEntity<String> result = menuController.insertData(bodyData, bindingResult);
			assertEquals(HttpStatus.OK, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void insertDataTestError() {

		try {
			doReturn(true).when(bindingResult).hasErrors();
			ResponseEntity<String> result = menuController.insertData(new BodyDataRequest(), bindingResult);
			assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void insertDataTestException() {

		try {
			doReturn(false).when(bindingResult).hasErrors();
			doReturn("dummyUsername").when(util).varifyJwt(Mockito.anyString(), Mockito.anyString());
			doReturn(new UserInformation()).when(daoUser).findByUsername(Mockito.anyString());
			doThrow(new EntityExistsException()).when(daoData).persist(Mockito.any());

			ResponseEntity<String> result = menuController.insertData(new BodyDataRequest(), bindingResult);
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

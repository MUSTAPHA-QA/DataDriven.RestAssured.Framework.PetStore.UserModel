package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class UserTests {
	
	Logger logger = LogManager.getLogger(this.getClass());
	
	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void postUser(String userId, String userName, String firstName, String lastName, String email, String password, String phone) {
		
		logger.info("------------------------Starting create user test------------------------");
		
		// Generating user data
		User userPayload = new User();
		userPayload.setId(Integer.parseInt(userId));
		userPayload.setUsername(userName);
		userPayload.setFirstname(firstName);
		userPayload.setLastname(lastName);
		userPayload.setEmail(email);
		userPayload.setPassword(password);
		userPayload.setPhone(phone);
		
		// Sending the request to create a user
		Response response = UserEndpoints.createUser(userPayload);
		
		// Validations
		logger.info("Response Status Code: {}", response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 200, "Unexpected status code");
		
		logger.info("Content-Type Header: {}", response.getHeader("Content-Type"));
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Unexpected Content-Type");

		// Validating JSON schema
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("userModelCreateUserJsonSchema.json"));
		
		logger.info("------------------------Finished create user test------------------------");
	}
	
	@Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void getUser(String userName) {
		
		logger.info("------------------------Starting read user test------------------------");
		
		// Sending the request to get user details
		Response response = UserEndpoints.readUser(userName);
		
		// Validations
		logger.info("Response Status Code: {}", response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 200, "Unexpected status code");
		
		logger.info("Content-Type Header: {}", response.getHeader("Content-Type"));
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Unexpected Content-Type");
		
		// Validating JSON schema
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("userModelReadUserJsonSchema.json"));
		
		logger.info("Response Body: {}", response.getBody().asString());
		response.then().log().body();
		
		logger.info("------------------------Finished read user test------------------------");	
	}
	
	@Test(priority = 3, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void deleteUser(String userName) {
		
		logger.info("------------------------Starting delete user test------------------------");
		
		// Sending the request to delete the user
		Response response = UserEndpoints.deleteUser(userName);
		
		// Validations
		logger.info("Response Status Code: {}", response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 200, "Unexpected status code");
		
		logger.info("Content-Type Header: {}", response.getHeader("Content-Type"));
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "Unexpected Content-Type");
		
		logger.info("------------------------Finished delete user test------------------------");
	}
}

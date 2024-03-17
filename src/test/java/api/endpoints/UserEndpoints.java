package api.endpoints;

import static io.restassured.RestAssured.given;
import java.util.ResourceBundle;
import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndpoints {
	
	public static ResourceBundle getURL() {
		
		ResourceBundle routes = ResourceBundle.getBundle("routes");
		return routes;
	}
	
	public static Response createUser(User userPayload){
		
		String post_url = getURL().getString("post_url");
		
		Response response =
				
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(userPayload)
		
		.when()
			.post(post_url);
		
		return response;
	}

	public static Response readUser(String userName){
		
		String get_url = getURL().getString("get_url");

		Response response =
				
		given()
			.pathParam("username", userName)
		
		.when()
			.get(get_url);
		
		return response;
	}
	
	public static Response deleteUser(String userName){
		
		String delete_url = getURL().getString("delete_url");

		Response response =
				
		given()
			.pathParam("username", userName)
		
		.when()
			.delete(delete_url);
		
		return response;
	}
}

package applogic;

import io.restassured.RestAssured;
import io.restassured.http.Header;

public class API_Helper1 extends DriverBasedHelper {

	public API_Helper1(ApplicationManager1 app) {
		super(app);
	}

	private static final Header H_CONTENT_TYPE_XML = new Header("Content-Type", "application/xml; charset=UTF-8");
	private static final Header H_CONTENT_TYPE_JSON = new Header("Content-Type", "application/json");

	private void setupRestAssured() {

		RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
		// RestAssured.port = 443;


	}

	public String runAPI_Get(String topic, String requestedValue) {
		setupRestAssured();

		log.debug("\n\n============\nRequest: \n =============\n" + topic + "/" + requestedValue);
		String response = RestAssured.given().log().all().header(H_CONTENT_TYPE_JSON)
				.get(topic + "/" + requestedValue).andReturn().asString();

		log.debug("\n\n============\nResponse: \n =============\n" + response);
		return response;
	}

	public String runAPI_Post(String topic, String body) {

		setupRestAssured();

		log.debug("\n\n============\nRequest: \n =============\n" + topic + "\n" + body);
		String response = RestAssured.given().log().all().header(H_CONTENT_TYPE_JSON).body(body).post(topic).andReturn()
				.asString();

		log.debug("\n\n============\nResponse: \n =============\n" + response);
		return response;
	}

	public String runAPI_Put(String topic, String valueToUpdate, String body) {
		setupRestAssured();

		log.debug("\n\n============\nRequest: \n =============\n" + topic + "/" + valueToUpdate + "\nbody: " + body);
		String response = RestAssured.given().log().all().header(H_CONTENT_TYPE_JSON).body(body)
				.put(topic + "/" + valueToUpdate).andReturn().asString();
		log.debug("\n\n============\nResponse XML: \n =============\n" + response);
		return response;
	}

	public String runAPI_Delete(String topic, String valueToDelete) {
		setupRestAssured();
		log.debug("\n\n============\nRequest: \n =============\n" + topic + "/" + valueToDelete);
		String response = RestAssured.given().log().all().header(H_CONTENT_TYPE_JSON)
				.delete(topic + "/" + valueToDelete).andReturn().asString();

		log.debug("\n\n============\nResponse: \n =============\n" + response);
		return response;
	}

}

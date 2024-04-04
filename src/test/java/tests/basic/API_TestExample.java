package tests.basic;

import org.testng.annotations.Test;

import tests.supers.TestBase;

public class API_TestExample extends TestBase {

	@Test
	public void test() {
		try {

			// GET EXAMPLE //
			app.getAPI_Helper().runAPI_Get("posts", "1");

			// POST EXAMPLE //
			String body = "{" + "\"userId\": 1," + "\"id\": 1," + "\"title\": \"quidem molestiae enim\"}";

			app.getAPI_Helper().runAPI_Post("albums", body);
			
			// PUT EXAMPLE //
			body="{" + 
					"\"postId\": 1," + 
					"\"id\": 1," + 
					"\"name\": \"id labore ex et quam laborum\"," + 
					"\"email\": \"Eliseo@gardner.biz\"," + 
					"\"body\": \"laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium\"" + 
					"}";
			app.getAPI_Helper().runAPI_Put("comments", "1", body);

			// DELETE EXAMPLE //
			app.getAPI_Helper().runAPI_Delete("users", "1");


			endTestSuccess();
		} catch (Throwable t) {
			onTestFailure(t);
		}
	}
	
}

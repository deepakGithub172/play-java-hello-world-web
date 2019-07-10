/**
 * 
 */
package controllers;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.typesafe.config.ConfigFactory;

import play.Application;
import play.api.ConfigLoader;
import play.api.Configuration;
import play.libs.F;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author deesingh13
 *
 */
public class EmployeeInfo extends Controller {

	public Result employeeId() {	
		return ok(views.html.employeeId.render());
	}

	public Result getInfo(String employeeId) {

		Cluster cluster = CouchbaseCluster.create("localhost:8091");
		cluster.authenticate("Administrator", "612353");
		Bucket bucket = cluster.openBucket("Employee");

		//Note : To access data we had to execute this query on Couchbase server "CREATE PRIMARY INDEX ON default;"
		//JsonObject params = JsonObject.create().put("id", employeeId);
		N1qlQueryResult result = bucket.query(N1qlQuery.simple("select * from `Employee`"));
		for(N1qlQueryRow row : result) {
			JsonObject object = row.value().getObject("Employee");
			if(object.getString("id").equals(employeeId))
				return ok(row.toString());
		}
		return ok(result.allRows().toString());

		/* JsonObject obj = JsonObject.create().put("id", "001")
				.put("name", "alok")
				.put("phone", "9125612353")
				.put("email", "alok@gmail.com");
		bucket.upsert(JsonDocument.create("alok", obj));*/

		/*JsonDocument doc = bucket.get(employeeId);
		return ok(doc.content().toString());*/

	}

	private static final String WS_PATH = "ws_path";
	@Inject WSClient ws;
	public Result getWsInfo(String employeeId) {

		WSRequest request = ws.url(ConfigFactory.load().getString(WS_PATH));
		try {
			return request.get()
					.thenApply(res ->  (ArrayNode)res.asJson().get("useData"))
					.thenApply(jsonArrayNode -> {
						for(int index = 0; index <jsonArrayNode.size(); index++) {
							JsonNode data = jsonArrayNode.get(index);
							if(data.get("firstName").textValue().equals(employeeId))
								return ok(employeeId +" "+data.get("lastName").textValue());
						}
						return ok("Record not found.");
					}).toCompletableFuture().get();
		} catch (InterruptedException | ExecutionException e) {
			return ok("Error : "+ e.getMessage());
		}

		//CompletionStage<JsonNode> jsonArray = responsePromise.thenApply(response ->  response.asJson().get("useData"));
		//CompletionStage<JsonNode> jsonArray = jsonPromise.thenApply(jsonNode -> jsonNode.get("useData"));
		/*	jsonArray.thenAccept(jsonNode -> {
				if(jsonNode.get("firstName").textValue().equals(employeeId))
					ok(employeeId +" "+jsonNode.get("lastName").textValue());
					});*/
		/*	JsonNode dataNode;
		try {
			dataNode = jsonPromise.toCompletableFuture().get();
			ArrayNode listData = (ArrayNode) dataNode.get("useData");
			for(int index = 0; index <listData.size(); index++) {
				JsonNode data = listData.get(index);
				if(data.get("firstName").textValue().equals(employeeId))
					return ok(employeeId +" "+data.get("lastName").textValue());
			}*/
		//return ok("Record not found.");
		/*} catch (InterruptedException | ExecutionException e) {
			return ok("Error : "+e.getMessage());
		}	*/	
	}

}

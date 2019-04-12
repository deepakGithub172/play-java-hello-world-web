/**
 * 
 */
package controllers;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.query.Statement;
import com.couchbase.client.java.query.dsl.path.AsPath;

import play.libs.Json;
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
		JsonObject params = JsonObject.create().put("id", employeeId);
		N1qlQueryResult result = bucket.query(N1qlQuery.parameterized("select * from `Employee` where id = $id", params));
		for(N1qlQueryRow row : result) {
			return ok(row.toString());
		}
		return ok("No records found..!!");
		
		/* JsonObject obj = JsonObject.create().put("id", "001")
				.put("name", "alok")
				.put("phone", "9125612353")
				.put("email", "alok@gmail.com");
		bucket.upsert(JsonDocument.create("alok", obj));*/
		
		/*JsonDocument doc = bucket.get(employeeId);
		return ok(doc.content().toString());*/
	}

}

/**
 * 
 */
package controllers;

import model.DCircularLinkedStructure;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author deesingh13
 *
 */
public class CircularStructureController extends Controller {

	private DCircularLinkedStructure circularLinkedStructure;

	public Result createStructure() {
		return ok(views.html.createCircularStructure.render());
	}

	public Result operations(Integer threshold) {
		circularLinkedStructure = DCircularLinkedStructure.getInstance(threshold);
		return ok(views.html.circularStructureOperations.render());
	}

	public Result perform(int number, String operation) {

		if(operation.equals("add")) {
			try {
				circularLinkedStructure.addElement(number);
				return ok("Element added. New Element count : "+ circularLinkedStructure.getElementCount());
			} catch (Exception e) {
				return ok(e.getMessage());
			}
		} 

		try {
			circularLinkedStructure.removeElement(number);
			return ok("Element removed. New Element count : "+ circularLinkedStructure.getElementCount());
		} catch (Exception e) {
			return ok(e.getMessage());
		}

	}

}

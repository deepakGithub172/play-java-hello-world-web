/**
 * 
 */
package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author deesingh13
 *
 */
public class PalindromeController extends Controller {
	
	public Result enterPalindrome() {
		return ok(views.html.palindromeCheck.render());
	}

}

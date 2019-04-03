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
public class PalindromeResult extends Controller {

	public Result check(Integer number) {
		int numberCopy = number;
		int temp = 0;
		while(numberCopy%10 != 0) {
			temp = temp*10+numberCopy%10;
			numberCopy = numberCopy/10;
		}
		if(temp == number)
			return ok(number + " is a palindrome number.");
		else
			return ok(number + " is not a palindrome number.");
	}
}

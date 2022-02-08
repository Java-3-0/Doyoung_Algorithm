// 19168KB, 276ms

package baek3986;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int numWords = Integer.parseInt(br.readLine());

		int count = 0;
		for (int i = 0; i < numWords; i++) {
			String word = br.readLine();
			if (isGoodWord(word)) {
				count++;
			}
		}
		
		System.out.println(count);
	}

	public static boolean isGoodWord(String word) {
		Stack<Character> stack = new Stack<> ();
		for (char c : word.toCharArray()) {
			if (!stack.empty() && stack.peek() == c) {
				stack.pop();
			}
			else {
				stack.push(c);
			}
		}
		
		if (stack.isEmpty()) {
			return true;
		}
		
		return false;
	}
}
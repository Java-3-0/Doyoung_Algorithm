// 14324KB, 140ms

package baek9012;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int numLines = Integer.parseInt(br.readLine());
		// 입력받는 라인 수만큼 수행
		for (int i = 0; i < numLines; i++) {
			String str = br.readLine();
			// 괄호 관계의 올바름 여부를 StringBuilder에 추가
			if (isValid(str)) {
				sb.append("YES\n");
			} else {
				sb.append("NO\n");
			}
		}
		
		System.out.print(sb.toString());
	}

	/**
	 * String의 괄호 관계가 올바른지 여부를 리턴
	 * 
	 * @param s : 괄호의 올바름을 파악할 String
	 * @return 괄호가 올바르면 true, 아니면 false
	 */
	public static boolean isValid(String s) {
		Stack<Integer> stack = new Stack<Integer> ();
		for (int i = 0; i < s.length(); i++)
		{
			// 괄호를 열 때 -> 스택에 push
			if (s.charAt(i) == '(') {
				stack.push(1);
			}
			// 괄호를 닫을 때 -> 스택이 비어있다면 괄호 관계는 올바르지 않음. 비어있지 않다면 pop
			else {
				if (stack.empty()) {
					return false;
				}
				
				stack.pop();
			}
		}
		
		// 모두 수행한 이후 스택이 비어 있어야 올바른 괄호 문자열
		if (stack.empty()) {
			return true;
		}
		return false;
	}
}

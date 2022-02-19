// 38456KB, 332ms

package bj2812;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		// N, K 입력
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		// 수를 문자열 형태로 입력
		String input = br.readLine();

		// K개를 지웠을 때 얻을 수 있는 가장 큰 수를 계산
		String answer = getMaxNumberErasingK(input, K);

		System.out.println(answer);
	}

	/** 문자열 s에서 K개의 문자를 지우고 얻을 수 있는 가장 큰 수를 문자열 타입으로 리턴 */
	public static String getMaxNumberErasingK(String s, int K) {
		int len = s.length();
		int finalLen = len - K;
		
		// 스택을 만들고 문자열의 0번 문자를 넣어둔다.
		Stack<Character> stack = new Stack<>();
		stack.push(s.charAt(0));
		
		// 문자열의 1번 문자부터 탐색
		int countErased = 0;
		for (int idx = 1; idx < len; idx++) {
			char prev = s.charAt(idx - 1);
			char here = s.charAt(idx);

			// 큰 걸 만나면, 스택에 쌓인 것 중 이것보다 작은 것들을 삭제한다.
			if (prev < here) {
				while (countErased < K  && !stack.isEmpty() && stack.peek() < here) {
					stack.pop();
					countErased++;
				}
			}
			
			// 스택에 이번 것을 넣는다.
			stack.push(here);
		}

		// 스택에 있는 것을 모두 pop해서 StringBuilder에 붙이면 찾으려는 답의 역순으로 문자열이 만들어짐
		StringBuilder stackRev = new StringBuilder();
		while (!stack.isEmpty()) {
			stackRev.append(stack.pop());
		}
		
		// 이것을 다시 역순으로 바꿔서 문자열로 만들고 리턴
		// 문자열이 이미 모든 문자가 >= 상태라 위에서 K개만큼 안 잘랐다면, 뒤쪽에서 부족한 개수만큼 잘라주기 위해 substring 함수 사용
		return stackRev.reverse().toString().substring(0, finalLen); 
	}
}
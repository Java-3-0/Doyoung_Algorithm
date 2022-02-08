// 16440KB, 188ms

package baek10799;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {

	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String parenthesis = br.readLine();
		br.close();

		// 정답 계산
		int answer = countPieces(parenthesis);

		// 출력
		System.out.println(answer);
	}

	/**
	 * 괄호를 표현한 문자열이 주어질 때, 잘린 쇠막대기 조각 개수 리턴
	 * 
	 * @param parenthesis : 괄호를 표현한 문자열
	 * @return 잘린 쇠막대기 조각 개수
	 */
	public static int countPieces(String parenthesis) {
		Stack<Integer> stack = new Stack<>();

		/** 절단 수를 저장할 변수 */
		int count = 0;
		/** 레이저인지 아닌지 알아보기 위해 이전 character 추적 필요 */
		char before = 0;
		for (int i = 0; i < parenthesis.length(); i++) {
			char c = parenthesis.charAt(i);

			switch (c) {
			case '(':
				stack.push(1);
				break;
			case ')':
				// 레이저든 쇠막대기든 여기서 끝나는 것이므로 스택에서 pop
				stack.pop();
				// 레이저인 경우, 스택에 남아있는 쇠막대기들 수만큼 조각 개수 증가
				if (before == '(') {
					count += stack.size();
				}
				// 쇠막대기인 경우, 쇠막대기 자체가 원래 1조각이었으므로 그걸 세어 줌
				else {
					count += 1;
				}
				break;
			default:
			}

			before = c;
		}

		return count;
	}
}
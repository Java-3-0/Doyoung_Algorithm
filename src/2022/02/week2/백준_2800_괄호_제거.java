// 30556KB, 348ms

package baek2800;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class Main {
	/** 입력받을 수식을 저장할 문자열 */
	public static String expression;
	/** 입력받은 수식에서 괄호를 제거한 모든 부분집합을 담는 집합 */
	public static Set<String> subsets = new TreeSet<>(); // TreeSet으로 만들어서 자동으로 사전순으로 정렬
	/** 입력받을 수식의 길이를 저장할 변수 */
	public static int length;
	/** 여는 괄호의 index에서 닫는 괄호의 index로의 맵핑 */
	public static Map<Integer, Integer> openToClose;
	/** 특정 위치가 지워졌는지 여부를 나타낼 boolean 값 배열 */
	public static boolean[] isDeleted;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		expression = br.readLine();
		length = expression.length();

		// 여는 괄호의 index에서 닫는 괄호의 index로의 맵핑 생성
		openToClose = getPairMap(expression);

		// 모든 위치는 지워지지 않은 상태에서 시작
		isDeleted = new boolean[length];
		Arrays.fill(isDeleted, false);

		// 괄호 쌍 삭제/보존에 대한 모든 부분집합 생성
		generateSubsets(0, "");

		// 부분집합들 출력
		for (String s : subsets) {
			System.out.println(s);
		}
	}

	/** 짝이 맞는 (여는 괄호의 index -> 닫는 괄호의 index)를 모두 map에 담아서 리턴 */
	public static Map<Integer, Integer> getPairMap(String s) {
		Map<Integer, Integer> ret = new HashMap<>();

		// 여는 괄호의 index를 담아둘 스택
		Stack<Integer> stack = new Stack<>();

		// 문자열 전체를 탐색
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '(':
				stack.push(i);
				break;
			// 문자열은 항상 괄호가 올바르다 하였으니 체크할 필요 없이 바로 스택에서 가져와서 맵에 넣는다
			case ')':
				ret.put(stack.pop(), i);
				break;
			default:
				break;
			}
		}

		return ret;
	}

	/**
	 * 입력받은 수식에서 괄호를 제거하는 모든 부분집합을 생성하여 subsets 전역변수에 담음
	 * 
	 * @param startIdx : 문자열에서 이 위치부터 시작
	 * @param accum    : 현재까지 쌓인 문자열을 담는 스트링빌더
	 */
	public static void generateSubsets(int startIdx, String accum) {
		// 끝까지 왔을 경우 부분집합 하나가 완성되었으므로 subsets 전역변수에 추가
		if (startIdx == length) {
			// 부분집합 == 전체집합인 것은 제외
			if (!accum.equals(expression)) {
				subsets.add(accum);
			}
			return;
		}

		char c = expression.charAt(startIdx);
		int nextIdx = startIdx + 1;
		switch (c) {
		case '(':
			// 지우는 경우의 부분집합 계산
			isDeleted[openToClose.get(startIdx)] = true;
			generateSubsets(nextIdx, accum);

			// 지우지 않는 경우 부분집합 계산
			isDeleted[openToClose.get(startIdx)] = false;
			generateSubsets(nextIdx, accum + c);
			break;
		case ')':
			if (isDeleted[startIdx]) {
				generateSubsets(nextIdx, accum);
			} else {
				generateSubsets(nextIdx, accum + c);
			}
			break;
		default:
			generateSubsets(nextIdx, accum + c);
			break;
		}
	}
}

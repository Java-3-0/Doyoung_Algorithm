package pg67258;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Solution {
	static final int MAX_GEMS = 100000;
	static final int INF = 987654321;

	static Map<String, Integer> strToNum = new HashMap<>();
	static Map<Integer, Integer> numToCnt = new HashMap<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		final int TESTS = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			strToNum.clear();
			numToCnt.clear();

			// 입력
			int N = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine(), " ");
			String[] gems = new String[N];
			for (int i = 0; i < N; i++) {
				gems[i] = st.nextToken();
			}

			// 계산
			int[] answer = solution(gems);

			// 출력
			System.out.printf("[%d, %d]\n", answer[0], answer[1]);
		}
	}

	public static int[] solution(String[] gems) {
		int[] answer = new int[2];

		int len = gems.length;

		int[] seq = new int[len];
		int cntGemTypes = 0;
		for (int i = 0; i < len; i++) {
			if (!strToNum.containsKey(gems[i])) {
				cntGemTypes++;
				strToNum.put(gems[i], cntGemTypes);
				seq[i] = cntGemTypes;
			} else {
				seq[i] = strToNum.get(gems[i]);
			}
		}
		
		int right = -1;
		int left = -1;

		// 초기 상태 세팅
		int minLength = INF;
		answer[0] = 1;
		answer[1] = len;
		while (true) {
			// 모든 보석이 존재할 때까지 오른쪽 커서를 옮긴다.
			while (right < len - 1 && numToCnt.size() < cntGemTypes) {
				right++;
				int numIn = seq[right];
				addToMap(numIn);
			}
			
			// 오른쪽 끝까지 가도 모든 보석이 들어가지 못한다면 더 이상 성공할 수 없으니 break
			if (numToCnt.size() < cntGemTypes) {
				break;
			}

			// 보석이 하나 빠지게 되는 순간까지 왼쪽 커서를 옮긴다.
			while (numToCnt.size() == cntGemTypes) {
				left++;
				int numOut = seq[left];
				removeFromMap(numOut);
			}

			// 이 때  [left, right] 구간이 답이 될 수 있다.
			// 이 구간의 길이를 계산하고, 최소 길이가 갱신될 경우 정답도 갱신
			int lengthOfInterval = right - (left) + 1;
			if (lengthOfInterval < minLength) {
				minLength = lengthOfInterval;
				// 0번이 아닌 1번 인덱스부터 시작이므로 1을 더해서 answer에 넣는다.
				answer[0] = left + 1;
				answer[1] = right + 1;
			}
		}

		return answer;
	}

	public static void removeFromMap(int numOut) {
		if (!numToCnt.containsKey(numOut)) {
			return;
		}

		if (numToCnt.get(numOut) == 1) {
			numToCnt.remove(numOut);
		} else {
			numToCnt.put(numOut, numToCnt.get(numOut) - 1);
		}
	}

	public static void addToMap(int numIn) {
		if (numToCnt.containsKey(numIn)) {
			numToCnt.put(numIn, numToCnt.get(numIn) + 1);
		} else {
			numToCnt.put(numIn, 1);
		}
	}
}

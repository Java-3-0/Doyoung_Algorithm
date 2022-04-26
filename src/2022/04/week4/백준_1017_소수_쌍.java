// 11996KB, 84ms

package bj1017;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 50;
	static final int MAX_NUMBER = 2000;
	static boolean[] isPrime = new boolean[MAX_NUMBER + 1];

	static List<Integer> domain = new ArrayList<>();
	static List<Integer> range = new ArrayList<>();
	static boolean[] isChecked;
	static int[] isMatchedTo;
	static List<Integer>[] adjList;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 소수 여부 미리 계산
		precalcPrimes();

		// 수열 크기 입력
		int N = Integer.parseInt(br.readLine());

		// 수열 입력받아서 홀짝으로 나눈다
		st = new StringTokenizer(br.readLine(), " ");
		int first = Integer.parseInt(st.nextToken());
		domain.add(first);
		for (int i = 1; i < N; i++) {
			int input = Integer.parseInt(st.nextToken());
			if (input % 2 == first % 2) {
				domain.add(input);
			} else {
				range.add(input);
			}
		}

		// 메모리 할당
		isChecked = new boolean[range.size()];
		isMatchedTo = new int[range.size()];
		adjList = new ArrayList[domain.size()];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// 간선 정보 생성
		for (int left = 0; left < domain.size(); left++) {
			for (int right = 0; right < range.size(); right++) {
				int sum = domain.get(left) + range.get(right);
				if (isPrime[sum]) {
					adjList[left].add(right);
				}
			}
		}

		// 정답 계산
		List<Integer> answers = solve();

		// 출력
		if (answers.isEmpty()) {
			System.out.println(-1);
		} else {
			for (int num : answers) {
				sb.append(num).append(" ");
			}

			sb.append("\n");
			System.out.print(sb.toString());
		}

	} // end main

	/** 도메인의 첫 번째 원소와 매칭 가능한 레인지의 원소들을 리스트에 담아서 리턴 */
	public static List<Integer> solve() {
		List<Integer> ret = new ArrayList<>();

		if (domain.size() != range.size()) {
			return ret;
		}

		// 각각의 오른쪽 수에 대해서 첫 번째 수와 매칭해보고 모든 매칭이 가능할 때마다, 리턴할 리스트에 추가
		for (int right = 0; right < range.size(); right++) {
			if (isPossible(right)) {
				ret.add(range.get(right));
			}
		}

		// 결과를 오름차순으로 정렬
		Collections.sort(ret);
		return ret;
	}

	/** 첫 번째 수를 target번째 수와 매칭시켰을 때 모두를 매칭시킬 수 있는지 여부를 리턴 */
	public static boolean isPossible(int target) {
		// 배열 초기화
		Arrays.fill(isMatchedTo, -1);

		// 첫 번째 수와의 매칭 처리
		if (!isPrime[domain.get(0) + range.get(target)]) {
			return false;
		}
		isChecked[target] = true;
		isMatchedTo[target] = 0;
		int cnt = 1;

		// 첫 번째 수의 매칭은 바꾸지 않은 채로, 다른 수들의 매칭을 시도
		for (int left = 1; left < domain.size(); left++) {
			Arrays.fill(isChecked, false);
			isChecked[target] = true;

			if (dfs(left)) {
				cnt++;
			}
		}

		if (cnt == domain.size()) {
			return true;
		} else {
			return false;
		}
	}

	/** domain의 left번 인덱스 원소를 range쪽으로 매칭 시도 */
	public static boolean dfs(int left) {
		for (int right : adjList[left]) {
			if (!isChecked[right]) {
				isChecked[right] = true;
				if (isMatchedTo[right] == -1 || dfs(isMatchedTo[right])) {
					if (isPrime[domain.get(left) + range.get(right)]) {
						isMatchedTo[right] = left;
						return true;
					}
				}
			}
		}

		return false;
	}

	/** MAX_NUMBER 이하의 모든 수들에 대해 소수 여부를 미리 계산 */
	public static void precalcPrimes() {
		// 초기 상태 설정
		Arrays.fill(isPrime, true);
		isPrime[0] = false;
		isPrime[1] = false;

		// 에라토스테네스의 체 수행
		int sqrt = (int) Math.sqrt(MAX_NUMBER);
		for (int k = 2; k <= sqrt; k++) {
			if (isPrime[k]) {
				for (int num = k + k; num <= MAX_NUMBER; num += k) {
					isPrime[num] = false;
				}
			}
		}
	}

}
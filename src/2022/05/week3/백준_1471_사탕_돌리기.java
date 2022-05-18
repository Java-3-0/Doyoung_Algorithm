// 18380KB, 924ms

package bj1471;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class Main {
	static final int MAX_N = 200000;
	static final int EMPTY = -1;

	static int N;

	/** toCycle[i]는 i에서 출발해서 처음으로 사이클이 생길 때까지의 이동 횟수 */
	static int[] toCycle = new int[MAX_N + 1];
	/** 방문 순서 */
	static int[] discovered = new int[MAX_N + 1];
	/** 하나의 dfs에서 방문한 정점들을 담을 스택 */
	static Stack<Integer> stack = new Stack<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 정점 수 입력
		N = Integer.parseInt(br.readLine());

		// toCycle 배열 초기화
		Arrays.fill(toCycle, EMPTY);

		// dfs를 통해 모든 정점의 toCycle 값을 찾는다.
		for (int start = 1; start <= N; start++) {
			if (toCycle[start] == EMPTY) {
				dfs(start);
			}
		}

		// 최대값 계산 및 출력
		int answer = 0;
		for (int i = 1; i <= N; i++) {
			answer = Math.max(answer, toCycle[i]);
		}
		System.out.println(answer);

	} // end main

	/** dfs를 통해 start로부터 방문 가능한 정점들의 toCycle 값을 모두 구해서 넣는다 */
	public static void dfs(int start) {
		Arrays.fill(discovered, EMPTY);
		stack.clear();

		int now = start;
		int discoverCnt = 0;

		// 스택 오버플로우를 막기 위해, 재귀 대신 while문으로 dfs
		while (true) {
			discoverCnt++;

			// 이미 toCycle을 찾은 정점을 만난 경우, toCycle 처리 후 종료
			if (toCycle[now] != EMPTY) {
				int cycleSize = toCycle[now];

				int stackCnt = 0;
				while (!stack.isEmpty()) {
					toCycle[stack.pop()] = cycleSize + (++stackCnt);
				}

				break;
			}

			// 이미 방문한 정점을 만난 경우, toCycle 처리 후 종료
			if (discovered[now] != EMPTY) {
				int cycleSize = discoverCnt - discovered[now];

				for (int i = 0; i < cycleSize; i++) {
					toCycle[stack.pop()] = cycleSize;
				}

				int stackCnt = 0;
				while (!stack.isEmpty()) {
					toCycle[stack.pop()] = cycleSize + (++stackCnt);
				}

				break;
			}

			// 이 외의 경우 현재 정점을 방문해서 스택에 넣고, 다음 정점으로 현재 정점을 갱신
			stack.push(now);
			discovered[now] = discoverCnt;

			int next = getNextNum(now);
			now = next;
		}

		return;
	}

	/** num에서 다음으로 방문할 수를 리턴 */
	public static int getNextNum(int num) {
		int ret = (num + digitSum(num)) % N;
		if (ret == 0) {
			ret = N;
		}

		return ret;
	}

	/** n의 각 자리수의 합을 구해서 리턴한다 */
	public static int digitSum(int num) {
		int ret = 0;
		while (num > 0) {
			int digit = num % 10;
			num /= 10;

			ret += digit;
		}

		return ret;
	}

}
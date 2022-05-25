package bj8172;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000000;
	static final long INF = 987654321098765L;

	static int N;

	/** 코끼리 번호 -> 초기 상태에서의 위치로의 매핑 */
	static Map<Integer, Integer> numToOriginalPosition = new HashMap<>();
	static long[] weights;
	static int[] seqBefore;
	static int[] seqAfter;
	static boolean[] isVisited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 코끼리 수 입력
		N = Integer.parseInt(br.readLine());

		// 전역변수 메모리 할당
		weights = new long[N + 1];
		seqBefore = new int[N + 1];
		seqAfter = new int[N + 1];
		isVisited = new boolean[N + 1];

		// 코끼리 무게 정보 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			weights[i] = Long.parseLong(st.nextToken());
		}

		// 초기 상태에서의 코끼리 번호들 입력받고, 매핑도 생성
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			int elephantNum = Integer.parseInt(st.nextToken());
			seqBefore[i] = elephantNum;
			numToOriginalPosition.put(elephantNum, i);
		}

		// 정렬된 후의 상태에서의 코끼리 번호들 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			seqAfter[i] = Integer.parseInt(st.nextToken());
		}

		// 사이클마다 그룹으로 묶기
		List<List<Long>> cycleList = new ArrayList<>();
		for (int i = 1; i <= N; i++) {
			// 이미 처리한 위치면 패스
			if (isVisited[i]) {
				continue;
			}

			// 현 위치의 수들 가져오기
			int original = seqBefore[i];
			int sorted = seqAfter[i];

			// 두 수가 같으면 이 위치는 이미 정렬되어 있다.
			if (original == sorted) {
				continue;
			}

			// 아닌 경우 이 위치로부터 사이클을 찾는다
			else {
				List<Long> cycle = findCycle(i);
				cycleList.add(cycle);
			}
		}

		// 전체 코끼리 중 가장 가벼운 코끼리의 무게 구하기
		long minWeightOfAll = INF;
		for (int i = 1; i <= N; i++) {
			minWeightOfAll = Math.min(minWeightOfAll, weights[i]);
		}

		// 사이클마다 정렬에 필요한 최소 비용을 구하고, 그 합을 누적한다
		long answer = 0L;
		int size = cycleList.size();
		for (int i = 0; i < size; i++) {
			List<Long> cycle = cycleList.get(i);

			// 사이클을 구성하는 코끼리들 중 가장 가벼운 코끼리의 무게 구하기
			// 사이클 내 코끼리 무게의 합도 구하기
			long sum = 0L;
			long minWeightOfCycle = INF;
			for (long weight : cycle) {
				minWeightOfCycle = Math.min(minWeightOfCycle, weight);
				sum += weight;
			}

			// 사이클 내에서 가장 작은 원소랑 swap하여 정렬하는 경우
			sum -= minWeightOfCycle;
			long cnt = (long) (cycle.size() - 1);
			long cost1 = sum + minWeightOfCycle * cnt;

			// 전체에서 가장 작은 원소랑 swap하여 정렬하는 경우
			long cost2 = (sum + minWeightOfAll * cnt) + (minWeightOfAll * 2 + minWeightOfCycle * 2);

			// 둘 중 나은 경우로 합산
			long minCost = Math.min(cost1, cost2);
			answer += minCost;
		}

		// 정답 출력
		System.out.println(answer);

	} // end main

	/** startIdx로부터 사이클을 찾으면서 방문체크하고, 방문한 코끼리들의 무게를 List에 담아 리턴한다 */
	public static List<Long> findCycle(int startIdx) {
		List<Long> ret = new ArrayList<>();

		// 초기 상태 처리
		int cur = startIdx;
		isVisited[cur] = true;
		int curNum = seqBefore[cur];
		ret.add(weights[curNum]);

		// 사이클을 만날 때까지 반복
		while (true) {
			int nextNum = seqAfter[cur];
			int next = numToOriginalPosition.get(nextNum);
			if (isVisited[next]) {
				break;
			}

			isVisited[next] = true;
			ret.add(weights[nextNum]);

			cur = next;
			curNum = nextNum;
		}

		return ret;
	}

}
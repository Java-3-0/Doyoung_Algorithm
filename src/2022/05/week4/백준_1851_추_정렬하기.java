// 15536Kb, 184ms

package bj1851;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class Main {
	static final int MAX_N = 1000;

	static int N;
	static MyNumber[] seq;
	static MyNumber[] sortedSeq;
	static boolean[] isVisited;

	static class MyNumber implements Comparable<MyNumber> {
		int idx;
		long num;

		public MyNumber(int idx, long num) {
			super();
			this.idx = idx;
			this.num = num;
		}

		@Override
		public String toString() {
			return "MyNumber [idx=" + idx + ", num=" + num + "]";
		}

		@Override
		public int compareTo(MyNumber m) {
			return Long.compare(this.num, m.num);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 수열 크기 입력
		final int N = Integer.parseInt(br.readLine());

		// 메모리 할당
		seq = new MyNumber[N];
		sortedSeq = new MyNumber[N];
		isVisited = new boolean[N];
		
		// 수열 입력받아서 원본 수열과 정렬된 수열 만들기
		for (int idx = 0; idx < N; idx++) {
			long num = Long.parseLong(br.readLine());
			seq[idx] = new MyNumber(idx, num);
			sortedSeq[idx] = new MyNumber(idx, num);
		}

		Arrays.sort(sortedSeq);

		// 사이클마다 그룹으로 묶기
		List<TreeSet<MyNumber>> cycleList = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			// 이미 처리한 위치면 패스
			if (isVisited[i]) {
				continue;
			}
			
			// 새로운 방문한 위치면 방문 체크
			isVisited[i] = true;
			
			// 현 위치의 수들 가져오기
			MyNumber original = seq[i];
			MyNumber sorted = sortedSeq[i];
			
			// 두 수가 같으면 이 위치는 이미 정렬되어 있다.
			if (original.num == sorted.num) {
				continue;
			}
			
			//아닌 경우 이 위치로부터 사이클을 찾는다
			else {
				TreeSet<MyNumber> cycle = findCycle(i);
				cycleList.add(cycle);
			}
		}

		long minNumOfAll = sortedSeq[0].num;
		
		// 사이클마다 최소 비용으로 정렬하여, 그 합을 구한다
		long answer = 0L;
		int size = cycleList.size();
		for (int i = 0; i < size; i++) {
			TreeSet<MyNumber> cycle = cycleList.get(i);

			long sum = 0L;
			for (MyNumber myNumber : cycle) {
				sum += myNumber.num;
			}
			
			// 사이클 내에서 가장 작은 원소랑 swap하여 정렬하는 경우
			long minNumOfCycle = cycle.first().num;
			sum -= minNumOfCycle;
			long cnt = cycle.size() - 1;
			long cost1 = sum + minNumOfCycle * cnt;
			
			// 전체에서 가장 작은 원소랑 swap하여 정렬하는 경우
			long cost2 = (sum + minNumOfAll * cnt) + (minNumOfAll * 2 + minNumOfCycle * 2);
			
			// 둘 중 나은 경우로 합산
			long minCost = Math.min(cost1, cost2);
			answer += minCost;
		}
		
        // 정답 출력
		System.out.println(answer);
		
	} // end main

	/** startIdx로부터 사이클을 찾으면서 방문체크하고, 방문한 위치들을 TreeSet에 담아 리턴한다 */
	public static TreeSet<MyNumber> findCycle(int startIdx) {
		TreeSet<MyNumber> ret = new TreeSet<>();
		
		int cur = startIdx;
		isVisited[cur] = true;
		ret.add(seq[cur]);
		
		while (true) {
			int next = sortedSeq[cur].idx;
			if (isVisited[next]) {
				break;
			}
			
			isVisited[next] = true;
			ret.add(seq[next]);
			
			cur = next;
		}
		
		return ret;
	}

}
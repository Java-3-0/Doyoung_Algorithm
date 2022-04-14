// 65496KB, 920ms

package bj2568;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
	static final int MAX_N = 100000;
	static final int MAX_POS = 500000;
	static final int INF = 987654321;

	static int N;
	static Line[] lines;

	/** minLast[i]는 길이가 i인 LIS에서 맨 뒤에 올 수 있는 가장 작은 수 */
	static int[] minLast = new int[MAX_N + 1];
	/** (i, minLast[i]) 에서 (i - 1, minLast[i - 1])로의 매핑 */
	static Map<Status, Status> beforeMap = new TreeMap<>();

	static class Line implements Comparable<Line> {
		int from;
		int to;

		public Line(int from, int to) {
			super();
			this.from = from;
			this.to = to;
		}

		@Override
		public String toString() {
			return "Line [from=" + from + ", to=" + to + "]";
		}

		@Override
		public int compareTo(Line l) {
			return this.from - l.from;
		}
	}

	static class Status implements Comparable<Status> {
		int len;
		int num;

		public Status(int len, int num) {
			super();
			this.len = len;
			this.num = num;
		}

		@Override
		public int compareTo(Status s) {
			if (this.len == s.len) {
				if (this.num == s.num) {
					return 0;
				} else if (this.num < s.num) {
					return -1;
				} else {
					return 1;
				}
			}

			else if (this.len < s.len) {
				return -1;
			} else {
				return 1;
			}
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 전역변수 배열 초기값 세팅
		Arrays.fill(minLast, INF);
		minLast[0] = -INF;

		// 전깃줄 개수 입력
		N = Integer.parseInt(br.readLine());

		// 전깃줄 정보 입력
		lines = new Line[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			lines[i] = new Line(from, to);
		}

		// 전기줄을 시작 위치 번호 오름차순으로 정렬
		Arrays.sort(lines);

		// minLast 배열 계산
		for (int i = 0; i < N; i++) {
			int num = lines[i].to;
			int idx = binSearch(minLast, num);
			minLast[idx] = num;

			Status cur = new Status(idx, num);
			Status prev = new Status(idx - 1, minLast[idx - 1]);
			if (beforeMap.containsKey(cur) && beforeMap.get(cur).num >= minLast[idx - 1]) {
				continue;
			} else {
				beforeMap.put(cur, prev);
			}

		}

		// LIS 길이 계산
		int lenLIS = 1;
		for (int i = N; i >= 1; i--) {
			if (minLast[i] != INF) {
				lenLIS = i;
				break;
			}
		}

		// 최소 전선 절단 개수를 출력 스트링빌더에 추가
		int minCuts = N - lenLIS;
		sb.append(minCuts).append("\n");

		// LIS에 포함되지 않은 수들의 from 위치들을 출력 스트링빌더에 추가
		Status now = new Status(lenLIS, minLast[lenLIS]);

		Set<Integer> set = new HashSet<>();
		while (now != null && now.len != 0) {
			set.add(now.num);
			now = beforeMap.get(now);
		}

		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		for (Line line : lines) {
			if (!set.contains(line.to)) {
				pq.offer(line.from);
			}
		}

		while (!pq.isEmpty()) {
			sb.append(pq.poll()).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	}

	/** 오름차순으로 정렬되어 있는 arr에서, num이상의 수 중 가장 왼쪽 것의 인덱스를 리턴 */
	public static int binSearch(int[] arr, int num) {
		int left = 0;
		int right = arr.length - 1;

		int mid;
		while (left < right) {
			mid = (left + right) / 2;

			if (arr[mid] < num) {
				left = mid + 1;
			} else {
				right = mid;
			}
		}

		return left;
	}

}
// 411596KB, 1740ms

package bj14003;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
	static final int MAX_N = (int) 1e6;
	static final int INF = (int) (1e9 + 1e6);

	static int N;
	/** 수열 */
	static int[] seq = new int[MAX_N];
	/** minLast[i]는 길이가 i인 LIS에서 맨 뒤에 올 수 있는 가장 작은 수 */
	static int[] minLast = new int[MAX_N + 1];
	/** (i, minLast[i]) 에서 (i - 1, minLast[i - 1])로의 매핑 */
	static Map<Status, Status> beforeMap = new TreeMap<>();

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

		@Override
		public String toString() {
			return "[l=" + len + ", n=" + num + "]";
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 전역변수 배열 초기값 세팅
		Arrays.fill(minLast, INF);
		minLast[0] = -INF;

		// 수열 크기 입력
		N = Integer.parseInt(br.readLine());

		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// minLast 배열 계산
		for (int i = 0; i < N; i++) {
			int num = seq[i];
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

		// 출력 스트링빌더에 LIS 길이를 추가
		sb.append(lenLIS).append("\n");

		// 출력 스트링빌더에 이 길이의 LIS 수열을 추가
		Status now = new Status(lenLIS, minLast[lenLIS]);
		Stack<Integer> stack = new Stack<>();
		while (now != null && now.len != 0) {
			stack.add(now.num);
			now = beforeMap.get(now);
		}

		while (!stack.isEmpty()) {
			sb.append(stack.pop()).append(" ");
		}
		sb.append("\n");

		// 출력
		System.out.print(sb.toString());

	} // end main

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
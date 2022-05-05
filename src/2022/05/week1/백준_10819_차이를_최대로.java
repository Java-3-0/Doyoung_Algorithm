// 11592KB, 80ms

package bj10819;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static final int INF = 987654321;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 수열 크기 입력
		int N = Integer.parseInt(br.readLine());

		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		int[] seq = new int[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}
		
		// 수열 오름차순 정렬
		Arrays.sort(seq);

		// 수열에서 작은 거 하나, 큰 거 하나 번갈아가면서 reordered에 넣는다
		List<Integer> reorderedSeq = new ArrayList<Integer>();
		int left = 0;
		int right = N - 1;

		while (left <= right) {
			reorderedSeq.add(seq[left]);
			left++;
			if (left <= right) {
				reorderedSeq.add(seq[right]);
				right--;
			}
		}

		// reorderedSeq를 원형으로 보면서 인접한 것들 사이의 차이를 합산
		int answer = 0;
		int minDiff = INF;
		int size = reorderedSeq.size();
		for (int i = 0; i < size; i++) {
			int now = i % size;
			int next = (i + 1) % size;
			int diff = Math.abs(reorderedSeq.get(next) - reorderedSeq.get(now));
			minDiff = diff < minDiff ? diff : minDiff;
			answer += diff;
		}

		// 가장 diff가 작은 한 곳을 끊는다
		answer -= minDiff;
		
		// 출력
		System.out.println(answer);

	} // end main

}
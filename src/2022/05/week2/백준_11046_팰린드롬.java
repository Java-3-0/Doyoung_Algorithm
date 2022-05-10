// 318644KB, 1084ms

package bj11046;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) 1e6, MAX_Q = (int) 1e6;

	static int N;
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 수열 크기 입력
		N = Integer.parseInt(br.readLine());

		// 수열 메모리 할당
		seq = new int[2 * N + 1];

		// 수열 입력 (사이사이에 0을 넣는다)
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[2 * i] = 0;
			seq[2 * i + 1] = Integer.parseInt(st.nextToken());
		}
		seq[2 * N] = 0;
		
		// Manacher 알고리즘으로 각 위치에서의 팰린드롬 최대 길이를 계산한다
		int[] palindromes = manacher();

		// 쿼리 개수 입력
		int Q = Integer.parseInt(br.readLine());

		// 쿼리 수행
		for (int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int s = Integer.parseInt(st.nextToken()) - 1;
			int e = Integer.parseInt(st.nextToken()) - 1;
			int len = e - s + 1;
			
			int left = 2 * s + 1;
			int right = 2 * e + 1;
			int mid = (left + right) / 2;

			if (palindromes[mid] >= len) {
				sb.append(1);
			} else {
				sb.append(0);
			}
			sb.append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** Manacher 알고리즘으로 문자열의 각 위치를 중심으로 한 팰린드롬의 최대 길이를 구해서 배열로 리턴한다 */
	public static int[] manacher() {
		int len = seq.length;

		// ret[i]는 i번 인덱스를 중심으로 한 팰린드롬의 최대 길이
		int[] ret = new int[len];

		// p는 j < i인 j 중에서 (j + ret[j])가 최대가 되는 j의 값 (팰린드롬의 중심점)
		int p = 0;

		// r는 이미 팰린드롬을 구한 구간의 오른쪽 끝 인덱스
		int r = 0;

		// Manacher 알고리즘 수행
		for (int i = 0; i < len; i++) {
			// ret[i]의 초기값 결정
			if (i <= r) {
				ret[i] = Math.min(ret[2 * p - i], r - i);
			}

			// text[i - ret[i] - 1]와 text[i + ret[i] + 1]가 같은 동안 A[i]를 증가시킨다
			while (0 <= i - ret[i] - 1 && i + ret[i] + 1 < len && seq[i - ret[i] - 1] == seq[i + ret[i] + 1]) {
				ret[i]++;
			}

			// r, p값 갱신
			if (r < i + ret[i]) {
				r = i + ret[i];
				p = i;
			}
		}

		return ret;
	}
}
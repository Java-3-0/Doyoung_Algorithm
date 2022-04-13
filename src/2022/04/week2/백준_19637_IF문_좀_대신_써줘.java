// 60324KB, 416ms

package bj19637;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) 1e5, MAX_M = (int) 1e5;
	
	static int N, M;
	static String[] names;
	static int[] statLimits;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 칭호와 요구 전투력 입력
		names = new String[N];
		statLimits = new int[N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			names[i] = st.nextToken();
			statLimits[i] = Integer.parseInt(st.nextToken());
		}

		// M개 캐릭터의 칭호 처리
		for (int i = 0; i < M; i++) {
			int stat = Integer.parseInt(br.readLine());

			String answer = getName(stat);

			sb.append(answer).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	public static String getName(int stat) {
		int left = 0;
		int right = N - 1;

		while (left < right) {
			int mid = (left + right) / 2;

			if (statLimits[mid] < stat) {
				left = mid + 1;
			} else {
				right = mid;
			}

		}

		return names[right];
	}

}
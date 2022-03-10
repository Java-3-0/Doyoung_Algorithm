// 22076KB, 196ms

package bj9547;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		tc : for (int testCase = 1; testCase <= TESTS; testCase++) {
			// C, V 입력
			st = new StringTokenizer(br.readLine(), " ");
			int C = Integer.parseInt(st.nextToken());
			int V = Integer.parseInt(st.nextToken());

			// 각 유권자의 후보들에 대한 선호도 입력
			int[][] prefs = new int[V + 1][C + 1];
			for (int v = 1; v <= V; v++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int c = 1; c <= C; c++) {
					prefs[v][c] = Integer.parseInt(st.nextToken());
				}
			}

			// 1회차 투표 진행
			int[] counts = new int[C + 1];
			for (int v = 1; v <= V; v++) {
				counts[prefs[v][1]]++;
			}
			
			// 1등, 2등 후보 파악
			int firstCnt = 0;
			int secondCnt = 0;
			int firstIdx = -1;
			int secondIdx = -1;
			for (int c = 1; c <= C; c++) {
				int cnt = counts[c];
				if (cnt >= firstCnt) {
					secondCnt = firstCnt;
					secondIdx = firstIdx;
					firstCnt = cnt;
					firstIdx = c;
				}
				else if (cnt >= secondCnt) {
					secondCnt = cnt;
					secondIdx = c;
				}
			}
			
			// 1등이 과반 이상의 표를 가져갔다면 당선
			if (firstCnt > V / 2) {
				sb.append(firstIdx).append(" ").append("1").append("\n");
				continue tc;
			}
			
			// 2회차 투표 진행
			firstCnt = 0;
			secondCnt = 0;
			voter : for (int v = 1; v <= V; v++) {
				for (int c = 1; c <= C; c++) {
					if (prefs[v][c] == firstIdx) {
						firstCnt++;
						continue voter;
					}
					else if (prefs[v][c] == secondIdx) {
						secondCnt++;
						continue voter;
					}
				}
			}
						
			if (firstCnt >= secondCnt) {
				sb.append(firstIdx).append(" ").append("2").append("\n");
			}
			else {
				sb.append(secondIdx).append(" ").append("2").append("\n");
			}
		}
		
		System.out.print(sb.toString());

	} // end main

}
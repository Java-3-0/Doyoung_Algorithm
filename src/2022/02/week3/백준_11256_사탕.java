// 13372KB, 112ms

package bj11256;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= T; testCase++) {
			st = new StringTokenizer(br.readLine(), " ");
			int J = Integer.parseInt(st.nextToken());
			int N = Integer.parseInt(st.nextToken());
			
			// 박스들 입력
			Integer[] boxes = new Integer[N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int height = Integer.parseInt(st.nextToken());
				int width = Integer.parseInt(st.nextToken());
				boxes[i] = height * width;
			}
			
			// 박스들을 담을 수 있는 무게의 내림차순으로 정렬
			Arrays.sort(boxes, Collections.reverseOrder());
			
			// 최소 박스 수 계산
			int count = 0;
			int sum = 0;
			for (int num : boxes) {
				count++;
				sum += num;
				if (sum >= J) {
					break;
				}
			}
			
			// 스트링빌더에 답 추가
			sb.append(count).append("\n");
		}
		
		// 출력
		System.out.print(sb.toString());
	}
}
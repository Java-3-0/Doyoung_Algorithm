// 16920KB, 152ms

package bj16162;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int A = Integer.parseInt(st.nextToken());
		int D = Integer.parseInt(st.nextToken());
		
		int target = A;
		int count = 0;
		
		// 타겟을 찾을 수 있는 횟수를 센다
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			int note = Integer.parseInt(st.nextToken());
			// 타겟을 찾으면 다음 타겟은 공차만큼 증가한다.
			if (note == target) {
				target += D;
				count++;
			}
		}
		
		System.out.println(count);
	}
}
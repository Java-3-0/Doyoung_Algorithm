// 11996KB, 96ms

package bj16435;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int L = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine(), " ");
		int[] fruits = new int[N];
		for (int i = 0; i < N; i++) {
			fruits[i] = Integer.parseInt(st.nextToken());
		}
		
		// 과일을 높이 오름차순으로 정렬
		Arrays.sort(fruits);
		
		// 가장 높이가 낮은 과일부터 순서대로 먹어보면서 몸 길이 갱신
		for (int fruitHeight : fruits) {
			if (fruitHeight <= L) {
				L++;
			}
			else {
				break;
			}
		}
		
		// 몸 길이 출력
		System.out.println(L);
	}
}
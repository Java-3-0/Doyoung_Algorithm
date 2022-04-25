// 11628KB, 76ms

package bj1026;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 배열 크기 입력
		int N = Integer.parseInt(br.readLine());
		
		// 배열 메모리 할당
		int[] A = new int[N];
		int[] B = new int[N];
		
		// A 배열 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}
		
		// B 배열 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			B[i] = Integer.parseInt(st.nextToken());
		}
		
		// 오름차순 정렬
		Arrays.sort(A);
		Arrays.sort(B);
		
		// A의 작은 것과 B의 큰 것을 매칭하는 것을 반복
		int answer = 0;
		for (int i = 0; i < N; i++) {
			answer += A[i] * B[N - 1 - i];
		}
		
		System.out.println(answer);
		
	} // end main

}
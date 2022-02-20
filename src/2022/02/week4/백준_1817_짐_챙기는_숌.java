package bj1817;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_BOOKS = 50;
	public static final int MAX_WEIGHT = 1000;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		if (N == 0) {
			System.out.println(0);
			return;
		}
		
		st = new StringTokenizer(br.readLine(), " ");
		int bag = M;
		int count = 1;
		for (int i = 0 ; i < N; i++) {
			int book = Integer.parseInt(st.nextToken());
			// 가방 최대 무게보다도 무거운 책이 있다면 불가능
			if (book > M) {
				System.out.println(0);
				return;
			}
			
			// 가방의 남은 공간보다 작으면 넣는다.
			else if (book <= bag) {
				bag -= book;
			} 
			
			// 공간이 부족하면 새 가방에 넣는다.
			else {
				bag = M - book;
				count++;
			}
		}
		
		// 출력
		System.out.println(count);
		
		
	}
}
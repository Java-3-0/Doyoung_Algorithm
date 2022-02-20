// 11516KB, 80ms

package bj19939;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_N = 100000;
	public static final int MAX_K = 1000;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		int answer = minDiff(N, K);
		
		System.out.println(answer);
	}
	
	public static int minDiff (int N, int K) {
		// 일단 각각 1, 2, 3, ... K개 씩을 넣어야 한다.
		int essentialPut = K * (K + 1) / 2;
		
		// 이만큼을 넣을 수 없으면 불가능
		if (N < essentialPut) {
			return -1;
		}
		
		// 이만큼 넣고 나서 더 넣을 게 있으면 큰 쪽부터 한 개씩 넣는 것을 반복하기 때문에
		// 더 넣어야 하는 양이 K의 배수이면 차이가 그대로일 것이고, K의 배수가 아니면 차이가 1 더 벌어질 것이다.
		N -= essentialPut;
		if (N % K == 0) {
			return K - 1;
		} else {
			return K;
		}
	}
}
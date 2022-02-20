// 45444KB, 504ms

/*
 * 직선상에서 왼쪽 점과의 거리 + 오른쪽 점과의 거리는 이 두 점 사이 위치 중 어디에 놓든 똑같다.
 * 이를 가장 왼쪽 점, 가장 오른쪽 점에 대해 반복하면, 점의 개수가 홀수든 짝수든 결국 median 위치에 놓는 것이 최적
 * x축에 대해 median을 구하고, y축에 대해 median을 구하는 것을 따로 수행해서
 * 그 x좌표와 y좌표를 사용하면 된다.
 */

package bj14400;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		long[] xCoordinates = new long[N];
		long[] yCoordinates = new long[N];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			xCoordinates[i] = Integer.parseInt(st.nextToken());
			yCoordinates[i] = Integer.parseInt(st.nextToken());
		}

		// 중간값을 구하기 위해 정렬
		Arrays.sort(xCoordinates);
		Arrays.sort(yCoordinates);
		
		// 중간값을 구함
		int midIdx = N / 2;
		long medianX = xCoordinates[midIdx];
		long medianY = yCoordinates[midIdx];

		// 이 중간값과의 거리 합을 계산
		long answer = 0L;
		for (int i = 0; i < N; i++) {
			answer += Math.abs(xCoordinates[i] - medianX);
			answer += Math.abs(yCoordinates[i] - medianY);
		}
		
		System.out.println(answer);
	}
}
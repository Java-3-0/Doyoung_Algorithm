// 86720KB, 368ms

package bj11509;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_HEIGHT = 1000000;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		int N = Integer.parseInt(br.readLine());

		st = new StringTokenizer(br.readLine(), " ");
		int[] balloons = new int[N];
		for (int i = 0; i < N; i++) {
			balloons[i] = Integer.parseInt(st.nextToken());
		}

		// 각 화살 높이에 대해 그 높이의 화살 개수를 담을 배열
		int[] arrows = new int[MAX_HEIGHT + 1];

		// 모든 칸에 대해서 왼쪽부터 탐색하면서, 날아오는 화살이 있는지, 아님 새로운 화살을 쏴야 할지 파악한다.
		int answer = 0;
		for (int i = 0; i < N; i++) {
			int height = balloons[i];
			// 현재 높이로 날아오고 있는 화살이 있다면, 그 화살을 없앤다.
			if (arrows[height] > 0) {
				arrows[height]--;
			}

			// 현재 높이로 날아오고 있는 화살이 없다면, 새로 쏜다
			else {
				answer++;
			}
			
			// 이 풍선에 맞았으니 높이가 1 줄어든 화살이 하나 생긴다.
			arrows[height - 1]++;
		}
		
		System.out.println(answer);
	}
}
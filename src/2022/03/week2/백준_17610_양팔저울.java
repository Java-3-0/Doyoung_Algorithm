// 82116KB, 728ms

package bj17610;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_K = 13;
	static final int LEFT = 0, RIGHT = 1, NOT_USED = 2;

	static int K;
	static int[] weights;
	static int maxWeight = 0;
	static Set<Integer> possibleWeights = new HashSet<>();
	static int[] powerSet;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// K 입력
		K = Integer.parseInt(br.readLine());

		// 각 추의 무게 입력
		weights = new int[K];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < K; i++) {
			int input = Integer.parseInt(st.nextToken());
			weights[i] = input;
			maxWeight += input;
		}

		powerSet = new int[K];
		
		// 각 추마다 왼쪽에 넣거나, 오른쪽에 넣거나, 안 넣는 것을 모두 시도해본다.
		makePowerSet(0);
		
		// 불가능한 경우의 수를 센다
		int answer = 0;
		for (int num = 1; num <= maxWeight; num++) {
			if (!possibleWeights.contains(num)) {
				answer++;
			}
		}
		
		// 출력
		System.out.println(answer);
	}

	public static void makePowerSet(int idx) {
		// 부분집합이 완성된 경우
		if (idx == K) {
			// 왼쪽 저울에 올라간 무게와 오른쪽 저울에 올라간 무게를 계산
			int leftSum = 0;
			int rightSum = 0;
			for (int i = 0; i < K; i++) {
				if (powerSet[i] == LEFT) {
					leftSum += weights[i];
				}
				else if (powerSet[i] == RIGHT) {
					rightSum += weights[i];
				}
			}
			
			// 왼쪽 저울과 오른쪽 저울의 차만큼은 측정 가능하다.
			int diff = leftSum - rightSum;
			if (diff > 0) {
				possibleWeights.add(diff);
			}
		
			return;
		}

		// 부분집합이 아직 완성되지 않은 경우 계속 만들어 본다.
		powerSet[idx] = LEFT;
		makePowerSet(idx + 1);

		powerSet[idx] = RIGHT;
		makePowerSet(idx + 1);

		powerSet[idx] = NOT_USED;
		makePowerSet(idx + 1);

	}
}
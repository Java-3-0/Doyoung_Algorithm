// 20668KB, 236ms

package bj2116;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	/** 주사위의 각 면을 나타내는 상수 */
	public static final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5;
	/** 주사위의 면 개수 */
	public static final int SURFACES = 6;

	/** 주사위의 면 -> 그 반대쪽 면으로의 매핑 */
	public static final Map<Integer, Integer> oppositeSurfaceIdx = new HashMap<Integer, Integer>() {
		{
			put(A, F);
			put(B, D);
			put(C, E);
			put(D, B);
			put(E, C);
			put(F, A);
		}
	};

	static class Dice {
		int[] numbers;

		/** 주사위의 각 면의 수들을 입력받아, 주사위 객체를 생성하는 생성자 */
		public Dice(int[] numbers) {
			super();
			this.numbers = numbers;
		}

		/** 밑면의 인덱스가 주어졌을 때, 옆면에 있는 수들 중 최대값을 리턴 */
		public int getMaxNumberOnSide(int bottomSurfaceIdx) {
			int topSurfaceIdx = oppositeSurfaceIdx.get(bottomSurfaceIdx);

			int ret = 0;

			for (int i = 0; i < SURFACES; i++) {
				if (i == bottomSurfaceIdx || i == topSurfaceIdx) {
					continue;
				}
				ret = Math.max(ret, this.numbers[i]);
			}

			return ret;
		}
		
		/** 밑면의 인덱스가 주어졌을 때, 윗면에 있는 수를 리턴 */
		public int getNumberOnTop(int bottomSurfaceIdx) {
			int topSurfaceIdx = oppositeSurfaceIdx.get(bottomSurfaceIdx);
			
			return this.numbers[topSurfaceIdx];
		}

		/** 인덱스가 주어졌을 때, 그 면에 있는 수를 리턴 */
		public int getNumberAt(int idx) {
			return numbers[idx];
		}
		
		/** 수가 주어졌을 때, 그 수 가 적혀 있는 면의 인덱스를 리턴 */
		public int getIdxOf (int num) {
			for (int idx = 0; idx < SURFACES; idx++) {
				if (getNumberAt(idx) == num) {
					return idx;
				}
			}
			
			return -1;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		int numDices = Integer.parseInt(br.readLine());
		Dice[] dices = new Dice[numDices];
		for (int diceIdx = 0; diceIdx < numDices; diceIdx++) {
			st = new StringTokenizer(br.readLine(), " ");
			int[] input = new int[SURFACES];
			for (int surfaceIdx = 0; surfaceIdx < SURFACES; surfaceIdx++) {
				input[surfaceIdx] = Integer.parseInt(st.nextToken());
				dices[diceIdx] = new Dice(input);
			}
		}

		// 옆면 수 합의 최댓값을 계산
		int answer = maxSumOfSides(dices);

		// 출력
		System.out.println(answer);
	}

	/** 옆면의 합의 최대치를 리턴 */
	public static int maxSumOfSides(Dice[] dices) {
		int ret = 0;

		// 첫 주사위의 밑면을 하나씩 해보는 완전탐색
		for (int firstBottomIdx = 0; firstBottomIdx < SURFACES; firstBottomIdx++) {
			ret = Math.max(ret, maxSumOfSides(dices, firstBottomIdx));
		}

		return ret;
	}

	/** 맨 첫 주사위의 아랫면 인덱스 까지 주어졌을 때, 옆면의 합의 최대치를 리턴 */
	public static int maxSumOfSides(Dice[] dices, int firstBottomIdx) {
		int ret = 0;

		// 첫 주사위 옆면 중 최대값을 리턴값에 더하고, 이 주사위의 윗면 값을 저장해 둠
		Dice firstDice = dices[0];
		ret += firstDice.getMaxNumberOnSide(firstBottomIdx);
		int prevTopNumber = firstDice.getNumberOnTop(firstBottomIdx);

		// 첫 주사위 위에 쌓이는 나머지 주사위들에 대해서 반복
		int len = dices.length;
		for (int i = 1; i < len; i++) {
			Dice dice = dices[i];
			
			// 이전 윗면의 수와 같은 수를 가진 면을 찾는다
			int bottomIdx = dice.getIdxOf(prevTopNumber);
			
			// 이 면이 밑면일 때, 옆면 중 최대값을 리턴값에 더함
			ret += dice.getMaxNumberOnSide(bottomIdx);
			
			// 윗면 값 갱신
			prevTopNumber = dice.getNumberOnTop(bottomIdx);
		}

		return ret;
	}
}
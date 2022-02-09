// 90004KB, 696ms

// 17298번 오큰수 문제와 비슷한 문제이다. 이번 문제에서는 왼큰수를 구한다고 보면 된다.
package baek2493;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

class Tower {
	/** 탑의 번호 */
	int num;
	/** 탑의 높이 */
	int height;

	public Tower(int num, int height) {
		super();
		this.num = num;
		this.height = height;
	}
}

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// 입력을 받아 탑의 배열에 넣음
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int numTowers = Integer.parseInt(br.readLine());
		String[] tokens = br.readLine().split(" ");
		Tower[] towers = new Tower[numTowers];
		for (int i = 0; i < numTowers; i++) {
			towers[i] = new Tower(i + 1, Integer.parseInt(tokens[i]));
		}
		br.close();

		// 각각의 탑이 쏜 레이저를 수신하는 탑의 번호를 계산
		int[] laserReceivers = getLaserReceivers(towers);

		// 출력
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < laserReceivers.length; i++) {
			sb.append(laserReceivers[i]).append(" ");
		}

		System.out.print(sb.toString());
	}

	/**
	 * 각각의 탑이 쏜 레이저를 수신하는 탑의 번호를 차례대로 배열에 담아서 리턴
	 * 
	 * @param towers : 탑의 배열
	 * @return 레이저를 수신하는 탑 번호의 배열
	 */
	public static int[] getLaserReceivers(Tower[] towers) {
		int length = towers.length;
		// 송신한 레이저를 아직 아무도 수신하지 않아서 대기중인 타워들이 담길 스택 생성
		Stack<Tower> stack = new Stack<Tower>();
		//
		int[] laserReceivers = new int[length + 1];

		// 맨 오른쪽 타워부터 왼쪽으로 탐색
		for (int i = length - 1; i >= 0; i--) {
			// 이번 타워를 receiver로 봄
			Tower receiver = towers[i];

			// receiver가 수신하는 레이저들을 찾아서 갱신
			while (!stack.empty() && stack.peek().height < receiver.height) {
				Tower sender = stack.pop();
				laserReceivers[sender.num] = receiver.num;
			}

			// receiver 자체도 레이저를 쏘니 stack에 push
			stack.push(receiver);
		}

		// 루프를 다 돌고 났는데 스택에 남아있는 타워들은 레이저를 아무도 수신하지 않으므로 0
		while (!stack.empty()) {
			Tower t = stack.pop();
			laserReceivers[t.num] = 0;
		}

		return laserReceivers;
	}
}
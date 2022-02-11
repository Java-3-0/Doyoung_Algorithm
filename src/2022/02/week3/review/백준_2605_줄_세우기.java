// 17636KB, 220ms

package baek2605;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int numStudents = sc.nextInt();
		
		// 줄 세우기
		List<Integer> studentLine = new ArrayList<>();
		for (int num = 1; num <= numStudents; num++) {
			// 앞으로 갈 칸 수 입력
			int moveForward = sc.nextInt();
			// 도착하는 칸의 인덱스 계산
			int idx = num - 1 - moveForward;
			// 그 칸에 배치
			studentLine.add(idx, num);
		}
		
		// 출력
		StringBuilder sb = new StringBuilder();
		for (int num : studentLine) {
			sb.append(num).append(" ");
		}
		sb.append("\n");
		System.out.print(sb);
		
		sc.close();
	}

}

// 11952KB, 88ms

package baek13300;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int SEX = 2;
	public static final int GRADE = 6;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 학생 수, 방 크기 입력
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int numStudents = Integer.parseInt(st.nextToken());
		int roomCapacity = Integer.parseInt(st.nextToken());

		// 각 학생 입력
		int[][] students = new int[SEX][GRADE];
		for (int i = 0; i < numStudents; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int sex = Integer.parseInt(st.nextToken());
			int grade = Integer.parseInt(st.nextToken()) - 1;
			students[sex][grade]++;
		}

		// 필요한 방 개수 계산
		int answer = 0;
		for (int[] arr : students) {
			for (int num : arr) {
				int roomNeeded = num / roomCapacity + (num % roomCapacity != 0 ? 1 : 0);
				answer += roomNeeded;
			}
		}

		// 출력
		System.out.println(answer);
	}

}

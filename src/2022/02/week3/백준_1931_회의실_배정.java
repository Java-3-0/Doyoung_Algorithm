// 42872KB, 520ms

package bj1931;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Meeting implements Comparable<Meeting>{
	int startTime;
	int endTime;
	public Meeting(int startTime, int endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	/** 회의가 지금 시간에 시작 가능하면 true, 아니면 false 리턴 */
	public boolean canStart() {
		if (Main.timeNow <= this.startTime) {
			return true;
		}
		
		return false;
	}

	/** 종료 시간 오름차순, 종료 시간이 같으면 시작 시간 오름차순으로 정렬하기 위한 비교함수 */
	@Override
	public int compareTo(Meeting m) {
		if (this.endTime == m.endTime) {
			return this.startTime - m.startTime;
		}
		return this.endTime - m.endTime;
	}
}

public class Main {
	public static int timeNow = 0;

	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int N = Integer.parseInt(br.readLine());
		
		// 회의 시간 정보 입력 받아서 meetings 배열에 저장
		Meeting[] meetings = new Meeting[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int startTime = Integer.parseInt(st.nextToken());
			int endTime = Integer.parseInt(st.nextToken());
			meetings[i] = new Meeting(startTime, endTime);
		}
		
		// 종료시간 오름차순으로 정렬
		Arrays.sort(meetings);
		
		// 종료 시간이 빠른 순으로, 진행할 수 있는 회의를 모두 진행
		int count = 0;
		for (Meeting m : meetings) {
			// 시작 가능한 회의면 진행
			if (m.canStart()) {
				count++;
				timeNow = m.endTime;
			}
		}
		
		// 진행한 회의 개수 출력
		System.out.println(count);
	}
}

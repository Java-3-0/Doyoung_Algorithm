// 12120KB, 96ms

package bj6068;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_TIME = 1000000;
	
	static class Job implements Comparable<Job> {
		int timeNeeded;
		int deadline;
		
		public Job(int timeNeeded, int deadline) {
			super();
			this.timeNeeded = timeNeeded;
			this.deadline = deadline;
		}

		@Override
		public String toString() {
			return "Job [timeNeeded=" + timeNeeded + ", deadline=" + deadline + "]";
		}

		/** 데드라인 내림차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Job j) {
			return j.deadline - this.deadline;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 일 개수 입력
		int N = Integer.parseInt(br.readLine());
		
		// 일 입력
		Job[] jobs = new Job[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int timeNeeded = Integer.parseInt(st.nextToken());
			int deadline = Integer.parseInt(st.nextToken());
			jobs[i] = new Job(timeNeeded, deadline);
		}
		
		// 일을 데드라인 내림차순으로 정렬
		Arrays.sort(jobs);
		
		// 일을 하나씩 탐색하면서, 일어나야 하는 시간을 갱신
		int wakeUpTime = MAX_TIME;
		for (Job j : jobs) {
			wakeUpTime = Math.min(wakeUpTime, j.deadline) - j.timeNeeded;
		}
		
		// 출력
		if (wakeUpTime < 0) {
			System.out.println(-1);
		}
		else {
			System.out.println(wakeUpTime);
		}
	}

}
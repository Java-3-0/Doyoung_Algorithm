// 11932KB, 88ms

package bj2852;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static class Score implements Comparable<Score> {
		int team;
		int time;

		public Score(int team, int time) {
			super();
			this.team = team;
			this.time = time;
		}

		@Override
		public int compareTo(Score s) {
			return this.time - s.time;
		}

		@Override
		public String toString() {
			return "Score [team=" + team + ", time=" + time + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		Score[] scores = new Score[N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int team = Integer.parseInt(st.nextToken());
			int time = stringToTime(st.nextToken());

			scores[i] = new Score(team, time);
		}

		Arrays.sort(scores);

		int prev = 0;
		int now = 0;

		int winTime1 = 0;
		int winTime2 = 0;

		int team1Score = 0;
		int team2Score = 0;

		boolean isWinning1 = false;
		boolean isWinning2 = false;

		for (Score score : scores) {
			now = score.time;
			int timeDiff = now - prev;
			if (isWinning1) {
				winTime1 += timeDiff;
			} else if (isWinning2) {
				winTime2 += timeDiff;
			}

			if (score.team == 1) {
				team1Score++;
			} else {
				team2Score++;
			}

			if (team1Score == team2Score) {
				isWinning1 = false;
				isWinning2 = false;
			} else if (team1Score < team2Score) {
				isWinning1 = false;
				isWinning2 = true;
			} else {
				isWinning1 = true;
				isWinning2 = false;
			}

			prev = now;
		}

		int lastTime = 48 * 60;
		int timeDiff = lastTime - prev;

		if (team1Score > team2Score) {
			winTime1 += timeDiff;
		} else if (team1Score < team2Score) {
			winTime2 += timeDiff;
		}

		System.out.println(timeToString(winTime1));
		System.out.println(timeToString(winTime2));

	} // end main

	public static int stringToTime(String s) {
		StringTokenizer st = new StringTokenizer(s, ":");
		int minute = Integer.parseInt(st.nextToken());
		int second = Integer.parseInt(st.nextToken());

		return 60 * minute + second;
	}

	public static String timeToString(int time) {
		int minute = time / 60;
		int second = time % 60;

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%02d", minute)).append(":").append(String.format("%02d", second));

		return sb.toString();

	}
}
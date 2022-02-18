// 128584KB, 404ms

/* 
 * 풀이 아이디어 :
 * 모든 시작지점(맨 왼쪽 열의 각 칸들)에서 탐색을 시작해 본다
 * 항상 greedy하게 위쪽에서 가장 가까운 경로를 택해서 간다
 * 마지막 열까지 도착 성공할 경우, 이 시작지점에서 시작한 탐색은 더 이상 돌지 않고 끝낸다.
 * 다음 시작지점부터 탐색할 때는, 이전 탐색에서 가 봤던 곳은 다시 가볼 필요가 없다.
 */

package bj3109;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	// 상수 선언
	
	/** 격자 최대 높이 */
	static final int MAX_HEIGHT = 10000;
	/** 격자 최대 너비 */
	static final int MAX_WIDTH = 500;
	/** 빈 칸을 나타내는 상수 */
	static final char BLANK = '.';
	/** 건물을 나타내는 상수 */
	static final char BUILDING = 'x';
	/** 방향 개수 */
	static final int DIRECTIONS = 3;
	/** 우상, 우, 우하 순서로의 y 변화량 */
	static final int[] dy = {-1, 0, 1};
	/** x 변화량은 항상 오른쪽이므로 1 */
	static final int dx = 1;
	
	// 전역변수 선언
	
	/** 격자 높이 */
	static int height;
	/** 격자 너비 */
	static int width;
	/** 입력받은 지도 상태를 저장할 2차원 배열 */
	static char[][] grid = new char[MAX_HEIGHT][MAX_WIDTH];
	/** 방문 여부를 저장할 2차원 배열 */
	static boolean[][] isVisited = new boolean[MAX_HEIGHT][MAX_WIDTH];
	/** 매 탐색마다 맨 오른쪽 열까지 도착했는지 못했는지를 저장할 변수 */
	static boolean arrivedLastColumn;
	/** 정답을 저장할 변수 */
	static int answer = 0;
	
	// 객체 선언
	static class Position {
		int y;
		int x;
		
		/** y좌표, x좌표를 입력받아 위치 객체를 생성하는 생성자 */
		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}
		
		/** 위치가 방문 가능하면 true, 아니면 false를 리턴한다 */
		public boolean canVisit() {
			// 격자 범위 내이고, 이미 놓인 파이프가 없고, 빌딩도 없다면 방문 가능
			if (this.isInRange() && !isVisited[this.y][this.x] && grid[this.y][this.x] != BUILDING) {
				return true;
			}
			// 아니면 방문 불가능
			return false;
		}
		
		/** 위치가 격자 범위 내에 들어가면 true, 아니면 false를 리턴한다. */
		private boolean isInRange() {
			if (0 <= this.y && this.y < height && 0 <= this.x && this.x < width) {
				return true;
			}
			
			return false;
		}
	}
	
	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());
		for (int y = 0; y < height; y++) {
			String line = br.readLine();
			for (int x = 0; x < width; x++) {
				grid[y][x] = line.charAt(x);
			}
		}
		br.close();
		
		// 맨 위 시작지점부터 하나씩 시도해 보면서, 성공할 때마다 answer를 갱신
		for (int startY = 0; startY < height; startY++) {
			Position startPos = new Position(startY, 0);
			if (startPos.canVisit()) {
				tryPathFrom(startPos);
			}
		}
		
		// 정답 출력
		System.out.println(answer);
	}
	
	/** 맨 왼쪽 열의 시작 지점이 주어졌을 때, 그곳으로부터의 경로를 dfs를 통해 탐색하면서 맨 오른쪽까지 도달 가능한지 시도해 보는 함수 */
	public static void tryPathFrom (Position startPos) {
		arrivedLastColumn = false;
		
		dfs(startPos);
	}
	
	/** here이라는 위치에서부터 dfs를 재귀적으로 수행하는 함수 */
	public static void dfs(Position here) {
		// 이미 다른 경로에서 마지막 칸까지 도착을 성공한 경우 리턴
		if (arrivedLastColumn) {
			return;
		}
		
		// 새로 방문
		isVisited[here.y][here.x] = true;
		
		// 이번 칸이 마지막 열, 즉 도착 지점이라면
		if (here.x == width - 1) {
			// 도착 성공 여부 갱신하고, 정답 개수 하나 센다
			arrivedLastColumn = true;
			answer++;
			return;
		}
		
		// 우상, 우, 우하 순서로 dfs를 해 본다.
		for (int dir = 0; dir < DIRECTIONS; dir++) {
			Position there = new Position(here.y + dy[dir], here.x + dx);
			if (there.canVisit()) {
				dfs(there);
			}
		}
	}
}

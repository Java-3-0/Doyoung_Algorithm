import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class GraphTemplate2D {
	static final int MAX_R = 100, MAX_C = 100;
	static final int INF = 987654321;
	static final int DIRECTIONS = 4;
	static final int[] dy = { -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, -1, 1 };

	static int R, C;
	static int[][] grid = new int[MAX_R][MAX_C];
    static Queue<Position> q = new ArrayDeque<>();
    static boolean[][] visitCheck = new boolean[MAX_R][MAX_C];
	
    static class Position {
		int y;
		int x;

		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		@Override
		public String toString() {
			return "Pos [y=" + y + ", x=" + x + "]";
		}

		public Position getNextPos(int dir) {
			return new Position(this.y + dy[dir], this.x + dx[dir]);
		}

		public boolean isInRange() {
			if (0 <= this.y && this.y < R && 0 <= this.x && this.x < C) {
				return true;
			}
			return false;
		}
		
		public boolean isVisited() {
			return visitCheck[this.y][this.x];
		}
		
		public void visit() {
			visitCheck[this.y][this.x]= true; 
		}
		
		public int getGridVal() {
			return grid[this.y][this.x];
		}
	}
    
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 테스트케이스 개수 입력
		final int TESTS = Integer.parseInt(br.readLine());

		// 테스트케이스 수행
		for (int tc = 1; tc <= TESTS; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
		}

		System.out.print(sb.toString());

	} // end main

}
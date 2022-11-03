import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	// 상수 선언
	static final int MAX_V = 1000;
	static final double MAX_POS = 1000000.0;
	static final double ZERO = 0.0;

	/** 세율 */
	static double taxRate;

	/** 위치를 나타내는 객체 */
	static class Position {
		double x;
		double y;

		public Position(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		public double getTunnelCost(Position p) {
			double xDist = this.x - p.x;
			double yDist = this.y - p.y;
			double distSquare = xDist * xDist + yDist * yDist;

			return distSquare * taxRate;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// 정점 수 입력
			final int V = Integer.parseInt(br.readLine());
			Position[] islands = new Position[V];

			/* 입력이 x0, y0, x1, y1, x2, y2, ... 형태가 아니라
			 x0, x1, x2, ... x(n-1) 이후에 y0, y1, y2, ... y(n-1)이 들어오기 때문에
			 임시로 담아두었다가 처리하기 위한 배열 */
			double[] tmpX = new double[V];

			// 각 정점 x좌표 입력
			st = new StringTokenizer(br.readLine(), " ");
			for (int islandIdx = 0; islandIdx < V; islandIdx++) {
				tmpX[islandIdx] = Double.parseDouble(st.nextToken());
			}

			// 각 정점 y좌표 입력받아서 위에서 임시로 저장해 둔 x좌표와 묶어서 위치 객체로 배열에 저장
			st = new StringTokenizer(br.readLine(), " ");
			for (int islandIdx = 0; islandIdx < V; islandIdx++) {
				double y = Double.parseDouble(st.nextToken());
				islands[islandIdx] = new Position(tmpX[islandIdx], y);
			}

			// 세율 입력
			taxRate = Double.parseDouble(br.readLine());

			// 배열 초기화
			double[][] adjMatrix = new double[V][V];
			double[] minEdges = new double[V];
			boolean[] isVisited = new boolean[V];

			// 각 정점간의 터널 비용 계산해서 adjMatrix에 저장
			for (int y = 0; y < V; y++) {
				for (int x = 0; x < V; x++) {
					adjMatrix[y][x] = islands[y].getTunnelCost(islands[x]);
				}
				minEdges[y] = Double.MAX_VALUE;
			}

			// 0번 정점에서 시작
			minEdges[0] = ZERO;
			double answer = ZERO;

			// 모든 정점이 연결될 때까지 반복
			for (int connected = 0; connected < V; connected++) {
				double minEdge = Double.MAX_VALUE;
				int minVertexIdx = 0;

				// 아직 연결되지 않은 정점들 중에서 가장 작은 weight의 간선으로 갈 수 있는 정점을 택함
				for (int vertexIdx = 0; vertexIdx < V; vertexIdx++) {
					double edge = minEdges[vertexIdx];
					if (!isVisited[vertexIdx] && edge < minEdge) {
						minEdge = edge;
						minVertexIdx = vertexIdx;
					}
				}

				// 간선 weight만큼 정답에 더하고, 방문 여부 갱신
				answer += minEdge;
				isVisited[minVertexIdx] = true;

				// 아직 방문하지 않은 정점들에 대해서 minEdges 갱신
				for (int vertexIdx = 0; vertexIdx < V; vertexIdx++) {
					if (!isVisited[vertexIdx]) {
						minEdges[vertexIdx] = Math.min(minEdges[vertexIdx], adjMatrix[minVertexIdx][vertexIdx]);
					}
				}
			}

			// 정답을 형식에 맞게 출력 스트링빌더에 추가
			sb.append("#").append(testCase).append(" ").append(String.format("%.0f", answer)).append("\n");

		} // end for testCase

		// 출력
		System.out.print(sb.toString());

	} // end main
}
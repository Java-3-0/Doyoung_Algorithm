// 70760KB, 620ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

class Point implements Comparable<Point> {
	int x;
	int y;
	
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Point p) {
		if (this.x == p.x) {
			return this.y - p.y;
		}
		return this.x - p.x;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(x).append(" ").append(y).append("\n");
		return sb.toString();
	}
	
	
}

public class Main {
	public static void main(String[] args) throws IOException {
//		Scanner sc = new Scanner(System.in);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(br.readLine());
		
		Set<Point> set = new TreeSet<>();
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			set.add(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
		}
		
		for (Point p : set) {
			sb.append(p.toString());
		}
		
		System.out.println(sb.toString());
		
	}
}
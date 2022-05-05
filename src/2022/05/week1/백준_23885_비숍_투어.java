// 11500KB, 80ms

package bj23885;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine(), " ");
		int sx = Integer.parseInt(st.nextToken());
		int sy = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine(), " ");
		int ex = Integer.parseInt(st.nextToken());
		int ey = Integer.parseInt(st.nextToken());
		
		String answer = isPossible(N, M, sx, sy, ex, ey) ? "YES" : "NO";
		
		System.out.println(answer);

		
	} // end main
	
	public static boolean isPossible(int N, int M, int sx, int sy, int ex, int ey) {
		if (N == 1 && sy != ey) {
			return false;
		}
		
		if (M == 1 && sx != ex) {
			return false;
		}
		
		if ((sx + sy) % 2 == (ex + ey) % 2) {
			return true;
		} else {
			return false;
		}
	}

}
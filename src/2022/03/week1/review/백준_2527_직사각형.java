// 11580KB, 76ms

package baek2564;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Rectangle {
	int left;
	int bottom;
	int right;
	int top;

	public Rectangle(int left, int bottom, int right, int top) {
		super();
		this.left = left;
		this.bottom = bottom;
		this.right = right;
		this.top = top;
	}
}

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		for (int i = 0; i < 4; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			Rectangle r1 = new Rectangle(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			Rectangle r2 = new Rectangle(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

			char answer = overlap(r1, r2);
			System.out.println(answer);
		}

	}

	static char overlap(Rectangle r1, Rectangle r2) {
		// 만나지 않는 경우
		if ((r1.right < r2.left) || (r1.left > r2.right) || (r1.bottom > r2.top) || (r1.top < r2.bottom)) {
			return 'd';
		}

		// 점 또는 선에서 만나는 경우
		if ((r1.right == r2.left) || (r1.left == r2.right)) {
			if (r1.bottom == r2.top || r1.top == r2.bottom) {
				return 'c';
			} else {
				return 'b';
			}
		}

		if ((r1.top == r2.bottom) || (r1.bottom == r2.top)) {
			if (r1.left == r2.right || r1.right == r2.left) {
				return 'c';
			} else {
				return 'b';
			}
		}

		// 이외의 경우
		return 'a';
	}
}

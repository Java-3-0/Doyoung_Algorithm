// 22440KB, 216ms

package bj19939;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		
		int N = Integer.parseInt(br.readLine());
		
		// 나무 입력받으면서 전체 높이의 합과 2짜리 물뿌리개를 최대 몇 번 사용 가능한지 센다.
		// 1짜리 물뿌리개는 어차피 아무 데나 들어갈 수 있기 때문에 셀 필요가 없다.
		st = new StringTokenizer(br.readLine(), " ");
		
		int sum = 0;
		int usableTwo = 0;
		for (int i = 0; i < N; i++) {
			int height = Integer.parseInt(st.nextToken());
			sum += height;
			usableTwo += (height / 2);
		}
		
		// 합이 3의 배수여야 다 뿌릴 수 있고, 2짜리 사용 횟수 = 1짜리 사용 횟수 = 전체 sum에 대해 3씩 뿌리는 횟수이기 때문에
		// 2짜리 사용 횟수가 충분한지 보고 성공 여부를 판단한다.
		if (sum % 3 == 0 && usableTwo >= sum / 3) {
			System.out.println("YES");
		}
		else {
			System.out.println("NO");
		}
	}
}
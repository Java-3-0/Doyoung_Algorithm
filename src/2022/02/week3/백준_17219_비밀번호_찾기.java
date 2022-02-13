// 63608KB, 520ms

package bj17219;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		// 사이트 주소 -> 비밀번호로의 매핑 생성
		Map<String, String> addrToPW = new HashMap<>();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			String address = st.nextToken();
			String password = st.nextToken();
			addrToPW.put(address, password);
		}

		// 맵에서 사이트 주소에 해당하는 비밀번호를 가져와서 출력에 추가
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < M; i++) {
			String address = br.readLine();
			sb.append(addrToPW.get(address)).append("\n");
		}

		br.close();

		// 출력
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}

}

// 66208KB, 536ms

package bj19583;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int S = timeToInt(st.nextToken());
		int E = timeToInt(st.nextToken());
		int Q = timeToInt(st.nextToken());
		
		Set<String> enter = new HashSet<> ();
		Set<String> exit = new HashSet<> ();
		
		String line = "";
		while ((line = br.readLine()) != null) {
			st = new StringTokenizer(line, " ");
			int chatTime = timeToInt(st.nextToken());
			String chatID = st.nextToken();
			
			// 제 때 입장했다면 입장 set에 등록
			if (chatTime <= S) {
				enter.add(chatID);
			}
			
			// 제 때 퇴장했다면 퇴장 set에 등록
			if (E <= chatTime && chatTime <= Q) {
				exit.add(chatID);
			}
		}
		
		// 입장 set에도 있으면서 퇴장 set에도 있는 id의 개수를 파악
		int count = 0;
		for (String id : enter) {
			if (exit.contains(id)) {
				count++;
			}
		}
		
		// 정답 출력
		System.out.println(count);
	}
	
	/** (HH:MM 형태로 주어진 시간)을 (분을 나타내는 정수 하나)로 변환 */
	public static int timeToInt (String time) {
		StringTokenizer st = new StringTokenizer(time, ":");
		int hour = Integer.parseInt(st.nextToken());
		int minute = Integer.parseInt(st.nextToken());
		
		return hour * 60 + minute;
	}
}
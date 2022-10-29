import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static final int DIGITS = 10;
    static final String[] names = {"ZRO", "ONE", "TWO", "THR", "FOR", "FIV", "SIX", "SVN", "EGT", "NIN"};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 테스트케이스 개수 입력
        final int TESTS = Integer.parseInt(br.readLine());

        // 테스트케이스 수행
        for (int tc = 1; tc <= TESTS; tc++) {
            st = new StringTokenizer(br.readLine(), " ");

            // 테스트케이스 번호 입력
            int tcNum = Integer.parseInt(st.nextToken().substring(1));

            // 문자열 개수 입력
            int N = Integer.parseInt(st.nextToken());

            // 단어들을 입력받아서 각 단어의 개수 카운팅
            st = new StringTokenizer(br.readLine(), " ");
            Map<String, Integer> countMap = new HashMap<>();
            while (st.hasMoreTokens()) {
                String word = st.nextToken();
                countMap.put(word, 1 + countMap.getOrDefault(word, 0));
            }

            // 테스트케이스 번호를 출력에 추가
            sb.append("#").append(tcNum).append("\n");

            // 가장 작은 수부터 그 개수만큼 출력에 추가
            for (String name : names) {
                int cnt = countMap.getOrDefault(name, 0);
                for (int i = 0; i < cnt; i++) {
                    sb.append(name).append(" ");
                }
            }

            sb.append("\n");

        }

        System.out.print(sb.toString());

    } // end main

}
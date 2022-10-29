import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static final int PASSWORD_LEN = 56;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        final int TESTS = Integer.parseInt(br.readLine());

        Map<String, Integer> codeToNum = new HashMap<>();
        codeToNum.put("0001101", 0);
        codeToNum.put("0011001", 1);
        codeToNum.put("0010011", 2);
        codeToNum.put("0111101", 3);
        codeToNum.put("0100011", 4);
        codeToNum.put("0110001", 5);
        codeToNum.put("0101111", 6);
        codeToNum.put("0111011", 7);
        codeToNum.put("0110111", 8);
        codeToNum.put("0001011", 9);

        for (int tc = 1; tc <= TESTS; tc++) {
            // 그리드 크기 입력
            st = new StringTokenizer(br.readLine(), " ");
            int H = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());

            // 그리드 입력
            String[] lines = new String[H];
            for (int y = 0; y < H; y++) {
                lines[y] = br.readLine();
            }

            // 마지막 '1'의 위치를 찾는다.
            int lastOneY = 0, lastOneX = 0;
            OUTER:
            for (int y = H - 1; y >= 0; y--) {
                for (int x = W - 1; x >= 0; x--) {
                    if (lines[y].charAt(x) == '1') {
                        lastOneY = y;
                        lastOneX = x;
                        break OUTER;
                    }
                }
            }

            // 암호코드의 시작 위치
            int startY = lastOneY;
            int startX = lastOneX - PASSWORD_LEN + 1;

            // 7칸씩 8번 자르면서 해석
            int sum = 0;
            int verifyCode = 0;
            for (int i = 0; i < 8; i++) {
                String code = lines[startY].substring(startX + 7 * i, startX + 7 * i + 7);
                int num = codeToNum.get(code);
                sum += num;
                verifyCode += (i % 2 == 0) ? 3 * num : num;
            }

            // 출력에 결과 추가
            sb.append("#").append(tc).append(" ");

            if (verifyCode % 10 == 0) {
                sb.append(sum);
            } else {
                sb.append(0);
            }
            sb.append("\n");

        } // end for (tc);

        // 출력
        System.out.print(sb.toString());

    } // end main

}
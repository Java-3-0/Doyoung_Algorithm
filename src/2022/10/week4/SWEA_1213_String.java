import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        final int TESTS = 10;

        for (int tc = 1; tc <= TESTS; tc++) {
            int testCaseNum = Integer.parseInt(br.readLine());

            String pattern = br.readLine();
            String text = br.readLine();

            List<Integer> foundPositions = kmp(text, pattern);
            int answer = foundPositions.size();

            sb.append("#").append(testCaseNum).append(" ");
            sb.append(answer).append("\n");
        }

        System.out.print(sb.toString());

    } // end main

    public static List<Integer> kmp(String text, String pattern) {
        // 패턴을 찾는 데 성공한 위치들을 담을 리스트
        List<Integer> ret = new ArrayList<>();

        char[] arrT = text.toCharArray();
        char[] arrP = pattern.toCharArray();

        // 텍스트와 패턴의 길이
        int lenT = arrT.length;
        int lenP = arrP.length;

        // p[i] : pattern[0]부터 pattern[i]까지의 문자열에서 접두사 = 접미사가 되는 부분의 최대 길이
        int[] p = getPrefixTable(pattern);

        for (int i = 0, j = 0; i < lenT; i++) {
            // j가 0이 되기 전까지, 문자열이 일치하지 않으면 j 갱신
            while (j > 0 && arrT[i] != arrP[j]) {
                j = p[j - 1];
            }

            if (arrT[i] == arrP[j]) {
                if (j == lenP - 1) {
                    ret.add(i - j + 1);
                    j = p[j];
                } else {
                    j++;
                }
            }

        }

        return ret;
    }

    public static int[] getPrefixTable(String pattern) {
        char[] arrP = pattern.toCharArray();

        int lenP = pattern.length();

        // p[i] : pattern[0]부터 pattern[i]까지의 문자열에서 접두사 = 접미사가 되는 부분의 최대 길이
        int[] p = new int[lenP];

        // p[0]만 예외적으로 0으로 처리
        p[0] = 0;

        for (int i = 1, j = 0; i < lenP; i++) {
            while (j > 0 && arrP[i] != arrP[j]) {
                j = p[j - 1];
            }

            if (arrP[i] == arrP[j]) {
                p[i] = ++j;
            }

            else {
                p[i] = 0;
            }
        }

        return p;
    }
}
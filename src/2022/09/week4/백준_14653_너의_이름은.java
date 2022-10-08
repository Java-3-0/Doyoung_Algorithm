// 14608KB, 136ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 26, MAX_K = 10000, MAX_Q = MAX_K;

    static int N, K, Q;

    static class Message {
        int notReadCnt;
        char senderName;

        public Message(int notReadCnt, char senderName) {
            this.notReadCnt = notReadCnt;
            this.senderName = senderName;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 사람 수, 메시지 수, 조회할 메시지 번호 입력
        st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken()) - 1; // index 0부터 시작

        // 메시지들을 입력 받아서 배열로 저장
        Message[] messages = new Message[K];
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int notReadCnt = Integer.parseInt(st.nextToken());
            char senderName = st.nextToken().charAt(0);

            messages[i] = new Message(notReadCnt, senderName);
        }

        // 읽은 사람들을 저장할 set
        Set<Character> readPeople = new HashSet<>();

        // Q번 메시지와 그 이후에 메시지를 전송한 사람들은 확실히 읽었음
        for (int i = K - 1; i >= Q; i--) {
            readPeople.add(messages[i].senderName);
        }

        // Q번 메시지보다 이전에 메시지를 전송한 사람들 중, 자기 메시지와 Q번 메시지의 unReadCnt 가 같다면 읽은 사람
        for (int i = Q - 1; i >= 0; i--) {
            if (messages[i].notReadCnt == messages[Q].notReadCnt) {
                readPeople.add(messages[i].senderName);
            } else {
                break;
            }
        }

        // 나는 'A' 이고 모든 메시지를 읽는다
        readPeople.add('A');

        // 출력
        if (messages[Q].notReadCnt == 0) {
            sb.append(-1).append("\n");
        } else {
            for (int i = 0; i < N; i++) {
                char person = (char) ('A' + i);
                if (!readPeople.contains(person)) {
                    sb.append(person).append(" ");
                }
            }
            sb.append("\n");
        }

        System.out.print(sb.toString());

    } // end main

}
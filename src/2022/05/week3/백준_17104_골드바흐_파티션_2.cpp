// 60020KB, 400ms

#include <iostream>
#include <cstring>
#include <vector>
#include <cmath>
#include <complex>

using namespace std;

typedef complex<double> cpx;
typedef long long ll;

const double PI = M_PI;
const int MAX_N = 1000000;

bool isPrime[MAX_N + 1];

void precalcPrimes();
void fft(vector<cpx> &f, bool isReverse);
vector<ll> square(vector<ll>& f);

int main(void)
{
    precalcPrimes();

    // �Ҽ� ���׽�
    vector<ll> f(MAX_N + 1, 0);
    for (int i = 0; i <= MAX_N; i++) {
        if (isPrime[i]) {
            f[i] = 1;
        }
    }

    // ���׽��� �� ���
    vector<ll> product = square(f);

    // �׽�Ʈ���̽� �� �Է�
    int T;
    scanf("%d", &T);

    // �׽�Ʈ ���̽����� N�� �Է¹޾Ƽ� ó��
    int N;
    for (int i = 0; i < T; i++) {
        scanf("%d", &N);

        // ������ �ٸ� ���� �ߺ����� 2���� ���� �ǹǷ� �������� ������
        ll answer = product[N] / 2;

        // ���� ���� �� �� ���ؼ� ���� ���� ��쿡�� ������ 1������ �����Ƿ�, ������ �� �����Ѵ�.
        if (isPrime[N / 2]) {
            answer++;
        }

        // ���
        printf("%lld\n", answer);
    }

    return 0;
}

/** MAX_N������ �Ҽ� ���θ� �̸� ����Ͽ� isPrime[] �迭�� ���� */
void precalcPrimes() {
    memset(isPrime, true, sizeof(isPrime));
    isPrime[0] = false;
    isPrime[1] = false;

    for (int div = 2; div <= 1000; div++) {
        if (isPrime[div]) {
            for (int num = div * div; num <= MAX_N; num += div) {
                isPrime[num] = false;
            }
        }
    }
}

/** ���׽� f�� ��� ǻ���� ��ȯ�� ����� f�� ���� */
void fft(vector<cpx> &f, bool isReverse) {
    int n = f.size();

    // �����ϱ�
    for (int i = 1, j = 0; i < n; i++) {
        int bit = (n >> 1);
        while(!((j ^= bit) & bit)) {
            bit >>= 1;
        }

        if (i < j) {
            swap(f[i], f[j]);
        }
    }

    // ��� ��� for������ Ǯ� �޸� �Ƴ���
    for (int len = 2; len <= n; len <<= 1) {
        int halfLen = (len >> 1);

        double angle = 2 * PI / len;
        if (isReverse) {
            angle *= -1;
        }

        cpx wLen(cos(angle), sin(angle));
        for (int i = 0; i < n; i += len) {
            cpx w(1, 0);
            for (int j = 0; j < halfLen; j++) {
                cpx u = f[i + j];
                cpx v = f[i + j + halfLen] * w;
                f[i + j] = u + v;
                f[i + j + halfLen] = u - v;
                w *= wLen;
            }
        }
    }

    // ����ȯ�̸� n���� �����ֱ�
    if (isReverse) {
        for (int i = 0; i < n; i++) {
            f[i] /= n;
        }
    }

    return;
}

/** ���׽� f�� ������ ����ؼ� ���� */
vector<ll> square(vector<ll>& fll) {
    vector<cpx> f(fll.begin(), fll.end());

    int size = fll.size();

    // 2�� ������ �� f * f�� ���� �� �ִ� �ּ� ũ�⸦ ���Ѵ�
    int n = 1;
    while (n < size + size) {
        n <<= 1;
    }

    // �� ũ��� �迭 ���̸� �ø���
    f.resize(n);

    // ���׽��� FFT ��ȯ�Ѵ�
    fft(f, false);

    // FFT�� ���鳢�� ���Ѵ�
    for (int i = 0; i < n; i++) {
        f[i] *= f[i];
    }

    // ���� ����� FFT ����ȯ�Ѵ�
    fft(f, true);

    // �Ǽ� �κи� round ó���ؼ� �����Ѵ�
    vector<ll> ret(n);
    for (int i = 0; i < n; i++) {
        ret[i] = (ll) (round(f[i].real()));
    }
    return ret;
}

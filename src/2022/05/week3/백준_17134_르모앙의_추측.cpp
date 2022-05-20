// 100488KB, 584ms

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

void fft(vector<cpx> &f, bool isReverse);
vector<ll> multiply(vector<ll>& f, vector<ll>& g);


int main(void)
{
    // �Ҽ� ���ε��� �̸� ���
    bool isPrime[MAX_N + 1];
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

    // Ȧ�� �Ҽ� ���׽�
    vector<ll> f(MAX_N + 1, 0);
    for (int i = 3; i <= MAX_N; i += 2) {
        if (isPrime[i]) {
            f[i] = 1;
        }
    }

    // ¦�� ���̼Ҽ� ���׽�
    vector<ll> g(MAX_N + 1, 0);
    g[4] = 1;
    for (int i = 3; i <= MAX_N / 2; i += 2) {
        if (isPrime[i]) {
            g[2 * i] = 1;
        }
    }

    // ���׽��� �� ���
    vector<ll> product = multiply(f, g);

    int T;
    scanf("%d", &T);

    int N;
    for (int i = 0; i < T; i++) {
        scanf("%d", &N);
        printf("%lld\n", product[N]);
    }

    return 0;
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

/** ���׽� f�� g�� ���� ����ؼ� ���� */
vector<ll> multiply(vector<ll>& fll, vector<ll>& gll) {
    vector<cpx> f(fll.begin(), fll.end());
    vector<cpx> g(gll.begin(), gll.end());

    int size1 = fll.size();
    int size2 = gll.size();

    // 2�� ������ �� f * g�� ���� �� �ִ� �ּ� ũ�⸦ ���Ѵ�
    int n = 1;
    while (n < size1 + size2) {
        n <<= 1;
    }

    // �� ũ��� �迭 ���̸� �ø���
    f.resize(n);
    g.resize(n);

    // ������ ���׽��� FFT ��ȯ�Ѵ�
    fft(f, false);
    fft(g, false);

    // FFT�� ���鳢�� ���Ѵ�
    for (int i = 0; i < n; i++) {
        f[i] *= g[i];
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

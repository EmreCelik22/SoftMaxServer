import java.util.Random;

class SoftmaxYukDengeleyici {
    private int sunucuSayisi;
    private double sicaklik;        // Algoritmadaki 'tau' parametresi
    private double ogrenmeOrani;    // Yeni verinin ne kadar baskın olacağını belirler (alpha)
    private double[] qDegerleri;    // Sunucuların beklenen performans puanları
    private Random rastgele;

    public SoftmaxYukDengeleyici(int sunucuSayisi, double sicaklik, double ogrenmeOrani) {
        this.sunucuSayisi = sunucuSayisi;
        this.sicaklik = sicaklik;
        this.ogrenmeOrani = ogrenmeOrani;
        this.qDegerleri = new double[sunucuSayisi];
        this.rastgele = new Random();
    }

    public int sunucuSec() {
        double[] olcekliQ = new double[sunucuSayisi];
        double enBuyukQ = Double.NEGATIVE_INFINITY;

        // 1. NÜMERİK STABİLİTE: Taşmayı önlemek için en büyük değeri bul
        for (int i = 0; i < sunucuSayisi; i++) {
            olcekliQ[i] = qDegerleri[i] / sicaklik;
            if (olcekliQ[i] > enBuyukQ) {
                enBuyukQ = olcekliQ[i];
            }
        }

        // 2. Softmax Olasılıklarını Hesapla
        double[] olasiliklar = new double[sunucuSayisi];
        double toplamUst = 0.0;
        for (int i = 0; i < sunucuSayisi; i++) {
            // Formülden en büyük değeri çıkararak kararlılık sağlıyoruz
            double ustDeger = Math.exp(olcekliQ[i] - enBuyukQ);
            olasiliklar[i] = ustDeger;
            toplamUst += ustDeger;
        }

        // Olasılıkları normalize et (toplamları 1 olacak şekilde)
        for (int i = 0; i < sunucuSayisi; i++) {
            olasiliklar[i] /= toplamUst;
        }

        // 3. Rulet Tekerleği ile Seçim Yap
        double rastgeleSayi = rastgele.nextDouble();
        double kumulatifOlasilik = 0.0;

        for (int i = 0; i < sunucuSayisi; i++) {
            kumulatifOlasilik += olasiliklar[i];
            if (rastgeleSayi <= kumulatifOlasilik) {
                return i;
            }
        }

        return sunucuSayisi - 1;
    }

    public void guncelle(int secilenSunucu, double gecikme) {
        // Ödül (Reward) gecikmenin negatifidir: Düşük gecikme = Yüksek ödül
        double odul = -gecikme;

        // Sabit adım boyu güncelleme kuralı (Non-stationary durumlar için ideal)
        qDegerleri[secilenSunucu] += ogrenmeOrani * (odul - qDegerleri[secilenSunucu]);
    }

    public double[] qDegerleriniGetir() {
        return qDegerleri;
    }
}
import java.util.Random;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        int K = 5;      // Sunucu sayısı
        int T = 1000;   // Toplam istek sayısı
        Random rastgele = new Random();

        // Sunucuları oluştur
        DegiskenSunucu[] sunucular = new DegiskenSunucu[K];
        for (int i = 0; i < K; i++) {
            sunucular[i] = new DegiskenSunucu(0.5 + (1.5 * rastgele.nextDouble()));
        }

        // Yük dengeleyiciyi başlat
        SoftmaxYukDengeleyici dengeleyici = new SoftmaxYukDengeleyici(K, 0.2, 0.15);

        double toplamGecikme = 0.0;

        // İstek dağıtımını başlat
        for (int t = 0; t < T; t++) {
            int secilenIndis = dengeleyici.sunucuSec();
            double gecikmeSuresi = sunucular[secilenIndis].gecikmeSuresiGetir();

            toplamGecikme += gecikmeSuresi;
            dengeleyici.guncelle(secilenIndis, gecikmeSuresi);
        }

        // Sonuç raporu
        System.out.println("--- Simülasyon Tamamlandı ---");
        System.out.println("Toplam İstek Sayısı: " + T);
        System.out.printf("Ortalama Gecikme Süresi: %.4f sn\n", (toplamGecikme / T));
        System.out.println("Sunucu Başarı Puanları (Q Değerleri):");
        System.out.println(Arrays.toString(dengeleyici.qDegerleriniGetir()));
    }
}
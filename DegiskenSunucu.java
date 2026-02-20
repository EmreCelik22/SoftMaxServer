import java.util.Random;

class DegiskenSunucu {
    private double ortalamaGecikme;
    private Random rastgele;

    public DegiskenSunucu(double baslangicGecikmesi) {
        this.ortalamaGecikme = baslangicGecikmesi;
        this.rastgele = new Random();
    }

    public double gecikmeSuresiGetir() {
        // Rastgele Yürüyüş (Random Walk): Sunucu performansı zamanla kayar
        this.ortalamaGecikme += rastgele.nextGaussian() * 0.02;

        // Anlık gürültü: Her istekte oluşan küçük dalgalanmalar
        double anlikGecikme = this.ortalamaGecikme + rastgele.nextGaussian() * 0.1;

        // Gecikme süresi fiziksel olarak 0'dan küçük olamaz
        return Math.max(0.01, anlikGecikme);
    }
}


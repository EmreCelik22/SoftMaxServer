# Olasılıksal Yük Dengeleyici (Softmax Client-Side Load Balancer)

Bu proje, performansları zamanla değişen (non-stationary) ve gürültülü (noisy) bir sunucu kümesine (cluster) gelen istekleri dağıtmak için tasarlanmış istemci taraflı bir yük dengeleyici simülasyonudur. Nesne Yönelimli Programlama (OOP) prensipleriyle Java dilinde geliştirilmiştir.

Klasik *Round-Robin* veya *Random* algoritmalarının aksine, bu projede Pekiştirmeli Öğrenme (Reinforcement Learning) tabanlı **Softmax Action Selection** algoritması kullanılarak sistemin toplam bekleme süresi (latency) minimize edilmiştir.

##  Projenin Amacı ve Yaklaşımı

Dağıtık sistemlerde sunucuların yanıt süreleri sabit değildir. Ağ yoğunluğu veya anlık işlemci yükleri nedeniyle performanslar sürekli dalgalanır. Bu projede, sunucuların bu değişken durumu bir Çok Kollu Canavar (Multi-Armed Bandit) problemi olarak modellenmiştir.

* **Round-Robin / Random Yetersizliği:** Klasik algoritmalar sunucuların o anki gecikme sürelerini dikkate almaz. Yavaşlayan bir sunucuya ısrarla yük göndermeye devam edebilirler.
* **Softmax Çözümü:** Sistem, her sunucunun geçmiş performansına dayalı bir başarı puanı ($Q$ değeri) tutar. Yavaşlayan sunucunun seçilme olasılığı anında düşürülür ancak sıfırlanmaz (Keşif ve Sömürü - Exploration vs. Exploitation dengesi).

##  Algoritma ve Matematiksel Altyapı



### 1. Softmax Olasılık Hesaplaması ve Nümerik Stabilite
Her bir sunucunun seçilme olasılığı Softmax fonksiyonu ile hesaplanır. Ancak $e^x$ (üstel) fonksiyonu bilgisayar hafızasında hızlıca büyüyeceği için (Overflow/NaN hatası), **Nümerik Stabilite** sağlanmıştır. En büyük $Q$ değeri, işleme girmeden önce tüm değerlerden çıkarılır.

### 2. Sabit Adım Boyu (Constant Step-Size) Güncellemesi
Ortam "Non-stationary" (zamanla değişen) olduğu için, eski verilerin ağırlığını zamanla azaltıp yeni tecrübelere odaklanmak amacıyla basit ortalama yerine Sabit Öğrenme Oranı alpha kullanılmıştır.


##  Zaman Karmaşıklığı (Runtime Analysis)

Sisteme gelen tek bir isteğin işlenme maliyeti son derece düşüktür ve gerçek zamanlı sistemler için uygundur:
* **Sunucu Seçimi:** Olasılık hesaplama ve Rulet Tekerleği (Roulette Wheel) seçimi $O(K)$ zaman alır ($K$ = Sunucu sayısı).
* **İstek ve Güncelleme:** Seçilen sunucuya istek atma ve dönen sonuca göre Q değerini güncelleme işlemi $O(1)$ zaman alır.
* **Toplam Karmaşıklık:** **$O(K)$**


##  Kurulum ve Çalıştırma

Projenin çalışması için bilgisayarınızda Java yüklü olması yeterlidir. Herhangi bir harici kütüphane (dependency) gerektirmez.


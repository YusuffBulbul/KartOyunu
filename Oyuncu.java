import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.awt.Color;
import javax.swing.*; // JFrame, JPanel, JButton, JLabel ve diğer Swing bileşenleri için
import java.awt.*;    // Layout yöneticileri, Color, Dimension ve Graphics sınıfları için
import java.awt.event.*;


public class Oyuncu {

    // Özellikler
    private int oyuncuID;
    private String oyuncuAdi;
    private int skor;
    private List<String> kartListesi; // Kart listesi, kartları tutmak için bir liste
    private List<Integer> dayaniklilikListesi; // Kartların dayanıklılık değerlerini tutan liste
    private List<Integer> seviyePuaniListesi;
    private List<Integer> kullanilmaListesi;
    private List<String> secilenKartlar; // Seçilen kartları tutacak liste

    // Parametresiz yapıcı metot
    public Oyuncu() {
        this.oyuncuID = 0;
        this.oyuncuAdi = "Bilinmeyen";
        this.skor = 0;
        this.kartListesi = new ArrayList<>();
        this.dayaniklilikListesi = new ArrayList<>();
        this.seviyePuaniListesi = new ArrayList<>();
        this.kullanilmaListesi = new ArrayList<>();
        this.secilenKartlar = new ArrayList<>();
    }

    // Parametreli yapıcı metot
    public Oyuncu(int oyuncuID, String oyuncuAdi, int skor) {
        this.oyuncuID = oyuncuID;
        this.oyuncuAdi = oyuncuAdi;
        this.skor = skor;
        this.kartListesi = new ArrayList<>();
        this.dayaniklilikListesi = new ArrayList<>();
        this.seviyePuaniListesi = new ArrayList<>();
        this.kullanilmaListesi = new ArrayList<>();
        this.secilenKartlar = new ArrayList<>();
    }

    // Getter ve Setter metotları
    public int getOyuncuID() {
        return oyuncuID;
    }

    public void setOyuncuID(int oyuncuID) {
        this.oyuncuID = oyuncuID;
    }

    public String getOyuncuAdi() {
        return oyuncuAdi;
    }

    public void setOyuncuAdi(String oyuncuAdi) {
        this.oyuncuAdi = oyuncuAdi;
    }

    public int getSkor() {
        return skor;
    }

    public void setSkor(int skor) {
        this.skor = skor;
    }

    public List<String> getKartListesi() {
        return kartListesi;
    }

    public void setKartListesi(List<String> kartListesi) {
        this.kartListesi = kartListesi;
    }
    public List<Integer> getDayaniklilikListesi() {
        return dayaniklilikListesi;
    }   
    public void setDayaniklilikListesi(List<Integer> dayaniklilikListesi) {
        this.dayaniklilikListesi = dayaniklilikListesi;
    }

    public List<Integer> getSeviyePuaniListesi() {
        return seviyePuaniListesi;
    }
    public void setSeviyePuaniListesi(List<Integer> seviyePuaniListesi) {
        this.seviyePuaniListesi = seviyePuaniListesi;
    }
    public List<Integer> getKullanilmaListesi() {
        return kullanilmaListesi;
    }
    public void setKullanilmaListesi(List<Integer> kullanilmaListesi) {
        this.kullanilmaListesi = kullanilmaListesi;
    }
    public List<String> getSecilenKartlar() {
        return secilenKartlar;
    }
    public String getSecilenKartlar(int index) {
        return secilenKartlar.get(index);
    }
    public void setSecilenKartlar(List<String> secilenKartlar) {
        this.secilenKartlar = secilenKartlar;
    }

    
    public void kartUret() {
        String[] kartHavuzu = {"Ucak", "Obus", "Firkateyn"}; // Kart havuzu
        int[] baslangicDayaniklilik = {20, 20, 25}; // Her kart türü için başlangıç dayanıklılık değerleri Sınıflardan çağırılak yapılacak SONRA
        Random rastgele = new Random();
        int[] kartSayaclari = new int[kartHavuzu.length]; // Kart türü sayacı

        for (int i = 0; i < 6; i++) {
            int rastgeleIndex = rastgele.nextInt(kartHavuzu.length); // Rastgele bir indeks seç
            kartSayaclari[rastgeleIndex]++; // Seçilen kartın sayacını artır
            String kart = kartHavuzu[rastgeleIndex] + kartSayaclari[rastgeleIndex]; // Kart adını oluştur
            kartListesi.add(kart); // Kartı listeye ekle
            dayaniklilikListesi.add(baslangicDayaniklilik[rastgeleIndex]); // Dayanıklılık değerini ekle
            seviyePuaniListesi.add(0); // Seviye puanını 0 olarak ekle
            kullanilmaListesi.add(0);
        }

        System.out.println(oyuncuAdi + " icin olusturulan kartlar: " + kartListesi);
        System.out.println("Baslangic Dayaniklilik: " + dayaniklilikListesi);
        System.out.println("Baslangic Seviye Puanlari: " + seviyePuaniListesi);
        System.out.println("Kullanilma Listesi: " + kullanilmaListesi);

        
    }

    public void kartUret(int skor,Oyuncu oyuncu,Oyuncu bilgisayar) {
        // Kart havuzu ve dayanıklılık değerlerini skor durumuna göre belirle
        String[] kartHavuzu;
        int[] baslangicDayaniklilik;
    
        if (skor < 20) {
            // Skor 20'nin altında ise basit kartlar
            kartHavuzu = new String[]{"Ucak", "Obus", "Firkateyn"};
            baslangicDayaniklilik = new int[]{20, 20, 25};
        } else {
            // Skor 20 veya üzerinde ise gelişmiş kartlar dahil
            kartHavuzu = new String[]{"Ucak", "Obus", "Firkateyn", "Siha", "Sida", "KFS"};
            baslangicDayaniklilik = new int[]{20, 20, 25, 15, 15, 10};
        }
    
        Random rastgele = new Random();
        int rastgeleIndex = rastgele.nextInt(kartHavuzu.length); // Rastgele bir kart seç
    
        String kartTuru = kartHavuzu[rastgeleIndex];
    
        // Mevcut listede aynı türdeki kartların sayısını hesapla
        long mevcutKartSayisi = kartListesi.stream()
                .filter(kart -> kart.startsWith(kartTuru))
                .count();
    
        // Yeni kart ismini oluştur
        String yeniKart = kartTuru + (mevcutKartSayisi + 1);
    
        // Kartı listelere ekle
        kartListesi.add(yeniKart); // Kartı listeye ekle
        dayaniklilikListesi.add(baslangicDayaniklilik[rastgeleIndex]); // Dayanıklılığı ekle
        seviyePuaniListesi.add(0); // Seviye puanını 0 olarak ekle
        kullanilmaListesi.add(0); // Kullanım sayısını 0 olarak ekle
    
        // Kart üretildiğini gösteren çıktı
        System.out.println("Yeni Kart Üretildi: " + yeniKart);
        System.out.println("Mevcut Kartlar: " + kartListesi);
    }
    
    
    // SkorGoster fonksiyonu
    public void SkorGoster() {
        // Toplam skor gösterme
    }

    // kartSec fonksiyonu
    public void kartSec()
    {
        secilenKartlar.clear();
        if (oyuncuAdi.equals("Bilgisayar")) {
            Random rastgele = new Random();
            List<String> ayniMiKontrol = new ArrayList<>(); // Her tur için benzersiz kart kontrol listesi
        
            // Bilgisayarın 3 kart seçmesi için döngü
            for (int i = 0; i < 3; i++) {
                List<Integer> uygunKartIndexleri = new ArrayList<>();
        
                // Hiç kullanılmamış kartları bul
                for (int j = 0; j < kullanilmaListesi.size(); j++) {
                    if ((kullanilmaListesi.get(j) == 0 && !secilenKartlar.contains(kartListesi.get(j))) 
                        || (kartListesi.containsAll(secilenKartlar) && secilenKartlar.containsAll(kartListesi))) {
                        uygunKartIndexleri.add(j);
                    }
                }
        
                int secimIndex;
        
                if (!uygunKartIndexleri.isEmpty()) {
                    // Eğer hiç kullanılmamış kartlar varsa, bunlardan rastgele birini seç
                    while (true) {
                        secimIndex = uygunKartIndexleri.get(rastgele.nextInt(uygunKartIndexleri.size()));
                        // *** Aynı kartın seçilmesini önlemek için kontrol ***
                        if (!ayniMiKontrol.contains(kartListesi.get(secimIndex))) {
                            break;
                        }
                    }
                } else {
                    // Eğer tüm kartlar en az bir kez kullanılmışsa, rastgele istediği kartı seçebilir
                    while (true) {
                        secimIndex = rastgele.nextInt(kartListesi.size());
                        // *** Aynı kartın seçilmesini önlemek için kontrol ***
                        if (!ayniMiKontrol.contains(kartListesi.get(secimIndex))) {
                            break;
                        }
                    }
                }
        
                // Seçilen kartı secilenKartlar ve ayniMiKontrol listelerine ekle
                ayniMiKontrol.add(kartListesi.get(secimIndex)); // Bu tur için kontrol listesine ekle
                secilenKartlar.add(kartListesi.get(secimIndex)); // Genel secilenKartlar listesine ekle
                kullanilmaListesi.set(secimIndex, kullanilmaListesi.get(secimIndex) + 1); // Kullanım sayısını artır
            }
        
            // Seçilen kartları yazdır
            System.out.println("Bilgisayarin sectigi kartlar: " + ayniMiKontrol);
        
            // *** Listeyi temizle ***
            ayniMiKontrol.clear(); // Her tur sonunda listeyi temizle
    }
}

public int kartSec(List<JButton> butonlar, JFrame pencere, Oyuncu oyuncu) {
    oyuncu.secilenKartlar.clear(); // Seçilen kartlar listesini temizle

    // Seçimin tamamlanıp tamamlanmadığını izlemek için bayrak
    final boolean[] isSelectionComplete = {false};

    // Her butona tıklama olayı ekle
    for (JButton buton : butonlar) {
        buton.addActionListener(e -> {
            if (oyuncu.secilenKartlar.size() < 3) {
                oyuncu.secilenKartlar.add(buton.getText());
                System.out.println("Seçilen kart: " + buton.getText());
                buton.setEnabled(false); // Seçilen kartı devre dışı bırak
                buton.setBackground(Color.GREEN); // Seçilen kartı işaretle

                // Tüm seçimler tamamlandıysa bayrağı true yap
                if (oyuncu.secilenKartlar.size() == 3) {
                    isSelectionComplete[0] = true;
                }
            }
        });
    }
    // Enter tuşu dinleyicisi
    pencere.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (oyuncu.secilenKartlar.size() == 3) {
                    isSelectionComplete[0] = true; // Seçim tamamlandı
                } else {
                    JOptionPane.showMessageDialog(pencere, "Lütfen 3 kart seçiniz!", "Eksik Seçim", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    });

    // Pencerenin odak almasını sağla
    pencere.requestFocus();

    // Kullanıcı seçim tamamlanana kadar bekle
    while (!isSelectionComplete[0]) {
        try {
            Thread.sleep(100); // Kısa bir süre bekle
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    // Seçim tamamlandıktan sonra 1 döndür
    return 1;
}




    
}
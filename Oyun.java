import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
public class Oyun {

    // Özellikler
    private Oyuncu oyuncu;
    private Oyuncu bilgisayar;

    // Yapıcı (Constructor) metot
    public Oyun() {
        // Oyuncu ve Bilgisayar nesnelerinin oluşturulması
        oyuncu = new Oyuncu(1, "Oyuncu", 0);
        bilgisayar = new Oyuncu(2, "Bilgisayar", 0);
    }
    
    // Getter ve Setter metotları
    public Oyuncu getOyuncu() {
        return oyuncu;
    }

    public void setOyuncu(Oyuncu oyuncu) {
        this.oyuncu = oyuncu;
    }

    public Oyuncu getBilgisayar() {
        return bilgisayar;
    }

    public void setBilgisayar(Oyuncu bilgisayar) {
        this.bilgisayar = bilgisayar;
    }
    
    // SaldiriHesapla fonksiyonu
    public static int SaldiriHesapla(String oyuncuKarti, String bilgisayarKarti, Oyuncu hedefOyuncu) {
        // Örnek sınıflar (Ucak, Obus, Firkateyn, Siha, Sida, KFS)
        Ucak ucak = new Ucak(0, "Ucak", 10, 20, "Hava", 10);
        Obus obus = new Obus(0, "Obus", 5, 20, "Kara", 10);
        Firkateyn firkateyn = new Firkateyn(0, "Firkateyn", 5, 25, "Deniz", 10);
        Siha siha = new Siha(0, "Siha", 10, 15, "Hava", 10,10);
        Sida sida = new Sida(0, "Sida", 10, 15, "Deniz", 10,10);
        KFS kfs = new KFS(0, "KFS", 10, 10, "Kara", 10,20);
    
        // Kartların sınıflarını belirleme
        String hedefKart = (hedefOyuncu.getOyuncuAdi().equals("Oyuncu")) ? oyuncuKarti : bilgisayarKarti;
        String rakipKart = (hedefOyuncu.getOyuncuAdi().equals("Oyuncu")) ? bilgisayarKarti : oyuncuKarti;
    
        String hedefKartSinif = hedefKart.replaceAll("\\d", ""); // Örneğin "Ucak1" -> "Ucak"
        String rakipKartSinif = rakipKart.replaceAll("\\d", ""); // Örneğin "Obus2" -> "Obus"
    
        // Hedef kartın vuruş değerini belirleme
        int hedefVurus = 0;
        if (hedefKartSinif.equals("Ucak")) {
            hedefVurus = ucak.getVurus();
        } else if (hedefKartSinif.equals("Obus")) {
            hedefVurus = obus.getVurus();
        } else if (hedefKartSinif.equals("Firkateyn")) {
            hedefVurus = firkateyn.getVurus();
        } else if (hedefKartSinif.equals("Siha")) {
            hedefVurus = siha.getVurus();
        } else if (hedefKartSinif.equals("Sida")) {
            hedefVurus = sida.getVurus();
        } else if (hedefKartSinif.equals("KFS")) {
            hedefVurus = kfs.getVurus();
        }
    
        // Hedef kart avantaj kontrolü
        if (hedefKartSinif.equals("Ucak")) {
            if (rakipKartSinif.equals("Obus") || rakipKartSinif.equals("KFS") ) {
                hedefVurus += ucak.getKaraVurusAvantaji();
            }
        } else if (hedefKartSinif.equals("Obus")) {
            if (rakipKartSinif.equals("Firkateyn") || rakipKartSinif.equals("Sida") ) {
                hedefVurus += obus.getDenizVurusAvantaji();
            }
        } else if (hedefKartSinif.equals("Firkateyn")) {
            if (rakipKartSinif.equals("Ucak") || rakipKartSinif.equals("Siha")) {
                hedefVurus += firkateyn.getHavaVurusAvantaji();
            }
        } else if (hedefKartSinif.equals("Siha")) {
            if (rakipKartSinif.equals("Obus") || rakipKartSinif.equals("KFS")) {
                hedefVurus += siha.getKaraVurusAvantaji(); // Siha Kara veya Deniz'e avantajlı
            }
            if(rakipKartSinif.equals("Firkateyn") || rakipKartSinif.equals("Sida"))
            {
                hedefVurus += siha.getDenizVurusAvantaji();
            }
        } else if (hedefKartSinif.equals("Sida")) {
            if (rakipKartSinif.equals("Obus") || rakipKartSinif.equals("KFS")) {
                hedefVurus += sida.getKaraVurusAvantaji(); // Siha Kara veya Deniz'e avantajlı
            }
            if(rakipKartSinif.equals("Ucak") || rakipKartSinif.equals("Siha"))
            {
                hedefVurus += sida.getHavaVurusAvantaji();
            }
        } else if (hedefKartSinif.equals("KFS")) {
            if (rakipKartSinif.equals("Ucak") || rakipKartSinif.equals("Siha")) {
                hedefVurus += kfs.getHavaVurusAvantaji(); // Siha Kara veya Deniz'e avantajlı
            }
            if(rakipKartSinif.equals("Firkateyn") || rakipKartSinif.equals("Sida"))
            {
                hedefVurus += kfs.getDenizVurusAvantaji();
            }
        }
    
        // Sonuç yazdırma (isteğe bağlı)
        System.out.println(hedefOyuncu.getOyuncuAdi() + " Karti: " + hedefKart + " -> Vurus Degeri: " + hedefVurus);
        System.out.println("Rakip Karti: " + rakipKart);
    
        // Hedef oyuncunun vuruş değeri döndürülür
        return hedefVurus;
    }
    

    public static Object KartObjesiOlustur(String kartAdi) {
        System.out.println("***************");
        System.out.println("Kart adimiz: "+kartAdi);
        String kartSinif = kartAdi.replaceAll("\\d", ""); // Kart türünü ayırır (örneğin: "Ucak1" -> "Ucak")
        System.out.println("Son sayisi atilmis hali: "+kartSinif);
        System.out.println("***************");
        switch (kartSinif) {
            case "Ucak":
                return new Ucak(0, "Ucak", 10, 20, "Hava", 10);
            case "Obus":
                return new Obus(0, "Obus", 5, 20, "Kara", 10);
            case "Firkateyn":
                return new Firkateyn(0, "Firkateyn", 5, 25, "Deniz", 10);
            case "Siha":
                return new Siha(0, "Siha", 10, 15, "Hava", 10, 10);
            case "Sida":
                return new Sida(0, "Sida", 10, 15, "Deniz", 10, 10);
            case "KFS":
                return new KFS(0, "KFS", 10, 10, "Kara", 10, 20);
            default:
                System.out.println("HATA: Bilinmeyen kart türü: " + kartSinif);
                return null;
        }
    }
    
    private static JFrame pencere;
    private JPanel merkezPanel;
    private JPanel bilgisayarKartPaneli;
    private JPanel oyuncuKartPaneli;

    // Oyunun başlangıcındaki görselleştirme
    public void gorselBaslat(List<String> bilgisayarKartlar, List<String> oyuncuKartlar, List<JButton> butonlar) {
    
        // Yeni pencereyi oluştur
        pencere = new JFrame("Kart Oyunu");
        pencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pencere.setLayout(new BorderLayout());
    
        // Tam ekran boyutu ayarla
        pencere.setExtendedState(JFrame.MAXIMIZED_BOTH); // Tam ekran mod
        pencere.setUndecorated(false); // Başlık çubuğu ve pencere sınırları korunur
    
        // Bilgisayar kartları paneli
        bilgisayarKartPaneli = new JPanel();
        bilgisayarKartPaneli.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10)); // Yatay ve dikey boşluk artırıldı
        pencere.add(bilgisayarKartPaneli, BorderLayout.NORTH);
    
        // Oyuncu kartları paneli
        oyuncuKartPaneli = new JPanel();
        oyuncuKartPaneli.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10)); // Yatay ve dikey boşluk artırıldı
        pencere.add(oyuncuKartPaneli, BorderLayout.SOUTH);
    
        // Ortadaki panel (Seçilen kartlar için)
        merkezPanel = new JPanel();
        merkezPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20)); // Yatay ve dikey boşluk artırıldı
        pencere.add(merkezPanel, BorderLayout.CENTER);
    
        // Dinamik genişlik hesaplama
        int kartSayisi = Math.max(bilgisayarKartlar.size(), oyuncuKartlar.size()); // Kart sayısını belirle
        int kartWidth = 1200 / kartSayisi; // Dinamik genişlik (örneğin, toplam genişlik 1200 px'e bölünür)
        kartWidth = Math.max(kartWidth, 100); // Minimum genişlik 100 px olmalı
        kartWidth = Math.min(kartWidth, 200); // Maksimum genişlik 200 px olmalı
        int kartHeight = 250; // Sabit yükseklik
    
        // Bilgisayar kartlarını kapalı olarak göster
        for (String kart : bilgisayarKartlar) {
            JLabel kapaliKart = new JLabel("Kapalı Kart");
            kapaliKart.setOpaque(true);
            kapaliKart.setBackground(Color.GRAY);
            kapaliKart.setPreferredSize(new Dimension(kartWidth, kartHeight)); // Dinamik boyutlar
            kapaliKart.setHorizontalAlignment(SwingConstants.CENTER);
            kapaliKart.setVerticalAlignment(SwingConstants.CENTER);
            bilgisayarKartPaneli.add(kapaliKart);
        }
    
        // Oyuncu kartlarını açık olarak buton şeklinde göster
        for (String kart : oyuncuKartlar) {
            JButton kartButonu = new JButton(kart);
            kartButonu.setOpaque(true);
            kartButonu.setBackground(Color.CYAN);
            kartButonu.setPreferredSize(new Dimension(kartWidth, kartHeight)); // Dinamik boyutlar
            butonlar.add(kartButonu); // Butonu listeye ekle
            oyuncuKartPaneli.add(kartButonu);
        }
    
        pencere.setVisible(true); // Yeni pencereyi görünür yap
    }
    
    
    
    
    public void durumGuncelle(List<String> bilgisayarSecilenKartlar, List<String> oyuncuSecilenKartlar) {
    
        // Yeni bir pencere oluştur
        JFrame yeniPencere = new JFrame("Seçilen Kartlar");
        yeniPencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        yeniPencere.setLayout(new BorderLayout());
    
        // Pencere boyutunu tam ekran yap
        yeniPencere.setExtendedState(JFrame.MAXIMIZED_BOTH);
    
        // Üst panel: Bilgisayar kartları
        JPanel bilgisayarPaneli = new JPanel();
        bilgisayarPaneli.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20)); // Yatay ve dikey boşluklar
        for (String kart : bilgisayarSecilenKartlar) {
            JLabel bilgisayarKartLabel = new JLabel(kart);
            bilgisayarKartLabel.setOpaque(true);
            bilgisayarKartLabel.setBackground(Color.GRAY);
            bilgisayarKartLabel.setPreferredSize(new Dimension(200, 350)); // Kart boyutları
            bilgisayarKartLabel.setHorizontalAlignment(SwingConstants.CENTER);
            bilgisayarKartLabel.setVerticalAlignment(SwingConstants.CENTER);
            bilgisayarKartLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Siyah çerçeve
            bilgisayarPaneli.add(bilgisayarKartLabel);
        }
    
        // Alt panel: Oyuncu kartları
        JPanel oyuncuPaneli = new JPanel();
        oyuncuPaneli.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20)); // Yatay ve dikey boşluklar
        for (String kart : oyuncuSecilenKartlar) {
            JLabel oyuncuKartLabel = new JLabel(kart);
            oyuncuKartLabel.setOpaque(true);
            oyuncuKartLabel.setBackground(Color.CYAN);
            oyuncuKartLabel.setPreferredSize(new Dimension(200, 350)); // Kart boyutları
            oyuncuKartLabel.setHorizontalAlignment(SwingConstants.CENTER);
            oyuncuKartLabel.setVerticalAlignment(SwingConstants.CENTER);
            oyuncuKartLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Siyah çerçeve
            oyuncuPaneli.add(oyuncuKartLabel);
        }
    
        // Çizgi ile ayırıcı panel
        JPanel ayiriciPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, getHeight() / 2 - 2, getWidth(), 4); // Yatay çizgi
            }
        };
        ayiriciPanel.setPreferredSize(new Dimension(0, 10));
    
        // Panelleri pencereye ekle
        yeniPencere.add(bilgisayarPaneli, BorderLayout.NORTH); // Bilgisayar kartları üstte
        yeniPencere.add(ayiriciPanel, BorderLayout.CENTER);   // Çizgi ortada
        yeniPencere.add(oyuncuPaneli, BorderLayout.SOUTH);    // Oyuncu kartları altta
    
        // KeyListener ekle
        yeniPencere.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { // Enter tuşu
                    yeniPencere.dispose(); // Pencereyi kapat
                }
            }
        });
    
        // Pencereyi görünür yap
        yeniPencere.setVisible(true);
    
        // KeyListener'ın çalışabilmesi için pencerenin odak almasını sağla
        yeniPencere.requestFocus();
    }
    
    
    
    
    
    
    
    
    
    

    // main metodu
    public static void main(String[] args) {
        Oyun oyun = new Oyun();
        List<JButton> butonlar = new ArrayList<>();
        JFrame frame = new JFrame();
            frame.setSize(1, 1);
            frame.setLocation(200, 200); // Çerçeveyi ekranın sol tarafına taşı
            frame.setUndecorated(true); // Çerçevenin başlık çubuğunu kaldır
            frame.setVisible(true);
    
        // Kartları üret
        oyun.bilgisayar.kartUret();
        oyun.oyuncu.kartUret();
        
        int istisnadurum=0;
        for (int i = 0; i < 5; i++) { // 5 tur için
            

// İlgili yerin düzenlenmiş hali
            if (i != 0) {
            oyun.oyuncu.kartUret(oyun.oyuncu.getSkor(), oyun.oyuncu, oyun.bilgisayar);
            oyun.bilgisayar.kartUret(oyun.bilgisayar.getSkor(), oyun.oyuncu, oyun.bilgisayar);
            System.out.println("Eklenen Kartli Liste Oyuncu: " + oyun.oyuncu.getKartListesi());
            System.out.println("Eklenen Kartli Liste Bilgisayar: " + oyun.bilgisayar.getKartListesi());

            // Kullanıcıdan ENTER tuşuna basmasını bekle
            
            JOptionPane.showMessageDialog(frame, "Devam etmek için ENTER tuşuna basin.");

            System.out.println("ENTER tuşuna basildi, devam ediliyor...");
            }


            

            oyun.gorselBaslat(oyun.bilgisayar.getKartListesi(), oyun.oyuncu.getKartListesi(), butonlar);
            oyun.bilgisayar.kartSec();
        if (oyun.oyuncu.kartSec(butonlar, oyun.pencere,oyun.oyuncu) == 1) {
            pencere.dispose();
            oyun.durumGuncelle(oyun.bilgisayar.getSecilenKartlar(),oyun.oyuncu.getSecilenKartlar());
            
        }
        System.out.println("Oyuncu seçilen kartlar: " + oyun.oyuncu.getSecilenKartlar());
            for (int j = 0; j < 3; j++) { // Her turda 3 kart seçimi için
                String oyuncuKart = oyun.oyuncu.getSecilenKartlar(j); // Oyuncunun seçtiği kart
                String bilgisayarKart = oyun.bilgisayar.getSecilenKartlar(j); // Bilgisayarın seçtiği kart
            
                int oyuncuVurus = SaldiriHesapla(oyuncuKart, bilgisayarKart, oyun.oyuncu); // Oyuncu vuruş gücü
                int bilgisayarVurus = SaldiriHesapla(oyuncuKart, bilgisayarKart, oyun.bilgisayar); // Bilgisayar vuruş gücü
                
            
                Object oyuncuKartObjesi = KartObjesiOlustur(oyuncuKart);
                Object bilgisayarKartObjesi = KartObjesiOlustur(bilgisayarKart);
                
                // Oyuncunun kartını güncelle
                if (oyuncuKartObjesi instanceof Ucak) {
                    ((Ucak) oyuncuKartObjesi).DurumGuncelle(oyuncuKart, bilgisayarKart, bilgisayarVurus, oyun.oyuncu, oyun.bilgisayar, 1);
                } else if (oyuncuKartObjesi instanceof Obus) {
                    ((Obus) oyuncuKartObjesi).DurumGuncelle(oyuncuKart, bilgisayarKart, bilgisayarVurus, oyun.oyuncu, oyun.bilgisayar, 1);
                } else if (oyuncuKartObjesi instanceof Firkateyn) {
                    ((Firkateyn) oyuncuKartObjesi).DurumGuncelle(oyuncuKart, bilgisayarKart, bilgisayarVurus, oyun.oyuncu, oyun.bilgisayar, 1);
                } else if (oyuncuKartObjesi instanceof Siha) {
                    ((Siha) oyuncuKartObjesi).DurumGuncelle(oyuncuKart, bilgisayarKart, bilgisayarVurus, oyun.oyuncu, oyun.bilgisayar, 1);
                } else if (oyuncuKartObjesi instanceof Sida) {
                    ((Sida) oyuncuKartObjesi).DurumGuncelle(oyuncuKart, bilgisayarKart, bilgisayarVurus, oyun.oyuncu, oyun.bilgisayar, 1);
                } else if (oyuncuKartObjesi instanceof KFS) {
                    ((KFS) oyuncuKartObjesi).DurumGuncelle(oyuncuKart, bilgisayarKart, bilgisayarVurus, oyun.oyuncu, oyun.bilgisayar, 1);
                }
            
                // Bilgisayarın kartını güncelle
                if (bilgisayarKartObjesi instanceof Ucak) {
                    ((Ucak) bilgisayarKartObjesi).DurumGuncelle(bilgisayarKart, oyuncuKart, oyuncuVurus,oyun.oyuncu, oyun.bilgisayar);
                } else if (bilgisayarKartObjesi instanceof Obus) {
                    ((Obus) bilgisayarKartObjesi).DurumGuncelle(bilgisayarKart, oyuncuKart, oyuncuVurus, oyun.oyuncu, oyun.bilgisayar);
                } else if (bilgisayarKartObjesi instanceof Firkateyn) {
                    ((Firkateyn) bilgisayarKartObjesi).DurumGuncelle(bilgisayarKart, oyuncuKart, oyuncuVurus,oyun.oyuncu,oyun.bilgisayar);
                } else if (bilgisayarKartObjesi instanceof Siha) {
                    System.out.println("Siha Girdi*");
                    ((Siha) bilgisayarKartObjesi).DurumGuncelle(bilgisayarKart, oyuncuKart, oyuncuVurus, oyun.oyuncu, oyun.bilgisayar);
                } else if (bilgisayarKartObjesi instanceof Sida) {
                    System.out.println("Sida Girdi*");
                    ((Sida) bilgisayarKartObjesi).DurumGuncelle(bilgisayarKart, oyuncuKart, oyuncuVurus, oyun.oyuncu, oyun.bilgisayar);
                } else if (bilgisayarKartObjesi instanceof KFS) {
                    System.out.println("KFS Girdi*");
                    ((KFS) bilgisayarKartObjesi).DurumGuncelle(bilgisayarKart, oyuncuKart, oyuncuVurus, oyun.oyuncu, oyun.bilgisayar);
                }
                
             
                // Dayanıklılık Kontrolü
                int kartIndex = oyun.oyuncu.getDayaniklilikListesi().indexOf(0);
                if (oyun.oyuncu.getDayaniklilikListesi().contains(0)) {
                    
                    int eklenenSeviye = oyun.oyuncu.getSeviyePuaniListesi().get(kartIndex);
                    eklenenSeviye = Math.max(eklenenSeviye, 10); // Eğer kart seviyesi 10'dan küçükse en az 10 ekle
                    oyun.bilgisayar.setSkor(oyun.bilgisayar.getSkor() + eklenenSeviye);
                    System.out.println("Bilgisayar skoruna " + eklenenSeviye + " puan eklendi.");
                }
                int kartIndex2 = oyun.bilgisayar.getDayaniklilikListesi().indexOf(0);
                if (oyun.bilgisayar.getDayaniklilikListesi().contains(0)) {
                    
                    int eklenenSeviye = oyun.bilgisayar.getSeviyePuaniListesi().get(kartIndex2);
                    eklenenSeviye = Math.max(eklenenSeviye, 10); // Eğer kart seviyesi 10'dan küçükse en az 10 ekle
                    oyun.oyuncu.setSkor(oyun.oyuncu.getSkor() + eklenenSeviye);
                    System.out.println("Oyuncu skoruna " + eklenenSeviye + " puan eklendi.");
                }
                if (oyun.bilgisayar.getDayaniklilikListesi().contains(0)) 
                {
                oyun.bilgisayar.getDayaniklilikListesi().remove(kartIndex2);
                oyun.bilgisayar.getSeviyePuaniListesi().remove(kartIndex2);
                oyun.bilgisayar.getKullanilmaListesi().remove(kartIndex2);
                oyun.bilgisayar.getKartListesi().remove(kartIndex2);
                }
                if (oyun.oyuncu.getDayaniklilikListesi().contains(0))
                {
                oyun.oyuncu.getDayaniklilikListesi().remove(kartIndex);
                oyun.oyuncu.getSeviyePuaniListesi().remove(kartIndex);
                oyun.oyuncu.getKullanilmaListesi().remove(kartIndex);
                oyun.oyuncu.getKartListesi().remove(kartIndex);
                }  
            }
            if(istisnadurum == 1)
                {
                    break;
                }
                if(oyun.oyuncu.getKartListesi().size() == 0)
                {
                    System.out.println("-------------BILGISAYAR KAZANDI-------------");
                }
                if(oyun.bilgisayar.getKartListesi().size() == 0)
                {
                    System.out.println("-------------OYUNCU KAZANDI-------------");
                }
                if(oyun.oyuncu.getKartListesi().size() == 1)
                {
                    oyun.oyuncu.kartUret(oyun.oyuncu.getSkor(),oyun.oyuncu,oyun.bilgisayar);
                    istisnadurum=1;
                }
                if(oyun.bilgisayar.getKartListesi().size() == 1)
                {
                    oyun.bilgisayar.kartUret(oyun.bilgisayar.getSkor(),oyun.oyuncu,oyun.bilgisayar);
                    istisnadurum=1;
                }
            System.out.println("Oyuncu Kartlar Listesi: "+oyun.oyuncu.getKartListesi());
            System.out.println("Blgisayar Dayaniklilik Listesi: "+oyun.bilgisayar.getKartListesi());
    
            // Dayanıklılık durumlarını yazdır
            System.out.println("Oyuncu Dayaniklilik Listesi: " + oyun.oyuncu.getDayaniklilikListesi());
            System.out.println("Bilgisayar Dayaniklilik Listesi: " + oyun.bilgisayar.getDayaniklilikListesi());
            System.out.println("---------------------------------------");
            System.out.println("Oyuncu SeviyePuani Listesi: "+oyun.oyuncu.getSeviyePuaniListesi());
            System.out.println("Bilgisayar SeviyePuani Listesi: "+oyun.bilgisayar.getSeviyePuaniListesi());
            
            System.out.println("Oyuncu Skoru: "+oyun.oyuncu.getSkor());
            System.out.println("Bilgisayar Skoru: "+oyun.bilgisayar.getSkor());
            
        }
        JOptionPane.showMessageDialog(null, "Devam etmek için ENTER tuşuna basın.");
        if(oyun.oyuncu.getSkor()>oyun.bilgisayar.getSkor())
         {
            System.out.println("-------------OYUNCU KAZANDI-------------");
            System.out.println("Oyuncu Puani: "+oyun.oyuncu.getSkor());
         }
         else if(oyun.oyuncu.getSkor()<oyun.bilgisayar.getSkor())
         {
            System.out.println("-------------BILGISAYAR KAZANDI-------------");
            System.out.println("Bilgisayar Puani: "+oyun.bilgisayar.getSkor());
         }
         else
         {
            int oyuncuDayaniklilikToplam = oyun.oyuncu.getDayaniklilikListesi().stream().mapToInt(Integer::intValue).sum();
            int bilgisayarDayaniklilikToplam = oyun.bilgisayar.getDayaniklilikListesi().stream().mapToInt(Integer::intValue).sum();
            System.out.println("Oyuncu Dayaniklilik Toplami: "+oyuncuDayaniklilikToplam);
            System.out.println("Bilgisayar Dayaniklilik Toplami: "+bilgisayarDayaniklilikToplam);
            if(bilgisayarDayaniklilikToplam>oyuncuDayaniklilikToplam)
            {
                oyun.bilgisayar.setSkor(oyun.bilgisayar.getSkor()+(bilgisayarDayaniklilikToplam-oyuncuDayaniklilikToplam));
                System.out.println("-------------BILGISAYAR KAZANDI-------------");
                System.out.println("Bilgisayar Puani: "+oyun.bilgisayar.getSkor());
            }
            else
            {
                oyun.oyuncu.setSkor(oyun.oyuncu.getSkor()+(oyuncuDayaniklilikToplam-bilgisayarDayaniklilikToplam));
                System.out.println("-------------OYUNCU KAZANDI-------------");
                System.out.println("Oyuncu Puani: "+oyun.oyuncu.getSkor());
            }
         }
    }
    
    
}

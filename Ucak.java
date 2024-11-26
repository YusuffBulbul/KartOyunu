import java.util.ArrayList;
import java.util.List;

public class Ucak extends Hava {

    private String altsinif;
    private int karaVurusAvantaji;
    private int dayaniklilik;
    private String sinif;
    private int vurus;

    public Ucak(int seviyePuani, String altsinif, int karaVurusAvantaji, int dayaniklilik, String sinif, int vurus) {
        super(seviyePuani);
        this.altsinif = altsinif;
        this.karaVurusAvantaji = karaVurusAvantaji;
        this.dayaniklilik = dayaniklilik;
        this.sinif = sinif;
        this.vurus = vurus;
    }

    // Abstract metotların implementasyonu
    @Override
    protected String getAltsinif() {
        return altsinif;
    }

    @Override
    protected void setAltsinif(String altsinif) {
        this.altsinif = altsinif;
    }

    @Override
    protected int getKaraVurusAvantaji() {
        return karaVurusAvantaji;
    }

    @Override
    protected void setKaraVurusAvantaji(int karaVurusAvantaji) {
        this.karaVurusAvantaji = karaVurusAvantaji;
    }

    @Override
    public int getDayaniklilik() {
        return dayaniklilik;
    }

    @Override
    public void setDayaniklilik(int dayaniklilik) {
        this.dayaniklilik = dayaniklilik;
    }

    @Override
    public String getSinif() {
        return sinif;
    }

    @Override
    public void setSinif(String sinif) {
        this.sinif = sinif;
    }

    @Override
    public int getVurus() {
        return vurus;
    }

    @Override
    public void setVurus(int vurus) {
        this.vurus = vurus;
    }

    // KartPuaniGoster metodunun override edilmesi
    @Override
    public void KartPuaniGoster() {
        // Dayanıklılık ve seviye puani gösterimi yapacak
    }

    // DurumGuncelle metodunun override edilmesi
    
    @Override
    protected void DurumGuncelle(String kart, String karsiKart, int hasar, Oyuncu oyuncu, Oyuncu bilgisayar, int hedef) {
        // Geçici liste oluştur ve oyuncunun dayanıklılık listesini kopyala
        List<Integer> geciciDayaniklilikListesi = new ArrayList<>(oyuncu.getDayaniklilikListesi());
    
        System.out.println("Oyuncu Karti: " + kart);
        System.out.println("Oyuncu Kart Listesi: " + oyuncu.getKartListesi());
    
        // Kartın indeksini bul
        int kartIndex = oyuncu.getKartListesi().indexOf(kart);
    
        if (kartIndex < 0) {
            System.out.println("HATA: Oyuncu kartı bulunamadı! Kart: " + kart);
            return;
        }
    
        // Dayanıklılığı güncelle
        int mevcutDayaniklilik = geciciDayaniklilikListesi.get(kartIndex);
        int yeniDayaniklilik = mevcutDayaniklilik - hasar; // Negatif olabilir
        if(yeniDayaniklilik<0)
        {
            yeniDayaniklilik=0;
        }
        geciciDayaniklilikListesi.set(kartIndex, yeniDayaniklilik);
    
        // Geçici listeyi oyuncunun orijinal dayanıklılık listesine eşitle
        oyuncu.setDayaniklilikListesi(geciciDayaniklilikListesi);
        
    
        // Güncelleme bilgisini yazdır
        System.out.println("Oyuncu karti: " + kart + " dayanikliligi guncellendi: " + yeniDayaniklilik);
    
        // Eğer dayanıklılık 0 veya daha küçük ise seviye puanı işlemleri
        if (yeniDayaniklilik == 0) {
            // Kartın seviyesini oyuncunun SeviyePuaniListesi'nden al
            int kartSeviyesi = oyuncu.getSeviyePuaniListesi().get(kartIndex);
    
            // Karşı kartın indeksini bilgisayarın KartListesi'nden bul
            int karsiKartIndex = bilgisayar.getKartListesi().indexOf(karsiKart);
    
            if (karsiKartIndex >= 0) {
                // Bilgisayarın SeviyePuaniListesi'nde güncelleme yap
                int mevcutKarsiKartSeviyePuani = bilgisayar.getSeviyePuaniListesi().get(karsiKartIndex);
                int eklenenSeviye = (kartSeviyesi < 10) ? 10 : kartSeviyesi; // Kart seviyesi 10'dan düşükse 10 ekle
                int yeniKarsiKartSeviyePuani = mevcutKarsiKartSeviyePuani + eklenenSeviye;
                
                // Seviye puanını güncelle
                bilgisayar.getSeviyePuaniListesi().set(karsiKartIndex, yeniKarsiKartSeviyePuani);
    
                System.out.println("Bilgisayar karti: " + karsiKart + " seviyesine " + eklenenSeviye + " eklendi.");
            } else {
                System.out.println("HATA: Karsi kart bilgisayarin kart listesinde bulunamadi! Kart: " + karsiKart);
            }
            
        }
        
    }
    
    


    protected void DurumGuncelle(String kart, String karsiKart, int hasar, Oyuncu oyuncu, Oyuncu bilgisayar) {
        // Geçici liste oluştur ve bilgisayarın dayanıklılık listesini kopyala
        List<Integer> geciciDayaniklilikListesi2 = new ArrayList<>(bilgisayar.getDayaniklilikListesi());
    
        System.out.println("Bilgisayar Karti: " + kart);
        System.out.println("Bilgisayar Kart Listesi: " + bilgisayar.getKartListesi());
    
        // Kartın indeksini bul
        int kartIndex = bilgisayar.getKartListesi().indexOf(kart);
    
        if (kartIndex < 0) {
            System.out.println("HATA: Bilgisayar karti bulunamadi! Kart: " + kart);
            return;
        }
    
        // Dayanıklılığı güncelle
        int mevcutDayaniklilik = geciciDayaniklilikListesi2.get(kartIndex);
        int yeniDayaniklilik = mevcutDayaniklilik - hasar; // Negatif olabilir
        if(yeniDayaniklilik<0)
        {
            yeniDayaniklilik=0;
        }
        geciciDayaniklilikListesi2.set(kartIndex, yeniDayaniklilik);
    
        // Geçici listeyi bilgisayarın orijinal dayanıklılık listesine eşitle
        bilgisayar.setDayaniklilikListesi(geciciDayaniklilikListesi2);
        
    
        // Güncelleme bilgisini yazdır
        System.out.println("Bilgisayar karti: " + kart + " dayanikliligi güncellendi: " + yeniDayaniklilik);
    
        // Eğer dayanıklılık 0 veya daha küçük ise seviye puanı işlemleri
        if (yeniDayaniklilik == 0) {
            // Kartın seviyesini bilgisayarın SeviyePuaniListesi'nden al
            int kartSeviyesi = bilgisayar.getSeviyePuaniListesi().get(kartIndex);
    
            // Karşı kartın indeksini oyuncunun KartListesi'nden bul
            int karsiKartIndex = oyuncu.getKartListesi().indexOf(karsiKart);
    
            if (karsiKartIndex >= 0) {
                // Oyuncunun SeviyePuaniListesi'nde güncelleme yap
                int mevcutKarsiKartSeviyePuani = oyuncu.getSeviyePuaniListesi().get(karsiKartIndex);
                int eklenenSeviye = (kartSeviyesi < 10) ? 10 : kartSeviyesi; // Kart seviyesi 10'dan düşükse 10 ekle
                int yeniKarsiKartSeviyePuani = mevcutKarsiKartSeviyePuani + eklenenSeviye;
                // Seviye puanını güncelle
                oyuncu.getSeviyePuaniListesi().set(karsiKartIndex, yeniKarsiKartSeviyePuani);
    
                System.out.println("Oyuncu karti: " + karsiKart + " seviyesine " + eklenenSeviye + " eklendi.");
            } else {
                System.out.println("HATA: Karsi kart oyuncunun kart listesinde bulunamadi! Kart: " + karsiKart);
            }
            

        }
        
    }

}

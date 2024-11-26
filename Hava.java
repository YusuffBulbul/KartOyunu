public abstract class Hava extends SavasAraclari {

    protected abstract String getAltsinif();
    protected abstract void setAltsinif(String altsinif);
    protected abstract int getKaraVurusAvantaji();
    protected abstract void setKaraVurusAvantaji(int karaVurusAvantaji);

    public Hava(int seviyePuani) {
        super(seviyePuani);
    }

    @Override
    public void KartPuaniGoster() {
         // Metodun içeriği ileride doldurulacak
    }

    @Override
    protected abstract void DurumGuncelle(String kart,String karsiKart, int hasar, Oyuncu hedefOyuncu, Oyuncu rakipOyuncu, int hedef);
}

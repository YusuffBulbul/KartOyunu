public abstract class Kara extends SavasAraclari {

    protected abstract String getAltsinif();
    protected abstract void setAltsinif(String altsinif);
    protected abstract int getDenizVurusAvantaji();
    protected abstract void setDenizVurusAvantaji(int denizVurusAvantaji);

    public Kara(int seviyePuani) {
        super(seviyePuani);
    }

    @Override
    public void KartPuaniGoster() {
        // Metodun içeriği ileride doldurulacak
    }

    @Override
    protected abstract void DurumGuncelle(String kart,String karsiKart, int hasar, Oyuncu hedefOyuncu, Oyuncu rakipOyuncu, int hedef);
}

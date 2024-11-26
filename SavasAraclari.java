public abstract class SavasAraclari {

    protected int seviyePuani;
    protected abstract int getDayaniklilik();
    protected abstract void setDayaniklilik(int dayaniklilik);
    protected abstract String getSinif();
    protected abstract void setSinif(String sinif);
    protected abstract int getVurus();
    protected abstract void setVurus(int vurus);

    public SavasAraclari(int seviyePuani) {
        this.seviyePuani = seviyePuani;
    }

    public void KartPuaniGoster() {

   }

    protected abstract void DurumGuncelle(String kart,String karsiKart, int hasar, Oyuncu hedefOyuncu, Oyuncu rakipOyuncu, int hedef);
}

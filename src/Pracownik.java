import java.io.Serializable;

public class Pracownik implements Serializable {
protected int PESEL;
protected String nazwiskoImie;
protected String dataUrodzenia;
protected String stanowisko;
protected float pensja;
protected int staz;
protected float premia;

public Pracownik(int PESEL, String nazwiskoImie, String dataUrodzenia, String stanowisko, float pensja, int staz) {
    float premia = 0;  //poniżej 10 lat pracy brak premii.
    if (staz >= 10) { premia = (float) (pensja * 0.1); }
    if (staz >= 20) { premia = (float) (pensja * 0.2); }

    this.PESEL = PESEL;
    this.nazwiskoImie = nazwiskoImie;
    this.dataUrodzenia = dataUrodzenia;
    this.stanowisko = stanowisko;
    this.pensja = pensja;
    this.staz = staz;
    this.premia = premia;
}



}

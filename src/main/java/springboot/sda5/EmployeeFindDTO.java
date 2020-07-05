package springboot.sda5;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class EmployeeFindDTO {

    @NotEmpty
    private String imie;

    @NotEmpty
    private String nazwisko;


    private int zarobki;

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public int getZarobki() {
        return zarobki;
    }

    public void setZarobki(int zarobki) {
        this.zarobki = zarobki;
    }
}


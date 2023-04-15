import java.io.*; // Używam sporo do serializacji i deserializacji
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    protected static TwoWayCycledListWithSentinel<Pracownik> listaPracownikow = new TwoWayCycledListWithSentinel<>();
    protected static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
//        dodajPracownika(new Pracownik(1137533839, "Maciuk Kuba", "01.01.1990", "dyrektor", 9000, 30));
//        dodajPracownika(new Pracownik(1238574983, "Jackowska Klaudia", "01.01.1999", "sekretarka", 5000, 10));
//        dodajPracownika(new Pracownik(1384759603, "Ptak Michał", "01.01.2004", "goniec", 4000, 1));
//        dodajPracownika(new Pracownik(1492738405, "Kowalski Jan", "01.01.2000", "kierowca", 5000, 5));
//        dodajPracownika(new Pracownik(1592738405, "Kowalski Kuba", "01.01.2001", "kierowca", 5000, 6));
        menuWyboru();

    }

    public static void menuWyboru() {
        boolean running = true;
        int wybor;
        while (running) {
            System.out.println("""
                    0 - Wyjdź\s
                    1 - Utworzenie nowej bazy danych\s
                    2 - Odczyt bazy z pliku\s
                    3 - Wyświetlenie wszystkich rekordów\s
                    4 - Wyświetlenie danych jednego pracownika\s
                    5 - Dopisanie nowego pracownika\s
                    6 - Usunięcie pracownika z bazy\s
                    7 - Aktualizowanie danych pracownika\s
                    8 - Obliczanie średniej pensji w firmie\s
                    9 - Obliczanie ilu pracowników zarabia poniżej średniej\s
                    10 - Zapis bazy do pliku\s
                    11 - Symulacja parkingu\s""");

            try {
                wybor = scanner.nextInt();
                scanner.nextLine();
                switch (wybor) {
                    case 0 -> running = false;
                    case 1 -> listaPracownikow.clear();
                    case 2 -> deserializuj();
                    case 3 -> wyswietlPracownikow(0);
                    case 4 -> {
                        System.out.println("Podaj PESEL pracownika:");
                        int PESEL = scanner.nextInt();scanner.nextLine();
                        wyswietlPracownikow(PESEL);
                    }
                    case 5 -> inputDodajPracownika();
                    case 6 -> {
                        System.out.println("Podaj PESEL pracownika:");
                        int PESEL = scanner.nextInt();
                        scanner.nextLine();
                        usunPracownika(PESEL);
                    }
                    case 7 -> zmianaDanychPracownika();
                    case 8 -> System.out.println("Średnia pensja to: " + sredniaPensja());
                    case 9 -> System.out.println("Liczba pracowników zarabiających poniżej średniej: " + pracownicyPonizejSredniej());
                    case 10 -> Serializuj();
                    case 11 -> symulacjaParkingu();
                    default -> System.out.println("Błędny numer opcji");
                }
            }
            catch ( java.util.InputMismatchException | java.lang.NumberFormatException e) {
                System.out.println("Wpisałeś błędny typ danych!");
                e.printStackTrace();
                scanner.nextLine();
            }
        }
    }

    public static void inputDodajPracownika() {
        try {
        System.out.println("Podaj PESEL pracownika:");
        int PESEL = scanner.nextInt();scanner.nextLine();
        System.out.println("Podaj nazwisko i imię pracownika:");
        String nazwiskoImie = scanner.nextLine();
        System.out.println("Podaj datę urodzenia pracownika:");
        String dataUrodzenia = scanner.nextLine();
        System.out.println("Podaj stanowisko pracownika:");
        String stanowisko = scanner.nextLine();
        System.out.println("Podaj pensję pracownika:");
        float pensja = scanner.nextFloat();scanner.nextLine();
        System.out.println("Podaj staż pracownika:");
        int staz = scanner.nextInt();scanner.nextLine();
        dodajPracownika(new Pracownik(PESEL, nazwiskoImie, dataUrodzenia, stanowisko, pensja, staz));
        }
        catch ( java.util.InputMismatchException | java.lang.NumberFormatException e) {
            System.out.println("Wpisałeś błędny typ danych!");
            e.printStackTrace();
            scanner.nextLine();
    }}

    public static void zmianaDanychPracownika(){
        System.out.println("Podaj pesel pracownika do zmiany danych:"); // ludzie nie zmieniają peselów
        try {
        int PESEL = scanner.nextInt();scanner.nextLine();
        for (Pracownik pracownik:listaPracownikow) {
            if (pracownik.PESEL == PESEL) {
                System.out.println("Podaj nazwisko i imię pracownika:");
                String nazwiskoImie = scanner.nextLine();
                System.out.println("Podaj datę urodzenia pracownika:");
                String dataUrodzenia = scanner.nextLine();
                System.out.println("Podaj stanowisko pracownika:");
                String stanowisko = scanner.nextLine();
                System.out.println("Podaj pensję pracownika:");
                float pensja = scanner.nextFloat();scanner.nextLine();
                System.out.println("Podaj staż pracownika:");
                int staz = scanner.nextInt();scanner.nextLine();
                pracownik.nazwiskoImie = nazwiskoImie;
                pracownik.dataUrodzenia = dataUrodzenia;
                pracownik.stanowisko = stanowisko;
                pracownik.pensja = pensja;
                pracownik.staz = staz;
                break;
            }
        }}
        catch ( java.util.InputMismatchException | java.lang.NumberFormatException e) {
            System.out.println("Wpisałeś błędny typ danych!");
            e.printStackTrace();
            scanner.nextLine();
        }
    }

    public static int pracownicyPonizejSredniej() {
        int liczba = 0;
        float srednia = sredniaPensja(); // zapisuje do zmiennej, żeby nie wywoływać tej metody niepotrzebnie
        for (Pracownik pracownik:listaPracownikow) {
            if (pracownik.pensja < srednia) {
                liczba++;
            }
        }
        return liczba;
    }

    public static float sredniaPensja() {
        float suma = 0;
        for (Pracownik pracownik:listaPracownikow) {
            suma += pracownik.pensja;
        }
        return suma/listaPracownikow.size();
    }

    public static void dodajPracownika(Pracownik pracownik) {
        boolean dodany = false;
        if (listaPracownikow.isEmpty()) {
            listaPracownikow.add(pracownik);
        }
        else {
            for (Pracownik ipracownik:listaPracownikow) {
                    if (ipracownik.PESEL >= pracownik.PESEL) {
                        dodany = true;
                        listaPracownikow.add(listaPracownikow.indexOf(ipracownik), pracownik);
                        break;
                }
            }
            if (!dodany) { // Jeżeli nie ma żadnego pracownika z większym peselem to dodaje na koniec
                listaPracownikow.add(pracownik);
            }
        }

    }

    public static void usunPracownika(int PESEL) {
        for (Pracownik pracownik:listaPracownikow) {
            if (pracownik.PESEL == PESEL) {
                listaPracownikow.remove(pracownik);
                System.out.println("Pracownik pomyślnie usunięty");
                break;
            }
        }
    }

    public static void wyswietlPracownikow(int PESEL) {
        System.out.println("Liczba pracowników: " + listaPracownikow.size()+"\n");
        boolean istnieje = false;
        for (Pracownik pracownik:listaPracownikow) {
            if (pracownik.PESEL == PESEL) {
                istnieje = true;
                printPracownik(pracownik);
                break;
            }
        }
        if (!istnieje) { // jeżeli podamy niepoprawny pesel to wyświetlamy wszystkich pracowników
            System.out.println("Wyświetlanie wszystkich pracowników:");
            for (Pracownik pracownik:listaPracownikow) { printPracownik(pracownik); }
        }
    }

    public static void printPracownik(Pracownik pracownik) {
        System.out.println("Pracownik o peselu: " + pracownik.PESEL);
        System.out.println("Nazwisko i imie: "+ pracownik.nazwiskoImie);
        System.out.println("Data urodzenia: "+ pracownik.dataUrodzenia);
        System.out.println("Stanowisko: "+ pracownik.stanowisko);
        System.out.println("Pensja: "+ pracownik.pensja);
        System.out.println("Staż pracy: "+ pracownik.staz);
        System.out.println("Premia: "+ pracownik.premia);
        System.out.println();
    }

    public static void Serializuj() {
        try {
            FileOutputStream fout = new FileOutputStream("bazaDanych");
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(listaPracownikow);
            oout.close();
            fout.close();
        }
        catch (IOException e) {
            System.out.println("Błąd zapisywania pliku");
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked") // Zapisana baza danych nie może być innego typu, tylko jedna metoda ją zapisuje.
    public static void deserializuj() {
        try {
            FileInputStream fin = new FileInputStream("bazaDanych");
            ObjectInputStream oin = new ObjectInputStream(fin);
            listaPracownikow = (TwoWayCycledListWithSentinel<Pracownik>) oin.readObject();
            oin.close();
            fin.close();
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Błąd odczytu pliku");
            e.printStackTrace();
    }
    }

    public static void symulacjaParkingu() {
        ListStack<Pracownik> parking = new ListStack<>();
        ListStack<Pracownik> wyjazd = new ListStack<>();
        for (Pracownik pracownik:listaPracownikow) {parking.push(pracownik);} // Wszyscy pracownicy parkują.

        while (!parking.isEmpty()) {
            //losowy pracownik chce wyjechać.
            int random = ThreadLocalRandom.current().nextInt(0, parking.size());
            for(int i = -1; i < random; i++) {

                if (i == random-1) { System.out.println("Pracownik "+parking.pop().nazwiskoImie+" odjechał do domu"); } // Jeżeli to ten pracownik co chciał wyjechać to już nie wraca.
                else {
                    wyjazd.push(parking.top()); // Jeżeli tylko wyjeżdża na chwilkę, to powróci.
                    System.out.println("Pracownik "+parking.pop().nazwiskoImie+" musi wyjechać");
                }
            }
            while (!wyjazd.isEmpty()) { parking.push(wyjazd.pop()); } // Ci co musieli wyjechać wracają
        }
        System.out.println("Wszyscy pracownicy wyjechali, zamykamy na noc ;)");
    }


}
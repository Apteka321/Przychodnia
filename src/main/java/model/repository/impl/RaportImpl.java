/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.repository.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import model.HibernateUtil;
import model.Plan_pracy;
import model.repository.Raport;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author Daniel
 */
public class RaportImpl implements Raport {

    public List pobierzDaneDoRaportu(String rok) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List miesiecznyRaport = null;

        String hqlPlanuPracy = "SELECT MONTH(Data_platnosci) as  Miesiac,"
                + " COUNT(*) as Ilosc,SUM(kwota) as  Przychod FROM Platnosc"
                + " WHERE YEAR(Data_platnosci)=2017"
                + " GROUP BY MONTH(Data_platnosci)";

        SQLQuery query = session.createSQLQuery(hqlPlanuPracy);
        miesiecznyRaport = query.list();
        Iterator it = miesiecznyRaport.iterator();
        List<MiesiecznyRaport> miesiacznaLiczbaZyskow = new ArrayList<MiesiecznyRaport>();
        while (it.hasNext()) {

            Object object[] = (Object[]) it.next();
            MiesiecznyRaport miesiecznyRapor = new MiesiecznyRaport();

            miesiecznyRapor.setIlosc(new Double(object[1].toString()));
            miesiecznyRapor.setCena(new Double(object[2].toString()));
            miesiacznaLiczbaZyskow.add(miesiecznyRapor);
        }
        session.close();

        return miesiacznaLiczbaZyskow;

    }

    public double[] pobierzMiesiaczneKoszty() {
        List<Plan_pracy> wyplatyPracownikow;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hqlPlanuPracy = "FROM Plan_pracy";
        Query query = session.createQuery(hqlPlanuPracy);
        wyplatyPracownikow = query.list();
        session.close();

        double[] miesieczneZarobki = new double[12];

        for (Plan_pracy Pracownik : wyplatyPracownikow) {

            java.util.Date date = this.zamianaStringData(Pracownik.getUmowa_od().toString());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int miesiacZatrudniania = cal.get(Calendar.MONTH);
            int rokPracy = cal.get(Calendar.YEAR);

            if (rokPracy - 2017 != 0) {
                for (int i = 0; i < 12; i++) {
                    miesieczneZarobki[i] += Pracownik.getWyplata().doubleValue();

                }
            } else {
                {
                    for (int i = miesiacZatrudniania + 1; i < 12; i++) {
                        miesieczneZarobki[i] += Pracownik.getWyplata().doubleValue();

                    }

                }
            }
        }

        for (int i = 0; i < 12; i++) {
            System.err.println(miesieczneZarobki[i]);

        }
        return miesieczneZarobki;

    }

    public Date zamianaStringData(String dataUzytkownika) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dataUzytkownika);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;

    }

    public double[] pobierzKosztyZamowien() {
        List miesieczneKosztyZamowien = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        String hqlPlanuPracy
                = "SELECT MONTH(Data_zamowienia) as Miesiac, Sum(Produkt.Cena*Produkt_Zamowienia.Ilosc) as SumaKosztow"
                + " FROM Zamowienia,Produkt,Produkt_Zamowienia"
                + " WHERE YEAR(Data_zamowienia)=2017"
                + " AND Produkt.ID=Produkt_Zamowienia.ProduktID"
                + " AND Produkt_Zamowienia.ZamowieniaID=Zamowienia.ID"
                + " GROUP BY MONTH(Data_zamowienia)";

        SQLQuery query = session.createSQLQuery(hqlPlanuPracy);
        miesieczneKosztyZamowien = query.list();
        Iterator it = miesieczneKosztyZamowien.iterator();
        double[] tablicaMiesiecznychKosztowZamowien = new double[12];

        //DODAC FLOATA !!!!!!!!!!!!!!!!!!!!!!
        while (it.hasNext()) {

            Object object[] = (Object[]) it.next();
            for (int i = 0; i <= 11; i++) {

                if (Integer.parseInt(object[0].toString()) == i) {
                    tablicaMiesiecznychKosztowZamowien[i] += Double.parseDouble(object[1].toString());
                    System.err.println(Double.parseDouble(object[1].toString()) + "obieg petli :" + i);

                } else if ((Integer.parseInt(object[0].toString()) != i && tablicaMiesiecznychKosztowZamowien[i] == 0)) {
                    tablicaMiesiecznychKosztowZamowien[i] = 0;
                    System.err.println("wartosc z zerem :obieg petli :" + i);

                }
            }

        }

        for (int i = 0; i < 12; i++) {
            System.out.println(tablicaMiesiecznychKosztowZamowien[i]);
        }

        session.close();

        return tablicaMiesiecznychKosztowZamowien;
    }

    public List pobierzLiczbeWizytWedlugMiesiaca(String rok) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List listaWizytPacjentow = null;

        String query = "SELECT MONTH(Data) as  Miesiac,"
                + " COUNT(*) as LiczbaWizyt "
                + "FROM wizyta WHERE YEAR(Data)=2017 "
                + "GROUP BY MONTH(Data)";
        SQLQuery databaseQuery = session.createSQLQuery(query);
        listaWizytPacjentow = databaseQuery.list();
        session.close();
        return listaWizytPacjentow;
    }

    public int[] listaWizytPacjentow() {

        RaportImpl raport = new RaportImpl();
        List listaWizytPacjentow = raport.pobierzLiczbeWizytWedlugMiesiaca("2017");
        Iterator it = listaWizytPacjentow.iterator();
        int[] tablicaLiczyWiytWedlugMiesiaca = new int[12];

        //DODAC FLOATA !!!!!!!!!!!!!!!!!!!!!!
        while (it.hasNext()) {

            Object object[] = (Object[]) it.next();
            for (int i = 0; i <= 11; i++) {

                if (Integer.parseInt(object[0].toString()) == i) {
                    tablicaLiczyWiytWedlugMiesiaca[i] += Integer.parseInt(object[1].toString());
//                    System.err.println(Double.parseDouble(object[1].toString()) + "obieg petli :" + i);

                } else if ((Integer.parseInt(object[0].toString()) != i && tablicaLiczyWiytWedlugMiesiaca[i] == 0)) {
                    tablicaLiczyWiytWedlugMiesiaca[i] = 0;
//                    System.err.println("wartosc z zerem :obieg petli :" + i);

                }
            }
           
        }
         for (int i = 0; i < 12; i++) {
                System.err.println(tablicaLiczyWiytWedlugMiesiaca[i]);

            }
        return tablicaLiczyWiytWedlugMiesiaca;

    }

    public List poberzLiczbeZamowienWedlugMiesiaca(String rok) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        List listaZamowien = null;

        String query = "SELECT MONTH(Data_zamowienia) as  Miesiac,"
                + " COUNT(*) as LiczbaZamowien "
                + "FROM zamowienia WHERE YEAR(Data_zamowienia)=2017 "
                + "GROUP BY MONTH(Data_zamowienia)";
        SQLQuery databaseQuery = session.createSQLQuery(query);
        listaZamowien = databaseQuery.list();
        session.close();
        return listaZamowien;
    }

    public int[] listaZamowien() {
   RaportImpl raport = new RaportImpl();
        List listaWizytPacjentow = raport.poberzLiczbeZamowienWedlugMiesiaca("2017");
        Iterator it = listaWizytPacjentow.iterator();
        int[] tablicaLiczyZamowienWedlugMiesiaca = new int[12];

        //DODAC FLOATA !!!!!!!!!!!!!!!!!!!!!!
        while (it.hasNext()) {

            Object object[] = (Object[]) it.next();
            for (int i = 0; i <= 11; i++) {

                if (Integer.parseInt(object[0].toString()) == i) {
                    tablicaLiczyZamowienWedlugMiesiaca[i] += Integer.parseInt(object[1].toString());
//                    System.err.println(Double.parseDouble(object[1].toString()) + "obieg petli :" + i);

                } else if ((Integer.parseInt(object[0].toString()) != i && tablicaLiczyZamowienWedlugMiesiaca[i] == 0)) {
                    tablicaLiczyZamowienWedlugMiesiaca[i] = 0;
//                    System.err.println("wartosc z zerem :obieg petli :" + i);

                }
            }
           
        }
         for (int i = 0; i < 12; i++) {
                System.err.println(tablicaLiczyZamowienWedlugMiesiaca[i]);

            }
        return tablicaLiczyZamowienWedlugMiesiaca;        


    }

    public class PensjePracownikow {

    }

    public class Miesiac {

        private Double Koszty;
        private String Nazwa;

        public Miesiac(Double Koszty, String Nazwa) {
            this.Koszty = Koszty;
            this.Nazwa = Nazwa;
        }

        public Double getKoszty() {
            return Koszty;
        }

        public void setKoszty(Double Koszty) {
            this.Koszty = Koszty;
        }

        public String getNazwa() {
            return Nazwa;
        }

        public void setNazwa(String Nazwa) {
            this.Nazwa = Nazwa;
        }

    }

    public class MiesiecznyRaport {

        private Double ilosc;
        private Double cena;

        public Double getIlosc() {
            return ilosc;
        }

        public void setIlosc(Double ilosc) {
            this.ilosc = ilosc;
        }

        public Double getCena() {
            return cena;
        }

        public void setCena(Double cena) {
            this.cena = cena;
        }

    }

}

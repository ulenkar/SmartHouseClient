/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.HibernateUtil;
import model.Sprzet;
import org.hibernate.Session;

/**
 *
 * @author Ulka
 */
public class ProducentPomiarowClient {

    private static final String QUERY_GNIAZDKA = "from Sprzet where typ = 'gniazdko'";
    private static final String QUERY_CZUJNIKI = "from Sprzet where typ = 'czujnik'";

    private ArrayList<Sprzet> czujniki, gniazdka;
    private BigDecimal startingTemp, startingNap, startingPrad, startingMoc;
    private final Random rnd = new Random();
    private Session session;

    public ProducentPomiarowClient() {
        loadStartingTempValues();
        loadStartingGniazdkaValues();
        loadData();
    }

    private void loadData() {
        session = HibernateUtil.getSessionFactory().openSession();
        List resultCzujniki = HibernateUtil.executeHQLListQuery(session, QUERY_CZUJNIKI);
        List resultGniazdka = HibernateUtil.executeHQLListQuery(session, QUERY_GNIAZDKA);

        czujniki = new ArrayList<>();
        for (Object o : resultCzujniki) {
            Sprzet s = (Sprzet) o;
            czujniki.add(s);
        }

        gniazdka = new ArrayList<>();
        for (Object o : resultGniazdka) {
            Sprzet s = (Sprzet) o;
            gniazdka.add(s);
        }

    }

    private void loadStartingTempValues() {
        int startingT = rnd.nextInt(69) - 19;
        startingTemp = new BigDecimal(startingT);
    }

    private void loadStartingGniazdkaValues() {
        int startingN = rnd.nextInt(298) + 1;
        startingNap = new BigDecimal(startingN);
        int startingP = rnd.nextInt(62) - 31;
        startingPrad = new BigDecimal(startingP);
        int startingM = rnd.nextInt(14718) - 7359;
        startingMoc = new BigDecimal(startingM);
    }

    public String newPomiarTemp() {
        boolean znak = rnd.nextBoolean();
        Double roznica = rnd.nextDouble() / 10;
        BigDecimal bigroznica = new BigDecimal(roznica);
        if (znak) {
            startingTemp = startingTemp.subtract(bigroznica);
        } else {
            startingTemp = startingTemp.add(bigroznica);
        }
        if (startingTemp.compareTo(new BigDecimal(-20)) == -1 || (startingTemp.compareTo(new BigDecimal(50)) == 1)) {
            loadStartingTempValues();
        }
        return startingTemp.setScale(2, RoundingMode.HALF_UP).toString();
    }

    public String newPomiarGniazdko() {
        boolean znak = rnd.nextBoolean();
        Double roznica = rnd.nextDouble();
        if (znak) {
            startingNap = startingNap.subtract(new BigDecimal(roznica));
            startingPrad = startingPrad.subtract(new BigDecimal(roznica / 10));
            startingMoc = startingMoc.subtract(new BigDecimal(roznica * 2));
        } else {
            startingNap = startingNap.add(new BigDecimal(roznica));
            startingPrad = startingPrad.add(new BigDecimal(roznica / 10));
            startingMoc = startingMoc.add(new BigDecimal(roznica * 2));
        }
        if ((startingNap.compareTo(new BigDecimal(0)) == -1)
                || (startingNap.compareTo(new BigDecimal(300)) == 1)
                || (startingPrad.compareTo(new BigDecimal(-32)) == -1)
                || (startingPrad.compareTo(new BigDecimal(32)) == 1)
                || (startingMoc.compareTo(new BigDecimal(-7360)) == -1)
                || (startingMoc.compareTo(new BigDecimal(7360)) == 1)) {
            loadStartingGniazdkaValues();
        }
        return startingNap.setScale(2, RoundingMode.HALF_UP).toString() + ";"
                + startingPrad.setScale(2, RoundingMode.HALF_UP).toString() + ";"
                + startingMoc.setScale(2, RoundingMode.HALF_UP).toString();
    }

    public String produkujNowePomiary() {
        //nowe pomiary temperatury
        //ArrayList<String> pomiary = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        czujniki.forEach(s -> {
            String nowy = "T;" + s.getSprzetId() + ";" + newPomiarTemp();
            sb.append(nowy).append("#");
        });
        gniazdka.forEach(s -> {
            String nowy = "G;" + s.getSprzetId() + ";" + newPomiarGniazdko();
            sb.append(nowy).append("#");
        });
        System.out.println("Nowe pomiary: " + sb.toString());
        return sb.toString();
    }
}

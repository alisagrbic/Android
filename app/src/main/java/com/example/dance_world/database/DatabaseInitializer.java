package com.example.dance_world.database;

import android.content.Context;

import com.example.dance_world.R;
import com.example.dance_world.database.entities.Artist;
import com.example.dance_world.database.entities.ArtistFestival;
import com.example.dance_world.database.entities.Dj;
import com.example.dance_world.database.entities.Festival;
import com.example.dance_world.database.entities.User;

public class DatabaseInitializer {
    private Context context;
    private DatabaseHelper helper;

    public DatabaseInitializer(Context c) {
        context = c;
    }

    public void InitData() {
        helper = DatabaseHelper.getInstance(context);
        User user1 = new User("Alisa", "Grbic", "alisic", "alisa.grbic@gmail.com", "alisa123", 100, false, "");
        User user2 = new User("Maja", "Grubor", "maja", "maja.grubor@gmail.com", "maja123", 70, false, "");
        User user3 = new User("Milica", "Tomic", "milica", "milica.tomic@gmail.com", "milica123", 120, false, "");
        helper.UserDao().insertUser(user1);
        helper.UserDao().insertUser(user2);
        helper.UserDao().insertUser(user3);

        Festival f1 = new Festival("Bachateando", "Miami", -80.191788, 25.761681, "One of the two or three most prestigious dance festivals on the planet, Bachateando is always full to bursting with worldwide stars.",
                "01.04.2021.", user1.id, R.drawable.bachateando);
        Festival f2 = new Festival("Baltimore Salsa Congress", "Baltimore", -76.609383, 39.299236, "EXIT is an award-winning summer music festival that takes place at the Petrovaradin Fortress in Novi Sad, Serbia, with more than 1000 artists who play at over 40 stages and festival zones.",
                "09.04.2021.", user1.id, R.drawable.baltimore);
        Festival f3 = new Festival("Adicto", "Berlin", 13.381777, 52.531677, "Indie King and forceful supporter of new music, Primavera Sound prides itself on pushing the alternative scene forward. ",
                "12.06.2020.", user2.id, R.drawable.berlin);
        Festival f4 = new Festival("Bachata Day", "Milano", 9.191383, 45.464211, "Transported across the Atlantic from Miami's glamourous city centre, Ultra Europe wasted no time in establishing itself as a worthy successor to the original UMF. ",
                "05.03.2021.", user2.id, R.drawable.milan);
        Festival f5 = new Festival("Summer Sensual Days", "Rovinj", 13.641608, 45.078132, "Sziget is one of those festivals that could never spend another cent on marketing and still sell out every single year.",
                "19.06.2021.", user3.id, R.drawable.rovinj);
        helper.FestivalDao().insertFestival(f1);
        helper.FestivalDao().insertFestival(f2);
        helper.FestivalDao().insertFestival(f3);
        helper.FestivalDao().insertFestival(f4);
        helper.FestivalDao().insertFestival(f5);

        Dj dj1 = new Dj("DJ Khalid", "Bachata", helper.FestivalDao().getIdFestivalByName("Bachateando"));
        Dj dj2 = new Dj("DJ Khalid", "Bachata", helper.FestivalDao().getIdFestivalByName("Baltimore Salsa Congress"));
        Dj dj3 = new Dj("DJ Khalid", "Bachata", helper.FestivalDao().getIdFestivalByName("Adicto"));
        Dj dj4 = new Dj("DJ El Tiquere", "Bachata", helper.FestivalDao().getIdFestivalByName("Adicto"));
        Dj dj5 = new Dj("DJ El Tiquere", "Bachata", helper.FestivalDao().getIdFestivalByName("Bachata Day"));
        Dj dj6 = new Dj("DJ El Tiquere", "Bachata", helper.FestivalDao().getIdFestivalByName("Summer Sensual Days"));
        Dj dj7 = new Dj("DJ Latin Master", "Bachata, Salsa Romantica", helper.FestivalDao().getIdFestivalByName("Summer Sensual Days"));
        Dj dj8 = new Dj("DJ Latin Master", "Bachata, Salsa Romantica", helper.FestivalDao().getIdFestivalByName("Bachateando"));
        Dj dj9 = new Dj("DJ X-tra", "Kizomba", helper.FestivalDao().getIdFestivalByName("Bachata Day"));
        Dj dj10 = new Dj("DJ X-tra", "Kizomba", helper.FestivalDao().getIdFestivalByName("Baltimore Salsa Congress"));
        helper.DjDao().insertDj(dj1);
        helper.DjDao().insertDj(dj2);
        helper.DjDao().insertDj(dj3);
        helper.DjDao().insertDj(dj4);
        helper.DjDao().insertDj(dj5);
        helper.DjDao().insertDj(dj6);
        helper.DjDao().insertDj(dj7);
        helper.DjDao().insertDj(dj8);
        helper.DjDao().insertDj(dj9);
        helper.DjDao().insertDj(dj10);

        Artist a1 = new Artist("Ronald & Alba", "Bachata", helper.FestivalDao().getIdFestivalByName("Bachateando"), R.drawable.ronald_alba);
        Artist a2 = new Artist("Ronald & Alba", "Bachata ", helper.FestivalDao().getIdFestivalByName("Baltimore Salsa Congress"), R.drawable.ronald_alba);
        Artist a3 = new Artist("Korke & Judit", "Salsa", helper.FestivalDao().getIdFestivalByName("Adicto"), R.drawable.korke_judit);
        Artist a4 = new Artist("Daniel & Desiree", "Bachata", helper.FestivalDao().getIdFestivalByName("Bachateando"), R.drawable.daniel_desiree);
        Artist a5 = new Artist("Korke & Judit", "Salsa", helper.FestivalDao().getIdFestivalByName("Bachata Day"), R.drawable.korke_judit);
        Artist a6 = new Artist("Ataca & La Alemana", "Bachata modern", helper.FestivalDao().getIdFestivalByName("Adicto"), R.drawable.ataca_laalemana);
        Artist a7 = new Artist("Ataca & La Alemana", "Bachata modern", helper.FestivalDao().getIdFestivalByName("Baltimore Salsa Congress"), R.drawable.ataca_laalemana);
        Artist a8 = new Artist("Luis & Andrea", "Kizomba", helper.FestivalDao().getIdFestivalByName("Bachata Day"), R.drawable.luis_andrea);
        Artist a9 = new Artist("Luis & Andrea", "Kizomba", helper.FestivalDao().getIdFestivalByName("Summer Sensual Days"), R.drawable.luis_andrea);
        Artist a10 = new Artist("Luis & Andrea", "Kizomba", helper.FestivalDao().getIdFestivalByName("Adicto"), R.drawable.luis_andrea);
        Artist a11 = new Artist("Luis & Andrea", "Kizomba", helper.FestivalDao().getIdFestivalByName("Bachateando"), R.drawable.luis_andrea);

        helper.ArtistDao().insertArtist(a1);
        helper.ArtistDao().insertArtist(a2);
        helper.ArtistDao().insertArtist(a3);
        helper.ArtistDao().insertArtist(a4);
        helper.ArtistDao().insertArtist(a5);
        helper.ArtistDao().insertArtist(a6);
        helper.ArtistDao().insertArtist(a7);
        helper.ArtistDao().insertArtist(a8);
        helper.ArtistDao().insertArtist(a9);
        helper.ArtistDao().insertArtist(a10);
        helper.ArtistDao().insertArtist(a11);
    }
}

package thiengo.com.br.fasteremail.data

import android.content.Context
import android.net.Uri
import thiengo.com.br.fasteremail.domain.User

/*
 * Classe mock para simular um banco de dados no
 * exemplo com a API MaterialChipsInput
 * */
class Database {
    companion object {

        fun getContacts(context: Context) = arrayListOf<User>(
                User(
                        context,
                        Uri.parse("https://i.pinimg.com/736x/dc/5c/ca/dc5ccad5bd921a27a657ecfada3f00de--live-life-anti-aging.jpg"),
                        "Mathilda Gallop",
                        "mathilda.gallop@gmail.com" ),
                User(
                        context,
                        Uri.parse("https://i.pinimg.com/736x/d5/7a/e1/d57ae1e0abaa478e79388007b6d6dd09--woman-face-woman-style.jpg"),
                        "Mathilda Gallop Souza",
                        "mathilda.gallop@gmail.com" ),
                User(
                        context,
                        Uri.parse("https://static1.squarespace.com/static/560c6ac4e4b081f0a96d5b42/560dee8ce4b035d279a65441/560dee92e4b0631d94ac0d31/1443753769057/Ilona+Concetta+Cute.jpg"),
                        "Concetta Hartson",
                        "concetta.hartson@gmail.com" ),
                User(
                        context,
                        Uri.parse("http://www2.pictures.zimbio.com/gi/Elmer+Figueroa+Arce+XcBUdTwIuJEm.jpg"),
                        "Elmer Malick",
                        "elmer.malick@gmail.com" ),
                User(
                        context,
                        Uri.parse("https://pbs.twimg.com/profile_images/692429203254280193/E7BfX3FW.jpg"),
                        "Denita Konecny",
                        "denita.konecny@gmail.com" ),
                User(
                        context,
                        Uri.parse("http://provenmgmt.com/wp-content/uploads/2014/09/denita-conway.jpg"),
                        "Denita Konecny",
                        "denita.konecny.dogs.house@gmail.com" ),
                User(
                        context,
                        Uri.parse("http://strengthforthesoul.com/wp-content/uploads/2015/10/Cindi-McMenamin-sm.jpg"),
                        "Cindi Saylor",
                        "cindi.saylor@gmail.com" ),
                User(
                        context,
                        Uri.parse("https://onmilwaukee.com/images/articles/pa/packers-aaron-rodgers-face-gif/packers-aaron-rodgers-face-gif_fullsize_story1.jpg"),
                        "Aaron Hope",
                        "aaron.hope@gmail.com" ),
                User(
                        context,
                        Uri.parse("https://i.pinimg.com/736x/8e/2c/c6/8e2cc6c9a93d362bf159c0f288414ad7--childhood-director.jpg"),
                        "Eugenio Pinales",
                        "eugenio.pinales@gmail.com" ),
                User(
                        context,
                        Uri.parse("https://www.milbank.com/images/content/9/1/v2/91034/Yusman-Rosaline-SG-web.jpg"),
                        "Rosaline Roehrig",
                        "rosaline.roehrig@gmail.com" )
        )
    }
}
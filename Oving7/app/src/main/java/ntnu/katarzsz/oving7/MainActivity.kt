package ntnu.katarzsz.oving7

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import ntnu.katarzsz.oving7.managers.FileManager
import ntnu.katarzsz.oving7.service.Database

class MainActivity : Activity() {
    private lateinit var result: TextView
    private lateinit var db: Database
    private lateinit var fm: FileManager
    private lateinit var view: View
    //Krav1: Ha lagret en fil (bestem format selv) i mappen “res/raw” (du må sannsynligvis lage denne
    //selv). Filen skal inneholde informasjon om forskjellige filmer.
    //Krav2: Hver film skal ha en tittel, regissør og minst 2 skuespillere. Legg inn minst 5 filmer.
    //SVAR: filen ligger inne i /res/raw/movies.txt med 5 filmer, med regissørene og skuespillere

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view = findViewById<View>(android.R.id.content).rootView

        val preferences: SharedPreferences = getSharedPreferences("prefs", 0)
        val backgroundColor: String? = preferences.getString("backgroundColor", "")

        when(backgroundColor) {
            "pink" -> view.setBackgroundColor(Color.argb(255, 255, 216, 236))
            "purple" -> view.setBackgroundColor(Color.argb(255, 219, 185, 255))
            "blue" -> view.setBackgroundColor(Color.argb(255, 185, 250, 255))
        }

        result = findViewById(R.id.result)
        db = Database(this)
        val movies = FileManager(this).readFileFromResFolder(R.raw.movies).split("\n")
        movies.forEach {
            if (it.isNotEmpty()) {
                val movieDetails = it.split(",")

                val movieTitle = movieDetails[0]
                val director = movieDetails[1]
                val actors = movieDetails.slice(2 until movieDetails.size)
                // Krav3: Når du starter applikasjonen skal filmene leses og settes inn i en lokal database
                //SVAR: Filmene lagres i lokal database i linja under
                db.insert(actors, movieTitle, director)
            }
        }

        //Krav4: Filmene skal også skrives skrives over i en ny lokal fil.
        //SVAR: Jeg skriver filmene til en lokal fil som finnes i /data/data/ntnu.katarzsz.oving7/files/filename.txt
        fm = FileManager(this)
        fm.write(FileManager(this).readFileFromResFolder(R.raw.movies))

    }
    private fun showResults(list: ArrayList<String>) {
        val res = StringBuffer("")
        for (s in list) res.append("$s\n")
        result.text = res
    }

    //Krav 5: Applikasjonen skal la brukeren velge hvilken informasjon som skal vises (f.eks. alle
    //filmer, filmer av en regissør, skuespillere for en film osv.)
    //SVAR: Brukeren får velge å vise forskjellig følgende informasjon ut fra menyen (inkludert settings for å endre bakgrunnsfarge):
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        menu.add(0, 1, 0, "All movies")
        menu.add(0, 2, 0, "All directors")
        menu.add(0, 3, 0, "All actors")
        menu.add(0, 4, 0, "Movies by Robert Stromberg")
        menu.add(0, 5, 0, "Actors from \"Interstellar\"")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //Krav 6: Visningen av informasjonen skal ha en bakgrunnsfarge, brukeren skal kunne velge hvilken
            //farge de ønsker som bakgrunn (ha minst 3 alternativer). Dette velger brukeren i en egen
            //aktivitet som brukeren navigerer til fra f.eks. menylinjen
            //SVAR: SettingsActivity
            R.id.settings -> startActivityForResult(Intent("ntnu.katarzsz.oving7.SETTINGS"), 1)
            1             -> showResults(db.allMovies)
            2             -> showResults(db.allDirectors)
            3             -> showResults(db.allActors)
            4             -> showResults(db.getMoviesByDirector("Robert Stromberg"))
            5             -> showResults(db.getActorsByMovie("Interstellar"))
            else          -> return false
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val preferences: SharedPreferences = getSharedPreferences("prefs", 0)
        val backgroundColor: String? = preferences.getString("backgroundColor", "")
        Log.e("backgroundColor", backgroundColor.toString())
        when(backgroundColor) {
            "pink" -> view.setBackgroundColor(Color.argb(255, 255, 216, 236))
            "purple" -> view.setBackgroundColor(Color.argb(255, 219, 185, 255))
            "blue" -> view.setBackgroundColor(Color.argb(255, 185, 250, 255))
        }
    }
}


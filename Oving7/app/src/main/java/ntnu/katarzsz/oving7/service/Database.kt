package ntnu.katarzsz.oving7.service

import android.content.Context
import ntnu.katarzsz.oving7.managers.DatabaseManager

class Database(context: Context) : DatabaseManager(context) {

	init {
		try {
			this.clear()
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	val allMovies: ArrayList<String>
		get() = performQuery(TABLE_MOVIE, arrayOf(MOVIE_TITLE))

	val allActors: ArrayList<String>
		get() = performQuery(TABLE_ACTOR, arrayOf(ID, ACTOR_NAME), null)

	val allDirectors: ArrayList<String>
		get() = performQuery(TABLE_DIRECTOR, arrayOf(ID, DIRECTOR_NAME), null)

	val allMoviesAndActors: ArrayList<String>
		get() {
			val select = arrayOf("$TABLE_MOVIE.$MOVIE_TITLE", "$TABLE_ACTOR.$ACTOR_NAME")
			val from = arrayOf(TABLE_ACTOR, TABLE_MOVIE, TABLE_ACTOR_MOVIE)
			val join = JOIN_ACTOR_MOVIE

			return performRawQuery(select, from, join)
		}

	val allMoviesAndDirectors: ArrayList<String>
		get() {
			val select = arrayOf("$TABLE_MOVIE.$MOVIE_TITLE", "$TABLE_DIRECTOR.$DIRECTOR_NAME")
			val from = arrayOf(TABLE_ACTOR, TABLE_MOVIE, TABLE_DIRECTOR_MOVIE)
			val join = JOIN_DIRECTOR_MOVIE

			return performRawQuery(select, from, join)
		}

	fun getMoviesByActor(author: String): ArrayList<String> {
		val select = arrayOf("$TABLE_MOVIE.$MOVIE_TITLE")
		val from = arrayOf(TABLE_ACTOR, TABLE_MOVIE, TABLE_ACTOR_MOVIE)
		val join = JOIN_ACTOR_MOVIE
		val where = "$TABLE_ACTOR.$ACTOR_NAME='$author'"

		return performRawQuery(select, from, join, where)
	}

	fun getMoviesByDirector(director: String): ArrayList<String> {
		val select = arrayOf("$TABLE_MOVIE.$MOVIE_TITLE")
		val from = arrayOf(TABLE_DIRECTOR, TABLE_MOVIE, TABLE_DIRECTOR_MOVIE)
		val join = JOIN_DIRECTOR_MOVIE
		val where = "$TABLE_DIRECTOR.$DIRECTOR_NAME='$director'"

		return performRawQuery(select, from, join, where)
	}

	fun getActorsByMovie(title: String): ArrayList<String> {
		val select = arrayOf("$TABLE_ACTOR.$ACTOR_NAME")
		val from = arrayOf(TABLE_ACTOR, TABLE_MOVIE, TABLE_ACTOR_MOVIE)
		val join = JOIN_ACTOR_MOVIE
		val where = "$TABLE_MOVIE.$MOVIE_TITLE='$title'"

		/*
		You can also just write out the querys manually like below, but this increases the chance of
		spelling mistakes and, creates a lot of work if you want to change names of fields etc.
		later.
		 */
		/*val query =
			"SELECT AUTHOR.name FROM AUTHOR, BOOK, AUTHOR_BOOK " + "WHERE AUTHOR._id = AUTHOR_BOOK.author_id " + "and BOOK._id = AUTHOR_BOOK.book_id " + "and BOOK.title = '$title'"
		*/
		return performRawQuery(select, from, join, where)
	}

	fun getDirectorByMovie(title: String): ArrayList<String> {
		val select = arrayOf("$TABLE_DIRECTOR.$DIRECTOR_NAME")
		val from = arrayOf(TABLE_DIRECTOR, TABLE_MOVIE, TABLE_DIRECTOR_MOVIE)
		val join = JOIN_DIRECTOR_MOVIE
		val where = "$TABLE_MOVIE.$MOVIE_TITLE='$title'"

		/*
		You can also just write out the querys manually like below, but this increases the chance of
		spelling mistakes and, creates a lot of work if you want to change names of fields etc.
		later.
		 */
		/*val query =
			"SELECT AUTHOR.name FROM AUTHOR, BOOK, AUTHOR_BOOK " + "WHERE AUTHOR._id = AUTHOR_BOOK.author_id " + "and BOOK._id = AUTHOR_BOOK.book_id " + "and BOOK.title = '$title'"
		*/
		return performRawQuery(select, from, join, where)
	}
}

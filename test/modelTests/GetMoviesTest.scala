package modelTests

import models.GetMovies
import org.scalatest.FlatSpec

/**
  * Tests for getting movie details depending on the id passed in
  *
  * @author Nelvin
  */
class GetMoviesTest extends FlatSpec {

  "Return movie details" should "a valid is passed" in {
    assertResult(GetMovies.movieDetails("335983")){"[\"Venom\",\"When Eddie Brock acquires the powers of a symbiote, he will have to release his alter-ego \\\"Venom\\\" to save his life.\",\"/2uNW4WbgBXL25BAbXGLnLqX71Sw.jpg\",\"2018-10-03\",112,\"dzxFdtWmjto\",\"/VuukZLgaCrho2Ar8Scl9HtV3yD.jpg\",\"Science Fiction\"]"}
  }

  "Invalid input" should "return No movie" in {
    assertResult(GetMovies.movieDetails("")){"\"No Movie\""}
  }

  "Invalid id input" should "return No movie" in {
    assertResult(GetMovies.movieDetails("szhb")){"\"No Movie\""}
  }

}

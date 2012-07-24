package ro.igstan.learning

import org.scalatra.ScalatraServlet
import org.scalatra.test.scalatest._

class LearningScalatra extends ScalatraSpec {

  class TestServlet extends ScalatraServlet {
    get("/") {
      "Hello, World!"
    }
  }

  addServlet(new TestServlet, "/*")

  describe("testing Scalatra using ScalaTest") {
    it("works for a simple GET request") {
      get("/") {
        status must be(200)
        body must be("Hello, World!")
      }
    }
  }
}

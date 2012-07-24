package ro.igstan.learning

import org.scalatest.FunSpec
import org.scalatest.matchers.MustMatchers

class LearningScalaTest extends FunSpec with MustMatchers {
  describe("what") {
    it("does...") {
      true must be(true)
    }
  }
}

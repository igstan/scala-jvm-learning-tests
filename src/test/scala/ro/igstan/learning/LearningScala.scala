package ro.igstan.learning

import org.scalatest.FunSpec
import org.scalatest.matchers.MustMatchers

class LearningScala extends FunSpec with MustMatchers {
  describe("a trait") {
    it("may extend a trait") {
      trait A
      trait B extends A
    }

    it("may extend multiple traits") {
      trait A
      trait B
      trait C extends A with B
    }

    it("may extend a class") {
      class A
      trait B extends A
    }
  }
}

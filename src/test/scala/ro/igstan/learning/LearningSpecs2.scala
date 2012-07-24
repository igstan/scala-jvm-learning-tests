package ro.igstan.learning

import org.specs2.mutable.Specification

class LearningSpecs2 extends Specification {

  "Specs2" should {
    "skip tests" in {
      skipped
    }
  }

  "JSON matchers" should {
    "verify presence of an object property name and value" in {
      """ { "numbers": false } """ must /("numbers" -> false)
    }

    "verify presence of an array element" in {
      // """ { "numbers": [1] } """ must /("numbers") /(1.0)
      true mustEqual false // make compiler happy
    }.pendingUntilFixed("causes compiler error")
  }
}

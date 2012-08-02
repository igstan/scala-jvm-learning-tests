package ro.igstan.learning

import org.specs2.matcher.ThrownExpectations
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

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

  trait Logger {
    def error(a: String, e: Throwable)
  }

  trait mocks extends Mockito with Scope with ThrownExpectations {
    val mockedLogger = mock[Logger]
  }

  "mocking" should {
    "be able to verify arguments passed to mocks" in new mocks {
      val exception = new RuntimeException
      mockedLogger.error("message", exception)

      got {
        one(mockedLogger).error("message", exception)
      }
    }

    "be able to *partially* verify arguments passed to mocks" in new mocks {
      val exception = new RuntimeException
      mockedLogger.error("message", exception)

      got {
        one(mockedLogger).error(any, ===(exception))
      }
    }
  }
}

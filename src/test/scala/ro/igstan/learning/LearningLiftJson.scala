package ro.igstan.learning

import org.scalatest.FunSpec
import org.scalatest.matchers.MustMatchers

import net.liftweb.json.DefaultFormats
import net.liftweb.json.JsonAST._
import net.liftweb.json.Serialization.{ read, write }
import net.liftweb.json.parse

case class Sample(key: String)

class LearningLiftJson extends FunSpec with MustMatchers {
  implicit val formats = DefaultFormats

  describe("using the `parse` method") {
    it("will produce the expected JsonAST structure") {
      val ast = parse("""{ "key": "value" }""")
      ast must be(JObject(List(JField("key", JString("value")))))
    }

    it("parses the string into a case class") {
      val sample = read[Sample]("""{ "key": "value" }""")
      sample must be (Sample("value"))
    }

    it("serializes a case class into a correct string") {
      val json = write(Sample("value"))
      json must be ("""{"key":"value"}""")
    }
  }
}

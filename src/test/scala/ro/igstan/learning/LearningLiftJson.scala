package ro.igstan.learning

import org.scalatest.FunSpec
import org.scalatest.matchers.MustMatchers

import net.liftweb.json.parse
import net.liftweb.json.JsonAST._

class LearningLiftJson extends FunSpec with MustMatchers {
  describe("using the `parse` method") {
    it("will produce the expected JsonAST structure") {
      val ast = parse("""{ "key": "value" }""")
      ast must be(JObject(List(JField("key", JString("value")))))
    }
  }
}

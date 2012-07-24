package ro.igstan.learning

import org.scalatest.FunSpec
import org.scalatest.matchers.MustMatchers

class Base64Spec extends FunSpec with MustMatchers {
  describe("encoding") {
    it("returns an empty string") {
      Base64.encode(List.empty) must be("")
    }

    it("returns AA== for 0x00") {
      Base64.encode(List(0x00)) must be("AA==")
    }

    it("returns AAA= for 0x00 0x00") {
      Base64.encode(List(0x00, 0x00)) must be("AAA=")
    }

    it("returns AAAA for 0x00 0x00 0x00") {
      Base64.encode(List(0x00, 0x00, 0x00)) must be("AAAA")
    }
  }
}

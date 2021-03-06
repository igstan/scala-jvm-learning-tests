package ro.igstan.learning

import org.joda.time.DateTime
import org.joda.time.chrono.ISOChronology
import org.scalatest.FunSpec
import org.scalatest.matchers.MustMatchers

import net.liftweb._
import json._
import net.liftweb.json.parse
import net.liftweb.json.Serialization.{ read, write }
import net.liftweb.json.{ DefaultFormats, Formats, ShortTypeHints, TypeHints }

case class Sample(key: String)
case class SampleWithOptional(key: String, value: Option[String] = None, collection: Seq[String] = Seq.empty)
case class ClassWithDateField(date: DateTime, other: String)

trait Common
case class Foo(foo: String) extends Common
case class Bar(bar: String) extends Common
case class Baz(baz: String) extends Common

trait SimpleType[A, JS <: JValue] {
  def targetClass: Class[A]
  def unwrap(json: JS)(implicit format: Formats): A
  def wrap(a: A)(implicit format: Formats): JS
}

trait JIntType[A] extends SimpleType[A, JInt]
trait JStringType[A] extends SimpleType[A, JString]

class SimpleTypeSerializer[A, JS <: JValue](t: SimpleType[A, JS]) extends Serializer[A] {
  private val Class = t.targetClass

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), A] = {
    case (TypeInfo(Class, _), json) => json match {
      case json: JS => t.unwrap(json)
      case value => throw new MappingException("Can't convert " + value + " to " + Class)
    }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case d: A if d.asInstanceOf[AnyRef].getClass == t.targetClass => t.wrap(d)
  }
}

object DateTimeSerializer {
  import org.joda.time.{ DateTime, DateTimeZone }
  import org.joda.time.format.ISODateTimeFormat
  import net.liftweb.json.MappingException

  val formatter = ISODateTimeFormat.dateTimeNoMillis

  def apply() = new SimpleTypeSerializer(new JStringType[DateTime]() {
    def targetClass = classOf[DateTime]
    def unwrap(json: JString)(implicit format: Formats) = parse(json.s)
    def wrap(dateTime: DateTime)(implicit format: Formats) = JString(formatter.print(dateTime))
  })

  private def parse(json: String) = {
    try {
      formatter.withOffsetParsed.parseDateTime(json)
    } catch {
      case e => throw new MappingException("Invalid date format: " + json)
    }
  }
}

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

    it("parses a polymorphic type using ShortTypeHints") {
      implicit val formats = new Formats {
        val dateFormat = DefaultFormats.lossless.dateFormat
        override val typeHintFieldName = "type"
        override val typeHints = ShortTypeHints(List(classOf[Foo], classOf[Bar], classOf[Baz]))
      }

      val foo = read[Common]("""{
        "type": "Foo",
        "foo": "oof"
      }""")

      foo must be (Foo(foo="oof"))
    }

    // Adapted from this blog post:
    // http://www.scalafied.com/105/default-and-customized-lift-json-type-hints
    it("parses a polymorphic type using custom TypeHints") {
      case class LowerCaseTypeHints(hints: List[Class[_]]) extends TypeHints {
        def hintFor(klass: Class[_]): String = klass.getSimpleName.toLowerCase
        def classFor(hint: String) = hints find (hintFor(_) == hint)
      }

      implicit val formats = new Formats {
        val dateFormat = DefaultFormats.lossless.dateFormat
        override val typeHintFieldName = "type"
        override val typeHints = LowerCaseTypeHints(List(classOf[Foo], classOf[Bar], classOf[Baz]))
      }

      val foo = read[Common]("""{
        "type": "foo",
        "foo": "oof"
      }""")

      foo must be (Foo(foo="oof"))
    }

    it("ignores unknown JSON properties when deserializing a case class") {
      val sample = read[Sample]("""{ "key": "value", "ignore": true }""")
      sample must be (Sample("value"))
    }

    it("handles optional fields") {
      val sample = read[SampleWithOptional]("""{ "key": "value" }""")
      sample must be (SampleWithOptional("value", None, Seq.empty))
    }

    it("deserializes an array of strings") {
      read[List[String]]("""["123"]""") must be (List("123"))
    }
  }

  describe("date format deserialization using JodaTime") {
    it ("parses date values") {
      implicit val formats = net.liftweb.json.DefaultFormats ++ List(DateTimeSerializer())
      val sample = read[ClassWithDateField]("""{ "date": "2012-01-03T00:00:00Z", "other": "value" }""")
      sample must be (ClassWithDateField(new DateTime(2012, 1, 3, 0, 0, ISOChronology.getInstanceUTC), "value"))
    }
  }
}

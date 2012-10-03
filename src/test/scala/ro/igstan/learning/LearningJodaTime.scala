package ro.igstan.learning

import org.joda.time.{ DateTime, DateTimeZone }
import org.joda.time.format.ISODateTimeFormat
import org.scalatest.FunSpec
import org.scalatest.matchers.MustMatchers

class LearningJodaTime extends FunSpec with MustMatchers {
  describe("ISO 8601 parsing") {
    val formatter = ISODateTimeFormat.date();
    val date = formatter.parseDateTime("2012-01-03");

    it ("parses years") {
      date.getYear() must be(2012)
    }

    it ("parses months") {
      date.getMonthOfYear() must be(1)
    }

    it ("parses days") {
      date.getDayOfMonth() must be(3)
    }

    it ("can be converted to Java dates") {
      val javaDate = date.toDate

      javaDate.getYear must be(112)
      javaDate.getMonth must be(0)
      javaDate.getDate must be(3)
    }
  }

  describe("equals") {
    it ("reports two identical dates as equal") {
      val formatter = ISODateTimeFormat.date
      val date1 = formatter.parseDateTime("2012-01-03")
      val date2 = formatter.parseDateTime("2012-01-03")
      assert(date1 === date2)
    }

    it ("two DateTime instances are not equal if they have different Chronologies") {
      val formatter = ISODateTimeFormat.dateTimeNoMillis
      val date1 = formatter.withZone(DateTimeZone.forID("Europe/Bucharest"))
                           .parseDateTime("2012-01-03T00:00:00Z")
      val date2 = new DateTime(2012, 1, 3, 0, 0, DateTimeZone.UTC)

      date1 must not(be(date2))
    }
  }

  describe("isEqual") {
    it ("ignores chronologies and timezones") {
      val formatter = ISODateTimeFormat.dateTimeNoMillis
      val date1 = formatter.withZone(DateTimeZone.forID("Europe/Bucharest"))
                           .parseDateTime("2012-01-03T00:00:00Z")
      val date2 = new DateTime(2012, 1, 3, 0, 0, DateTimeZone.UTC)

      assert(date1 isEqual date2)
    }
  }

  describe("formatting") {
    it ("formats M/D/YYYY") {
      val date = new DateTime(2012, 1, 3, 0, 0, DateTimeZone.UTC)
      assert("1/3/2012" === date.toString("M/D/YYYY"))
    }
  }
}

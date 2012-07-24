package ro.igstan.learning

object Base64 {

  /**
   * Sequence of characters available for base 64.
   */
  val toChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"

  /**
   * A few type aliases to avoid cluttering the main `encode` method.
   */
  type Group = (Option[Int], Option[Int])
  type State = (Seq[Int], Group)

  /**
   * The Base64 encoding algorithm takes a sequence of bytes, splits each
   * group of three bytes into four, using sub-groups of 6 bits. The resulted
   * bytes are mapped to one of the 64 available chars in the alphabet.
   */
  def encode(bytes: Seq[Int]): String = {
    val state: State = Seq.empty -> (None, None)

    val (result, rest) = (state /: bytes) {
      case ((result, (None, None)),    c)    => result -> (Some(c), None)
      case ((result, (Some(a), None)), c)    => result -> (Some(a), Some(c))
      case ((result, (Some(a), Some(b))), c) =>
        (partitionBytes(a, b, c) ++: result) -> (None, None)
    }

    charsFrom(result) + paddingFrom(rest)
  }

  /**
   * When the number of bytes in the original stream is not evenly divisible
   * by six we may have to pad the encoded string with special "=" characters.
   */
  private def paddingFrom(rest: Group): String = rest match {
    case (None,    None)    => ""
    case (Some(a), None)    => charsFrom(partitionBytes(a, 0, 0).drop(2)) + "=="
    case (Some(a), Some(b)) => charsFrom(partitionBytes(a, b, 0).drop(1)) + "="
  }

  /**
   * Split a group of three bytes into a sequence of four bytes. For example:
   *
   *   [XXXX XXXX]  [YYYY YYYY]  [ZZZZ ZZZZ]
   *
   * will be split like this:
   *
   *   [00XXXX XX]  [00XX YYYY]  [00YYYY ZZ]  [00ZZ ZZZZ]
   */
  private def partitionBytes(a: Int, b: Int, c: Int): Seq[Int] = {
    val p = ((a >> 2))
    val q = ((a & 0x00000003) << 4) | (b >> 4)
    val r = ((b & 0x0000000F) << 2) | (c >> 6)
    val s = ((c & 0x0000003F))

    // Return in reverse, because we'd like to prepend this in front of the
    // longer list, instead of having the longer list prepended in front of
    // it as the complexity of the operation is O(n) where n is the length
    // of the list being prepended.
    Seq(s, r, q, p)
  }

  /**
   * Transform a sequence of groupped bytes into a string consisting only of
   * characters available in the base 64 alphabet.
   */
  private def charsFrom(seq: Seq[Int]) = seq.reverse.map(toChar(_)).mkString
}

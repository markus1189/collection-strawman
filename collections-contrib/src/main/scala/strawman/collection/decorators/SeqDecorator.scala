package strawman.collection
package decorators

/**
  * @param `this` the decorated collection
  * @tparam A     the type of elements
  * @tparam CC    the collection type constructor
  *
  * @define coll sequence
  * @define Coll `Seq`
  */
class SeqDecorator[A, CC[X] <: SeqOps[X, CC, _]](`this`: CC[A]) {

  /** Adds the element `sep` between each element of the sequence.
    * If the sequence has less than two elements, the collection is unchanged.
    *
    * @param sep the element to intersperse
    * @tparam B the element type of the returned $coll
    * @return a new collection of type `$Coll` consisting of all elements of this $coll
    *         interspersed with the element `sep`
    *
    * @example {{{
    * List(1, 2, 3, 4).intersperse(0) = List(1, 0, 2, 0, 3, 0, 4)
    * }}}
    */
  def intersperse[B >: A](sep: B): CC[B] =
    `this`.iterableFactory.from(View.Intersperse(`this`.toIterable, sep))

  /** Adds the element `sep` between each element of the sequence,
    * prepending `start` and appending `end`.
    * If the sequence has less than two elements, returns `start +: this :+ end`.
    *
    * @param start the element to prepend
    * @param sep the element to intersperse
    * @param end the element to append
    * @tparam B the element type of the returned $coll
    * @return a new collection of type `$Coll` consisting of all elements of this $coll
    *         interspersed with the element `sep`, beginning with `start` and ending with `end`
    *
    * @example {{{
    *      List(1, 2, 3, 4).intersperse(-1, 0, 5) => List(-1, 1, 0, 2, 0, 3, 0, 4, 5)
    * }}}
    */
  def intersperse[B >: A, C](start: B, sep: B, end: B): CC[B] =
    `this`.iterableFactory.from(View.IntersperseSurround(`this`.toIterable, start, sep, end))

  /** Splits this collection into groups according to the given predicate.
    *
    * @param p the predicate used to discriminate elements
    * @return A nested collection with groups of elements,
    *         opening new groups whenever the predicate
    *         changes the return type
    *
    * @example {{{
    * // Example 1: Split a list of integers into groups that are even / odd
    * List(1, 2, 4, 6, 7).splitWith(i => i % 2 == 0) => List(List(1), List(2, 4, 6), List(7))
    *
    * // Example 2: Split a list of chars into groups that are upper case or lower case
    * List('a', 'b', 'C', 'D', 'e', 'f').splitWith(_.isUpper) => List(List('a', 'b'), List('C', 'D'), List('e', 'f'))
    * }}}
    */
  def splitWith(p: A => Boolean): CC[CC[A]] = {
    def newGroupBuilder() = `this`.iterableFactory.newBuilder[A]()

    val groups = `this`.iterableFactory.newBuilder[CC[A]]()
    val it = `this`.iterator()

    var currentGroup = newGroupBuilder()
    var lastTestResult = Option.empty[Boolean]

    while (it.hasNext) {
      val elem = it.next()
      val currentTest = p(elem)

      lastTestResult match {
        case None =>
          currentGroup.add(elem)
        case Some(lastTest) if currentTest == lastTest =>
          currentGroup.add(elem)
        case Some(_) =>
          groups.add(currentGroup.result())
          currentGroup = newGroupBuilder().add(elem)
      }

      lastTestResult = Some(currentTest)
    }

    groups.add(currentGroup.result()).result()
  }
}

package strawman.collection
package decorators

class IteratorDecorator[A](`this`: Iterator[A]) {

  def intersperse[B >: A](sep: B): Iterator[B] = new Iterator[B] {
    var intersperseNext = false
    override def hasNext = intersperseNext || `this`.hasNext
    override def next() = {
      val elem = if (intersperseNext) sep else `this`.next()
      intersperseNext = !intersperseNext && `this`.hasNext
      elem
    }
  }

  def intersperse[B >: A](start: B, sep: B, end: B): Iterator[B] = new Iterator[B] {
    var started = false
    var finished = false
    var intersperseNext = false

    override def hasNext: Boolean = !finished || intersperseNext || `this`.hasNext

    override def next(): B =
      if (!started) {
        started = true
        start
      } else if (intersperseNext) {
        intersperseNext = false
        sep
      } else if (`this`.hasNext) {
        val elem = `this`.next()
        intersperseNext = `this`.hasNext
        elem
      } else if (!finished) {
        finished = true
        end
      } else {
        throw new NoSuchElementException("next on empty iterator")
      }
  }

  /** Finds the largest element.
    *
    *  @param    ord   An ordering to be used for comparing elements.
    *  @tparam   B    The type over which the ordering is defined.
    *  @return   the largest element of this $coll with respect to the ordering `ord` or
    *            None if this $coll is empty.
    */
  def maxOption[B >: A](implicit ord: Ordering[B]): Option[A] = {
    if (`this`.isEmpty) {
      None
    } else {
      Some(`this`.max[B])
    }
  }

  /** Finds the first element which yields the largest value measured by function f.
    *
    *  @param    cmp   An ordering to be used for comparing elements.
    *  @tparam   B     The result type of the function f.
    *  @param    f     The measuring function.
    *  @return   the first element of this $coll with the largest value measured by function f
    *  with respect to the ordering `cmp` or None if this $coll is empty.
    */
  def maxByOption[B](f: A => B)(implicit cmp: Ordering[B]): Option[A] = {
    if (`this`.isEmpty) {
      None
    } else {
      Some(`this`.maxBy(f))
    }
  }

  /** Finds the smallest element.
    *
    *  @param    ord   An ordering to be used for comparing elements.
    *  @tparam   B    The type over which the ordering is defined.
    *  @return   the smallest element of this $coll with respect to the ordering `ord` or
    *            None if this $coll is empty.
    */
  def minOption[B >: A](implicit ord: Ordering[B]): Option[A] = {
    if (`this`.isEmpty) {
      None
    } else {
      Some(`this`.min[B])
    }
  }

  /** Finds the first element which yields the smallest value measured by function f.
    *
    *  @param    cmp   An ordering to be used for comparing elements.
    *  @tparam   B     The result type of the function f.
    *  @param    f     The measuring function.
    *  @return   the first element of this $coll with the smallest value measured by function f
    *  with respect to the ordering `cmp` or None if this $coll is empty.
    */
  def minByOption[B](f: A => B)(implicit cmp: Ordering[B]): Option[A] = {
    if (`this`.isEmpty) {
      None
    } else {
      Some(`this`.minBy(f))
    }
  }
}

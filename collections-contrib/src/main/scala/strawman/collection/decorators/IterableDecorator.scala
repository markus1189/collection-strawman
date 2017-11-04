package strawman.collection
package decorators

class IterableDecorator[A, CC[X] <: IterableOps[X, CC, _]](`this`: CC[A]) {
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

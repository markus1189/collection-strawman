package strawman.collection.decorators

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import strawman.collection.Generators._
import strawman.collection.immutable.List

class SeqDecoratorProperties extends Properties("SeqDecorator") {
  property("splitWith retains elements") = forAll { seq: List[Char] =>
    seq.splitWith(_.isUpper).flatten.sameElements(seq)
  }
}

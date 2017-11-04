package strawman.collection.decorators

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import strawman.collection.Generators._
import strawman.collection.Iterator
import strawman.collection.immutable.Vector

import scala.util.Try

class IteratorDecoratorProperties extends Properties("IteratorDecorator") {
  property("maxOption") = forAll { is: Vector[Int] =>
    is.iterator().maxOption == Try(is.iterator().max).toOption
  }

  property("minOption") = forAll { is: Vector[Int] =>
    is.iterator().minOption == Try(is.iterator().min).toOption
  }

  property("maxOptionBy") = forAll { is: Vector[Char] =>
    is.iterator().maxByOption(_.toInt) == Try(is.iterator().maxBy(_.toInt)).toOption
  }

  property("minOptionBy") = forAll { is: Vector[Char] =>
    is.iterator().minByOption(_.toInt) == Try(is.iterator().minBy(_.toInt)).toOption
  }
}

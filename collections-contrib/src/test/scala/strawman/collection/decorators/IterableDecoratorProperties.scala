package strawman.collection.decorators

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import strawman.collection.Generators._
import strawman.collection.immutable.Vector

import scala.util.Try

class IterableDecoratorProperties extends Properties("IterableDecorator") {
  property("maxOption") = forAll { is: Vector[Int] =>
    is.maxOption == Try(is.max).toOption
  }

  property("minOption") = forAll { is: Vector[Int] =>
    is.minOption == Try(is.min).toOption
  }

  property("maxOptionBy") = forAll { is: Vector[Char] =>
    is.maxByOption(_.toInt) == Try(is.maxBy(_.toInt)).toOption
  }

  property("minOptionBy") = forAll { is: Vector[Char] =>
    is.minByOption(_.toInt) == Try(is.minBy(_.toInt)).toOption
  }
}

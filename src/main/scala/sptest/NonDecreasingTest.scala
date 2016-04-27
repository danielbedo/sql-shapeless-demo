package sptest

import scala.annotation.implicitNotFound
/** borrowed from shapeless */
object NonDecreasingTest {
  import shapeless._
  import nat._
  import ops.nat._
  import test._
  import LT._
  import LTEq._

  @implicitNotFound("${L} is not in a non decreasing order")
  trait NonDecreasing[L <: HList]

  implicit def hNilNonDecreasing = new NonDecreasing[HNil] {}
  implicit def hListNonDecreasing1[H <: Nat] = new NonDecreasing[H :: HNil] {}
  implicit def hListNonDecreasing2[H <: Nat, I <: Nat, Rem <: HList]
  (implicit lteq: H <= I, nd: NonDecreasing[I :: Rem]) = new NonDecreasing[H :: I :: Rem] {}

  def acceptNonDecreasing[L <: HList](l : L)(implicit ni : NonDecreasing[L]) = l
}

//import shapeless._; import shapeless.Nat._; import sptest.NonDecreasingTest._
//implicitly[NonDecreasing[_1 :: _2 :: _3 :: HNil]] // OK
//implicitly[NonDecreasing[_1 :: _3 :: _2 :: HNil]] // Doesn't compile
//
//// Apply at the value-level
//acceptNonDecreasing(_1 :: _2 :: _3 :: HNil)       // OK
//acceptNonDecreasing(_1 :: _3 :: _2 :: HNil)       // Doesn't compile
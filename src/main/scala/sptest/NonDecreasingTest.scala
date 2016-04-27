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

  // 1. case empty HList
  // 2. case single element HList
  // 3. induction step

  def acceptNonDecreasing[L <: HList](l : L)(implicit ni : NonDecreasing[L]) = l
}

//import shapeless._; import shapeless.Nat._; import sptest.NonDecreasingTest._
//implicitly[NonDecreasing[_1 :: _2 :: _3 :: HNil]] // OK
//implicitly[NonDecreasing[_1 :: _3 :: _2 :: HNil]] // Doesn't compile
//
//// Apply at the value-level
//acceptNonDecreasing(_1 :: _2 :: _3 :: HNil)       // OK
//acceptNonDecreasing(_1 :: _3 :: _2 :: HNil)       // Doesn't compile
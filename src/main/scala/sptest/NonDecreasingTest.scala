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

  implicit def hnilNonDecreasing = new NonDecreasing[HNil] {}
  implicit def hListNonDecreasing1[H] = new NonDecreasing[H :: HNil] {}
  implicit def hListNonDecreasing2[H <: Nat, L <: Nat, Rem <: HList]
  (implicit lteq: H <= L, ndq: NonDecreasing[L :: Rem]) = new NonDecreasing[H :: L :: Rem] {}

  def acceptNonDecreasing[L <: HList](l : L)(implicit ni : NonDecreasing[L]) = l
}

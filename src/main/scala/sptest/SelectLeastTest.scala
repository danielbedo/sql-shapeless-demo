package sptest

import scala.concurrent.Future
/** borrowed from shapeless */

object SelectLeastTest {
  import shapeless._
  import nat._
  import ops.nat._
  import test._
  import LT._
  import LTEq._

  trait SelectLeast[L <: HList, M <: Nat, Rem <: HList] {
    def apply(l: L): (M, Rem)
  }

  trait LowPrioritySelectLeast {
    implicit def hlistSelectLeast1[H <: Nat, T <: HList] =
      new SelectLeast[H :: T, H, T] {
        def apply(l: H :: T): (H, T) = (l.head, l.tail)
      }
  }

  object SelectLeast extends LowPrioritySelectLeast {
    implicit def hListSelectLeast2[H <: Nat, T <: HList, TMin <: Nat, TRem <: HList]
    (implicit tsl: SelectLeast[T, TMin, TRem], ev: TMin < H)= new SelectLeast[H :: T, TMin, H :: TRem] {
      def apply(l: H :: T) = {
        val (tm, tr) = tsl(l.tail)
        (tm, l.head :: tr)
      }
    }

  }

  def selectLeast[L <: HList, M <: Nat, Rem <: HList](l : L)(implicit sl : SelectLeast[L, M, Rem]) = sl(l)

// import shapeless._; import shapeless.Nat._; import sptest.SelectLeastTest._; import shapeless.Nat._0
//
//  val (l1, r1) = selectLeast(_1 :: _2 :: _3 :: HNil)
//  def typed[T](t: => T) {}
//  typed[_1](l1)
//  typed[_2 :: _3 :: HNil](r1)
}

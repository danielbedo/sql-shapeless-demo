package sptest

import sptest.SelectLeastTest.SelectLeast

/** borrowed from shapeless */

object SelectionSortTester {
  import shapeless._
  import nat._
  import ops.nat._
  import test._
  import LT._
  import LTEq._

  trait SelectionSort[L <: HList, S <: HList] {
    def apply(l : L) : S
  }

  trait LowPrioritySelectionSort {
    implicit def hlistSelectionSort1[S <: HList] = new SelectionSort[S, S] {
      def apply(l : S) : S = l
    }
  }

  object SelectionSort extends LowPrioritySelectionSort {
    implicit def hlistSelectionSort2[L <: HList, M <: Nat, Rem <: HList, ST <: HList]
    (implicit sl : SelectLeast[L, M, Rem], sr : SelectionSort[Rem, ST]) = new SelectionSort[L, M :: ST] {
      def apply(l : L) = {
        val (m, rem) = sl(l)
        m :: sr(rem)
      }
    }
  }

  def selectionSort[L <: HList, S <: HList](l : L)(implicit sort : SelectionSort[L, S]) = sort(l)
}

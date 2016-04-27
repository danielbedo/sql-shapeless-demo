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
    (implicit sl: SelectLeast[L, M, Rem], sr: SelectionSort[Rem, ST])= new SelectionSort[L, M :: ST] {
      def apply(l: L) = {
        val (min, rem) = sl(l)
        min :: sr(rem)
      }
    }

  }

  def selectionSort[L <: HList, S <: HList](l : L)(implicit sort : SelectionSort[L, S]) = sort(l)
}

// import shapeless._; import shapeless.Nat._;  import sptest.SelectionSortTester._;
// import shapeless.Nat._0;
//val unsorted = _3 :: _1 :: _4 :: _0 :: _2 :: HNil
//typed[_3 :: _1 :: _4 :: _0 :: _2 :: HNil](unsorted)
//acceptNonDecreasing(unsorted)  // Does not compile!
//
//val sorted = selectionSort(unsorted)
//typed[_0 :: _1 :: _2 :: _3 :: _4 :: HNil](sorted)
//acceptNonDecreasing(sorted)    // Compiles!
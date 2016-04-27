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
    // low priority case: already sorted list
  }

  object SelectionSort extends LowPrioritySelectionSort {
    // higher priority: not sorted yet
  }

  def selectionSort[L <: HList, S <: HList](l : L)(implicit sort : SelectionSort[L, S]) = sort(l)
}

// import shapeless._;   import sptest.SelectionSortTester._; import shapeless.Nat._;
// import sptest.NonDecreasingTest._
//val unsorted = _3 :: _1 :: _4 :: _2 :: HNil
//typed[_3 :: _1 :: _4 :: _2 :: HNil](unsorted)
//acceptNonDecreasing(unsorted)  // Does not compile!
//
//val sorted = selectionSort(unsorted)
//typed[_0 :: _1 :: _2 :: _3 :: _4 :: HNil](sorted)
//acceptNonDecreasing(sorted)    // Compiles!
package sptest

import shapeless.{Succ, _0, Nat}

import scala.annotation.implicitNotFound
/** borrowed from shapeless */
object LTEqTest {
  @implicitNotFound("${A} is not smaller or equal than ${B}")
  trait LTEq[A <: Nat, B <: Nat]

  object LTEq {
    def apply[A <: Nat, B <: Nat](implicit lteq: A <= B): LTEq[A, B] = lteq

    type <=[A <: Nat, B <: Nat] = LTEq[A, B]


  }
}


//import shapeless._; import shapeless.Nat._; import sptest.LTEqTest.LTEq._
//implicitly[_2 <= _5] // OK
//implicitly[_4 <= _2] // Does not compile
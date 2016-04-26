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

    implicit def LTEq00 = new <=[_0, _0] {}
    implicit def LTEq0a[A <: Nat] = new <=[_0, A] {}
    implicit def LTEqab[A <: Nat, B <: Nat](implicit lteq: A <= B) = new <=[Succ[A], Succ[B]] {}
  }
}

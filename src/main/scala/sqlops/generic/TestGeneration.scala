package sqlops.generic

import shapeless._
import shapeless.ops.record._
import shapeless.ops.hlist.{Mapper,ToTraversable}
import shapeless.tag._

object QueryGenerator {

  object attrsPoly extends Poly1 {
    implicit def atTaggedSymbol[T] = at[Symbol with Tagged[T]](_.name)
  }

  object valuesPoly extends Poly1 {
    implicit def atInt = at[Int](_.toString)
    implicit def atString = at[String](identity)
    implicit def atBoolean = at[Boolean](_.toString)
  }

  def plainInsert[T, ClassRepr <: HList, KeysRepr <: HList, KeysMapperRepr <: HList, ValuesRepr <: HList, ValuesMapperRepr <: HList](x: T, tableName: String)(implicit
    gen: LabelledGeneric.Aux[T, ClassRepr],
    keys: Keys.Aux[ClassRepr, KeysRepr],
    mapper: Mapper.Aux[attrsPoly.type, KeysRepr, KeysMapperRepr],
    keysTraversable: ToTraversable.Aux[KeysMapperRepr, List, String],

    values: Values.Aux[ClassRepr, ValuesRepr],
    valuesMapper: Mapper.Aux[valuesPoly.type, ValuesRepr, ValuesMapperRepr],
    valuesTraversable: ToTraversable.Aux[ValuesMapperRepr, List, String]) : String = {

    val attributes = keys().map(attrsPoly).toList
    val vals = values.apply(gen.to(x)).map(valuesPoly).toList

    s"INSERT INTO $tableName (${attributes.mkString(",")}) VALUES (${vals.mkString(",")})"
  }



}

object TestGeneration extends App {

  import QueryGenerator._

  case class Question(id: Int, title: String, strippedTitle: Boolean)
  val q1 = Question(1, "Very first question", true)

  println(plainInsert(q1, "question_table"))

}

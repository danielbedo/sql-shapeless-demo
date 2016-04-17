package sqlops.generic

object TestGeneration extends App {

  case class Question(id: Int, title: String)
  val q1 = Question(1, "Very first question")

  // println(q1.insert)

}

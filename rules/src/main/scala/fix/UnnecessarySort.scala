package fix

import scalafix.Diagnostic
import scalafix.Patch
import scalafix.lint.LintSeverity
import scalafix.v1.SyntacticDocument
import scalafix.v1.SyntacticRule
import scala.meta.Term
import scala.meta.Position

object UnnecessarySort {
  val scala213Methods: Map[String, String] = Map(
    "headOption" -> "minByOption",
    "lastOption" -> "maxByOption"
  )
  val map: Map[String, String] = Map(
    "head" -> "minBy",
    "last" -> "maxBy",
  ) ++ scala213Methods
}

class UnnecessarySort extends SyntacticRule("UnnecessarySort") {
  override def isLinter: Boolean = true

  override def fix(implicit doc: SyntacticDocument): Patch = {
    doc.tree.collect {
      case t @ Term.Select(
            Term.Apply(Term.Select(_, Term.Name("sortBy")), _ :: Nil),
            Term.Name(methodName)
          ) if UnnecessarySort.map.contains(methodName) =>
        Patch.lint(
          UnnecessarySortWarn(t.pos, s"maybe you can use ${UnnecessarySort.map(methodName)}")
        )
    }.asPatch
  }
}

case class UnnecessarySortWarn(override val position: Position, message: String) extends Diagnostic {
  override def severity = LintSeverity.Warning
}

package fix

import scalafix.Patch
import scalafix.v1.SyntacticDocument
import scalafix.v1.SyntacticRule
import scala.meta.Term

class CollectHeadOption extends SyntacticRule("CollectHeadOption") {
  override def fix(implicit doc: SyntacticDocument): Patch = {
    doc.tree.collect {
      case s: Term.Select if s.name.value == "collect" && ((s.parent, s.parent.flatMap(_.parent)) match {
            case (Some(a: Term.Apply), Some(s0: Term.Select))
                if a.argClause.values.length == 1 && s0.name.value == "headOption" =>
              true
            case _ => false
          }) =>
        val func = s.parent match {
          case Some(a: Term.Apply) => a.argClause.values.head
          case _ => ???
        }
        Patch.replaceTree(s.parent.get.parent.get, s"${s.qual}.collectFirst${func}")
    }
  }.asPatch
}

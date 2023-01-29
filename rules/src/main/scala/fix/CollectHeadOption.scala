package fix

import scalafix.Patch
import scalafix.v1.SyntacticDocument
import scalafix.v1.SyntacticRule
import scala.meta._

class CollectHeadOption extends SyntacticRule("CollectHeadOption") {
  override def fix(implicit doc: SyntacticDocument): Patch = {
    doc.tree.collect {
      case t @ q"$obj.collect($func).headOption" =>
        Patch.replaceTree(t, s"${obj}.collectFirst${func}")
    }
  }.asPatch
}

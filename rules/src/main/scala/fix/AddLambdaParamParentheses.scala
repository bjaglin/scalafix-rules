package fix

import scalafix.Patch
import scalafix.v1.SyntacticDocument
import scalafix.v1.SyntacticRule
import scala.meta.Term
import scala.meta.Token

class AddLambdaParamParentheses extends SyntacticRule("AddLambdaParamParentheses") {
  override def fix(implicit doc: SyntacticDocument): Patch = {
    doc.tree.collect {
      case t1 @ Term.Function(param :: Nil, _) if param.decltpe.nonEmpty && param.mods.isEmpty =>
        if (t1.tokens.find(_.is[Token.LeftParen]).exists(_.pos.start <= param.pos.start)) {
          Patch.empty
        } else {
          Seq(
            Patch.addLeft(param, "("),
            Patch.addRight(param, ")")
          ).asPatch
        }
    }.asPatch
  }
}

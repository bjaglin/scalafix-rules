package fix

import scala.meta.Decl
import scalafix.Patch
import scalafix.v1.SyntacticDocument
import scalafix.v1.SyntacticRule
import scala.meta.Ctor
import scala.meta.Defn
import scala.meta.Term
import scala.meta.Tree
import scala.meta.contrib.DocToken
import scala.meta.contrib.XtensionCommentOps
import scala.meta.inputs.Position
import scalafix.lint.Diagnostic
import scalafix.lint.LintSeverity

class IncorrectScaladocParam extends SyntacticRule("IncorrectScaladocParam") {
  override def fix(implicit doc: SyntacticDocument): Patch = {
    fix0(doc).map(Patch.lint).asPatch
  }
  private[fix] def fix0(implicit doc: SyntacticDocument): List[Diagnostic] = {
    doc.tree.collect {
      case t: Defn.Class =>
        p(t, t.ctor.paramss)
      case t: Defn.Def =>
        p(t, t.paramss)
      case t: Decl.Def =>
        p(t, t.paramss)
      case t: Defn.Macro =>
        p(t, t.paramss)
      case t: Ctor.Secondary =>
        p(t, t.paramss)
      case t: Defn.Enum =>
        p(t, t.ctor.paramss)
      case t: Defn.EnumCase =>
        p(t, t.ctor.paramss)
    }.flatten
  }

  private def p(t: Tree, paramss: List[List[Term.Param]])(implicit doc: SyntacticDocument): List[Diagnostic] = {
    val names = paramss.flatMap(_.map(_.name.value.trim)).toSet

    doc.comments.leading(t).toList.flatMap { x =>
      x.docTokens.toList.flatten.flatMap { c =>
        PartialFunction
          .condOpt(c.kind) { case DocToken.Param =>
            c.name
          }
          .flatten
          .map(_.trim)
          .flatMap { paramName =>
            if (names(paramName)) {
              None
            } else {
              PartialFunction.condOpt(x.value.linesIterator.zipWithIndex.collect {
                case (str, i) if str.contains(s" ${paramName} ") => (str.length, i)
              }.toList) { case List((len, index)) =>
                Diagnostic(
                  id = "",
                  message = "incorrect @param",
                  position = {
                    val line = x.pos.startLine + index
                    Position.Range(
                      input = doc.input,
                      startLine = line,
                      startColumn = 0,
                      endLine = line,
                      endColumn = len
                    )
                  },
                  severity = LintSeverity.Warning
                )
              }
            }
          }
      }
    }
  }
}
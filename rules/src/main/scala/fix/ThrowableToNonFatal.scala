package fix

import scala.meta.Case
import scala.meta.Pat
import scala.meta.Type
import scala.meta.XtensionQuasiquoteImporter
import scalafix.Patch
import scalafix.v1.SemanticDocument
import scalafix.v1.SemanticRule

class ThrowableToNonFatal extends SemanticRule("ThrowableToNonFatal") {
  override def fix(implicit doc: SemanticDocument): Patch = {
    doc.tree.collect { case c @ Case(Pat.Typed(v, Type.Name("Throwable")), _, _) =>
      List(
        Patch.addGlobalImport(importer"scala.util.control.NonFatal"),
        Patch.replaceTree(
          c.pat,
          s"NonFatal(${v.syntax})"
        )
      ).asPatch
    }.asPatch
  }
}

package org.glavo

import scala.language.experimental.macros
import scala.language.implicitConversions
import scala.reflect.macros.blackbox

package object ref {
  implicit def &[A](value: => A): Ref[A] = new Ref[A] {
    override def get: A = value
  }

  def &&[A](value: => A): MutableRef[A]  = macro refMacro[A]

  def refMacro[A: c.WeakTypeTag](c: blackbox.Context)(value: c.Tree): c.Tree = {
    import c.universe._
    val tpe = weakTypeOf[A]
    q"""
        new MutableRef[$tpe] {
          def get: $tpe = $value
          def set(value: $tpe): Unit = $value = value
        }
     """
  }
}

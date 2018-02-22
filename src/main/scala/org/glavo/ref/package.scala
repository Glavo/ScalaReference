package org.glavo

import scala.language.experimental.macros
import scala.language.implicitConversions
import scala.reflect.macros.blackbox

package object ref {
  @inline
  implicit def &[A](v: => A): Ref[A] = new Ref[A] {
    override def value: A = v
  }

  @inline
  implicit def &&[A](value: => A): MutableRef[A] = macro refMacro[A]

  @inline
  implicit def *[A](ref: Ref[A]): A = ref.value

  @inline
  implicit def *[A](ref: MutableRef[A]): A = ref.value

  object ref {
    def unapply[A](arg: Ref[A]): Option[A] = Option(arg).map(_.value)

    def unapply[A](arg: MutableRef[A]): Option[A] = Option(arg).map(_.value)

    def apply[A](value: => A): MutableRef[A] = macro refMacro[A]
  }

  def refMacro[A: c.WeakTypeTag](c: blackbox.Context)(value: c.Tree): c.Tree = {
    import c.universe._
    val tpe = weakTypeOf[A]
    q"""
        new MutableRef[$tpe] {
          def value: $tpe = $value
          def value_=(value: $tpe): Unit = $value = value
        }
     """
  }

  implicit class WrappedArray[A](val self: Array[A]) extends AnyVal {
    def &(index: Int): MutableRef[A] = {
      new MutableRef[A] {
        override def value_=(value: A): Unit = self(index) = value

        override def value: A = self(index)
      }
    }
  }

}

package org.glavo

import scala.language.experimental.macros
import scala.language.implicitConversions
import scala.reflect.macros.blackbox

package object ref {
  implicit def &[A](value: => A): Ref[A] = new Ref[A] {
    override def get: A = value
  }

  implicit def &&[A](value: => A): MutableRef[A] = macro refMacro[A]

  implicit def *[A](ref: Ref[A]): A = ref.get

  implicit def *[A](ref: MutableRef[A]): A = ref.get

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

  implicit class WrappedArray[A](val self: Array[A]) extends AnyVal {
    def &(index: Int): MutableRef[A] = {
      new MutableRef[A] {
        override def set(value: A): Unit = self(index) = value

        override def get: A = self(index)
      }
    }
  }

  implicit def mutableRef2ref[A](mutableRef: MutableRef[A]): Ref[A] = &(mutableRef.get)
}

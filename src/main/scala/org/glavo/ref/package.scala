package org.glavo

import scala.language.experimental.macros
import scala.language.implicitConversions
import scala.reflect.macros.blackbox

package object ref {
  @inline
  implicit def &[A](value: => A): Ref[A] = new Ref[A] {
    override def get: A = value
  }

  @inline
  implicit def &&[A](value: => A): MutableRef[A] = macro refMacro[A]

  @inline
  implicit def *[A](ref: Ref[A]): A = ref.get

  @inline
  implicit def *[A](ref: MutableRef[A]): A = ref.get

  object ref {
    def unapply[A](arg: Ref[A]): Option[A] = if(arg != null) Some(arg.get) else None
    def unapply[A](arg: MutableRef[A]): Option[A] = if(arg != null) Some(arg.get) else None

    def apply[A](value: => A): MutableRef[A] = macro refMacro[A]
  }

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

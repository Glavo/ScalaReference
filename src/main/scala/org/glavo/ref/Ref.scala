package org.glavo.ref

import scala.language.implicitConversions

trait Ref[+A] {
  def value: A

  def apply(): A = value

  override def toString: String = s"Ref($value)"
}

object Ref {
  def unapply[A](arg: Ref[A]): Option[A] = if(arg != null) Some(arg.value) else None
  def unapply[A](arg: MutableRef[A]): Option[A] = if(arg != null) Some(arg.value) else None
}

trait MutableRef[A] {
  def value: A

  def value_=(value: A): Unit

  def apply(): A = value

  def update(value: A): Unit = this.value = value

  override def toString: String = s"MutableRef($value)"
}

object MutableRef {
  def unapply[A](arg: MutableRef[A]): Option[A] = if(arg != null) Some(arg.value) else None


  implicit def mutableRef2ref[A](mutableRef: MutableRef[A]): Ref[A] = &(mutableRef.value)
}
package org.glavo.ref

trait Ref[+A] {
  def get: A

  override def toString: String = s"Ref($get)"
}

object Ref {
  def unapply[A](arg: Ref[A]): Option[A] = if(arg != null) Some(arg.get) else None
  def unapply[A](arg: MutableRef[A]): Option[A] = if(arg != null) Some(arg.get) else None
}

trait MutableRef[A] {
  def get: A

  def set(value: A): Unit

  def update(value: A): Unit = set(value)

  override def toString: String = s"MutableRef($get)"
}

object MutableRef {
  def unapply[A](arg: MutableRef[A]): Option[A] = if(arg != null) Some(arg.get) else None
}
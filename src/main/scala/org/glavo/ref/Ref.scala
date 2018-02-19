package org.glavo.ref

trait Ref[+A] {
  def get: A

  override def toString: String = s"Ref($get)"
}

trait MutableRef[A] extends Ref[A] {
  def set(value: A): Unit

  override def toString: String = s"MutableRef($get)"
}

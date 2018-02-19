package org.glavo.ref

trait Ref[+A] {
  def get: A

  override def toString: String = s"Ref($get)"
}

trait MutableRef[A] {
  def get: A

  def set(value: A): Unit

  def update(value: A): Unit = set(value)

  override def toString: String = s"MutableRef($get)"
}

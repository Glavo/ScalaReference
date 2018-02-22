# ScalaReference

[![](https://jitpack.io/v/org.glavo/ScalaReference.svg)](https://jitpack.io/#org.glavo/ScalaReference)

## Getting ScalaReference

If you're using SBT, add the following lines to your build file:
```sbt
resolvers += "jitpack" at "https://jitpack.io"
libraryDependencies += "org.glavo" %% "ScalaReference" % "0.5.0"
```

## Usage

```scala
import org.glavo.ref._

//immutable reference
var a: Int = 10
val aRef: Ref[Int] = a

assert(aRef.value == 10)

a = 100
assert(aRef.value == 100)

//mutable reference
var b: Int = 10
val bRef: MutableRef[Int] = b

assert(bRef.value == 10)

b = 100
assert(bRef.value == 100)

bRef.value = 50
assert(b == 50)

//unapply
val ref(a0) = aRef
assert(a0 == a)

val ref(b0) = bRef
assert(b0 == b)

//array element reference
val arr = Array(0, 0, 0, 0, 0)
val arrRef = arr.&(0)

assert(arrRef.value == 0)

arrRef.value = 10
assert(arr(0) == 10)

//swap
def swap[A](a1: MutableRef[A], a2: MutableRef[A]): Unit = {
  val n = a1.value
  a1.value = a2.value
  a2.value = n
}

var a1 = 10
var a2 = 20

swap(a1, a2)

assert(a1 == 20)
assert(a2 == 10)
```

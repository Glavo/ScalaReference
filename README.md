# ScalaReference

[![](https://jitpack.io/v/Glavo/ScalaReference.svg)](https://jitpack.io/#Glavo/ScalaReference)

## Getting ScalaReference

If you're using SBT, add the following lines to your build file:
```sbt
resolvers += "jitpack" at "https://jitpack.io"
libraryDependencies += "org.glavo" %% "ScalaReference" % "0.4.1"
```

## Usage

```scala
import org.glavo.ref._

//immutable reference
var a: Int = 10
val aRef: Ref[Int] = a

assert(aRef.get == 10)

a = 100
assert(aRef.get == 100)

//mutable reference
var b: Int = 10
val bRef: MutableRef[Int] = b

assert(bRef.get == 10)

b = 100
assert(bRef.get == 100)

bRef.set(50)
assert(b == 50)

//array element reference
val arr = Array(0, 0, 0, 0, 0)
val arrRef = arr.&(0)

assert(arrRef.get == 0)

arrRef.set(10)
assert(arr(0) == 10)

//swap
def swap[A](a1: MutableRef[A], a2: MutableRef[A]): Unit = {
  val n = a1.get
  a1.set(a2.get)
  a2.set(n)
}

var a1 = 10
var a2 = 20

swap(a1, a2)

assert(a1 == 20)
assert(a2 == 10)
```
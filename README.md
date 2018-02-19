# ScalaReference

[![](https://jitpack.io/v/Glavo/ScalaReference.svg)](https://jitpack.io/#Glavo/ScalaReference)

## Getting ScalaReference

If you're using SBT, add the following lines to your build file:
```sbt
resolvers += "jitpack" at "https://jitpack.io"
libraryDependencies += "com.github.Glavo" %% "ScalaReference" % "0.1"
```

## Usage

```scala
import org.glavo.ref._

//immutable reference
var a: Int = 10
val aRef: Ref[Int] = &(a)

assert(aRef.get == 10)

a = 100
assert(aRef.get == 100)

//mutable reference
var b: Int = 10
val bRef: MutableRef[Int] = &&(b)

assert(bRef.get == 10)

b = 100
assert(bRef.get == 100)

bRef.set(50)
assert(b == 50)
```
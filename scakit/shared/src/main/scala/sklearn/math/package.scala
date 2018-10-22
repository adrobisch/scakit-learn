package sklearn

package object math {
  trait Matrix[T] {
    def rows: Int
    def cols: Int

    def broadcastRows: Matrix[T]
    def broadcastColumns: Matrix[T]

    def +(matrix: Matrix[T]): Matrix[T]
    def -(matrix: Matrix[T]): Matrix[T]
    def *(matrix: Matrix[T]): Matrix[T]
    def *(double: Double): Matrix[T]

    def *:*(matrix: Matrix[T]): Matrix[T]
    def /:/(matrix: Matrix[T]): Matrix[T]
    def /(double: Double): Matrix[T]

    def t: Matrix[T]

    def asVector: Vector[T]

    def map[B](f: Vector[T] => B): Matrix[B]
  }

  trait Vector[T] extends Matrix[T] {
    def data: Seq[T]

    def apply(index: Int): T
    def length: Int
  }

  sealed trait Axis
  object Axis {
    type Value = Axis
    case object _0 extends Axis
    case object _1 extends Axis
  }

  trait MathContext {
    def pinv[T](matrix: Matrix[T]): Matrix[T]
    def pow[T](base: Double, exponent: Double): Double
    def pow[T](matrix: Matrix[T], exponent: Double): Matrix[T]
    def det[T](matrix: Matrix[T]): Double
    def exp[T](matrix: Matrix[T]): Matrix[T]
    def sum[T](matrix: Matrix[T], axis: Axis): Matrix[T]

    def eye[T](dimension: Int): Matrix[T]
  }

  implicit class MatrixAwareDouble(double: Double) {
    def *[T](matrix: Matrix[T]): Matrix[T] = matrix.*(double)
  }

}

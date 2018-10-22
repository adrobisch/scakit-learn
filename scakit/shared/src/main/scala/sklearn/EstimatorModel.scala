package sklearn

import sklearn.math.Matrix

trait EstimatorModel {
  def predict(input: Matrix[Double]): math.Vector[Double]
}

package sklearn

import breeze.linalg.{DenseMatrix, DenseVector}

trait EstimatorModel {
  def predict(input: DenseMatrix[Double]): DenseVector[Double]
}

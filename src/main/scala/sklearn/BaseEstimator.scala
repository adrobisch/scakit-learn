package sklearn

import breeze.linalg.{DenseMatrix, DenseVector}

trait BaseEstimator { self =>
  def fit(X: DenseMatrix[Double], y: Option[DenseVector[Double]]): EstimatorModel
}

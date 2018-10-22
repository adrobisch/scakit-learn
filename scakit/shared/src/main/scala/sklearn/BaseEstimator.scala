package sklearn

import sklearn.math._

trait BaseEstimator { self =>
  def fit(X: Matrix[Double], y: Option[math.Vector[Double]])(implicit mathContext: MathContext): EstimatorModel
}

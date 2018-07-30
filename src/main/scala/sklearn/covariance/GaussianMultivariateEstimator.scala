package sklearn.covariance

import breeze.linalg._
import breeze.stats._
import breeze.numerics._
import sklearn.{BaseEstimator, EstimatorModel}

case class GaussianMultivariateModel(mean: DenseVector[Double],
                                     covMatrix: DenseMatrix[Double]) extends EstimatorModel {
  lazy val inverseCovMatrix = inv(covMatrix)

  override def predict(input: DenseMatrix[Double]): DenseVector[Double] = {
    val deviation: DenseMatrix[Double] = (input -:- mean.toDenseMatrix)

    //(1 / (Math.pow(2* Math.PI, input.cols / 2) * Math.sqrt(det(covMatrix)))) * Math.exp(-0.5 * ())
    ???
  }
}

class GaussianMultivariateEstimator extends BaseEstimator {
  override def fit(X: DenseMatrix[Double], y: Option[DenseVector[Double]]): GaussianMultivariateModel = {
    GaussianMultivariateModel(mean(X(*, ::)), cov(X))
  }
}

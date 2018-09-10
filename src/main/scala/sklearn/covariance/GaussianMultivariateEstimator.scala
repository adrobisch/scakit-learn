package sklearn.covariance

import breeze.linalg._
import breeze.stats._
import breeze.numerics._
import sklearn.{BaseEstimator, EstimatorModel}

/**
  * based on
  * https://github.com/JWarmenhoven/Coursera-Machine-Learning/blob/master/notebooks/Programming%20Exercise%208%20-%20Anomaly%20Detection%20and%20Recommender%20Systems.ipynb
  * which is in turn based on https://www.coursera.org/learn/machine-learning
  *
  * @param mean
  * @param covMatrix
  */
final case class GaussianMultivariateModel(mean: DenseVector[Double],
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

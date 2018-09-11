package sklearn.covariance

import breeze.linalg.{DenseMatrix, _}
import breeze.numerics._
import sklearn.{BaseEstimator, EstimatorModel}

/**
  * based on the approach in https://www.coursera.org/learn/machine-learning
  *
  * @param mu vector of feature means
  * @param sigma2 covariance matrix
  */
final case class GaussianMultivariateModel(mu: DenseVector[Double],
                                           sigma2: DenseMatrix[Double]) extends EstimatorModel {
  override def predict(input: DenseMatrix[Double]): DenseVector[Double] = {
    val X: DenseMatrix[Double] = input(*, ::) - mu
    val appliedSigma = (X * pinv(sigma2)) *:* X

    pow(2 * Math.PI, - mu.length / 2.0) * pow(det(sigma2), -0.5) * exp(-0.5 * sum(appliedSigma, Axis._1))
  }
}

class GaussianMultivariateEstimator extends BaseEstimator {
  override def fit(X: DenseMatrix[Double], y: Option[DenseVector[Double]]): GaussianMultivariateModel = {
    val m = X.rows.toDouble
    val n = X.cols
    val mu: DenseVector[Double] = (sum(X, Axis._0) /:/ m).t

    val sumOfSquaredDiff: DenseVector[Double] = sum(pow( X(*, ::) - mu, 2.0), Axis._0).t
    val nEye = DenseMatrix.eye[Double](n)
    val sigma2: DenseMatrix[Double] = nEye(::, *) * (sumOfSquaredDiff / m)

    GaussianMultivariateModel(mu, sigma2)
  }
}

package sklearn.covariance

import breeze.linalg.{DenseMatrix, _}
import breeze.stats._
import breeze.numerics._
import sklearn.{BaseEstimator, EstimatorModel}

/**
  * based on the approach in https://www.coursera.org/learn/machine-learning
  *
  * @param mean
  * @param covMatrix
  */
final case class GaussianMultivariateModel(mean: DenseVector[Double],
                                           covMatrix: DenseMatrix[Double]) extends EstimatorModel {
  lazy val inverseCovMatrix = inv(covMatrix)

  override def predict(input: DenseMatrix[Double]): DenseVector[Double] = {
    val m = input.rows.toDouble
    val n = input.cols

    val mu: DenseVector[Double] = (sum(input, Axis._0) /:/ m).t
    val deviationFromMean: DenseMatrix[Double] = input(*, ::) - mu

    val sumOfSquaredDiff: DenseVector[Double] = sum(pow(deviationFromMean, 2.0), Axis._0).t
    val nEye = DenseMatrix.eye[Double](n)

    val sigma2: DenseMatrix[Double] = nEye(::, *) * (sumOfSquaredDiff / m)

    val sigmaInverse: DenseMatrix[Double] = pinv(sigma2)

    val appliedSigma = (deviationFromMean * sigmaInverse) *:* deviationFromMean

    val sumAppliedSigma = sum(appliedSigma, Axis._1)

    pow(2 * Math.PI, - mu.length / 2.0) * pow(det(sigma2), -0.5) * exp(-0.5 * sumAppliedSigma)
  }
}

class GaussianMultivariateEstimator extends BaseEstimator {
  override def fit(X: DenseMatrix[Double], y: Option[DenseVector[Double]]): GaussianMultivariateModel = {
    GaussianMultivariateModel(mean(X(*, ::)), cov(X))
  }
}
